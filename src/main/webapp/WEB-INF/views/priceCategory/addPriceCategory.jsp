<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>
	<sf:form id="add-price-category-form" method="POST"
		commandName="priceCategory" onsubmit="addPriceCategory(event)">
		<table class="w-100">
			<tbody>

				<tr>
					<td><spring:message code="addPriceCategory.name" /></td>
					<td><sf:input cssClass="form-control form-control-sm"
							path="name" /></td>
					<td><sf:errors path="name" /></td>
				</tr>

				<tr>
					<td><spring:message code="addPriceCategory.ratio" /></td>
					<td><sf:input type="number" step="any"
							cssClass="form-control form-control-sm" path="ratio" /></td>
					<td><sf:errors path="ratio" /></td>
				</tr>

				<tr>
					<td>
						<button class="btn btn-sm btn-success">
							<i class="fa fa-plus"></i>
						</button>
					</td>
				</tr>

			</tbody>

		</table>


	</sf:form>

</div>


<script>
	function addPriceCategory(event) {
		event.preventDefault();
		console.log("addPriceCategory->fired");

		var data = $("#add-price-category-form").serializeJSON();
		console.log("data=", data);

		$.ajax({
			type : "POST",
			url : "<c:url value="/priceCategories/add"/>",
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