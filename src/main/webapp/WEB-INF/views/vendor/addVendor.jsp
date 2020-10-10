<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="add-vendor-container">

	<sf:form id="add-vendor-form" method="POST" commandName="vendor"
		onsubmit="addVendor(event)">


		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="addVendor.fullName" /></td>
					<td><sf:input class="form-control" path="fullName" /></td>
					<td><sf:errors path="fullName" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message code="addVendor.phone" /></td>
					<td><sf:input class="form-control" path="phone" /></td>
					<td><sf:errors path="phone" /></td>
				</tr>


				<tr>
					<td class="text-left"><spring:message code="addVendor.address" /></td>
					<td><sf:input class="form-control" path="address" /></td>
					<td><sf:errors path="address" /></td>
				</tr>


				<tr>
					<td class="text-left"><spring:message code="addVendor.note" /></td>
					<td><sf:textarea class="form-control" path="note" /></td>
					<td><sf:errors path="note" /></td>
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
	function addVendor(event) {
		event.preventDefault();
		console.log("addVendor->fired");

		var data = $("#add-vendor-form").serializeObject();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/vendors/add"/>",
			headers : {
				'X-CSRF-TOKEN' : csrf
			},
			data : JSON.stringify(data),
			contentType : "application/json",
			success : function(response) {
				$("#add-vendor-container").html(response);
			},
			failure : function(errMsg) {
				alert(errMsg);
			}
		});
	}
</script>