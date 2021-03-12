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

app.controller('appCTRL', function ($scope, $http, $q) {

    $scope.isNumber = angular.isNumber;

    $scope.customers;
    $scope.selectedProduct;
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
        bonusQuantity: "",
        paymentAmount: "",
        packetQuantity: "",
        returnPrice: "",
    };
    $scope.resetProductStepUp = angular
        .copy($scope.productStepUp);

    $scope.cusotmerOrderReturn = {};

    $scope.productDs;

    $scope.url;

    $scope.init = function () {

        console.log("init->fired");

        $scope.url = url;
        $scope.stocks = JSON.parse(jsonStocks);

        console.log("jsonCustomers=", jsonCustomers);
        console.log("jsonCustomerOrderReturn=",
            jsonCustomerOrderReturn);

        $scope.customers = JSON.parse(jsonCustomers);
        $scope.customerOrderReturn = JSON
            .parse(jsonCustomerOrderReturn);

        console.log("$scope.customers=", $scope.customers);
        console.log("$scope.customerOrderReturn=",
            $scope.customerOrderReturn);

        console.log("jsonProductDs=", jsonProductDs);
        $scope.productDs = JSON.parse(jsonProductDs);
        console.log("$scope.productDs=", $scope.productDs);

        var productAuto = [];

        angular.forEach($scope.productDs, function (value, key) {
            var obj = {
                label: value.name + " " + value.code,
                value: value.name,
                data: value
            }
            productAuto.push(obj);
        });

        $("#autoselect").autocomplete(
            {
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
                    // $scope.productStepUp.paymentAmount =
                    // item.lastPrice;
                    console.log("JTEST=", item);
                    console.log("$scope.selectedProduct=",
                        $scope.selectedProduct);

                    $scope.$digest();
                }
            });

    }

    $scope
        .$watch(
            "productStepUp.packetQuantity",
            function (newValue, oldValue) {
                if (newValue) {
                    $scope.productStepUp.quantity = $scope.productStepUp.packetQuantity
                        * $scope.productStepUp.product.packetSize;
                }
            });

    $scope.totalPrice = function () {
        var totalPrice = 0;
        for (var i = 0; i < $scope.customerOrderReturn.productStepUps.length; i++) {
            totalPrice += $scope.customerOrderReturn.productStepUps[i].returnPrice;
        }
        return totalPrice;
    }

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
                    $scope.productStepUp.product.packetSize = response.data.packetSize;

                    $scope.productStepUp.returnPrice = $scope.productStepUp.returnPrice
                        * $scope.productStepUp.quantity;


                    $scope.customerOrderReturn.productStepUps
                        .unshift($scope.productStepUp);

                    $scope.productStepUp = angular
                        .copy($scope.resetProductStepUp);

                    //Select First Stock By Default
                    $scope.productStepUp.stock = $scope.stocks[0];

                    $scope.selectedProduct = null;

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
        console
            .log(
                "Delete item =",
                $scope.customerOrderReturn.productStepUps[index]);
        $scope.customerOrderReturn.productStepUps.splice(index,
            1);
    }

    $scope.editProductStepUp = function (index) {
        console.log("editProductStepUp->fired");
        var productStepUp = $scope.customerOrderReturn.productStepUps[index];

        $scope.productStepUp = angular.copy(productStepUp);
        $scope.productStepUp.returnPrice = +($scope.productStepUp.returnPrice / $scope.productStepUp.quantity)
            .toFixed(3);
        $scope.productStepUp.paymentAmount = +($scope.productStepUp.returnPrice / $scope.productStepUp.quantity)
            .toFixed(3);

        if (productStepUp.product.packetSize
            && productStepUp.quantity > productStepUp.product.packetSize) {
            $scope.productStepUp.packetQuantity = productStepUp.quantity
                / productStepUp.product.packetSize;
        } else {
            $scope.productStepUp.quantity = productStepUp.quantity;
        }
        $scope.customerOrderReturn.productStepUps.splice(index,
            1);
    }

    $scope.saveCustomerOrderReturn = function () {
        console.log("saveCustomerOrderReturn->fired");
        if ($scope.customerOrderReturn.customerProject != null) {
            if ($scope.customerOrderReturn.customerProject.id == null) {
                $scope.customerOrderReturn.customerProject = null;
            }
        }
        $scope.customerOrderReturn.totalPrice = $scope
            .totalPrice();
        console.log($scope.customerOrderReturn);
        $http({
            method: 'POST',
            data: $scope.customerOrderReturn,
            url: $$ContextURL + '/customerOrderReturns/update'
        }).then(function (response) {
            console.log(response);
            $("#modal-body").html(response.data);
            $("#modal").modal("show");
        }, function (response) {
            $("#modal-body").html(response.data);
            $("#modal").modal("show");
        });

    }
});