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
				value="${wastedProduct.returnDate}" />
		</p>

		<table style="width: 400px;">
			<tr>
				<td><spring:message code="customerWastedProduct.returnId" /></td>
				<td>${wastedProduct.id}</td>
			</tr>

			<tr>
				<td><spring:message code="addCustomerOrder.customerName" /></td>
				<td>${wastedProduct.customer.fullName}</td>
			</tr>

			<tr>
				<td><spring:message code="addCustomer.address" /></td>
				<td>${wastedProduct.customer.address}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.phone" /></td>
				<td>${wastedProduct.customer.phone}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.reference" /></td>
				<td>${wastedProduct.reference}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${wastedProduct.totalPrice}" /></td>
			</tr>
		</table>
		<hr>

		<table id="products-table" class="cus-center" style="width: 100%">

			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="printOrderPreProduct.pCode" /></th>
					<th><spring:message code="printOrderPreProduct.pName" /></th>
					<th><spring:message code="printOrderPreProduct.quantity" /></th>
					<th><spring:message code="printOrderPreProduct.price" /></th>
					<th><spring:message code="printOrderPreProduct.unitPrice" /></th>
					<th><spring:message code="printOrderPreProduct.note" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${wastedProduct.customerWastedProducts}"
					var="item" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.product.code}</td>
						<td>${item.product.name}</td>
						<td>${item.quantity}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.price}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.price/item.quantity}" /></td>
						<td>${item.note}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

	</div>
	<hr>


	<%--</div>--%>

	<script type="text/javascript">
		function printing() {
			console.log("print fired");
			window.print();
		}
		printing();
	</script>