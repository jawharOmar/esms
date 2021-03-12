<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	var csrf = '${_csrf.token}';
</script>

<div>
	<h2>
		<spring:message code="customerProjects.title" />
	</h2>
	<h3 class="text-info">${customer.fullName}</h3>

	<div class="add-new-doctor-div">
		<button class="btn btn-success"
			onclick="getAddingCustomerProject(${customer.id})">
			<i class="fa fa-plus"></i>
		</button>
	</div>

	<table class="table table-bordered">
		<thead>
			<tr>
				<td>#</td>
				<td><spring:message code="customerProjects.name" /></td>
				<td><spring:message code="customerProjects.function" /></td>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${customer.customerProjects}" var="item">
				<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>
						<div>
							<button class="btn btn-danger"
								onclick="deleteCustomerProject(${item.id})">
								<i class="fa fa-times"></i>
							</button>
							<button class="btn btn-warning"
								onclick="editingCustomerProject(${item.id})">
								<i class="fa fa-edit"></i>
							</button>
						</div>
					</td>


				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>





