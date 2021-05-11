<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="cus-print-container">



	<table style="width: 400px;">

		<tr>
			<td><spring:message code="getCustomerOrder.time" /></td>
			<fmt:setLocale value="en_US" scope="session" />
			<td><fmt:formatDate pattern="yyyy-MM-dd / hh:mm"
					value="${customerOrder.orderTime}" /></td>
		</tr>

		<fmt:setLocale value="${pageContext.response.locale}" scope="session" />

		<tr>
			<td><spring:message code="getCustomerOrder.invoiceId" /></td>
			<td style="color: red">${customerOrder.invoiceId}</td>
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
					<th><spring:message code="getCustomerOrder.code" /></th>
					<th style="text-align: center;"><spring:message
							code="getCustomerOrder.quantity" /></th>
					<th><spring:message code="getCustomerOrder.price" /></th>
					<th><spring:message code="getCustomerOrder.total" /></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="itemqty" value="0" />

				<c:forEach items="${customerOrder.customerOrderDetails}" var="item"
					varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${item.productName}</td>
						<td>${item.productCode}</td>
						<fmt:setLocale value="ar" scope="session" />
						<td style="text-align: center;">${user.locale}<fmt:formatNumber
								type="number" maxFractionDigits="3" groupingUsed="false"
								value="${item.quantity}" /> <c:set var="itemqty"
								value="${itemqty+item.quantity}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								groupingUsed="false" value="${item.price}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								groupingUsed="false" value="${item.price*item.quantity}" /></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td style="text-align: center" colspan="3"><spring:message
							code="getCustomerOrder.total" /></td>

					<td style="text-align: center"><fmt:formatNumber type="number"
							maxFractionDigits="3" value="${itemqty}" /></td>
					<td></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							groupingUsed="false" value="${customerOrder.totalPrice}" /></td>
				</tr>
			</tfoot>
		</table>


	</div>


	<hr>
	<table id="footerOrder" border="1" cellpadding="5"
		style="font-weight: bolder; width: 400px;">

		<tr>
			<td><spring:message code="getCustomerOrder.totalPrice" /></td>
			<td><fmt:formatNumber type="number" maxFractionDigits="3"
					groupingUsed="false" value="${customerOrder.totalPrice}" /></td>
		</tr>

		<c:if test="${customerOrder.discount!=null}">
		</c:if>

		<tr>
			<td><spring:message code="getCustomerOrder.totalPayment" /></td>
			<td><fmt:formatNumber type="number" groupingUsed='false'
					maxFractionDigits="3" value="${customerOrder.totalPayment}" /></td>
		</tr>

		<tr>
			<td><spring:message code="getCustomerOrder.remain" /></td>
			<td><fmt:formatNumber type="number" maxFractionDigits="3"
					groupingUsed="false"
					value="${customerOrder.totalPrice-customerOrder.discount-customerOrder.totalPayment}" /></td>
		</tr>

		<c:if test="${showLoan}">
			<tr>
				<td><spring:message code="getCustomerOrder.oldLoan" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						groupingUsed="false"
						value="${customerOrder.customer.totalLoan-(customerOrder.totalPrice-customerOrder.discount-customerOrder.totalPayment)}" /></td>
			</tr>
			<tr>
				<td><spring:message code="getCustomerOrder.totalLoan" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						groupingUsed="false" value="${customerOrder.customer.totalLoan}" /></td>
			</tr>
		</c:if>

	</table>


</div>

<fmt:setLocale value="${pageContext.response.locale}" scope="session" />

<script type="text/javascript">
	function printing() {
		console.log("print fired");
		window.print();
	}

	printing();
</script>


