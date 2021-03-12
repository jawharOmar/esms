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
		<spring:message code="customerInvoice.title" />
	</h4>


	<a class="btn btn-info" target="_blank"
		href="<c:url value="/customers/" />${customer.id}/printInvoice"> <i
		class="fa fa-print"></i>
	</a>

	<div class="my-2">
		<table style="width: 300px;" id="general">
			<tr class="text-danger">
				<td style=""><spring:message code="customerInvoice.totalLoan" />
				</td>
				<td><fmt:formatNumber value="${totalLoan}"
						maxFractionDigits="3" /></td>
			</tr>
		</table>
	</div>

	<hr>

	<h6>
		<spring:message code="customerInvoice.customerOrders" />
	</h6>

	<div style="overflow: auto">
		<table id="customerOrdersTable" class="display">
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
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.totalPrice}" /> <c:set var="sumTotalPrice"
								value="${sumTotalPrice+item.totalPrice}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.discount}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.totalPayment}" /> <c:set var="sumTotalPayment"
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
										<spring:message code="edit" />

									</a> <a class="dropdown-item" target="_blank"
										href="<c:url value="/customerOrders/" />${item.id}"> <spring:message
											code="print" />
									</a>
									<button class="dropdown-item"
										ng-click="deleteCustomerOrder(${item.id})">
										<spring:message code="delete" />
									</button>

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
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${sumTotalPrice}" /></td>
					<td>&nbsp;</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${sumTotalPayment}" /></td>
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

	<hr>
	<h6>
		<spring:message code="customerInvoice.customerPayments" />
	</h6>

	<div>

		<table id="customerPaymentsTable" class="display nowrap">
			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="customerPayments.totalPayment" /></th>
					<th><spring:message code="customerPayments.project" /></th>
					<th><spring:message code="customerPayments.time" /></th>
					<th><spring:message code="customerPayments.function" /></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="sumTotalPayment" value="${0}" />
				<c:forEach items="${customerPayments}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>${item.totalPayment}<c:set var="sumTotalPayment"
								value="${sumTotalPayment+item.totalPayment}" />
						</td>
						<td>${item.customerProject.name}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
								value="${item.time}" /></td>
						<td>
							<div>

								<a class="btn btn-info" target="_blank"
									href="<c:url value="/customerPayments/${item.id}/print/"/>">
									<i class="fa fa-print"></i>
								</a>
								<button class="btn btn-danger"
									onclick="deleteCustomerPayment(${item.id})">
									<i class="fa fa-times"></i>
								</button>
								<button class="btn btn-warning"
									onclick="editingCustomerPayment(${item.id},${customer.id})">
									<i class="fa fa-edit"></i>
								</button>
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr class="text-info">
					<td><spring:message code="customerPayments.total" /></td>
					<td><fmt:formatNumber value="${sumTotalPayment}"
							maxFractionDigits="3" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>

			</tbody>

			<tfoot>
				<tr>
					<th>#</th>
					<th><spring:message code="customerPayments.totalPayment" /></th>
					<th><spring:message code="customerPayments.project" /></th>
					<th><spring:message code="customerPayments.time" /></th>
					<th><spring:message code="customerPayments.function" /></th>
				</tr>
			</tfoot>

		</table>

	</div>
</div>