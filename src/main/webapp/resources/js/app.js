var $$ContextURL = js_root_applicaion.replace(/\/$/, "");

$.datepicker.setDefaults({
	changeMonth : true,
	changeYear : true
});

if (typeof $.fn.dataTable !== 'undefined'){
$.extend( true, $.fn.dataTable.defaults, {
	"order": [],
	oLanguage: {
		  "sSearch": "<span style='padding-left:5px;padding-right:5px;'>"+js_datatable_search+"</span>"
		}
} );

}

if($.ui){
	$.ui.dialog.prototype._focusTabbable = $.noop;
}

function cusConfirm() {
	var deferred=$.Deferred();
	console.log("cusConfirm->fired");
	$('<div></div>').appendTo('body').html(`
	<p>
	${js_app_confirm}
	</p>
	`).dialog({
				title : js_app_confirmWindow,
				zIndex : 10000,
				autoOpen : true,
				width : '300',
				resizable : false,
				buttons : {
					[js_app_yes] : function() {
						deferred.resolve(true);
						$(this).dialog("close");
					},
					[js_app_no] : function() {
						$(this).dialog("close");
					}
				},
				close : function(event, ui) {
					$(this).remove();
				}
			});
	return deferred.promise();
}



// Prevent Submit Form via enter
$(document).on("keypress", "form input", function(event) {
	return event.keyCode != 13;
});




function toggleMenu(){
	$("#section-right").toggleClass("ative-menu");
}


$(document).ready(function(){
	$("#app-loading-div").hide();
});

$(document).ready(function(){
    $.get( $$ContextURL+"/setting/GetSettingData", function( data ) {
$("#Systemname").html(JSON.parse(data).name);
    });
});
