<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>
	<sf:form id="edit-price-category-form" method="POST"
		commandName="priceCategory" onsubmit="editPriceCategory(event)">
		<sf:input type="hidden" path="id" />
		<table class="w-100">
			<tbody>

				<tr>
					<td><spring:message code="editPriceCategory.ratio" /></td>
					<td><sf:input cssClass="form-control form-control-sm"
							path="name" /></td>
					<td><sf:errors path="name" /></td>
				</tr>
				<tr>
					<td><spring:message code="editPriceCategory.ratio" /></td>
					<td><sf:input type="number" step="any"
							cssClass="form-control form-control-sm" path="ratio" /></td>
					<td><sf:errors path="ratio" /></td>
				</tr>
				<tr>
					<td>
						<button class="btn btn-sm btn-warning">
							<i class="fa fa-edit"></i>
						</button>
					</td>
				</tr>

			</tbody>

		</table>


	</sf:form>

</div>


<script>
	function editPriceCategory(event) {
		event.preventDefault();
		console.log("editPriceCategory->fired");

		var data = $("#edit-price-category-form").serializeJSON();
		console.log("data=", data);

		$.ajax({
			type : "POST",
			url : "<c:url value="/priceCategories/update"/>",
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