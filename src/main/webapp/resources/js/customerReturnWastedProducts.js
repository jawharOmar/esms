$(document).ready()
{
	$("#from").datepicker({
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", $("#from").val());

	$("#to").datepicker({
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", $("#to").val());

	// S-DataTable
	$('#customerWastedProductTable tfoot th:not(.cus-not-search)')
			.each(
					function() {
						var title = $(this).text();
						$(this)
								.html(
										'<input class="form-control form-control-sm cus-inline" type="text" />');
					});

	var table = $('#customerWastedProductTable').DataTable({
		fixedHeader : {
			header : true,
			footer : true,
			headerOffset : $("#header").outerHeight()
		},
		dom : 'lBfrtip',
		buttons : [ {
			extend : "excel",
			messageTop : reportTitle,
			filename : reportTitle,
			className : "btn btn-sm ml-5 btn-outline-info",
			exportOptions : {
				columns : ':not(.cus-not-export)'
			}
		} ],
		bInfo : false,
		paging : true,
		lengthChange : true
	});

	// Apply the search
	table.columns().every(function() {
		var that = this;
		console.log("that=", that);
		console.log("that.search()=", that.search());

		$('input', this.footer()).on('keyup change', function() {
			if (that.search() !== this.value) {
				that.search(this.value).draw();
			}
		});
	});
	// E-DataTable

}

app.controller('customerWastedProduct', function($scope, $http) {
	console.log("customerWastedProduct->controller->fired");

	$scope.deleteCustomerWastedProduct = function(orderId) {
		console.log("deleteCustomerWastedProduct->fired");
		console.log("orderId=", orderId);

		$.when(cusConfirm()).done(
				function() {

					$http.post(
							$$ContextURL + "/customerWastedProducts/delete/"
									+ orderId).then(function(response) {
						$("#modal-body").html(response.data);
						$("#modal").modal("show");

					}, function(response) {
						console.error("error occurred");
						$("#modal-body").html(response.data);
						$("#modal").modal("show");
					});

				});

	}
});
