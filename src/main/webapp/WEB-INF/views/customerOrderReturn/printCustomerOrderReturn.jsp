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
<div id="order">

	<div class="cus-print-container">

		<p>
			<fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
				value="${customerOrderReturn.time}" />
		</p>

		<table style="width: 400px;">
			<tr>
				<td><spring:message
						code="printCustomerOrderReturn.customerName" /></td>
				<td>${customerOrderReturn.customer.fullName}</td>
			</tr>
			<tr>
				<td><spring:message
						code="printCustomerOrderReturn.referecneInvoiceId" /></td>
				<td>${customerOrderReturn.referecneInvoiceId}</td>
			</tr>

			<tr>
				<td><spring:message code="printCustomerOrderReturn.orderId" /></td>
				<td>${customerOrderReturn.id}</td>
			</tr>

			<tr>
				<td><spring:message code="printCustomerOrderReturn.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${customerOrderReturn.totalPrice}" /></td>
			</tr>

			<tr>
				<td><spring:message code="printCustomerOrderReturn.totalLoan" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${totalLoan}" /></td>
			</tr>

		</table>
		<hr>

		<table class="cus-center" style="width: 100%">

			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="printCustomerOrderReturn.name" /></th>
					<th><spring:message code="printCustomerOrderReturn.code" /></th>
					<th><spring:message code="printCustomerOrderReturn.quantity" /></th>
					<th><spring:message
							code="printCustomerOrderReturn.packetQuantity" /></th>
					<th><spring:message code="printCustomerOrderReturn.unitPrice" /></th>
					<th><spring:message code="printCustomerOrderReturn.price" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${customerOrderReturn.productStepUps}" var="item"
					varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.product.name}</td>
						<td>${item.product.code}</td>
						<td>${item.quantity}</td>
						<td><c:if test="${item.quantity>item.product.packetSize}">
								<fmt:formatNumber maxFractionDigits="3">
						${item.quantity/item.product.packetSize}
						</fmt:formatNumber>
							</c:if></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.returnPrice/item.quantity}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.returnPrice}" /></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

	</div>
	<hr>


</div>

<script type="text/javascript">
	function printing() {
		console.log("print fired");
		window.print();
	}
	printing();
</script>




