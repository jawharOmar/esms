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
		<spring:message code="orderPreProducts.title" />
	</h2>
	<hr>

	<div>
		<form class="form-inline" action="<c:url value="/orderPreProducts"/>">
			<div class="form-group">
				<label for="from"><spring:message
						code="orderPreProducts.from"/></label>
				<input class="form-control mx-sm-3" id="from" name="from"
					   value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>
			</div>
			<div class="form-group">
				<label for="to"><spring:message
						code="orderPreProducts.to"/></label>
				<input class="form-control mx-sm-3" id="to" name="to"
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
	<br>

	<div>

		<table id="productStepUpsTable" class="display">
			<thead>
				<tr>
					<th>#</th>
					<th><spring:message code="orderPreProducts.companyName" /></th>
					<th><spring:message code="orderPreProducts.companyAddress" /></th>
					<th><spring:message code="orderPreProducts.time" /></th>
					<th><spring:message code="orderPreProducts.totalPrice" /></th>
					<th><spring:message code="orderPreProducts.totalSecondPrice" /></th>
					<th><spring:message code="orderPreProducts.totalSecondPayment" /></th>
					<th><spring:message code="orderPreProducts.totalSecondRemain" /></th>
					<th class="cus-not-export"><spring:message
							code="orderPreProducts.function" /></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="sumTotalSecondPrice" value="${0}" />
				<c:set var="sumTotalPrice" value="${0}" />
				<c:set var="sumTotalSecondPayment" value="${0}" />
				<c:set var="sumTotalSecondRemain" value="${0}" />
				<c:forEach items="${orderPreProducts}" var="item">
					<tr>
						<td>${item.id}</td>
						<td>${item.companyName}</td>
						<td>${item.companyAddress}</td>
						<td>${item.orderTime}</td>
						<td><fmt:formatNumber value="${item.totalPrice}" /> <c:set
								var="sumTotalPrice" value="${sumTotalPrice+item.totalPrice}" /></td>
						<td><fmt:formatNumber value="${item.totalSecondPrice}" /> <c:set
								var="sumTotalSecondPrice"
								value="${sumTotalSecondPrice+item.totalSecondPrice}" /></td>
						<td><fmt:formatNumber value="${item.totalSecondPayment}" />
							<c:set var="sumTotalSecondPayment"
								value="${sumTotalSecondPayment+item.totalSecondPayment}" /></td>
						<td><fmt:formatNumber
								value="${item.totalSecondPrice-item.totalSecondPayment}" /> <c:set
								var="sumTotalSecondRemain"
								value="${sumTotalSecondRemain+item.totalSecondPrice-item.totalSecondPayment}" /></td>

						<td>
							<div>

								<a class="btn btn-sm btn-outline-info" target="_blank"
									href="<c:url value="/orderPreProducts/" />${item.id}/print">
									<i class="fa fa-print"></i>
								</a>

								<button class="btn btn-danger btn-sm"
									ng-click="deleteOrderPreProduct(${item.id})">
									<i class="fa fa-times"></i>
								</button>

								<a href="<c:url value="/orderPreProducts/edit/" />${item.id}"
									class="btn btn-warning btn-sm"> <i class="fa fa-edit"></i>
								</a>

							</div>
						</td>
					</tr>


				</c:forEach>
				<tr class="text-info">
					<th><spring:message code="orderPreProducts.total" /></th>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${sumTotalPrice}" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${sumTotalSecondPrice}" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${sumTotalSecondPayment}" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3"
							value="${sumTotalSecondRemain}" /></td>
					<td>&nbsp;</td>
				</tr>

			</tbody>
		</table>

	</div>

</div>