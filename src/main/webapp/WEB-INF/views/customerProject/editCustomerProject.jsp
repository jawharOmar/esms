<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="edit-customer-project-form" method="POST"
		commandName="customerProject" onsubmit="editCustomerProject(event)">
		<sf:input type="hidden" path="id" />
		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="editCustomerProject.name" /></td>
					<td><sf:input class="form-control" path="name" /></td>
					<td><sf:errors path="name" /></td>
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
	var csrf = '${_csrf.token}';
	function editCustomerProject(event) {
		event.preventDefault();
		console.log("editCustomerProject->fired");

		var data = $("#edit-customer-project-form").serializeJSON();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/customerProjects/update"/>",
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