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

<div ng-app="productStepUps" ng-controller="productStepUps">


	<h2>
		<spring:message code="orderPorductStepUps.title" />
	</h2>
	<hr>

	<div>
		<form class="form-inline" action="<c:url value="/orderProductStepUps/detail"/>">
			<div class="form-group">
				<label for="from"><spring:message code="orderPreProducts.from"/></label>
				<input readonly class="form-control mx-sm-3" id="from" name="from"
					   value="<fmt:formatDate pattern = "yyyy-MM-dd"
                           value = "${from}" />"/>
			</div>
			<div class="form-group">
				<label for="to"><spring:message code="orderPreProducts.to"/></label>
				<input readonly class="form-control mx-sm-3" id="to" name="to"
					   value="<fmt:formatDate pattern = "yyyy-MM-dd"
                           value = "${to}" />"/>
			</div>

			<div class="form-group">
				<button class="btn btn-outline-info">
					<i class="fa fa-eye"></i>
				</button>
			</div>

		</form>
	</div>
	<hr>

	<div>

		<table id="productStepUpsTable" class="display">
			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="orderProductStepUpsDetail.vendor" /></th>
					<th><spring:message code="orderProductStepUpsDetail.time" /></th>
					<th><spring:message code="orderProductStepUpsDetail.code" /></th>
					<th><spring:message code="orderProductStepUpsDetail.name" /></th>
					<th><spring:message code="orderProductStepUpsDetail.quantity" /></th>
					<th><spring:message
							code="orderProductStepUpsDetail.packetQuantity" /></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="sumTotalPayment" value="${0}" />
				<c:set var="sumTotalPrice" value="${0}" />
				<c:forEach items="${orderProductStepUps}" var="item"
					varStatus="status">
					<c:forEach items="${item.productStepUps}" var="pItem"
						varStatus="pStatus">
						<tr>
							<td>${status.index+pStatus.index+1}</td>
							<td>${item.vendor.fullName}</td>
							<td>${item.orderTime}</td>
							<td>${pItem.product.code}</td>
							<td>${pItem.product.name}</td>
							<td>${pItem.quantity}</td>
							<td><c:if test="${pItem.quantity>=pItem.product.packetSize}">
									<fmt:formatNumber maxFractionDigits="3">
									${pItem.quantity/pItem.product.packetSize}
									</fmt:formatNumber>
								</c:if></td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th>#</th>
					<th><spring:message code="orderPorductStepUps.vendor" /></th>
					<th><spring:message code="orderPorductStepUps.time" /></th>
					<th>Product Code</th>
					<th>Product Name</th>
					<th>Quantity</th>
					<th>Packet Quantity</th>
				</tr>
			</tfoot>
		</table>

	</div>

</div>