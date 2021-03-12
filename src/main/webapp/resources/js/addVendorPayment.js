$(document).ready()
{
    console.log("csrf=", csrf);
}

// Angular
app.controller('appCTRL', function ($scope, $http) {

    $scope.vendorPayment;

    $scope.vendors;

    $scope.init = function () {
        console.log("init->fired");
        console.log("jsonVendors=", jsonVendors);

        $scope.vendorPayment = JSON.parse(jsonVendorPayment);

        $scope.vendors = JSON.parse(jsonVendors);


        // S-Vendor AutoCompletion
        var vendorAuto = [];

        angular.forEach($scope.vendors, function (value, key) {
            var obj = {
                label: value.fullName + " " + value.phone,
                value: value.fullName,
                data: value
            }
            vendorAuto.push(obj);
        });

        $("#vendor-autoselect").autocomplete(
            {
                source: vendorAuto,
                select: function (event, ui) {
                    var item = ui.item.data;
                    console.log("selected item =", item);

                    $scope.vendorPayment.vendor = item;

                    $scope.$digest();
                }
            });

        // E-Vendor AutoCompletion
    };

    $scope.addVendorPayment = function () {
        console.log("addVendorPayment->fired");
        console.log("$scope.vendorPayment=", $scope.vendorPayment);

        $http({
            method: 'POST',
            data: $scope.vendorPayment,
            url: $$ContextURL + '/vendorPayments/add'
        }).then(function (response) {
            console.log(response);
            var outPut = `
			
			<div>${response.data.message}
			</div>
			<div>
			<a class="btn btn-info" target="_blank" href="${$$ContextURL}/vendorPayments/${response.data.etc}/print"><i class="fa fa-print"></i></a></div>
			`;
            $("#freeze").addClass("cus-freeze");
            console.log("outPut=", outPut);

            $("#modal-body").html(outPut);
            $("#modal").modal("show");

			// Reload The Page
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

});