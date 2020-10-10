<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="edit-vendor-container">
	<sf:form id="edit-vendor-form" method="POST" commandName="vendor"
		onsubmit="addVendor(event)">

		<sf:input path="id" type="hidden" />

		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="editVendor.fullName" /></td>
					<td><sf:input class="form-control" path="fullName" /></td>
					<td><sf:errors path="fullName" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message code="editVendor.phone" /></td>
					<td><sf:input class="form-control" path="phone" /></td>
					<td><sf:errors path="phone" /></td>
				</tr>


				<tr>
					<td class="text-left"><spring:message
							code="editVendor.address" /></td>
					<td><sf:input class="form-control" path="address" /></td>
					<td><sf:errors path="address" /></td>
				</tr>


				<tr>
					<td class="text-left"><spring:message code="editVendor.note" /></td>
					<td><sf:textarea class="form-control" path="note" /></td>
					<td><sf:errors path="note" /></td>
				</tr>


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
	function addVendor(event) {
		event.preventDefault();
		console.log("editVendor->fired");

		var data = $("#edit-vendor-form").serializeObject();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/vendors/update"/>",
			data : JSON.stringify(data),
			contentType : "application/json",
			success : function(response) {
				$("#edit-vendor-container").html(response);
			},
			failure : function(errMsg) {
				alert(errMsg);
			}
		});
	}
</script>