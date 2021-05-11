$(document).ready()
{
	console.log("csrf=", csrf);
}
// Angular
app.controller('addCustomerOrder', function($scope, $http, $window) {

	$scope.customerOrder;

	$scope.invoiceId;

	$scope.customers;

	$scope.forPrintOnly = false;

	$scope.showLoan = false;


	$scope.url;

	$scope.stocks;

	$scope.productTotalPrice;
	$scope.priceToWeigth = false;

	$scope.selectedStock = null;

	$scope.product = {
		productId: "",
		code: "",
		name: "",
		unitType: "",
		stockLevel: "",
		cost: "",
		profit: "",
		price: "",
		quantity: "",
		packetQuantity: 0,
		stock: {
			id: "",
			name: ""
		},
		product: {
			packetSize: "",
			attachedFile: {}
		}
	};
	$scope.resetProduct = angular.copy($scope.product);

	$scope.getCustomerOrder = function() {
		console.log("$scope.getCustomerOrder->fired");
		if ($scope.invoiceId) {
			$window.open($$ContextURL + "/customerOrders/edit?invoiceId=" + $scope.invoiceId, '_blank');
		}
	};

	$scope.totalPrice = function() {
		var totalPrice = 0;
		for (var i = 0; i < $scope.customerOrder.customerOrderDetails.length; i++) {
			var quantity = $scope.customerOrder.customerOrderDetails[i].quantity;
			var price = $scope.customerOrder.customerOrderDetails[i].price;
			totalPrice += quantity * price;
		}

		return Number((totalPrice).toFixed(3));
	};

	$scope.setTotalPayment = function() {
		return $scope.customerOrder.totalPayment = parseFloat(($scope
			.totalPrice() - $scope.customerOrder.discount).toFixed(3));
	};

	$scope.$watch("customerOrder.customer", function(newValue, oldValue) {
		if (newValue) {
			document.title = $scope.customerOrder.customer.fullName;
		}
	}, true);

	$scope.$watch("selectedStock", function(newValue, oldValue) {
		if (newValue) {
			$scope.product.stock = $scope.selectedStock;
		} else {
			$scope.product.stock = null;
		}
	});

	$scope.$watch("productTotalPrice", function(newValue, oldValue) {
		console.log("productTotalPrice->changed");
		if (newValue) {
			$scope.product.quantity = parseFloat(($scope.productTotalPrice / $scope.product.price).toFixed(3));
		}
	});

	$scope.$watch("product.packetQuantity", function(newValue, oldValue) {
		if (newValue) {
			$scope.product.quantity = $scope.product.packetQuantity * $scope.product.packetSize;
		}
	});

	$scope.$watch("checkTotalPrice", function(newValue, oldValue) {
		if (newValue) {
			$scope.calculateDiscount();
		}
	});

	$scope.$watch("customerOrder.totalPayment", function(newValue, oldValue) {
		if (newValue) {
			$scope.paymentValidation = parseFloat(($scope.totalPrice() - $scope.customerOrder.discount).toFixed(3));
		}
	});

	$scope.getKeys = function(event) {
		if (event.keyCode === 13) {
			$scope.addCustomerOrderDetail();
		}
	};

	// Calculate Discount If Discount Ratio Changed
	$scope.calculateDiscount = function() {
		if ($scope.selectRatio && $scope.discountRatio <= 100) {
			var discountPercentage = $scope.discountRatio / 100;
			$scope.customerOrder.discount = parseFloat(($scope
				.totalPrice() * discountPercentage).toFixed(3));
			$scope.calculateTotalPayment();
		}
	};

	$scope.calculateTotalPayment = function() {
		if ($scope.customerOrder.totalPayment > 0 && $scope.customerOrder.totalPayment > $scope.setTotalPayment()) {
			$scope.setTotalPayment();
		}
	};


	$scope.init = function() {
		console.log("init->fired");

		$scope.url = url;

		$scope.stocks = JSON.parse(jsonStocks);


		$scope.customerOrder = JSON.parse(jsonCustomerOrder);
		console.log("$scope.customerOrder=", $scope.customerOrder);
		$scope.customerOrder.discount = 0;
		$scope.customerOrder.totalPayment = 0;

		$scope.discountRatio = 0;
		$scope.checkTotalPrice = 0;
		$scope.paymentValidation = 0;

		// S-Product AutoCompletion
		var productAuto = [];

		$scope.searchByProductCodeOrName = function() {
			productAuto = [];
			if ($scope.product.code) {
				$http({
					url: $$ContextURL + "/products/search/nameOrCode",
					method: 'GET',
					params: {  keyword: $scope.product.code }
				}).then(function(response) {
					console.log("response.data=", response.data);
					angular.forEach(response.data, function(value, key) {
						console.log("angular.forEach");
						console.log("value=", value);
						console.log("key=", key);
						var obj = {
							label: value.keyword + " " + value.secondKeyword,
							value: value.keyword,
							data: value
						};
						productAuto.push(obj);
					})
				}, function(error) {
					$("#modal-body").html(error.data);
					$("#modal").modal("show");
				});

				$("#autoselect").autocomplete({
					delay: 2000,
					source: productAuto,
					response: function(event, ui) {
						console.log("productAuto=", productAuto);
						console.log(" ui=", ui);
						while (ui.content.length > 0) {
							ui.content.pop();
						}

						angular.forEach(productAuto, function(value, key) {
							console.log("angular.forEach");
							console.log("value=", value);

							console.log("obj=", value);
							ui.content.push(value);
						})


						console.log("ui.content.length=", ui.content.length);
						if (ui.content.length === 1) {
							var item = ui.content[0].data;
							console.log("auto selected item =", item);
							$scope.product.code = item.keyword;
							$scope.getProduct(item.keyword);

							$("#autoselect").autocomplete("close");
							$scope.$digest();
						}
					},
					select: function(event, ui) {
						console.log("select->fired");
						var item = ui.item.data;
						console.log("selected item =", item);
						$scope.product.code = item.keyword;
						$scope.getProduct(item.keyword);
						$scope.$digest();
					}
				});

			}


		};
		if (productAuto.length < 1) {
			$("#autoselect").autocomplete({});
		}
		// E-Product AutoCompletion


		// customerAuto AutoCompletion
		var customerAuto = [];

		$scope.searchByCustomerNameOrPhone = function() {
			if ($scope.customerOrder.customer.fullName) {
				customerAuto = [];
				$http({
					url: $$ContextURL + "/customers/searchCustomer",
					method: 'POST',
					params: { keyword: $scope.customerOrder.customer.fullName }
				}).then(function(response) {
					console.log("customers=", response.data);
					angular.forEach(response.data, function(value, key) {
						var obj = {
							label: value.keyword + " " + value.secondKeyword,
							value: value.keyword,
							data: value
						};
						customerAuto.push(obj);
					});

				}, function(error) {
					$("#modal-body").html(error.data);
					$("#modal").modal("show");
				});
			}
			$("#customer-autoselect").autocomplete({
				source: customerAuto,
				select: function(event, ui) {
					var item = ui.item.data;
					console.log("selected item =", item);

					$("#autoselect").focus();

					$http({
						url: $$ContextURL + "/customers/searchCustomerId",
						method: 'POST',
						params: { id: item.id }
					}).then(function(response) {
						console.log("customer=", response.data);

						$scope.customerOrder.customer = response.data;
					}, function(error) {
						$("#modal-body").html(error.data);
						$("#modal").modal("show");
					});
					$scope.$digest();
				}
			});
		};
		if (customerAuto.length < 1) {
			$("#customer-autoselect").autocomplete({});
		}
		// -----------------End searchByCustomerOrPhone--------------
	};


	$scope.addedProduct = [];


	$scope.getProduct = function(id) {
		console.log("getProduct->fired");
		if (id.length > 0) {

			$http({
				method: "GET",
				url: $$ContextURL + "/products/find/code/" + $scope.product.code,
				params: {
					customerId: $scope.customerOrder.customer ? $scope.customerOrder.customer.id : null,
					stockId: $scope.product.stock.id ? $scope.product.stock.id : null
				}
			}).then(
				function(response) {
					console.log("success");

					console.log($scope.forPrintOnly);
					if (response.data.stockLevel == 0 && !$scope.forPrintOnly) {
						$("#modal-body").html(outOfTheStock);
						$("#modal").modal("show");
					} else {


						var exist = true;
						angular.forEach($scope.customerOrder.customerOrderDetails, function(value, key) {
							if (response.data.productId === value.productId) {
								response.data.stockLevel -= value.quantity;
							}
							if (response.data.stockLevel <= 0) {
								exist = false;
								$("#modal-body").html("Out of the stock");
								$("#modal").modal("show");
							}
						});

						if (exist) {
							var stock = $scope.product.stock;
							$scope.product = response.data;
							$scope.product.stock = stock;
							$scope.product.quantity = 1;

							$("#productName").focus();
							if ($scope.customerOrder.customer && $scope.customerOrder.customer.priceCategory &&
								$scope.product.productPriceCategories) {
								console.log("spaial price for customer");

								if ($scope.customerOrder.customer.priceCategory.ratio) {
									var spaialPrice = $scope.product.cost + $scope.product.cost * $scope.customerOrder.customer.priceCategory.ratio
									$scope.product.price = parseFloat(spaialPrice.toFixed(3));
								} else {
									var spaialPrice = $scope.product.productPriceCategories.find(function(e) {
										return e.priceCategory.id == $scope.customerOrder.customer.priceCategory.id;
									});
									$scope.product.price = spaialPrice.price;
								}

							}

						}
					}
				}, function(response) {
					console.error("failed");
					console.error("error occured");
					$("#modal-body").html(response.data);
					$("#modal").modal("show");
				});

		}

	};


	$scope.getProductCustomerOrderDetail = function() {
		$http({
			method: 'GET',
			data: $scope.customerOrder,
			url: $$ContextURL + '/products/' + $scope.product.productId + '/customerOrderDetails'
		}).then(function(response) {
			$("#modal-body").html(response.data);
			$("#modal").modal("show");
		}, function(response) {
			console.error("error occurred");
		});
	};

	$scope.addCustomerOrderDetail = function() {
		console.log("addCustomerOrderDetail->fired");

		var customerOrderDetail = {
			productId: $scope.product.productId,
			productCode: $scope.product.code,
			productName: $scope.product.name,
			quantity: $scope.product.quantity,
			price: $scope.product.price,
			product: $scope.product,
			stock: $scope.product.stock
		};
		$scope.customerOrder.customerOrderDetails.unshift(customerOrderDetail);
		console.log($scope.customerOrder.customerOrderDetails);
		$scope.product = $scope.resetProduct;
		if ($scope.selectedStock)
			$scope.product.stock = $scope.selectedStock;
		$scope.productTotalPrice = null;
		$("#autoselect").val("");
		$("#autoselect").focus();
	};

	$scope.addCustomerOrder = function() {
		console.log("addCustomerOrder->fired");
		if ($scope.customerOrder.customerProject != null) {
			if ($scope.customerOrder.customerProject.id == null) {
				$scope.customerOrder.customerProject = null;
			}
		}
		console.log("$scope.customerOrder=", $scope.customerOrder);

		if ($scope.forPrintOnly) {
			$http({
				method: 'POST',
				data: $scope.customerOrder,
				url: $$ContextURL + '/customerOrders/forPrintOnly'
			}).then(function(response) {
				var outPut = `
				<div>${response.data.message}
				</div>
				<div>
				<a class="btn btn-info" target="_blank" href="${$$ContextURL}/customerOrders/forPrintOnly"><i class="fa fa-print"></i></a></div>
				`;

				$("#modal-body").html(outPut);
				$("#modal").modal("show");


				// Reload The Page
				$('#modal').on('hidden.bs.modal', function() {
					location.reload();
				})
			}, function(response) {
			});
		} else {
			if (($scope.totalPrice() - $scope.customerOrder.totalPayment - $scope.customerOrder.discount + $scope.customerOrder.customer.totalLoan) > $scope.customerOrder.customer.limit) {
				$scope.limit = true;

				$("#modal-body").html(customerReachLimit);
				$("#modal").modal("show");

			} else {


				$http({
					method: 'POST',
					data: $scope.customerOrder,
					url: $$ContextURL + '/customerOrders/add'
				}).then(function(response) {
					console.log(response);
					var outPut = `
			
			<div>${response.data.message}
			</div>
			<div>
			<a class="btn btn-info" target="_blank" href="${$$ContextURL}/customerOrders/${response.data.etc}/${$scope.showLoan}"><i class="fa fa-print"></i></a></div>
			`;
					$("#freeze").addClass("cus-freeze");
					console.log("outPut=", outPut);

					$("#modal-body").html(outPut);
					$("#modal").modal("show");

					// Reload The Page
					$('#modal').on('hidden.bs.modal', function() {
						location.reload();
					})
				}, function(response) {
					console.error("error occurred");
				if (response.data.fieldErrors) {
					console.log(response.data.fieldErrors);
					var outPut = `<table><tbody>`;
					response.data.fieldErrors.forEach(function(element) {
						console.log("element=", element);
						outPut += `<tr><td>${element.message}</td></tr>`;
					});
					outPut += `</tbody></table>`;
					console.log("outPut=", outPut);
					$("#modal-body").html(outPut);
					$("#modal").modal("show");
				} else {
					if (response.data.message) {
						$("#modal-body").html(response.data.message);
						$("#modal").modal("show");
					} else {
						$("#modal-body").html(response.data);
						$("#modal").modal("show");
					}

				}
			});
			}
		}// else
	};


$scope.removeCustomerOrderDetail = function(index) {
	console.log("removeCustomerOrderDetail->fired");
	console.log("index=", index);
	$scope.customerOrder.customerOrderDetails.splice(index, 1);

	console.log("customerOrderDetails=",
		$scope.customerOrder.customerOrderDetails);
};

$scope.editCustomerOrderDetail = function(index) {
	console.log("editCustomerOrderDetail->fired");
	var customerOrderDetail = $scope.customerOrder.customerOrderDetails[index];
	$scope.product = angular.copy(customerOrderDetail.product);
	$scope.product.price = customerOrderDetail.price;
	console.log($scope.product);
	if (customerOrderDetail.product.packetSize && customerOrderDetail.quantity > customerOrderDetail.product.packetSize) {
		$scope.product.packetQuantity = customerOrderDetail.quantity / customerOrderDetail.product.packetSize;
	} else {
		$scope.product.quantity = customerOrderDetail.quantity;
	}
	$scope.customerOrder.customerOrderDetails.splice(index, 1);

}
});
function getAddingCustomer() {
	console.log("getAddingCustomer->fired");
	$.get($$ContextURL + "/customers/add", function(response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

