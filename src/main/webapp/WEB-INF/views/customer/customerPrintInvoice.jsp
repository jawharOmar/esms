<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
	@media screen {
		header,footer {display: none;}
	}

	@media print {
		header,footer {display: block;}
	}
</style>
<c:if test="${pageContext.response.locale==\"ar\"||pageContext.response.locale==\"ar_SY\"}">
	<style type="text/css">
		* {
			direction: rtl;
			text-align: right !important;
		}
	</style>
</c:if>
<c:if test="${pageContext.response.locale==\"en\"}">
	<style type="text/css">
		* {
			direction: ltr;
			text-align: left !important;
		}
	</style>
</c:if>

<div id="main">
	<h4>
		<spring:message code="customerInvoice.title" />
	</h4>

	<div id="general-info">
		<table style="width: 60%">
			<tr>
				<td><spring:message code="customerInvoice.customerName" /></td>
				<td>${customer.fullName}</td>
			</tr>
			<tr>
				<td><spring:message code="customerInvoice.phone" /></td>
				<td>${customer.phone}</td>
			</tr>
			<tr class="text-danger ">
				<td><spring:message code="customerInvoice.totalLoan" /></td>
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
		<table class="table table-striped">
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
				</tr>

			</tbody>

		</table>
	</div>

	<hr>
	<h6>
		<spring:message code="customerInvoice.customerPayments" />
	</h6>

	<div>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="customerPayments.totalPayment" /></th>
					<th><spring:message code="customerPayments.project" /></th>
					<th><spring:message code="customerPayments.time" /></th>
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
					</tr>
				</c:forEach>
				<tr class="text-info">
					<td><spring:message code="customerPayments.total" /></td>
					<td><fmt:formatNumber value="${sumTotalPayment}"
							maxFractionDigits="3" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>

			</tbody>
		</table>

	</div>
</div>

</div>