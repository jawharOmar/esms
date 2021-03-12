app.controller(
				'myApp',
				function($scope, $http, $q) {

					$scope.isNumber = angular.isNumber;
					$scope.venderReturns = {};
					$scope.detail = {};
					$scope.vendorname = '';
					$scope.selectedstock = '';
					$scope.productID = '';
					$scope.product = {};
					$scope.productDetail = {};
					$scope.lastprice = 0;

					$scope.resetproductDetail = angular
							.copy($scope.productDetail);
					$scope.resetdetail = angular.copy($scope.detail);
					$scope.resetproduct = angular.copy($scope.product);
					$scope.resetlastprice = angular.copy($scope.lastprice);

					$scope.init = function() {

						console.log("init->fired");
						$scope.venderReturns = JSON.parse(returnsJson);
						$scope.detail = JSON.parse(detailJson);

					}

					$scope.getlastPrice = function() {

						$http(
								{
									method : 'GET',
									url : $$ContextURL
											+ '/venderReturns/getPrice/'
											+ $scope.product.id
								}).then(function(response) {
							$scope.lastprice = response.data;
						});
					};

					$scope.selectVendor = function() {
						$http(
								{
									method : 'GET',
									url : $$ContextURL
											+ '/venderReturns/getVender/'
											+ $scope.vendorname
								}).then(function(response) {
							$scope.venderReturns.vendor = response.data;

							console.log($scope.venderReturns);

						});
					};

					$scope.findproduct = function() {
						console.log("Product -> fired");
						$http(
								{
									method : 'GET',
									url : $$ContextURL
											+ '/venderReturns/getProduct/'
											+ $scope.product.id + '/stock/'
											+ $scope.selectedstock
								}).then(function(response) {
							console.log(response.data);
							$scope.productDetail = response.data;
							$scope.detail.product = response.data.product;
							$scope.detail.stock = response.data.stock;
							$scope.product = response.data.product;
							$scope.getlastPrice();

						});
					};

					$scope.totalPrice = function() {
						var totalPrice = 0;
						for (var i = 0; i < $scope.venderReturns.venderReturnsDetail.length; i++) {
							totalPrice += $scope.venderReturns.venderReturnsDetail[i].amount
									* $scope.venderReturns.venderReturnsDetail[i].qty;
						}
						return totalPrice;
					}

					$scope.addItem = function() {

						$scope.detail.amount = $scope.lastprice;
						$scope.venderReturns.venderReturnsDetail
								.unshift($scope.detail);
						$scope.detail = angular.copy($scope.resetdetail);
						$scope.product = angular.copy($scope.resetproduct);
						$scope.lastprice = angular.copy($scope.resetlastprice);
						$scope.productDetail = angular
								.copy($scope.resetproductDetail);

						console.log($scope.venderReturns);
					};

					$scope.deleteProductStepUp = function(index) {
						console.log("Delete item index=", index);
						$scope.venderReturns.venderReturnsDetail.splice(index,
								1);
					};

					$scope.saveVenderReturn = function() {
						console.log("saveCustomerOrderReturn->fired");
						$scope.venderReturns.amount = $scope.totalPrice();
						console.log($scope.venderReturns);
						$http({
							method : 'POST',
							data : $scope.venderReturns,
							url : $$ContextURL + '/venderReturns/add'
						}).then(function(response) {
							$("#modal-body").html(response.data);
							$("#modal").modal("show");
						}, function(response) {
							$("#modal-body").html(response.data);
							$("#modal").modal("show");
						});

					}
				});