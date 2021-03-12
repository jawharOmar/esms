<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
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

<div id="section-to-print">
	<table class="table table-bordered">
		<tr>
			<td><spring:message code="vendorPaymentPrint.vendorName" /></td>
			<td>${vendorPayment.vendor.fullName}</td>
		</tr>

		<tr>
			<td><spring:message code="vendorPaymentPrint.time" /></td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
					value="${vendorPayment.time}" /></td>
		</tr>

		<tr>
			<td><spring:message code="vendorPaymentPrint.total" /></td>
			<td><fmt:formatNumber maxFractionDigits="3"
					value="${vendorPayment.totalPayment+vendorPayment.discount+totalLoan}" /></td>
		</tr>

		<tr>
			<td><spring:message code="vendorPaymentPrint.discount" /></td>
			<td>
				<c:if test="${vendorPayment.discount==null}">
					0
				</c:if>
				<fmt:formatNumber maxFractionDigits="3"
								  value="${vendorPayment.discount}" /></td>
		</tr>

		<tr>
			<td><spring:message code="vendorPaymentPrint.totalPayment" /></td>
			<td><fmt:formatNumber maxFractionDigits="3"
					value="${vendorPayment.totalPayment}" /></td>
		</tr>
		<tr>
			<td><spring:message code="vendorPaymentPrint.totalLoan" /></td>
			<td><fmt:formatNumber maxFractionDigits="3" value="${totalLoan}" /></td>
		</tr>

		<tr>
			<td><spring:message code="vendorPaymentPrint.firstSignature" /></td>
			<td><spring:message code="vendorPaymentPrint.secondSignature" /></td>
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


