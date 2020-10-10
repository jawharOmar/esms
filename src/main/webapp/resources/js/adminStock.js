//---------------AngularJs------------------
var app = angular.module("stockApp", []);

app.factory("httpRequestInterceptor", function () {
    return {
        request: function (config) {
            config.headers["X-CSRF-TOKEN"] = csrf;
            return config;
        }
    }
});


app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('httpRequestInterceptor');
});


app.controller("stockCtrl", function ($scope, $http, $window) {
    $scope.initStock = function () {
        $scope.contextURL = $$ContextURL;
        $scope.productDs = JSON.parse(jsonProductDs);
        $scope.priceCategories = JSON.parse(jsonPriceCategories);
        $scope.currentPage = CurrentPage;
        $scope.totalPage = TotalPage;
        $scope.showPerPage = ShowPerPage;
        $scope.stockId = stockIdCtrl;
        $scope.sumTotalCost = SumTotalCost;

        console.log("products", $scope.productDs);


        //Copy Values
        $scope.productDsCopy = angular.copy($scope.productDs);
        $scope.currentPageCopy = angular.copy($scope.currentPage);
        $scope.totalPageCopy = angular.copy($scope.totalPage);
        $scope.showPerPageCopy = angular.copy($scope.showPerPage);
        $scope.sumTotalCostCopy = angular.copy($scope.sumTotalCost);


        //Define Pagination
        $scope.pagination($scope.currentPage, $scope.totalPage);

        $scope.searchByProductCodeValue = "";
        $scope.searchByProductNameValue = "";
        $scope.searchByProductCodeNameValue = "";
        $scope.searchByProductCategoryValue = "";


        //selected column
        $scope.selectedList = {};


        $scope.downloadExcel = function () {
            var column = [];
            angular.forEach($scope.selectedList, function (selected, name) {
                if (selected) {
                    column.push({"name": name});
                }
            });

            $http({
                url: $$ContextURL + "/products/stock/download?stockId=" + $scope.stockId,
                method: 'POST',
                data: JSON.stringify(column),
                headers: {
                    'Content-type': 'application/json'
                },
                responseType: 'blob'
            }).then(function (response, status, headers, config) {

                var blob = new Blob([response.data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
                var objectUrl = URL.createObjectURL(blob);
                var anchor = document.createElement("a");
                //add Current Date To Name And Change TagName
                var today = new Date();

                anchor.download = "Stock_" + today.toLocaleString() + ".xlsx";
                anchor.href = objectUrl;
                anchor.click();
            });

        };
    };

    $scope.getSpecialPrice = function (productPriceCategories, productId) {
        var price = 0;
        angular.forEach(productPriceCategories, function (value) {
            if (value.priceCategory.id === productId) {
                if (value.price) {
                    price = Number((value.price).toFixed(3));
                }
            }
        });
        return price;
    };


    //Show Per Page
    $scope.showPerPageAction = function (number) {
        if ($scope.stockId) {
            $window.location.href = $$ContextURL + '/products/stock?stockId=' + $scope.stockId + '&show=' + number;
        } else {
            $window.location.href = $$ContextURL + '/products/stock?show=' + number;
        }
    };

    //Pagination

    $scope.range = [];
    $scope.pagination = function (currentPage, totalPage) {
        var number = 4;
        if (number >= currentPage) {
            number = currentPage - 1;
        }
        for (var i = 0; i < 9; i++) {
            if (currentPage - number >= 1) {
                $scope.range.push(currentPage - number);
                number--;
            } else {
                if (i + currentPage <= totalPage) {
                    $scope.range.push(i + currentPage);
                }
            }
        }
    };

    $scope.reloadProducts = function () {
        $scope.productDs = $scope.productDsCopy;
        $scope.currentPage = $scope.currentPageCopy;
        $scope.totalPage = $scope.totalPageCopy;
        $scope.showPerPage = $scope.showPerPageCopy;
        $scope.sumTotalCost = $scope.sumTotalCostCopy;
    };

    // productListCode AutoCompletion
    var productListCode = [];

    $scope.searchByProductCode = function () {
        if ($scope.searchByProductCodeValue) {
            productListCode = [];
            $http({
                url: $$ContextURL + "/products/search/code",
                method: 'GET',
                params: {productCode: $scope.searchByProductCodeValue}
            }).then(function (response) {
                console.log("ProductCodes=", response.data);
                angular.forEach(response.data, function (value, key) {
                    var obj = {
                        label: value.keyword,
                        value: value.keyword,
                        data: value
                    };
                    productListCode.push(obj);
                })
            }, function (error) {
                $("#modal-body").html(error.data);
                $("#modal").modal("show");
            });

        }

        $("#searchByProductCode").autocomplete({
            source: productListCode,
            select: function (event, ui) {
                var item = ui.item.data;
                console.log("selected item =", item);
                $scope.productDs = [];

                var restUrl = "";
                if ($scope.stockId) {
                    restUrl = "&stockId=" + $scope.stockId;
                }
                $http({
                    url: $$ContextURL + "/products/search/product?productId=" + item.productId + restUrl,
                    method: 'GET',
                }).then(function (response) {
                    console.log("Selected Product", response.data);
                    $scope.productDs.push(response.data);
                    $scope.currentPage = 1;
                    $scope.totalPage = 1;
                    $scope.searchByProductCodeValue = "";

                    $scope.sumTotalCost = 0;
                    angular.forEach($scope.productDs, function (productD, key) {
                        if (productD.cost && productD.stockLevel) {
                            $scope.sumTotalCost += productD.cost * productD.stockLevel;
                        }
                    });
                }, function (error) {
                    $("#modal-body").html(error.data);
                    $("#modal").modal("show");
                });
                $scope.$digest();
            }
        });
    };
    if (productListCode.length < 1) {
        $("#searchByProductCode").autocomplete({});
    }


//    -----------------End searchByProductCode--------------
    // productListCode AutoCompletion
    var productListName = [];

    $scope.searchByProductName = function () {
        if ($scope.searchByProductNameValue) {
            productListName = [];
            $http({
                url: $$ContextURL + "/products/search/name",
                method: 'GET',
                params: {productName: $scope.searchByProductNameValue}
            }).then(function (response) {
                console.log("ProductNames=", response.data);
                angular.forEach(response.data, function (value, key) {
                    var obj = {
                        label: value.keyword,
                        value: value.keyword,
                        data: value
                    };
                    productListName.push(obj);
                })
            }, function (error) {
                $("#modal-body").html(error.data);
                $("#modal").modal("show");
            });
        }
        $("#searchByProductName").autocomplete({
            source: productListName,
            select: function (event, ui) {
                var item = ui.item.data;
                console.log("Selected item =", item);
                $scope.productDs = [];
                var restUrl = "";
                if ($scope.stockId) {
                    restUrl = "&stockId=" + $scope.stockId;
                }
                $http({
                    url: $$ContextURL + "/products/search/product?productId=" + item.productId + restUrl,
                    method: 'GET',
                }).then(function (response) {
                    console.log("Selected Product", response.data);
                    $scope.productDs.push(response.data);
                    $scope.currentPage = 1;
                    $scope.totalPage = 1;
                    $scope.searchByProductNameValue = "";

                    $scope.sumTotalCost = 0;
                    angular.forEach($scope.productDs, function (productD, key) {
                        if (productD.cost && productD.stockLevel) {
                            $scope.sumTotalCost += productD.cost * productD.stockLevel;
                        }
                    });
                }, function (error) {
                    $("#modal-body").html(error.data);
                    $("#modal").modal("show");
                });
                $scope.$digest();
            }
        });
    };

    if (productListName.length < 1) {
        $("#searchByProductName").autocomplete({});
    }

//    -----------------End searchByProductName--------------

//    -----------------productListCodeName--------------
    //          Search By Code Or Name


    // productListCode AutoCompletion
    var productListCodeName = [];

    $scope.searchByProductCodeName = function () {
        if ($scope.searchByProductCodeNameValue) {
            productListCodeName = [];
            $http({
                url: $$ContextURL + "/products/search/nameOrCode",
                method: 'GET',
                params: {keyword: $scope.searchByProductCodeNameValue}
            }).then(function (response) {
                console.log("ProductCodeOrNames=", response.data);
                angular.forEach(response.data, function (value, key) {
                    var obj = {
                        label: value.keyword + " " + value.secondKeyword,
                        value: value.keyword,
                        data: value
                    };
                    productListCodeName.push(obj);
                })
            }, function (error) {
                $("#modal-body").html(error.data);
                $("#modal").modal("show");
            });
        }
        $("#searchByProductCodeName").autocomplete({
            source: productListCodeName,
            select: function (event, ui) {

                var item = ui.item.data;
                console.log("Selected item =", item);
                $scope.productDs = [];
                var restUrl = "";
                if ($scope.stockId) {
                    restUrl = "&stockId=" + $scope.stockId;
                }
                $http({
                    url: $$ContextURL + "/products/search/product?productId=" + item.id + restUrl,
                    method: 'GET',
                }).then(function (response) {
                    console.log("Selected Product", response.data);
                    $scope.productDs.push(response.data);
                    $scope.currentPage = 1;
                    $scope.totalPage = 1;
                    $scope.searchByProductCodeNameValue = "";

                    $scope.sumTotalCost = 0;
                    angular.forEach($scope.productDs, function (productD, key) {
                        if (productD.cost && productD.stockLevel) {
                            $scope.sumTotalCost += productD.cost * productD.stockLevel;
                        }
                    });
                }, function (error) {
                    $("#modal-body").html(error.data);
                    $("#modal").modal("show");
                });
                $scope.$digest();
            }
        });
    };

    if (productListCodeName.length < 1) {
        $("#searchByProductCodeName").autocomplete({});
    }
//Search By Code Or Name
//    -----------------End searchByProductCodeName--------------

    // productListCode AutoCompletion
    var productListCategory = [];

    $scope.searchByProductCategory = function () {
        if ($scope.searchByProductCategoryValue) {
            productListCategory = [];
            $http({
                url: $$ContextURL + "/products/search/category",
                method: 'GET',
                params: {category: $scope.searchByProductCategoryValue}
            }).then(function (response) {
                console.log("ProductCategory=", response.data);
                angular.forEach(response.data, function (value, key) {
                    var obj = {
                        label: value.keyword,
                        value: value.keyword,
                        data: value
                    };
                    productListCategory.push(obj);
                })
            }, function (error) {
                $("#modal-body").html(error.data);
                $("#modal").modal("show");
            });
        }
        $("#searchByProductCategory").autocomplete({
            source: productListCategory,
            select: function (event, ui) {
                var item = ui.item.data;
                console.log("Selected item =", item);
                $scope.productDs = [];
                var restUrl = "";
                if ($scope.stockId) {
                    restUrl = "&stockId=" + $scope.stockId;
                }
                $http({
                    url: $$ContextURL + "/products/search/productCategory?categoryId=" + item.productId + restUrl,
                    method: 'GET',
                }).then(function (response) {
                    console.log("Products=", response.data);

                    angular.forEach(response.data, function (value, key) {
                        $scope.productDs.push(value);
                    });
                    $scope.currentPage = 1;
                    $scope.totalPage = 1;
                    $scope.searchByProductCategoryValue = "";

                    $scope.sumTotalCost = 0;
                    angular.forEach($scope.productDs, function (productD, key) {
                        if (productD.cost && productD.stockLevel) {
                            $scope.sumTotalCost += productD.cost * productD.stockLevel;
                        }
                    });
                }, function (error) {
                    $("#modal-body").html(error.data);
                    $("#modal").modal("show");
                });
                $scope.$digest();
            }
        });
    };

    if (productListCategory.length < 1) {
        $("#searchByProductCategory").autocomplete({});
    }

//    -----------------End searchByProductCategory--------------


    //    -----------------General Search--------------
    $scope.generalSearch = function () {
        $scope.productDs = [];
        var restUrl = "";
        if ($scope.stockId) {
            restUrl = "&stockId=" + $scope.stockId;
        }
        $http({
            url: $$ContextURL + "/products/search/general?keyword=" + $scope.searchByProductCodeNameValue + restUrl,
            method: 'GET',
        }).then(function (response) {
            console.log("Products=", response.data);

            angular.forEach(response.data, function (value, key) {
                $scope.productDs.push(value);
            });
            $scope.currentPage = 1;
            $scope.totalPage = 1;
            $scope.searchByProductCodeNameValue = "";

            $scope.sumTotalCost = 0;
            angular.forEach($scope.productDs, function (productD, key) {
                if (productD.cost && productD.stockLevel) {
                    $scope.sumTotalCost += productD.cost * productD.stockLevel;
                }
            });
        }, function (error) {
            $("#modal-body").html(error.data);
            $("#modal").modal("show");
        });
    }

//    -----------------End General Search--------------
});


function toggleExport(_this) {
    _this.closest("th").classList.toggle("cus-not-export");
}


$(document).ready(function () {
    $('#stockTable_new').DataTable({paging: false, info: false, ordering: false, searching: false});
});

function getStock() {
    console.log("getStock->fired");
    var stockId = $("#stock").val();

    console.debug("stockId=", stockId);
    if (stockId)
        window.location.href = $$ContextURL + "/products/stock?stockId="
            + stockId;
    else
        window.location.href = $$ContextURL + "/products/stock";

}

function getAddProduct() {
    console.log("getAddProduct->fired");
    $.get($$ContextURL + "/products/add", function (response) {
        $("#modal-body").html(response);
    });

}

function deleteProduct(_this) {
    console.log(_this);
    var id = $(_this).data("product-id");
    console.log("id=", id);

    $.when(cusConfirm()).done(function () {

        $.ajax({
            type: "POST",
            url: $$ContextURL + "/products/delete/" + id,
            contentType: "application/json",
            success: function (data) {
                if (data == "success") {
                    location.reload();
                }
            },
            error: function (request, status, error) {
                $("#modal-body").html(request.responseText);
                $("#modal").modal("show");
            }
        });

    });

}

function editProduct(_this) {
    console.log("editProduct->fired");

    var id = $(_this).data("product-id");
    console.log("id=", id);
    $.get($$ContextURL + "/products/edit/" + id, function (response) {
        console.log("response=", response);
        $("#modal-body").html(response);
        $("#modal").modal("show");
    });

}

function productStepUp(_this) {
    console.log("productStepUp->fired");
    var id = $(_this).data("product-id");

    console.log("id=", id);
    $.get($$ContextURL + "/productStepUps/add/product/" + id,
        function (response) {
            console.log("response=", response);
            $("#modal-body").html(response);
            $("#modal").modal("show");
        });
}
