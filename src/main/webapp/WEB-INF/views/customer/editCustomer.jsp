<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<sf:form id="edit-customer-form" method="POST" commandName="customer"
		onsubmit="editCustomer(event)">

		<sf:input path="id" type="hidden" />

		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="editCustomer.fullName" /></td>
					<td><sf:input class="form-control" path="fullName" /></td>
					<td><sf:errors path="fullName" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message
							code="editCustomer.phone" /></td>
					<td><sf:input class="form-control" path="phone" /></td>
					<td><sf:errors path="phone" /></td>
				</tr>


				<tr>
					<td class="text-left"><spring:message
							code="editCustomer.address" /></td>
					<td><sf:input class="form-control" path="address" /></td>
					<td><sf:errors path="address" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message
							code="addCustomer.priceCategory" /></td>
					<td><select class="form-control" name="priceCategory[id]">
							<option value=""><spring:message
									code="editCustomer.choose" /></option>
							<c:forEach items="${priceCategories}" var="item">
								<c:if test="${customer.priceCategory.id==item.id}">
									<option selected="selected" value="${item.id}">${item.name}</option>
								</c:if>
								<c:if test="${customer.priceCategory.id!=item.id}">
									<option value="${item.id}">${item.name}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td><sf:errors path="priceCategory.id" /></td>
				</tr>


				<tr>
					<td class="text-left"><spring:message code="addCustomer.note" /></td>
					<td><sf:textarea class="form-control" path="note" /></td>
					<td><sf:errors path="note" /></td>
				</tr>

				<tr>
					<td class="text-left"><spring:message
							code="editCustomer.limit" /></td>
					<td><sf:input type="text" class="form-control" path="limit" /></td>
					<td><sf:errors path="limit" /></td>
				</tr>

				<tr>
					<td><button class="btn btn-warning">
							<i class="fa fa-plus"></i>
						</button></td>
				</tr>

			</tbody>

		</table>

	</sf:form>

</div>


<script>
	var csrf = '${_csrf.token}';
	function editCustomer(event) {
		event.preventDefault();
		console.log("editCustomer->fired");

		var data = $("#edit-customer-form").serializeJSON();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/customers/update"/>",
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