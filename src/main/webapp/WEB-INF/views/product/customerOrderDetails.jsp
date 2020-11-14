<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />


<div style="max-height: 400px; overflow: auto;">

	<h2>
		<spring:message code="customerOrderDetails.title" />
	</h2>

	<table class="table table-bordered">
		<thead>
			<tr>
				<td><spring:message code="customerOrderDetails.customerName" /></td>
				<td><spring:message code="customerOrderDetails.time" /></td>
				<td><spring:message code="customerOrderDetails.price" /></td>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${customerOrderDetails}" var="item">
				<tr>
					<td>${item.customerOrder.customer.fullName}</td>
					<td>${item.customerOrder.orderTime}</td>
					<td>${item.price}</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>

</div>





