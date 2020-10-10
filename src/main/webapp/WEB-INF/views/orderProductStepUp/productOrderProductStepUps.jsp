<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	var csrf = '${_csrf.token}';
	console.log("csrf=" + csrf);
	var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>

<div>


	<h2>
		<spring:message code="productOrderProductStepUps.title" />
	</h2>

	<div>

		<table id="table" class="display">
			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="productOrderProductStepUps.byType" /></th>
					<th><spring:message code="productOrderProductStepUps.name" /></th>
					<th><spring:message code="productOrderProductStepUps.time" /></th>
					<th><spring:message
							code="productOrderProductStepUps.reference" /></th>
					<th><spring:message code="productOrderProductStepUps.amount" /></th>
					<th class="cus-not-export"><spring:message
							code="productOrderProductStepUps.function" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderProductStepUps}" var="item">
					<tr>
						<td>${item.id}</td>
						<td><spring:message code="productOrderProductStepUps.vendor" /></td>
						<td>${item.vendor.fullName}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
								value="${item.orderTime}" /></td>
						<td>${item.referecneInvoiceId}</td>
						<td><fmt:formatNumber maxFractionDigits="3"
								value="${item.totalPayment}" /></td>
						<td>
							<div>
								<a href="<c:url value="/orderProductStepUps/edit/" />${item.id}"
									class="btn btn-warning btn-sm"> <i class="fa fa-edit"></i>
								</a>
							</div>
						</td>
					</tr>
				</c:forEach>

				<c:forEach items="${customerOrderReturns}" var="item">
					<tr>
						<td>${item.id}</td>
						<td><spring:message
								code="productOrderProductStepUps.customer" /></td>
						<td>${item.customer.fullName}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
								value="${item.time}" /></td>
						<td>${item.referecneInvoiceId}</td>
						<td><fmt:formatNumber maxFractionDigits="3"
								value="${item.totalPrice}" /></td>
						<td>
							<div>
								<a
									href="<c:url value="/customerOrderReturns/edit/" />${item.id}"
									class="btn btn-warning btn-sm"> <i class="fa fa-edit"></i>
								</a>

							</div>
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>

	</div>

</div>