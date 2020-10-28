$(document).ready()
{
    console.log("csrf=", csrf);
}
// Angular
app.controller('appCTRL', function ($scope, $http, $window) {
    $scope.stockTransfer = {
        from: {
            id: "",
            name: ""
        },
        to: {
            id: "",
            name: ""
        },
        stockTransferDetails: [],
        time: null
    };

    $scope.stocks;

    $scope.product = {
        productId: "",
        code: "",
        name: "",
        scientifiName: "",
        unitType: "",
        stockLevel: ""
    };
    $scope.resetProduct = angular.copy($scope.product);

    $scope.init = function () {
        console.log("init->fired");

        $scope.stocks = JSON.parse(jsonStocks);
        $scope.products = JSON.parse(jsonProducts);
        // $scope.stockTransfer =
        // JSON.parse(jsonStockTransfer);//for edit

        // S-Product AutoCompletion
        var productAuto = [];

        angular.forEach($scope.products, function (value, key) {
            var obj = {
                label: value.name + " " + value.code,
                value: value.code,
                data: value
            }
            productAuto.push(obj);
        });

        $("#autoselect").autocomplete({
            source: productAuto,
            select: function (event, ui) {
                var item = ui.item.data;
                console.log("selected item =", item);

                $scope.product.code = item.code;

                $scope.$digest();
            }
        });

        // E-Product AutoCompletion
    }

    $scope.getProduct = function (event) {
        console.log("getProduct->fired");
        if (event.which == 13) {
            document.getElementById("pk_info").style = "display:block";

            $http(
                {
                    method: "GET",
                    url: $$ContextURL
                        + "/products/find/code/"
                        + $scope.product.code,
                    params: {
                        stockId: $scope.stockTransfer.from.id
                    }
                })
                .then(
                    function (response) {
                        console.log("success");

                        if (response.data.stockLevel == 0) {
                            $("#modal-body")
                                .html(
                                    "Out of the stock or you may have expiration");
                            $("#modal").modal("show");
                        } else {
                            $scope.product = response.data;
                        }
                    },
                    function (response) {
                        console.error("failed");
                        console.error("error occured");
                        $("#modal-body").html(
                            response.data);
                        $("#modal").modal("show");
                    });

        }

    }

    $scope.addStockTransferDetail = function () {
        console.log("addStockTransferDetail->fired");

        document.getElementById("pk_info").style = "display:none;"
        var product = {
            id: $scope.product.productId,
            name: $scope.product.name,
            code: $scope.product.code
        };

        var stockTransferDetail = {
            product: product,
            amount: $scope.product.quantity
        };
        $scope.stockTransfer.stockTransferDetails
            .unshift(stockTransferDetail);
        $scope.product = $scope.resetProduct;
        $("#autoselect").val("");
        $("#autoselect").focus();
    }

    $scope.removeStockTransferDetail = function (index) {
        console.log("removeStockTransferDetail->fired");
        console.log("index=", index);
        $scope.stockTransfer.stockTransferDetails.splice(index,
            1);
    }

    $scope.saveStockTransfer = function () {
        console.log("saveStockTransfer->fired");
        console.log($scope.stockTransfer);
        $http({
            method: 'POST',
            data: $scope.stockTransfer,
            url: $$ContextURL + '/stockTransfers/add'
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