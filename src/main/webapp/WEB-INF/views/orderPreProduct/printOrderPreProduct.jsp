<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="order">
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

	<div class="cus-print-container">

		<p>
			<fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
				value="${orderPreProduct.orderTime}" />
		</p>

		<table style="width: 400px;">
			<tr>
				<td><spring:message code="printOrderProductStepUp.orderId" /></td>
				<td>${orderPreProduct.id}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.companyName" /></td>
				<td>${orderPreProduct.companyName}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.companyAddress" /></td>
				<td>${orderPreProduct.companyAddress}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.phone" /></td>
				<td>${orderPreProduct.phone}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.reference" /></td>
				<td>${orderPreProduct.reference}</td>
			</tr>

			<tr>
				<td><spring:message code="printOrderPreProduct.totalPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderPreProduct.totalPrice}" /></td>
			</tr>

			<tr>
				<td><spring:message
						code="printOrderPreProduct.totalSecondPrice" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderPreProduct.totalSecondPrice}" /></td>
			</tr>

			<tr>
				<td><spring:message
						code="printOrderPreProduct.totalSecondPayment" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderPreProduct.totalSecondPayment}" /></td>
			</tr>

			<tr>
				<td><spring:message
						code="printOrderPreProduct.totalSecondRemain" /></td>
				<td><fmt:formatNumber type="number" maxFractionDigits="3"
						value="${orderPreProduct.totalSecondPrice-orderPreProduct.totalSecondPayment}" /></td>
			</tr>


		</table>
		<hr>

		<table id="products-table" class="cus-center" style="width: 100%">

			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="printOrderPreProduct.image" /></th>
					<th><spring:message code="printOrderPreProduct.pCode" /></th>
					<th><spring:message code="printOrderPreProduct.pName" /></th>
					<th><spring:message code="printOrderPreProduct.weight" /></th>
					<th><spring:message code="printOrderPreProduct.packetQuantity" /></th>
					<th><spring:message code="printOrderPreProduct.packetSize" /></th>
					<th><spring:message code="printOrderPreProduct.quantity" /></th>
					<th><spring:message code="printOrderPreProduct.price" /></th>
					<th><spring:message code="printOrderPreProduct.unitPrice" /></th>
					<th><spring:message code="printOrderPreProduct.secondPrice" /></th>
					<th><spring:message
							code="printOrderPreProduct.secondUnitPrice" /></th>
					<th><spring:message code="printOrderPreProduct.note" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderPreProduct.preProducts}" var="item"
					varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td><img class="cus-avatar"
							src="<c:url value="/attachedFiles/1/" />${item.attachedFile.id}"></td>
						<td>${item.code}</td>
						<td>${item.name}</td>
						<td>${item.weight}</td>
						<td>${item.packetQuantity}</td>
						<td>${item.packetSize}</td>
						<td>${item.quantity}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.price}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.price/item.quantity}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.secondPrice}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3"
								value="${item.secondPrice/item.quantity}" /></td>
						<td>${item.note}</td>
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




