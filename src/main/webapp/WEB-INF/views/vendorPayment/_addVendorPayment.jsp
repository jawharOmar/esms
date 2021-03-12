<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="vendor-payment-form" method="POST"
		commandName="vendorPayment" onsubmit="addVendorPayment(event)">
		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="addVendorPayment.totalPayment" /></td>
					<td><sf:input type="number" step="any" class="form-control"
							path="totalPayment" /></td>
					<td><sf:errors path="totalPayment" /></td>
				</tr>
				<tr>
					<td><button class="btn btn-success">
							<i class="fa fa-plus"></i>
						</button></td>
				</tr>

			</tbody>

		</table>

	</sf:form>

</div>


<script>
	var csrf = '${_csrf.token}';
	function addVendorPayment(event) {
		event.preventDefault();
		console.log("addVendorPayment->fired");

		var data = $("#vendor-payment-form").serializeJSON();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/vendorPayments/add/vendor/"/>${vendor.id}",
			headers : {
				'X-CSRF-TOKEN' : csrf
			},
			data : JSON.stringify(data),
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
</script>