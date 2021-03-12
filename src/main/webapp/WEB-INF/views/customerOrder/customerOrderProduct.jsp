<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>
<div ng-app="customerOrders" ng-controller="customerOrders">

	<h4>
		<spring:message code="customerOrderProduct.title" />
	</h4>
	<hr>

	<div style="overflow: auto">
		<table id="customerOrdersTable" class="display">
			<thead>
				<tr>
					<td>#</td>
					<td><spring:message code="customerOrderProduct.customerName" /></td>
					<td><spring:message code="customerOrderProduct.time" /></td>
					<td>#<spring:message code="customerOrderProduct.productCode" /></td>
					<td><spring:message code="customerOrderProduct.productName" /></td>
					<td><spring:message code="customerOrderProduct.quantity" /></td>
					<td><spring:message code="customerOrderProduct.price" /></td>
					<td class="cus-not-export"><spring:message
							code="customerOrderProduct.function" /></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${customerOrderDetails}" var="item">
					<tr>
						<td>${item.customerOrder.invoiceId}</td>
						<td>${item.customerOrder.customer.fullName}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
								value="${item.customerOrder.orderTime}" /></td>

						<td>${item.product.code}</td>
						<td>${item.product.name}</td>
						<td>${item.quantity}</td>
						<td>${item.price}</td>

						<td width="20%"><a class="btn btn-sm btn-outline-warning"
							href="<c:url value="/customerOrders/edit/" />${item.customerOrder.id}">
								<i class="fa fa-edit"></i>
						</a>
							<button class="btn btn-sm btn-outline-danger"
								ng-click="deleteCustomerOrder(${item.customerOrder.id})">
								<i class="fa fa-times"></i>
							</button> <a target="_blank" class="btn btn-sm btn-outline-info"
							href="<c:url value="/customerOrders/" />${item.customerOrder.id}"><i
								class="fa fa-print"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>

			<tfoot>
				<tr>
					<th>#</th>
					<th>CustomerName</th>
					<th>Time</th>
					<th>#ProjectName</th>
					<th>ProjectName</th>
					<th>TotalPrice</th>
					<th>Discount</th>
					<th class="cus-not-search"></th>
				</tr>
			</tfoot>

		</table>
	</div>
</div>