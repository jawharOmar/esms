$(document).ready()
{
	console.log("csrf=", csrf);


	$("#newProductStepUpExpirationDate").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", new Date());

}

// Angular

app.controller('appCTRL', function($scope, $http, $q) {

	$scope.preProduct = {
		code : "",
		name : "",
		weight : "",
		packetQuantity : "",
		packetSize : "",
		quantity : "",
		price : "",
		secondPrice : ""
	};
	$scope.resetPreProduct = angular.copy($scope.preProduct);

	$scope.orderPreProduct = {};

	$scope.files = [];
	

	$scope.init = function() {

		console.log("init->fired");

		console.log("jsonOrderPreProduct=", jsonOrderPreProduct);

		$scope.orderPreProduct = JSON.parse(jsonOrderPreProduct);
		console.log("$scope.orderPreProduct=", $scope.orderPreProduct);
	}
	
	$scope.unitPrice;
	$scope.$watchGroup(["unitPrice","preProduct.quantity"],function(newValues,oldValues,scope){
		if(newValues[0]&&newValues[1]){
			$scope.preProduct.price=newValues[0]*newValues[1];
		}
		else{
			$scope.preProduct.price=0;
		}
		
	});
	
	$scope.secondUnitPrice;
	$scope.$watchGroup(["secondUnitPrice","preProduct.quantity"],function(newValues,oldValues,scope){
		if(newValues[0]&&newValues[1]){
			$scope.preProduct.secondPrice=newValues[0]*newValues[1];
		}
		else{
			$scope.preProduct.secondPrice=0;
		}
		
	});
	
	
	$scope.$watchGroup(["preProduct.packetQuantity","preProduct.packetSize"],function(newValues,oldValues,scope){
		var element = angular.element('[ng-model="preProduct.quantity"]');
		if(newValues[0]&&newValues[1]){
			$scope.preProduct.quantity=newValues[0]*newValues[1];
			element.prop('disabled', true);
		}else{
			element.prop('disabled', false);
		}
		
		
	});

	$scope.totalPrice = function() {
		var totalPrice = 0;
		for (var i = 0; i < $scope.orderPreProduct.preProducts.length; i++) {
			totalPrice += $scope.orderPreProduct.preProducts[i].price;
		}
		return totalPrice;
	}

	$scope.totalSecondPrice = function() {
		var totalPrice = 0;
		for (var i = 0; i < $scope.orderPreProduct.preProducts.length; i++) {
			totalPrice += $scope.orderPreProduct.preProducts[i].secondPrice;
		}
		return totalPrice;
	}

	$scope.addPreProduct = function() {
		console.log("addPreProduct->fired");

		$scope.orderPreProduct.preProducts.push($scope.preProduct);

		$scope.preProduct = angular.copy($scope.resetPreProduct);

		var fileInput = document.getElementById("fileSelect");

		var file = fileInput.files[0];
		if (file) {
			$scope.files.push(file);
		} else {
			var nullFile = new Blob([ "" ], {
				type : 'image/png'
			});
			$scope.files.push(nullFile);
		}

		fileInput.value = "";

	}

	$scope.deletePreProduct = function(index) {
		console.log("Delete item index=", index);
		$scope.orderPreProduct.preProducts.splice(index, 1);
	};

	$scope.saveOrderPreProduct = function() {
		console.log("saveOrderPreProduct->fired");
		console.log($scope.orderPreProduct);
		$scope.orderPreProduct.totalPrice = $scope.totalPrice();
		$scope.orderPreProduct.totalSecondPrice = $scope.totalSecondPrice();
		console.log($scope.orderPreProduct);

		var data = $scope.orderPreProduct;

		var formData = new FormData();

		var formData = window.jsonToFormData($scope.orderPreProduct);

		for (var i = 0; i < $scope.files.length; i++) {
			formData.append("files", $scope.files[i]);
		}

		$http({
			method : 'POST',
			data : formData,
			headers : {
				'Content-Type' : undefined
			},
			url : $$ContextURL + '/orderPreProducts/add'
		}).then(function(response) {
			console.log(response);
			var outPut = `
			
			<div>${response.data.message}
			</div>
			<div>
			<a class="btn btn-info" target="_blank" href="${$$ContextURL}/orderPreProducts/${response.data.etc}/print"><i class="fa fa-print"></i></a></div>
			`;
			$("#freeze").addClass("cus-freeze");
			console.log("outPut=",outPut);
			
			$("#modal-body").html(outPut);
			$("#modal").modal("show");

			// Reload The Page
			$('#modal').on('hidden.bs.modal', function () {
				location.reload();
			})


		}, function(response) {
			$("#modal-body").html(response.data);
			$("#modal").modal("show");
		});

	}
});

angular.module('app').directive('showImage', function() {
	return {
		scope : {
			file : "="
		},
		link : function($scope, element, attrs) {
			var file = $scope.file;
			if (file && file.size > 0) {
				var img = document.createElement("img");
				img.setAttribute('height', '75px');
				img.setAttribute('width', '75px');
				var reader = new FileReader();
				reader.onloadend = function() {
					img.src = reader.result;
				}
				reader.readAsDataURL(file);
				$(element).after(img);
			}
		}
	};
});
