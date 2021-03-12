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
				value="${orderProductStepUp.orderTime}" />
		</p>

		<table style="width: 400px;">
			<tr>
				<td><spring:message code="printOrderProductStepUp.vendorName" /></td>
				<td>${orderProductStepUp.vendor.fullName}</td>
			</tr>
			<tr>
				<td><spring:message
						code="printOrderProductStepUp.referecneInvoiceId" /></td>
				<td>${orderProductStepUp.referecneInvoiceId}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderProductStepUp.orderId" /></td>
				<td>${orderProductStepUp.id}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderProductStepUp.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderProductStepUp.totalPrice}" /></td>
			</tr>

			<tr>
				<td><spring:message code="printOrderProductStepUp.discount" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
									  value="${orderProductStepUp.discount}" /></td>
			</tr>

			<tr>
				<td><spring:message code="printOrderProductStepUp.totalPayment" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderProductStepUp.totalPayment}" /></td>
			</tr>

			<tr>
				<td><spring:message code="printOrderProductStepUp.totalRemain" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderProductStepUp.totalPrice-orderProductStepUp.totalPayment-orderProductStepUp.discount}" /></td>
			</tr>


		</table>
		<hr>

		<table class="cus-center" style="width: 100%">

			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="printOrderProductStepUp.name" /></th>
					<th><spring:message code="printOrderProductStepUp.code" /></th>
					<th style="text-align: center;"><spring:message
							code="printOrderProductStepUp.quantity" /></th>
					<th style="text-align: center;"><spring:message
							code="printOrderProductStepUp.packetQuantity" /></th>
					<th><spring:message code="printOrderProductStepUp.unitPrice" /></th>
					<th><spring:message code="printOrderProductStepUp.price" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderProductStepUp.productStepUps}" var="item"
					varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.product.name}</td>
						<td>${item.product.code}</td>
						<td style="text-align: center;">${item.quantity}</td>
						<td style="text-align: center;"><c:if
								test="${item.quantity>item.product.packetSize}">
								<fmt:formatNumber maxFractionDigits="3">
						${item.quantity/item.product.packetSize}
						</fmt:formatNumber>
							</c:if></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.paymentAmount/item.quantity}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.paymentAmount}" /></td>
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




