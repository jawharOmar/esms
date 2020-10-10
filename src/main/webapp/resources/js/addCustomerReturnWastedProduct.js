$(document).ready()
{
    console.log("csrf=", csrf);


    $("#newProductStepUpExpirationDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", new Date());

}

// Angular

app = angular.module("app", []);

app.factory('httpRequestInterceptor', function () {
    return {
        request: function (config) {
            config.headers['X-CSRF-TOKEN'] = csrf;
            return config;
        }
    };
});
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('httpRequestInterceptor');
});

app.controller('appCTRL', function ($scope, $http, $q) {

    $scope.customerWastedProducts = {
        product: null,
        stock: null,
        quantity: "",
        price: "",
        note: ""
    };
    $scope.wastedProduct = {
        id: "",
        reference: "",
        totalPrice: "",
        customerWastedProducts: "",
        customer: "",
    };
    $scope.resetCustomerWastedProducts = angular.copy($scope.customerWastedProducts);


    $scope.init = function () {

        console.log("init->fired");

        console.log("jsonWastedProduct=", jsonWastedProduct);

        $scope.customers = JSON.parse(jsonCustomers);
        $scope.products = JSON.parse(jsonProducts);
        $scope.stocks = JSON.parse(jsonStocks);
        $scope.wastedProduct = JSON.parse(jsonWastedProduct);


        // S-Product AutoCompletion
        var productAuto = [];

        angular.forEach($scope.products, function (value, key) {
            var obj = {
                label: value.name + " " + value.code,
                value: value.code,
                data: value
            };
            productAuto.push(obj);
        });


        $("#autoselect").autocomplete({
            source: productAuto,
            select: function (event, ui) {
                var item = ui.item.data;
                console.log("selected item =", item);

                $http({
                    method: 'GET',
                    url: $$ContextURL + '/products/find/cost/' + item.code
                }).then(function successCallback(response) {
                    console.log(response);
                    $scope.customerWastedProducts.price = response.data;
                }, function errorCallback(response) {
                    $("#modal-body").html(response.data);
                    $("#modal").modal("show");
                });

                $scope.customerWastedProducts.product = angular.copy(item);

                $scope.$digest();
            }
        });

        // E-Product AutoCompletion


        // S-Customer AutoCompletion
        var customerAuto = [];

        angular.forEach($scope.customers, function (value, key) {
            var obj = {
                label: value.fullName + " " + value.phone,
                value: value.fullName,
                data: value
            };
            customerAuto.push(obj);
        });
        console.log("customerAuto=", customerAuto);

        $("#customer-autoselect")
            .autocomplete(
                {
                    source: customerAuto,
                    select: function (event, ui) {
                        var item = ui.item.data;
                        console.log("selected user=", item);
                        item.customerPayments = [];
                        $scope.wastedProduct.customer = item;

                        $scope.$digest();

                    }
                });

        // E-Customer AutoCompletion

    };

    $scope.totalPrice = function () {
        var totalPrice = 0;
        for (var i = 0; i < $scope.wastedProduct.customerWastedProducts.length; i++) {
            totalPrice += $scope.wastedProduct.customerWastedProducts[i].price;
        }
        return totalPrice;
    };


    $scope.addWastedProduct = function () {
        console.log("addWastedProduct->fired");
        $scope.customerWastedProducts.price = parseFloat(($scope.customerWastedProducts.price
            * $scope.customerWastedProducts.quantity).toFixed(3));

        $scope.wastedProduct.customerWastedProducts.push($scope.customerWastedProducts);
        $scope.customerWastedProducts = angular.copy($scope.resetCustomerWastedProducts);
    };

    $scope.deleteWastedProduct = function (index) {
        console.log("Delete item index=", index);
        $scope.wastedProduct.customerWastedProducts.splice(index, 1);
    };

    $scope.saveCustomerWastedProduct = function () {
        console.log("saveCustomerWastedProduct->fired");
        console.log($scope.wastedProduct);
        $scope.wastedProduct.totalPrice = $scope.totalPrice();


        var formData = new FormData();

        var formData = window.jsonToFormData($scope.wastedProduct);

        $http({
            method: 'POST',
            data: formData,
            headers: {
                'Content-Type': undefined
            },
            url: $$ContextURL + '/customerWastedProducts/add'
        }).then(function (response) {
            console.log(response);
            var outPut = `

        	<div>${response.data.message}
        	</div>
        	<div>
        	<a class="btn btn-info" target="_blank" href="${$$ContextURL}/customerWastedProducts/${response.data.etc}/print"><i class="fa fa-print"></i></a></div>
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
            $("#modal-body").html(response.data);
            $("#modal").modal("show");
        });

    }
});


