<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="add-customer-project-form" method="POST"
		commandName="customerProject" onsubmit="addCustomerProject(event)">
		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="addCustomerProject.name" /></td>
					<td><sf:input class="form-control" path="name" /></td>
					<td><sf:errors path="name" /></td>
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
	function addCustomerProject(event) {
		event.preventDefault();
		console.log("addCustomerProject->fired");

		var data = $("#add-customer-project-form").serializeJSON();
		console.log("data=", data);
		$
				.ajax({
					type : "POST",
					url : "<c:url value="/customerProjects/add/customer/"/>${customerId}",
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