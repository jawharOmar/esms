<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="expense-form" method="POST" commandName="expense"
		onsubmit="updateExpense(event)">

		<sf:input type="hidden" path="id" />


		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message code="addExpense.name" /></td>
					<td><sf:input class="form-control" path="name" /></td>
					<td><sf:errors path="name" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message code="addExpense.amount" /></td>
					<td><sf:input type="number" step="any" class="form-control"
							path="amount" /></td>
					<td><sf:errors path="amount" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message code="addExpense.note" /></td>
					<td><sf:textarea class="form-control" path="note" /></td>
					<td><sf:errors path="note" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message
							code="addExpense.expenseCategory" /></td>
					<td><select class="form-control" name="expenseCategory[id]">
							<option value=""><spring:message
									code="addExpense.choose" /></option>
							<c:forEach items="${expenseCategories}" var="item">
								<c:if test="${expense.expenseCategory.id==item.id}">
									<option selected="selected" value="${item.id}">${item.name}</option>
								</c:if>
								<c:if test="${expense.expenseCategory.id!=item.id}">
										<option value="${item.id}">${item.name}</option>
								</c:if>
							</c:forEach>

					</select></td>

					<td><sf:errors path="expenseCategory" /></td>
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
	function updateExpense(event) {
		event.preventDefault();
		console.log("updateExpense->fired");

		var data = $("#expense-form").serializeJSON();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/expenses/update"/>",
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