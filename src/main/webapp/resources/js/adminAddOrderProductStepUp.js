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

app.controller('appCTRL', function ($scope, $http, $q, $filter) {

    $scope.isNumber = angular.isNumber;

    $scope.vendors;
    $scope.selectedProduct;
    $scope.stocks;
    $scope.productStepUp = {
        product: {
            code: "",
            name: "",
            unitType: "",
            packetSize: "",
            category: "",
            attachedFile: {}
        },
        expirationDate: "",
        quantity: "",
        paymentAmount: "",
        packetQuantity: "",
    };
    $scope.resetProductStepUp = angular
        .copy($scope.productStepUp);

    $scope.orderProductStepUp = {};

    $scope.productDs;
    $scope.url;

    $scope.init = function () {
        $scope.paymentAmountToChange=null;
        console.log("init->fired");
        $scope.url = url;

        console.log("jsonVendors=", jsonVendors);
        console.log("jsonOrderProductStepUp=",
            jsonOrderProductStepUp);

        $scope.stocks = JSON.parse(jsonStocks);

        $scope.vendors = JSON.parse(jsonVendors);
        $scope.orderProductStepUp = JSON
            .parse(jsonOrderProductStepUp);

        console.log("$scope.vendors=", $scope.vendors);
        console.log("$scope.orderProductStepUp=",
            $scope.orderProductStepUp);

        console.log("jsonProductDs=", jsonProductDs);
        $scope.productDs = JSON.parse(jsonProductDs);
        console.log("$scope.productDs=", $scope.productDs);

        console.log("jsonCurrencyExchange=", jsonCurrencyExchange);
        if(jsonCurrencyExchange){
            $scope.currencyExchange = JSON.parse(jsonCurrencyExchange);
        }else {
            $scope.currencyExchange = null;
        }



        $scope.orderProductStepUp.totalPayment = 0;

        $scope.discountRatio = 0;
        $scope.checkTotalPrice = 0;
        $scope.paymentValidation = 0;

        var productAuto = [];

        angular.forEach($scope.productDs, function (value, key) {
            var obj = {
                label: value.name + " " + value.code,
                value: value.name,
                data: value
            };
            productAuto.push(obj);
        });

        $("#autoselect").autocomplete({
            source: productAuto,
            select: function (event, ui) {
                var item = ui.item.data;
                console.log("item=", item);
                var product = {
                    code: item.code,
                    name: item.name,
                    unitType: item.unitType,
                    packetSize: item.packetSize
                };
                $scope.selectedProduct = product;
                $scope.productStepUp.product = item;
                $scope.productStepUp.paymentAmount = item.lastPrice;
                console.log("JTEST=", item);
                console.log("$scope.selectedProduct=", $scope.selectedProduct);

                $scope.$digest();
            }
        });

    };


    $scope.paymentChangeCurrency = function () {
        $scope.productStepUp.paymentAmount = $scope.currencyExchange.reverseRate * $scope.paymentAmountToChange;
    };

    $scope.$watch("productStepUp.packetQuantity",
        function (newValue, oldValue) {
            if (newValue) {
                $scope.productStepUp.quantity = $scope.productStepUp.packetQuantity
                    * $scope.productStepUp.product.packetSize;
            }
        });

    $scope.totalPrice = function () {
        var totalPrice = 0;
        for (var i = 0; i < $scope.orderProductStepUp.productStepUps.length; i++) {
            totalPrice += $scope.orderProductStepUp.productStepUps[i].paymentAmount;
        }
        return totalPrice;
    };

    $scope.setTotalPayment = function () {
        return $scope.orderProductStepUp.totalPayment = parseFloat(($scope
            .totalPrice() - $scope.orderProductStepUp.discount).toFixed(3));
    };


    $scope.$watch("checkTotalPrice", function (newValue, oldValue) {
        if (newValue) {
            $scope.calculateDiscount();
        }
    });
    $scope.$watch("orderProductStepUp.totalPayment", function (newValue, oldValue) {
        if (newValue) {
            $scope.paymentValidation = parseFloat(($scope.totalPrice() - $scope.orderProductStepUp.discount).toFixed(3));
        }
    });

    //Calculate Discount If Discount Ratio Changed
    $scope.calculateDiscount = function () {
        if ($scope.selectRatio && $scope.discountRatio < 1) {
            $scope.orderProductStepUp.discount = parseFloat(($scope
                .totalPrice() * $scope.discountRatio).toFixed(3));
            $scope.calculateTotalPayment();
        }
    };

    //Calculate Total Payment Based On Discount
    $scope.calculateTotalPayment = function () {
        if ($scope.orderProductStepUp.totalPayment > 0 && $scope.orderProductStepUp.totalPayment > $scope.setTotalPayment()) {
            $scope.setTotalPayment();
        }
    };


    $scope.getProduct = function () {
        console.log("getProduct->fired");

        if ($scope.productStepUp.product.code)
            $http
                .get(
                    $$ContextURL
                    + "/products/find/code/"
                    + $scope.productStepUp.product.code)
                .then(
                    function (response) {

                        console.log(response.data);
                        $scope.productStepUp.product.id = response.data.productId;
                        $scope.productStepUp.product.name = response.data.name;
                        $scope.productStepUp.product.unitType = response.data.unitType;
                        $scope.productStepUp.product.packetSize = response.data.packetSize;
                    },
                    function (response) {
                        console.error("error occured");
                        $("#modal-body").html(
                            response.data);
                        $("#modal").modal("show");
                    });

    }

    $scope.addProductStepUp = function () {
        console.log("addProductStepUp->fired");

        $http.get($$ContextURL + "/products/find/code/" + $scope.productStepUp.product.code)
            .then(function (response) {

                    console.log(response.data);

                    $scope.productStepUp.product.id = response.data.productId;
                    $scope.productStepUp.product.name = response.data.name;
                    $scope.productStepUp.product.packetSize = response.data.packetSize;

                    $scope.productStepUp.paymentAmount = $scope.productStepUp.paymentAmount
                        * $scope.productStepUp.quantity;

                    $scope.orderProductStepUp.productStepUps
                        .unshift($scope.productStepUp);

                    $scope.productStepUp = angular
                        .copy($scope.resetProductStepUp);

                    //Select First Stock By Default
                    $scope.productStepUp.stock = $scope.stocks[0];

                    $scope.selectedProduct = null;

                    $scope.paymentAmountToChange = null;

                    $("#autoselect").focus();

                },
                function (response) {
                    console.error("error occured");
                    $("#modal-body")
                        .html(response.data);
                    $("#modal").modal("show");
                });

    }

    $scope.deleteProductStepUp = function (index) {
        console.log("Delete item index=", index);
        console.log("Delete item =", $scope.orderProductStepUp.productStepUps[index]);
        $scope.orderProductStepUp.productStepUps.splice(index, 1);
    };

    $scope.editProductStepUp = function (index) {
        console.log("editProductStepUp->fired");

        var productStepUp = $scope.orderProductStepUp.productStepUps[index];

        $scope.productStepUp = angular.copy(productStepUp);
        $scope.productStepUp.paymentAmount = +($scope.productStepUp.paymentAmount / $scope.productStepUp.quantity)
            .toFixed(3);
        if (productStepUp.product.packetSize
            && productStepUp.quantity > productStepUp.product.packetSize) {
            $scope.productStepUp.packetQuantity = productStepUp.quantity
                / productStepUp.product.packetSize;
        } else {
            $scope.productStepUp.quantity = productStepUp.quantity;
        }

        if($scope.currencyExchange) {
            $scope.paymentAmountToChange = $scope.productStepUp.paymentAmount * $scope.currencyExchange.curRate;
        }

        $scope.orderProductStepUp.productStepUps.splice(index, 1);
    };

    $scope.saveOrderProductStepUp = function () {

        $scope.orderProductStepUp.totalPrice = $scope
            .totalPrice();
        if ($scope.orderProductStepUp.discount > 0) {
            var discount = $scope.orderProductStepUp.discount / $scope.orderProductStepUp.productStepUps.length;
            for (var i = 0; i < $scope.orderProductStepUp.productStepUps.length; i++) {
                $scope.orderProductStepUp.productStepUps[i].paymentAmount = $scope.orderProductStepUp.productStepUps[i].paymentAmount - discount;
            }
        }


        console.log("saveOrderProductStepUp->fired");

        console.log($scope.orderProductStepUp);
        $http({
            method: 'POST',
            data: $scope.orderProductStepUp,
            url: $$ContextURL + '/orderProductStepUps/add'
        }).then(function (response) {
            console.log(response);
            $("#modal-body").html(response.data);
            $("#modal").modal("show");
        }, function (response) {
            if (typeof response.data === 'object') {
                $("#modal-body").html(response.data.message);
                $("#modal").modal("show");
            } else {
                $("#modal-body").html(response.data);
                $("#modal").modal("show");
            }
        });

    }
});