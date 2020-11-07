<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<script>
    var csrf = '${_csrf.token}';
    var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';

</script>

<div>

	<h2>
		<spring:message code="adminCustomers.title" />
	</h2>

	<div class="add-new-doctor-div">
		<sec:authorize url="/customers/add">
			<button class="btn btn-success" onclick="getAddingCustomer()">
				<i class="fa fa-plus"></i>
			</button>
		</sec:authorize>
	</div>

	<c:set var="total" value="${0}" />

	<table class="table table-striped table-bordered dt-responsive nowrap"
		id="dataTable" width="100%" cellspacing="0">
		<thead>
			<tr>
				<td><spring:message code="adminCustomers.fullName" /></td>
				<td><spring:message code="adminCustomers.phone" /></td>
				<td><spring:message code="adminCustomers.address" /></td>
				<td><spring:message code="adminCustomers.priceCategory" /></td>
				<td><spring:message code="adminCustomers.totalLoan" /></td>
				<td><spring:message code="adminCustomers.note" /></td>
				<td><spring:message code="adminCustomers.function" /></td>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${customers}" var="item">
				<tr>
					<td>${item.fullName}</td>
					<td>${item.phone}</td>
					<td>${item.address}</td>
					<td>${item.priceCategory.name}</td>
					<td><fmt:formatNumber maxFractionDigits="3"
							value="${item.totalLoan}" /></td>
					<c:set var="total" value="${total+item.totalLoan}" />

					<td>${item.note}</td>
					<td>

						<div class="dropdown">
							<button class="btn btn-secondary dropdown-toggle" type="button"
								id="d${item.id}" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								<spring:message code="adminCustomers.function" />
							</button>
							<div class="dropdown-menu" aria-labelledby="d${item.id}">
								<a class="dropdown-item"
									href="<c:url value="/customers/" />${item.id}/invoiceDetail">
									<spring:message code="customerInvoiceDetail.title" />
								</a> <a class="dropdown-item"
									href="<c:url value="/customers/" />${item.id}/invoice"> <spring:message
										code="customerInvoice.title" />
								</a>
								<sec:authorize url="/customerPayments">

									<a class="dropdown-item"
										href="<c:url value="/customerPayments/customer/" />${item.id}">
										<spring:message code="customerPayments.title" />
									</a>
								</sec:authorize>

								<sec:authorize url="/customerProjects">
									<a class="dropdown-item"
										href="<c:url value="/customerProjects/customer/" />${item.id}">
										<spring:message code="customerProjects.title" />
									</a>
								</sec:authorize>
								<div class="dropdown-divider"></div>
								<sec:authorize url="/customers/delete">


									<button class="dropdown-item"
										onclick="deleteCustomer(${item.id})">
										<i class="fa fa-times"></i>
									</button>
								</sec:authorize>

								<sec:authorize url="/customers/edit">
									<button class="dropdown-item"
										onclick="editingCustomer(${item.id})">
										<i class="fa fa-edit"></i>
									</button>
								</sec:authorize>
							</div>
						</div>
					</td>


				</tr>
			</c:forEach>

		</tbody>
		<tfoot>
			<tr>
				<td colspan="4"><spring:message code="adminCustomers.totalLoan" /></td>
				<td><fmt:formatNumber maxFractionDigits="3" value="${total}" />
				</td>
				<td colspan="2"></td>

			</tr>
		</tfoot>
	</table>

</div>





