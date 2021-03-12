<%@page import="java.util.Date"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean id="now" class="java.util.Date" />
<c:set var="tomorrow"
	value="<%=new Date(new Date().getTime() + 60 * 60 * 24 * 1000)%>" />

<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<fmt:formatDate var="tomorrow" value="${tomorrow}" pattern="yyyy-MM-dd" />

<section id="admin-body">
	<section id="section-right">
		<div>
			<ul class="list-group">
				<sec:authorize access="hasAnyRole('ADMIN', 'SALE')">

					<li class="list-group-item"><a
						href="<c:url value="/adminRoot" />"><spring:message
								code="layout.dashboard" /> </a></li>
					<li class="list-group-item"><a
						href="<c:url value="/customerOrders/add"/>"> <spring:message
								code="layout.salePoint" />
					</a></li>
					<li class="list-group-item"><a
						href="<c:url value="/customerOrders"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.customerOrders" />
					</a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ADMIN')">
					<li class="list-group-item"><a
						href="<c:url value="/products/stock"/>"><spring:message
								code="layout.stock" /> </a></li>
					<li class="list-group-item"><a
						href="<c:url value="/productCategories"/>"><spring:message
								code="layout.productCategory" /></a></li>
					<li class="list-group-item"><a
						href="<c:url value="/priceCategories"/>"> <spring:message
								code="layout.priceCategory" /></a></li>

					<li class="list-group-item"><a
						href="<c:url value="/orderProductStepUps/add"/>"> <spring:message
								code="layout.order" />
					</a></li>
					<li class="list-group-item"><a
						href="<c:url value="/orderProductStepUps"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.viewOrders" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/vendors"/>"> <spring:message
								code="layout.vendors" />
					</a></li>
					<li class="list-group-item"><a
						href="<c:url value="/customers"/>"> <spring:message
								code="layout.customers" />
					</a></li>


					<li class="list-group-item"><a
						href="<c:url value="/customerPayments/add"/>"> <spring:message
								code="layout.addCustomerPayment" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/customerPayments"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.customerPayments" />
					</a></li>
					
					<li class="list-group-item"><a
						href="<c:url value="/vendorPayments/add"/>"> <spring:message
								code="layout.addVendorPayment" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/vendorPayments"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.vendorPayments" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/expenseCategories"/>"> <spring:message
								code="layout.expenseCategories" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/expenses"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.expenses" />
					</a></li>


					<li class="list-group-item"><a
						href="<c:url value="/incomeCategories"/>"> <spring:message
								code="layout.incomeCategories" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/incomes"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.incomes" />
					</a></li>


					<li class="list-group-item"><a
						href="<c:url value="/customerOrders/sold"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.productSold" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/orderPreProducts/add"/>"> <spring:message
								code="layout.orderPreProduct" />
					</a></li>

					<li class="list-group-item"><a
						href="<c:url value="/orderPreProducts"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.orderPreProducts" />
					</a></li>



				</sec:authorize>

			</ul>
		</div>
	</section>



	<section id="main-content">
		<tiles:insertAttribute name="adminBody" />
	</section>

</section>