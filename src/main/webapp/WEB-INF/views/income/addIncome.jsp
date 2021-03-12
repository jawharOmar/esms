<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="income-form" method="POST" commandName="income"
		onsubmit="addIncome(event)">
		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message code="addIncome.name" /></td>
					<td><sf:input class="form-control" path="name" /></td>
					<td><sf:errors path="name" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message code="addIncome.amount" /></td>
					<td><sf:input type="number" step="any" class="form-control"
							path="amount" /></td>
					<td><sf:errors path="amount" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message code="addIncome.note" /></td>
					<td><sf:textarea class="form-control" path="note" /></td>
					<td><sf:errors path="note" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message
							code="addIncome.incomeCategory" /></td>
					<td><select class="form-control" name="incomeCategory[id]">
							<option value=""><spring:message
									code="addIncome.choose" /></option>
							<c:forEach items="${incomeCategories}" var="item">
								<c:if test="${income.incomeCategory.id==item.id}">
									<option selected="selected" value="${item.id}">${item.name}</option>
								</c:if>
								<c:if test="${income.incomeCategory.id!=item.id}">
									<option value="${item.id}">${item.name}</option>
								</c:if>
							</c:forEach>

					</select></td>

					<td><sf:errors path="incomeCategory" /></td>
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
	function addIncome(event) {
		event.preventDefault();
		console.log("addIncome->fired");

		var data = $("#income-form").serializeJSON();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/incomes/add"/>",
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