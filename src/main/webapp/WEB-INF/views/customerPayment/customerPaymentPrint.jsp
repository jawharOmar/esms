<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${pageContext.response.locale}" scope="session" />


<div id="section-to-print">
	<table class="table table-bordered">
		<tr>
			<td><spring:message code="customerPaymentPrint.customerName" /></td>
			<td>${customerPayment.customer.fullName}</td>
		</tr>

		<tr>
			<td><spring:message code="customerPaymentPrint.time" /></td>
			<td><fmt:formatDate pattern="yyyy-MM-dd / HH:mm"
					value="${customerPayment.time}" /></td>
		</tr>

		<tr>
			<td><spring:message code="customerPaymentPrint.total" /></td>
			<td><fmt:formatNumber groupingUsed="false" maxFractionDigits="3"
					value="${customerPayment.totalPayment+customerPayment.discount+totalLoan}" /></td>
		</tr>

		<tr>
			<td><spring:message code="customerPaymentPrint.totalPayment" /></td>
			<td><fmt:formatNumber groupingUsed="false" 
					maxFractionDigits="3" value="${customerPayment.totalPayment}" /></td>
		</tr>
		<tr>
			<td><spring:message code="customerPaymentPrint.discount" /></td>
			<td><c:if test="${customerPayment.discount==null}">
					0
				</c:if> <fmt:formatNumber groupingUsed="false" 
					maxFractionDigits="3" value="${customerPayment.discount}" /></td>
		</tr>
		<tr>
			<td><spring:message code="customerPaymentPrint.totalLoan" /></td>
			<td><fmt:formatNumber groupingUsed="false" 
					maxFractionDigits="3" value="${totalLoan}" /></td>
		</tr>

		<tr>
			<td><spring:message code="customerPaymentPrint.note" /></td>
			<td>${customerPayment.note}</td>
		</tr>
		<tr>
			<td><spring:message code="customerPaymentPrint.firstSignature" /></td>
			<td><spring:message code="customerPaymentPrint.secondSignature" /></td>
		</tr>


	</table>

</div>
<script type="text/javascript">
	function printing() {
		console.log("print fired");
		window.print();
	}
	printing();
</script>

