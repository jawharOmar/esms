window.onload = function () {

    $("#from").datepicker({
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", $("#from").val());

    $("#to").datepicker({
        dateFormat: "yy-mm-dd"
    }).datepicker("setDate", $("#to").val());


    $('#box_accounting').DataTable({
        dom: 'lBfrtip',
        buttons: [
            {
                extend: "excel",
                className: "ml-5 btn btn-sm  btn-outline-info",
                messageTop: reportTitle,
                filename: reportTitle,
                footer: true
            }
        ],
        "deferRender": true

    });
};
//Angular
app = angular.module("boxApp",[]);

app.factory('httpRequestInterceptor',function () {
    return{
        request: function (config) {
            config.headers['X-CSRF-TOKEN'] = csrf;
            return config;
        }
    }
});

app.config(function ($httpProvider) {
   $httpProvider.interceptors.push("httpRequestInterceptor");
});


app.controller('appCTRL', function ($scope, $http,$window) {

    $scope.init = function (){
      $scope.actionTypeId="";
    };



    $scope.filterByAction = function(){
        console.log("filterByAction->fired");
        console.log($scope.actionTypeId);
        if($scope.actionTypeId.length>0){
            if($scope.actionTypeId==='all'){
                $window.location.href=$$ContextURL + '/boxAccounting/main?from='+ from+'&to='+to;
            }else {
                $window.location.href=$$ContextURL + '/boxAccounting/filter?actionTypeId='+$scope.actionTypeId+'&from='+ from+'&to='+to;
            }
        }



    };
});