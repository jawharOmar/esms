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
			.each(function() {	var title = $(this).text();
						$(this)
								.html(
										'<input class="form-control form-control-sm cus-inline" type="text" />');
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
		bInfo : false,
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

function editingIncome(id) {
	console.log("editingIncome->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/incomes/edit/" + id,
		contentType : "application/json",
		success : function(response) {
			$("#modal-body").html(response);
			$("#modal").modal("show");
		},
		error : function(response) {
			$("#modal-body").html(response.responseText);
			$("#modal").modal("show");
		}
	});

}

function editingExpense(id) {
	console.log("editingExpense->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/expenses/edit/" + id,
		contentType : "application/json",
		success : function(response) {
			$("#modal-body").html(response);
			$("#modal").modal("show");
		},
		error : function(response) {
			$("#modal-body").html(response.responseText);
			$("#modal").modal("show");
		}
	});

}