<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />


<script>
	var csrf = '${_csrf.token}';
</script>


<div ng-app="adminProductCategories"
	ng-controller="adminProductCategories" class="admin-doctors">

	<h2>
		<spring:message code="productCategories.title" />
	</h2>


	<div class="py-1">
		<button class="btn btn-success" ng-click="getAddingProductCategory()">
			<i class="fa fa-plus"></i>
		</button>
	</div>

	<table class="table table-bordered w-100" id="category_product">
		<thead>
			<tr>
				<td>#</td>
				<td><spring:message code="productCategories.name" /></td>
				<td><spring:message code="productCategories.function" /></td>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${productCategories}" var="item">
				<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>
						<div class="dropdown">
							<button class="btn btn-sm btn-secondary dropdown-toggle"
								type="button" id="d${item.id}" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">
								<spring:message code="function" />
							</button>
							<div class="dropdown-menu" aria-labelledby="d${item.id}">


								<a href="<c:url value="/products/stock/" />${item.id}"
									class="dropdown-item"> <spring:message
										code="productCategories.orderProducts" />
								</a>

								<button class="dropdown-item"
									ng-click="deleteProductCategory(${item.id})">
									<i class="fa fa-times"></i>
								</button>

								<button class="dropdown-item"
									ng-click="editProductCategory(${item.id})">
									<i class="fa fa-edit"></i>
								</button>



							</div>
						</div>
					</td>


				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>




