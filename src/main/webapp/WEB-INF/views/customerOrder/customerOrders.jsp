<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>
<div ng-app="customerOrders" ng-controller="customerOrders">


	<h4>
		<spring:message code="customerOrders.title" />
	</h4>
	<hr>
	<div class="container">
		<div class="row">
			<div class="col">
				<form class="form-inline" action="<c:url value="/customerOrders" />">
					<div class="form-group">
						<label for="from"><spring:message
								code="customerOrders.from" /></label> <input
							class="form-control mx-sm-3" id="from" name="from"
							value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />" />
					</div>
					<div class="form-group">
						<label for="to"><spring:message code="customerOrders.to" /></label>
						<input class="form-control mx-sm-3" id="to" name="to"
							value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${to}" />" />
					</div>
					<div class="form-group">
						<button class="btn btn-outline-info">
							<i class="fa fa-eye"></i>
						</button>
					</div>

				</form>
			</div>
			<div class="col-3">
				<div class="form-group">
					<label class="checkboxLabel"
						ng-class="showLoan?'selectedLabel':'unSelectedLabel'"
						for="showLoan"><spring:message
							code="customerOrders.showLoan" /> </label> <input type="checkbox"
						id="showLoan" value="false" ng-model="showLoan"
						style="display: none;">
				</div>
			</div>

		</div>
	</div>
	<hr>

	<div>
		<table id="customerOrdersTable"
			class="table table-striped table-bordered dt-responsive nowrap">
			<thead>
				<tr>
					<td>#</td>
					<td><spring:message code="customerOrders.customerName" /></td>
					<td><spring:message code="customerOrders.receivedBy" /></td>
					<td><spring:message code="customerOrders.time" /></td>
					<td>#<spring:message code="customerOrders.projectName" /></td>
					<td><spring:message code="customerOrders.projectName" /></td>
					<td><spring:message code="customerOrders.totalPrice" /></td>
					<td><spring:message code="customerOrders.discount" /></td>
					<td><spring:message code="customerOrders.totalPayment" /></td>
					<td class="cus-not-export"><spring:message
							code="customerOrders.function" /></td>
				</tr>
			</thead>
			<tbody>

				<c:set var="sumTotalDiscount" value="${0}" />
				<c:set var="sumTotalPrice" value="${0}" />
				<c:set var="sumTotalPayment" value="${0}" />
				<c:forEach items="${customerOrders}" var="item">
					<tr>
						<td>${item.invoiceId}</td>
						<td>${item.customer.fullName}</td>
						<td>${item.receivedBy}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
								value="${item.orderTime}" /></td>
						<td>${item.customerProject.id }</td>
						<td>${item.customerProject.name }</td>
						<td><fmt:formatNumber groupingUsed="false" type="number"
								maxFractionDigits="3" value="${item.totalPrice}" /> <c:set
								var="sumTotalPrice" value="${sumTotalPrice+item.totalPrice}" /></td>
						<td><fmt:formatNumber groupingUsed="false" type="number"
								maxFractionDigits="3" value="${item.discount}" /></td>
						<c:set var="sumTotalDiscount"
							value="${sumTotalDiscount+item.discount}" />

						<td><fmt:formatNumber groupingUsed="false" type="number"
								maxFractionDigits="3" value="${item.totalPayment}" /> <c:set
								var="sumTotalPayment"
								value="${sumTotalPayment+item.totalPayment}" /></td>
						<td width="20%">

							<div class="dropdown">
								<button class="btn btn-secondary dropdown-toggle" type="button"
									id="d${item.id}" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<spring:message code="adminCustomers.function" />
								</button>
								<div class="dropdown-menu" aria-labelledby="d${item.id}">

									<a class="dropdown-item"
										href="<c:url value="/customerOrders/edit/" />${item.id}">
										<i class="fas fa-edit"></i>
									</a>

									<button class="dropdown-item"
										ng-click="deleteCustomerOrder(${item.id})">
										<i class="fa fa-times"></i>
									</button>

									<a target="_blank" class="dropdown-item"
										href="<c:url value="/customerOrders/" />${item.id}/{{showLoan}}">
										<i class="fa fa-print"></i>
									</a>


								</div>
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr class="text-info">
					<td><spring:message code="customerOrders.total" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>

					<td>&nbsp;</td>
					<td><fmt:formatNumber groupingUsed="false" type="number"
							maxFractionDigits="3" value="${sumTotalPrice}" /></td>
					<td><fmt:formatNumber groupingUsed="false" type="number"
							maxFractionDigits="3" value="${sumTotalDiscount}" /></td>
					<td><fmt:formatNumber groupingUsed="false" type="number"
							maxFractionDigits="3" value="${sumTotalPayment}" /></td>
					<td>&nbsp;</td>
				</tr>

			</tbody>

			<tfoot>
				<tr>
					<th>#</th>
					<th>CustomerName</th>
					<th>ReceivedBy</th>
					<th>Time</th>
					<th>#ProjectName</th>
					<th>ProjectName</th>
					<th>TotalPrice</th>
					<th>Discount</th>
					<th>TotalPayment</th>
					<th class="cus-not-search"></th>
				</tr>
			</tfoot>

		</table>
	</div>
</div>