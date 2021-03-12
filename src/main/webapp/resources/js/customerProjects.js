function getAddingCustomerProject(id) {
	console.log("getAddingCustomerProject->fired");
	$.get($$ContextURL + "/customerProjects/add/customer/" + id, function(
			response) {
		$("#modal-body").html(response);
		$("#modal").modal("show");
	});

}

function deleteCustomerProject(id) {
	console.log("id=", id);

	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/customerProjects/delete/" + id,
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

function editingCustomerProject(id) {
	console.log("editingCustomerProject->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/customerProjects/edit/" + id,
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
