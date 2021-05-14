<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<script>
	var csrf = '${_csrf.token}';
</script>


<div ng-app="adminVendors" ng-controller="adminVendors"
	class="admin-doctors">

	<h2>
		<spring:message code="adminVendor.title" />
	</h2>

	<div class="add-new-doctor-div">
		<button class="btn btn-success" ng-click="getAddingVendor()">
			<i class="fa fa-plus"></i>
		</button>
	</div>

	<table class="table table-striped table-bordered dt-responsive nowrap"
		id="vendorTable">
		<thead>
			<tr>
				<td><spring:message code="adminVendor.fullName" /></td>
				<td><spring:message code="adminVendor.phone" /></td>
				<td><spring:message code="adminVendor.address" /></td>
				<td><spring:message code="adminVendor.totalLoan" /></td>
				<td><spring:message code="adminVendor.note" /></td>
				<td><spring:message code="adminVendor.function" /></td>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${vendors}" var="item">
				<tr>
					<td>${item.fullName}</td>
					<td>${item.phone}</td>
					<td>${item.address}</td>
					<td><fmt:formatNumber groupingUsed="false" type="number"
							maxFractionDigits="3" value="${item.totalLoan}" /></td>
					<td>${item.note}</td>
					<td>



						<div class="dropdown">
							<button class="btn btn-secondary dropdown-toggle" type="button"
								id="d${item.id}" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								<spring:message code="function" />
							</button>
							<div class="dropdown-menu" aria-labelledby="d${item.id}">

								<a class="dropdown-item" target="_blank"
									href="<c:url value="/vendorPayments/vendor/" />${item.id}">
									<spring:message code="adminVendor.vendorPayments" />
								</a> <a class="dropdown-item" target="_blank"
									href="<c:url value="/orderProductStepUps/vendor/" />${item.id}">
									<spring:message code="adminVendor.vendorOrders" />
								</a>

								<button class="dropdown-item"
									ng-click="deleteVendor(${item.id})">
									<i class="fas fa-times"></i>
								</button>

								<button class="dropdown-item" ng-click="editVendor(${item.id})">
									<i class="fa fa-edit"></i>
								</button>
							</div>
						</div>


						<div></div>
					</td>


				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>


<script>
	window.addEventListener('load', function() {
		$('#vendorTable').DataTable();
	});
</script>






