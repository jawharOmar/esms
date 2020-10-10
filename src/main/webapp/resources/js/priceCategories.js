function getAddingPriceCategory() {
	console.log("getAddingPriceCategory->fired");
	$.ajax({
		type : "GET",
		url : $$ContextURL + "/priceCategories/add",
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

function deletePriceCategory(id) {
	console.log("deletePriceCategory->fired");
	$.when(cusConfirm()).done(function() {

		$.ajax({
			type : "POST",
			url : $$ContextURL + "/priceCategories/delete/" + id,
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

function getEditingPriceCategory(id) {
	console.log("getEditingPriceCategory->fired");

	$.ajax({
		type : "GET",
		url : $$ContextURL + "/priceCategories/edit/" + id,
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
