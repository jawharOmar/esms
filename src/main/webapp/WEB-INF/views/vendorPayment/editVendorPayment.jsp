<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="vendor-payment-form" method="POST"
		commandName="vendorPayment" onsubmit="editVendorPayment(event)">
		<sf:input type="hidden" path="id" />
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
					<td class="text-left"><spring:message
							code="addCustomerPayment.project" /></td>
				<tr>
					<td><button class="btn btn-warning">
							<i class="fa fa-edit"></i>
						</button></td>
				</tr>

			</tbody>

		</table>

	</sf:form>

</div>


<script>
	var csrf = '${_csrf.token}';
	function editVendorPayment(event) {
		event.preventDefault();
		console.log("editVendorPayment->fired");

		var data = $("#vendor-payment-form").serializeJSON();
		console.log("data=", data);
		$
				.ajax({
					type : "POST",
					url : "<c:url value="/vendorPayments/update/vendor/"/>${vendor.id}",
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