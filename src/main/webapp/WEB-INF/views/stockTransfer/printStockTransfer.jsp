<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="order">

	<div class="cus-print-container">

		<p>
			<fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
				value="${stockTransfer.time}" />
		</p>

		<table style="width: 400px;">
			<tr>
				<td><spring:message code="printStockTransfer.fromStock" /></td>
				<td>${stockTransfer.from.name}</td>
			</tr>
			<tr>
				<td><spring:message code="printStockTransfer.toStock" /></td>
				<td>${stockTransfer.to.name}</td>
			</tr>
		</table>
		<hr>

		<table class="cus-center" style="width: 100%">

			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="printStockTransfer.name" /></th>
					<th><spring:message code="printStockTransfer.code" /></th>
					<th><spring:message code="printStockTransfer.amount" /></th>
					<th><spring:message code="printStockTransfer.packetQuantity" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${stockTransfer.stockTransferDetails}" var="item"
					varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.product.name}</td>
						<td>${item.product.code}</td>
						<td><fmt:formatNumber value="${item.amount}"
								maxFractionDigits="3" /></td>
						<td><c:if test="${item.amount>item.product.packetSize}">
								<fmt:formatNumber maxFractionDigits="3">
						${item.amount/item.product.packetSize}
						</fmt:formatNumber>
							</c:if></td>
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




