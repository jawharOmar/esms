

let msgg=numofexp+" "+expirationMessage;
if(numofexp>0)
    toastr.error(msgg,expiration,{
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": bc,
    "showDuration": "300",
    "hideDuration": "300",
    "timeOut": "0",
    "extendedTimeOut": "0",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
});
toastr.options.tapToDismiss=false;

let msg=numofMin+" "+goodsAmountMessage;
if(numofMin>0)
toastr.warning(msg,goodsAmount,{
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-center",
    "preventDuplicates": false,
    "onclick": ab,
    "showDuration": "300",
    "hideDuration": "300",
    "timeOut": "0",
    "extendedTimeOut": "0",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
});


function ab(){
    $('#tablemins-table').DataTable( {
        "paging": false,
        dom: 'Bfrtip',
        buttons: [
            'print'
        ],
        "scrollY": 150,
        "scrollX": true,
        "bPaginate": false,
        "bInfo": false,
        "ajax": $$ContextURL+"/minProducts",
        "columns": [
            { "data": "amount" },
            { "data": "min" },
            { "data": "name" },
            { "data": "code" }
        ]
    } );

    $("#tablemins").dialog({width: 500,height:350});
}



function bc(){
    $('#Expiredmins-table').DataTable( {
        "paging": false,
        "scrollY": 150,
        dom: 'Bfrtip',
        "buttons": [
            'print'
        ],
        "scrollX": true,
        "bPaginate": false,
        "bInfo": false,
        "ajax": $$ContextURL+"/ExpiredProducts",
        "columns": [
            { "data": "expire" },
            { "data": "name" },
            { "data": "code" }
        ]
    } );

    $("#Expiredmins").dialog({width: 500,height:350});
}


