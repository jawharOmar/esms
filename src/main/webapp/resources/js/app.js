var app = angular.module("app", []);

app.controller("navbarCTRL", [ "$http", "$scope", "$window",
	function($http, $scope, $window) {

		$scope.changePassword={oldPassword:"",newPassword:"",confirmPassword:""};
		$scope.show=false;

		$scope.init = function() {
			console.debug("appCTRL init->fired");
		}
		
		$scope.saveChangePassword=function(){
			console.log("saveChangePassword->fired");
			
			console.log("changePassword=",$scope.changePassword);
			$http({
	            method: 'POST',
	            data: $scope.changePassword,
	            url: $$ContextURL + '/changePassword'
	        }).then(function (response) {
	            console.log(response);
	            
	            $('#password-dialog').dialog('close');
                $window.showSuccessAlert();
                $scope.changePassword={oldPassword:"",newPassword:"",confirmPassword:""};
	            
	            
	        }, function (response) {
	        	  $window.showFailedAlert();
	        });
			
			
		}
		
	} ]);




// /



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
				width : '400',
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

function showSuccessAlert(){
	console.debug("showSuccessAlert->fired");
	  $("#cus-success-alert").hide().slideDown(500).delay(2000).slideUp(500, function () {});
   
}

function showFailedAlert(){
	console.debug("showFailedAlert->fired");
	  $("#cus-failed-alert").hide().slideDown(500).delay(2000).slideUp(500, function () {});
}

$(document).ready(function(){
    $.get( $$ContextURL+"/setting/GetSettingData", function( data ) {
$("#Systemname").html(JSON.parse(data).name);
    });
});
