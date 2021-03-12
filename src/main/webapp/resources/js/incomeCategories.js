function getAddingIncomeCategory() {
	console.log("getAddingIncomeCategory->fired");
	$.get($$ContextURL + "/incomeCategories/add", function(response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

function deleteIncomeCategory(id) {
	console.log("id=", id);

	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/incomeCategories/delete/" + id,
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

function editingIncomeCategory(id) {
	console.log("editingIncomeCategory->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/incomeCategories/edit/" + id,
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
