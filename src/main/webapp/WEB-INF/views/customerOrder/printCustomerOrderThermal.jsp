<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>

<style type="text/css">
body {
	font-size: 12px;
}

table {
	font-size: 22px;
}

.title-header {
	text-align: center;
}

.cus-print-container {
	padding: 20px 3px;
	max-width: 500px;
	margin: 0 auto
}

.cus-center th, .cus-center td {
	text-align: center;
}

.print-btn-con {
	width: 500px;
	margin: 0 auto;
}

.print-btn-con button {
	font-size: 2em;
}

.cus-center {
	text-align: center;
}

@media print {
	@page {
		size: auto;
		margin: 0;
	}
	.cus-no-print, .cus-no-print * {
		display: none !important;
	}
}
</style>
<c:if
	test="${pageContext.response.locale==\"ar\"||pageContext.response.locale==\"ar_SY\"}">
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


</head>

<body>


	<div class="cus-print-container">

		<div style="width: 100%;border-bottom: 2px solid black;padding-bottom: 10px;" id="header">
			<h1>${setting.name}</h1>
			<h2>${setting.phone}</h2>
			<h2>${setting.address}</h2>
		</div>

		<table style="width: 400px;">

			<tr>
				<td><spring:message code="getCustomerOrder.time" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
						value="${customerOrder.orderTime}" /></td>
			</tr>

			<tr>
				<td><spring:message code="getCustomerOrder.invoiceId" /></td>
				<td>${customerOrder.invoiceId}</td>
			</tr>
			<tr>
				<td><spring:message code="getCustomerOrder.customerName" /></td>
				<td>${customerOrder.customer.fullName}</td>
			</tr>

			<c:if test="${customerOrder.receivedBy!=null}">
				<tr>
					<td><spring:message code="getCustomerOrder.receivedBy" /></td>
					<td>${customerOrder.receivedBy}</td>
				</tr>
			</c:if>

		</table>

		<hr>
		<div id="order">
			<table class="cus-center" style="width: 100%">

				<thead>
					<tr>
						<th>#</th>
						<th><spring:message code="getCustomerOrder.name" /></th>
						<th><spring:message code="getCustomerOrder.quantity" /></th>
						<th><spring:message code="getCustomerOrder.price" /></th>
						<th><spring:message code="getCustomerOrder.total" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${customerOrder.customerOrderDetails}" var="item"
						varStatus="status">
						<tr>
							<td>${status.index+1}</td>
							<td>${item.productName}</td>
							<td>${item.quantity}&nbsp;${item.product.unit}</td>
							<td><fmt:formatNumber type="number" maxFractionDigits="3"
									value="${item.price}" /></td>

							<td><fmt:formatNumber type="number" maxFractionDigits="3"
									value="${item.price*item.quantity}" /></td>

						</tr>
					</c:forEach>
				</tbody>

			</table>


		</div>


		<hr>
		<table style="width: 400px;">

			<tr>
				<td><spring:message code="getCustomerOrder.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${customerOrder.totalPrice}" /></td>
			</tr>

			<c:if test="${customerOrder.discount!=null}">
				<tr>
					<td><spring:message code="getCustomerOrder.discount" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${customerOrder.discount}" /></td>
				</tr>
			</c:if>

			<tr>
				<td><spring:message code="getCustomerOrder.totalPayment" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${customerOrder.totalPayment}" /></td>
			</tr>

			<tr>
				<td><spring:message code="getCustomerOrder.remain" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${customerOrder.totalPrice-customerOrder.discount-customerOrder.totalPayment}" /></td>
			</tr>


			<c:if test="${customerOrder.customer.totalLoan!=0}">
				<tr>
					<td><spring:message code="getCustomerOrder.totalLoan" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${customerOrder.customer.totalLoan}" /></td>
				</tr>

			</c:if>


		</table>


	</div>

	<hr>

	<script type="text/javascript">
		function printing() {
			console.log("print fired");
			window.print();
		}

		printing();
	</script>


</body>
</html>




