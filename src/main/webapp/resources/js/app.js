$(document).ready(function() {
	//when a group is shown, save it as the active accordion group
	$("#accordion").on('shown.bs.collapse', function() {
		var active = $("#accordion .in").attr('id');
		$.cookie('activeAccordionGroup', active);
		//  alert(active);
	});
	$("#accordion").on('hidden.bs.collapse', function() {
		$.removeCookie('activeAccordionGroup');
	});
	var last = $.cookie('activeAccordionGroup');
	if (last != null) {
		//remove default collapse settings
		$("#accordion .panel-collapse").removeClass('in');
		//show the account_last visible group
		$("#" + last).addClass("in");
	}
});

var app = angular.module("app", []);

angular.module('app').directive('ngEnter', function() {
	return function(scope, element, attrs) {
		element.bind("keydown keypress", function(event) {
			if (event.which === 13) {
				scope.$apply(function() {
					scope.$eval(attrs.ngEnter, { 'event': event });
				});

				event.preventDefault();
			}
		});
	};
});

app.controller("appCTRL", ["$http", "$scope", "$window",
	function($http, $scope, $window) {

		$scope.changePassword = { oldPassword: "", newPassword: "", confirmPassword: "" };
		$scope.show = false;

		$scope.init = function() {
			console.debug("appCTRL init->fired");
		}

		$scope.saveChangePassword = function() {
			console.log("saveChangePassword->fired");

			console.log("changePassword=", $scope.changePassword);
			$http({
				method: 'POST',
				data: $scope.changePassword,
				url: $$ContextURL + '/changePassword'
			}).then(function(response) {
				console.log(response);
				$window.showSuccessAlert();
				$scope.changePassword = { oldPassword: "", newPassword: "", confirmPassword: "" };
				$("#passwordModal").modal("hide");
			}, function(response) {
				$window.showFailedAlert();
			});


		}

	}]);




// /



var $$ContextURL = js_root_applicaion.replace(/\/$/, "");


$.datepicker.setDefaults({
	changeMonth: true,
	changeYear: true
});

if (typeof $.fn.dataTable !== 'undefined') {
	$.extend(true, $.fn.dataTable.defaults, {
		"order": [],
		language: {
			paginate: {
				next: js_datatable_next,
				previous: js_datatable_previous// or '←'
			}
		},
		oLanguage: {
			"sSearch": "<span style='padding-left:5px;padding-right:5px;'>" + js_datatable_search + "</span>"
		}
	});

}

if ($.ui) {
	$.ui.dialog.prototype._focusTabbable = $.noop;
}

function cusConfirm() {
	var deferred = $.Deferred();
	console.log("cusConfirm->fired");
	$('<div></div>').appendTo('body').html(`
	<p>
	${js_app_confirm}
	</p>
	`).dialog({
		title: js_app_confirmWindow,
		zIndex: 10000,
		autoOpen: true,
		width: '400',
		resizable: false,
		buttons: {
			[js_app_yes]: function() {
				deferred.resolve(true);
				$(this).dialog("close");
			},
			[js_app_no]: function() {
				$(this).dialog("close");
			}
		},
		close: function(event, ui) {
			$(this).remove();
		}
	});
	return deferred.promise();
}



// Prevent Submit Form via enter
$(document).on("keypress", "form input", function(event) {
	return event.keyCode != 13;
});




function toggleMenu() {
	$("#section-right").toggleClass("ative-menu");
}


$(document).ready(function() {
	$("#app-loading-div").hide();

	$('[data-toggle="tooltip"]').tooltip();

	$("#sidebarToggle").click(function() {
		console.log("sidebarToggle->fired");
		if (localStorage.getItem("toggle") === "true") {
			localStorage.setItem("toggle", "false");
		}
		else {
			localStorage.setItem("toggle", "true");
		}
	});

	if (!localStorage.getItem("toggle")) {
		// Check if theres anything in localstorage already
		localStorage.setItem("toggle", "false");
	}

	if (localStorage.getItem("toggle") === "true") {
		$("body").addClass("sidebar-toggled");
		$(".sidebar").addClass("toggled");
		$("#navbar-div").addClass("toggled");
		$("#content-wrapper").addClass("toggled");
		if ($(".sidebar").hasClass("toggled")) {
			$('.sidebar .collapse').collapse('hide');
		};
	}
	else {
		$("body").removeClass("sidebar-toggled");
		$(".sidebar").removeClass("toggled");
		$("#navbar-div").removeClass("toggled");
		$("#content-wrapper").removeClass("toggled");
		if ($(".sidebar").removeClass("toggled")) {
			$('.sidebar .collapse').collapse('show');
		};
	}

});

function showSuccessAlert() {
	console.debug("showSuccessAlert->fired");
	$("#cus-success-alert").hide().slideDown(500).delay(2000).slideUp(500, function() { });
}

function showFailedAlert() {
	console.debug("showFailedAlert->fired");
	$("#cus-failed-alert").hide().slideDown(500).delay(2000).slideUp(500, function() { });
}

$("#sidebarToggle, #sidebarToggleTop").on('click', function(e) {
	$("body").toggleClass("sidebar-toggled");
	$(".sidebar").toggleClass("toggled");
	$("#content-wrapper").toggleClass("toggled");
	$("#navbar-div").toggleClass("toggled");
	if ($(".sidebar").hasClass("toggled")) {
		$('.sidebar .collapse').collapse('hide');
	};
});

