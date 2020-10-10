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


<div id="order">

	<div class="cus-print-container">

		<p>
			<fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
				value="${VendorReturn.time}" />
		</p>

		<table style="width: 400px;">
			<tr>
				<td><spring:message
						code="orderPorductStepUps.vendor" /></td>
				<td>${VendorReturn.vendor.fullName}</td>
			</tr>
			<tr>
				<td><spring:message
						code="printCustomerOrderReturn.referecneInvoiceId" /></td>
				<td>${VendorReturn.id}</td>
			</tr>



			<tr>
				<td><spring:message code="printCustomerOrderReturn.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${VendorReturn.amount}" /></td>
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
				<c:forEach items="${VendorReturn.venderReturnsDetail}" var="item"
					varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.product.name}</td>
						<td>${item.product.code}</td>
						<td>${item.QTY}</td>
						<td><c:if test="${item.QTY>item.product.packetSize}">
								<fmt:formatNumber maxFractionDigits="3">
						${item.QTY/item.product.packetSize}
						</fmt:formatNumber>
							</c:if></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.amount}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.amount*item.QTY}" /></td>
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




