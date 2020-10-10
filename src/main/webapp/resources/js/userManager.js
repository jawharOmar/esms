$(document).ready()
{
	console.log("csrf=", csrf);
}
// Angular
app = angular.module("app", []);

angular.module('app').directive('ngEnter', function() {
	return function(scope, element, attrs) {
		element.bind("keydown keypress", function(event) {
			if (event.which === 13) {
				scope.$apply(function() {
					scope.$eval(attrs.ngEnter, {
						'event' : event
					});
				});

				event.preventDefault();
			}
		});
	};
});

app.factory('httpRequestInterceptor', function() {
	return {
		request : function(config) {
			config.headers['X-CSRF-TOKEN'] = csrf;
			return config;
		}
	};
});

app.config(function($httpProvider) {
	$httpProvider.interceptors.push('httpRequestInterceptor');
});

app.controller('appCTRL', function($scope, $http, $window) {

	$scope.users;
	$scope.roles;
	$scope.showUserPanel = false;
	$scope.user = {
		userName : "",
		enabled : false,
		password : ""
	};

	$resetUser = angular.copy($scope.user);

	$scope.userRoles;

	$scope.init = function() {
		console.log("init->fired");
		$scope.users = JSON.parse(jsonUsers);
		$scope.roles = JSON.parse(jsonRoles);
		$scope.getUserRoles();
	}

	$scope.getUserRoles = function() {
		$scope.userRoles = [];

		angular.forEach($scope.roles, function(v, k) {
			var hasRole = false;
			if ($scope.user && $scope.user.roles) {
				var found = $scope.user.roles.find(function(item) {
					return item.id == v.id;
				});
				if (found)
					hasRole = true;
			}

			var userRole = {
				hasRole : hasRole,
				role : v
			};

			$scope.userRoles.push(userRole);

		});

	};

	$scope.addUser = function() {
		console.log("addUser->fired");

		var roles = [];

		console.log("$scope.userRoles=", $scope.userRoles);

		angular.forEach($scope.userRoles, function(v, k) {
			if (v.hasRole)
				roles.push(v.role);
		});
		$scope.user.roles = roles;

		$http({
			data : $scope.user,
			method : "POST",
			url : $$ContextURL + '/users/add'
		}).then(function(response) {
			$("#modal-body").html(response.data);
			$("#modal").modal("show");
		}, function(response) {
			$("#modal-body").html(response.data);
			$("#modal").modal("show");
		});

	}

	$scope.updateUser = function() {
		console.log("updateUser->fired");

		var roles = [];

		console.log("$scope.userRoles=", $scope.userRoles);

		angular.forEach($scope.userRoles, function(v, k) {
			if (v.hasRole)
				roles.push(v.role);
		});

		$scope.user.roles = roles;

		$http({
			data : $scope.user,
			method : "POST",
			url : $$ContextURL + '/users/update'
		}).then(function(response) {
			$("#modal-body").html(response.data);
			$("#modal").modal("show");
		}, function(response) {
			$("#modal-body").html(response.data);
			$("#modal").modal("show");
		});

	}

	$scope.cancleEdit = function() {
		console.log("cancleEdit->fired");
		$scope.user = angular.copy($scope.resetUser);
		$('#usersTab a[href="#users"]').tab('show');
	}

	$scope.getEditingUser = function(user) {
		console.log("editUser->fired");
		$scope.user = user;
		$scope.getUserRoles();
		$('#usersTab a[href="#addUser"]').tab('show');

	}

});