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

	<table class="table table-bordered" id="vendorTable">
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
					<td>${item.totalLoan}</td>
					<td>${item.note}</td>
					<td>
						<div>
							<a class="btn btn-info" target="_blank"
								href="<c:url value="/vendorPayments/vendor/" />${item.id}">
								<i class="fa fa-money"></i>
							</a> <a class="btn btn-info" target="_blank"
								href="<c:url value="/orderProductStepUps/vendor/" />${item.id}">
								<i class="fa fa-arrow-up"></i>
							</a>

							<button class="btn btn-danger"
								ng-click="deleteVendor(${item.id})">
								<i class="fa fa-times"></i>
							</button>
							<button class="btn btn-warning" ng-click="editVendor(${item.id})">
								<i class="fa fa-edit"></i>
							</button>
						</div>
					</td>


				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>


<script>

	window.addEventListener('load', function () {
				$('#vendorTable').DataTable();
			}
	);
</script>






