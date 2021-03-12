function getAddingExpenseCategory() {
	console.log("getAddingExpense->fired");
	$.get($$ContextURL + "/expenseCategories/add", function(response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

function deleteExpenseCategory(id) {
	console.log("id=", id);

	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/expenseCategories/delete/" + id,
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

function editingExpenseCategory(id) {
	console.log("editingExpenseCategory->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/expenseCategories/edit/" + id,
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
