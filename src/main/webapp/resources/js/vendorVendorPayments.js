$(document).ready(function () {

    // S-DataTable
    $('#table tfoot th:not(.cus-not-search)')
        .each(
            function () {
                var title = $(this).text();
                $(this).html('<input class="form-control form-control-sm cus-inline" type="text" />');
            });

    var table = $('#table').DataTable({
        paginate: false,
        dom: 'Bfrtip',
        buttons: [{
            extend: "excel",
            className: "btn btn-sm  btn-outline-info",
            exportOptions: {
                columns: ':not(.cus-not-export)'
            }
        }, {
            extend: "csv",
            className: "btn btn-sm btn-outline-info",
            exportOptions: {
                columns: ':not(.cus-not-export)'
            }
        }, {
            extend: "pdf",
            className: "btn btn-sm btn-outline-info",
            exportOptions: {
                columns: ':not(.cus-not-export)'
            }
        }],
        bInfo: false,
    });

    // Apply the search
    table.columns().every(
        function () {
            var that = this;
            console.log("that=", that);
            console.log("that.search()=", that.search());

            $('input', this.footer()).on('keyup change',
                function () {
                    if (that.search() !== this.value) {
                        that.search(this.value).draw();
                    }
                });
        });
    // E-DataTable

});

function deleteVendorPayment(id) {
    console.log("id=", id);

    $.when(cusConfirm()).done(function () {

        $.ajax({
            type: "POST",
            url: $$ContextURL + "/vendorPayments/delete/" + id,
            contentType: "application/json",
            success: function (response) {
                $("#modal-body").html(response);
                $("#modal").modal("show");
            },
            error: function (response) {
                $("#modal-body").html(response.responseText);
                $("#modal").modal("show");
            }
        });

    });

}

function editingVendorPayment(id, vendorId) {
    console.log("editingVendorPayment->fired");
    console.log("id=", id);
    console.log("vendorId=", vendorId);

    $.ajax({
        type: "GET",
        url: $$ContextURL + "/vendorPayments/edit/" + id + "/vendor/"
            + vendorId,
        contentType: "application/json",
        success: function (response) {
            $("#modal-body").html(response);
            $("#modal").modal("show");
        },
        error: function (response) {
            $("#modal-body").html(response.responseText);
            $("#modal").modal("show");
        }
    });

}
