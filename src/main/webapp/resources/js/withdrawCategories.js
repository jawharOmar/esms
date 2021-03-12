function getAddingWithdrawCategory() {
    console.log("getAddingWithdrawCategory->fired");
    $.get($$ContextURL + "/withdrawCategories/add", function(response) {
        $("#modal-body").html(response);
        $("#modal").modal("show");
    });

}

function deleteWithdrawCategory(id) {
    console.log("id=", id);

    $.when(cusConfirm()).done(function() {

        $.ajax({
            type : "POST",
            url : $$ContextURL + "/withdrawCategories/delete/" + id,
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

function editingWithdrawCategory(id) {
    console.log("editingWithdrawCategory->fired");

    $.ajax({
        type : "GET",
        url : $$ContextURL + "/withdrawCategories/edit/" + id,
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
