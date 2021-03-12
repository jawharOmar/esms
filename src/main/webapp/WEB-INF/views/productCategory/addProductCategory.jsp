<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="add-product-category-container">
	<sf:form id="add-product-category-form" method="POST"
		commandName="productCategory" onsubmit="addProductCategory(event)">


		<table>
			<tbody>

				<tr>
					<td class="text-left"><spring:message
							code="addProductCategory.name" /></td>
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
	function addProductCategory(event) {
		event.preventDefault();
		console.log("addProductCategory->fired");

		var data = $("#add-product-category-form").serializeObject();
		console.log("data=", data);
		$.ajax({
			type : "POST",
			url : "<c:url value="/productCategories/add"/>",
			data : JSON.stringify(data),
			headers : {
				'X-CSRF-TOKEN' : csrf
			},
			contentType : "application/json",
			success : function(response) {
				$("#add-product-category-container").html(response);
			},
			error : function(response) {
				console.log(response);
				$("#add-product-category-container")
						.html(response.responseText);
			}
		});
	}
</script>