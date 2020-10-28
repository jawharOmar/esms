$(document).ready()
{
	$("#from").datepicker({
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", $("#from").val());

	$("#to").datepicker({
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", $("#to").val());

	// S-DataTable
	$('#table tfoot th:not(.cus-not-search)')
			.each(
					function() {
						var title = $(this).text();
						$(this)
								.html(
										'<input class="form-control fomt-control-sm cus-inline" type="text" />');
					});

	var table = $('#table').DataTable({
		fixedHeader : {
			header : true,
			footer : true,
			headerOffset : $("#header").outerHeight()
		},
		paginate : false,
		dom : 'Bfrtip',
		buttons : [ {
			extend : "excel",
			messageTop : reportTitle,
			filename : reportTitle,
			className : "btn btn-sm  btn-outline-info",
			exportOptions : {
				columns : ':not(.cus-not-export)'
			}
		}, {
			extend : "csv",
			messageTop : reportTitle,
			filename : reportTitle,
			className : "btn btn-sm btn-outline-info",
			exportOptions : {
				columns : ':not(.cus-not-export)'
			}
		}, {
			extend : "pdf",
			messageTop : reportTitle,
			filename : reportTitle,
			className : "btn btn-sm btn-outline-info",
			exportOptions : {
				columns : ':not(.cus-not-export)'
			}
		} ],
		bInfo : false
	});
	// E-DataTable

}

app.controller('appCTRL', function($scope, $http) {

	$scope.deleteCustomerOrderReturn = function(orderId) {
		console.log("deleteCustomerOrderReturn->fired");
		console.log("orderId=", orderId);

		$.when(cusConfirm()).done(
				function() {

					$http.post(
							$$ContextURL + "/customerOrderReturns/delete/"
									+ orderId).then(function(response) {
						$("#modal-body").html(response.data);
						$("#modal").modal("show");

					}, function(response) {
						console.error("error occured");
						$("#modal-body").html(response.data);
						$("#modal").modal("show");
					});

				});

	}
});
