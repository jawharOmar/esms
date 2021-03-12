$(document).ready()
{
	console.log("csrf=", csrf);
}

// Angular

app.controller('appCTRL', function($scope, $http, $window) {

	$scope.customer;
	$scope.init = function() {
		console.log("init->fired");
		$scope.projectId;
		$scope.report = [];

		$scope.actionType = "all";
		$scope.customers = JSON.parse(jsonCustomers);

		// S-Customer AutoCompletion
		var customerAuto = [];

		angular.forEach($scope.customers, function(value, key) {
			var obj = {
				label : value.fullName + " " + value.phone,
				value : value.fullName,
				data : value
			};
			customerAuto.push(obj);
		});

		$("#customer-autoselect").autocomplete({
			source : customerAuto,
			select : function(event, ui) {
				var item = ui.item.data;
				$scope.customer = item;
				console.log("selected item =", item);
				$scope.$digest();
			}
		});

		// E-Customer AutoCompletion
	};

	$scope.projectStatement = function() {
		console.log("projectStatement->fired");
		var urlVariables = "customerId=" + $scope.customer.id + "&projectId="
				+ $scope.projectId + "&actionType=" + $scope.actionType;
		$window.open($$ContextURL + "/report/projectStatement?" + urlVariables,
				"_self");
	}

});