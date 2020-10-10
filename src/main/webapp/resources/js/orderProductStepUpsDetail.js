$(document).ready()
{
	$("#from").datepicker({
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", $("#from").val());

	$("#to").datepicker({
		dateFormat : "yy-mm-dd"
	}).datepicker("setDate", $("#to").val());

	// S-DataTable
	$('#productStepUpsTable tfoot th:not(.cus-not-search)')
			.each(
					function() {
						var title = $(this).text();
						$(this)
								.html(
										'<input class="form-control fomt-control-sm cus-inline" type="text" />');
					});

	var table = $('#productStepUpsTable').DataTable({
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

appAddCusotmerOrder = angular.module("productStepUps", []);

appAddCusotmerOrder.factory('httpRequestInterceptor', function() {
	return {
		request : function(config) {
			config.headers['X-CSRF-TOKEN'] = csrf;
			return config;
		}
	};
});
appAddCusotmerOrder.config(function($httpProvider) {
	$httpProvider.interceptors.push('httpRequestInterceptor');
});

appAddCusotmerOrder.controller('productStepUps', function($scope, $http) {
	console.log("productStepUps->controller->fired");

	$scope.deleteOrderProductStepUp = function(orderId) {
		console.log("deleteOrderProductStepUp->fired");
		console.log("orderId=", orderId);

		$.when(cusConfirm()).done(
				function() {

					$http.post(
							$$ContextURL + "/orderProductStepUps/delete/"
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
