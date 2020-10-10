$(document).ready()
{
    console.log("csrf=", csrf);
}

// Angular

appAddCusotmerOrder = angular.module("app", []);


appAddCusotmerOrder.factory('httpRequestInterceptor', function () {
    return {
        request: function (config) {
            config.headers['X-CSRF-TOKEN'] = csrf;
            return config;
        }
    };
});

appAddCusotmerOrder.config(function ($httpProvider) {
    $httpProvider.interceptors.push('httpRequestInterceptor');
});

appAddCusotmerOrder.controller('appCTRL', function ($scope, $http) {

    $scope.customerPayment;

    $scope.customers;

    $scope.init = function () {
        console.log("init->fired");

        $scope.customerPayment = JSON.parse(jsonCustomerPayment);
        console.log("$scope.customerPayment=", $scope.customerPayment);




        if (customerAuto.length < 1) {
            $("#customer-autoselect").autocomplete({});
        }
        // E-Customer AutoCompletion
    };

    // S-Customer AutoCompletion
    var customerAuto = [];
    $scope.selectedCustomer;

    $scope.$watch("cusomerPayment.customer.fullName", function (newValue, oldValue) {
        if (newValue) {
            customerAuto = [];
            $http({
                url: $$ContextURL + "/customers/searchCustomer",
                method: 'POST',
                params: {keyword: $scope.cusomerPayment.customer.fullName}
            }).then(function (response) {
                console.log("customers=", response.data);
                angular.forEach(response.data, function (value, key) {
                    var obj = {
                        label: value.keyword + " " + value.secondKeyword,
                        value: value.keyword,
                        data: value
                    };
                    customerAuto.push(obj);
                })
            }, function (error) {
                $("#modal-body").html(error.data);
                $("#modal").modal("show");
            });


            $("#customer-autoselect").autocomplete({
                source: customerAuto,
                select: function (event, ui) {
                    var item = ui.item.data;
                    console.log("selected item =", item);
                    $http({
                        url: $$ContextURL + "/customers/searchCustomerId",
                        method: 'POST',
                        params: {id: item.id}
                    }).then(function (response) {
                        console.log("customer=", response.data);
                        $scope.customerPayment.customer = response.data;
                        $scope.selectedCustomer = angular.copy(response.data);
                    }, function (error) {
                        $("#modal-body").html(error.data);
                        $("#modal").modal("show");
                    });
                    $scope.$digest();
                }
            });
        }
    });

    $scope.$watch("customerPayment.customerProject.id", function (newValue, oldValue) {
        if (newValue) {
            $http({
                method: 'GET',
                data: $scope.customerPayment,
                url: $$ContextURL + '/customerPayments/totalLoan/' + $scope.customerPayment.customer.id + '/' + newValue
            }).then(function (response) {
                console.log(response);
                if (response.data !== null) {
                    $scope.customerPayment.customer.totalLoan = response.data;
                }
            });
        } else {
            $scope.customerPayment.customer=angular.copy($scope.selectedCustomer);
            $scope.customerPayment.customerProject = null;
        }
    });

    $scope.addCustomerPayment = function () {
        console.log("addCustomerPayment->fired");
        console.log("$scope.customerPayment=", $scope.customerPayment);

        $http({
            method: 'POST',
            data: $scope.customerPayment,
            url: $$ContextURL + '/customerPayments/add'
        }).then(function (response) {
            console.log(response);
            var outPut = `
			
			<div>${response.data.message}
			</div>
			<div>
			<a class="btn btn-info" target="_blank" href="${$$ContextURL}/customerPayments/${response.data.etc}/print"><i class="fa fa-print"></i></a></div>
			`;
            $("#freeze").addClass("cus-freeze");
            console.log("outPut=", outPut);

            $("#modal-body").html(outPut);
            $("#modal").modal("show");

            //Reload The Page
            $('#modal').on('hidden.bs.modal', function () {
                location.reload();
            })
        }, function (response) {
            console.error("error occurred");
            if (response.data.fieldErrors) {
                console.log(response.data.fieldErrors);
                var outPut = `<table><tbody>`;
                response.data.fieldErrors.forEach(function (element) {
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

})
;