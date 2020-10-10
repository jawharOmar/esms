$(document)
		.ready(
				function() {

					// S-DataTable
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
							className : "btn btn-sm  btn-outline-info",
							exportOptions : {
								columns : ':not(.cus-not-export)'
							}
						}, {
							extend : "csv",
							className : "btn btn-sm btn-outline-info",
							exportOptions : {
								columns : ':not(.cus-not-export)'
							}
						}, {
							extend : "pdf",
							className : "btn btn-sm btn-outline-info",
							exportOptions : {
								columns : ':not(.cus-not-export)'
							}
						} ],
						bInfo : false,
					});
					// E-DataTable

				});

function getAddingStock() {
	console.log("getAddingStock->fired");
	$.get($$ContextURL + "/stocks/add", function(response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

function deleteStock(id) {
	console.log("id=", id);

	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/stocks/delete/" + id,
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

	});

}

function editingStock(id) {
	console.log("editingStock->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/stocks/edit/" + id,
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
