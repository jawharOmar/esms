<%@page import="java.util.Date"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../version.jsp" />


<jsp:useBean id="now" class="java.util.Date" />
<c:set var="tomorrow"
	value="<%=new Date(new Date().getTime() + 60 * 60 * 24 * 1000)%>" />

<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<fmt:formatDate var="tomorrow" value="${tomorrow}" pattern="yyyy-MM-dd" />

<section id="admin-body">
	<section id="section-right" class="ative-menu">


		<sec:authorize url="/adminRoot">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/dashboard.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/adminRoot" />"> <spring:message
								code="layout.dashboard" />
					</a></td>
				</tr>
			</table>


		</sec:authorize>

		<sec:authorize url="/customers">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/customers.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/customers" />"> <spring:message
								code="layout.customers" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/customerOrders/add">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/salePoint.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/customerOrders/add"/>"> <spring:message
								code="layout.salePoint" />
					</a></td>
					<td><a class="text-info" href="<c:url value="/customers"/>">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/customerOrderReturns/add">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/addCustomerOrderReturn.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/customerOrderReturns/add"/>">
							<spring:message code="layout.addCustomerOrderReturn" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/customerOrderReturns"/>?from=${currentDate}&to=${tomorrow}">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/customerPayments/add">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/addCustomerPayment.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/customerPayments/add"/>"> <spring:message
								code="layout.addCustomerPayment" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/customerPayments"/>?from=${currentDate}&to=${tomorrow}">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/stocks">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/stocks.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/stocks"/>"> <spring:message
								code="layout.stocks" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/stockTransfers">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/stockTransfers.png" />" alt="" /></td>
					<td><a
						href="<c:url value="/stockTransfers"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.stockTransfers" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/products">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/stock.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/products/stock"/>"> <spring:message
								code="layout.stock" />
					</a></td>

					<td><a class="text-info"
						href="<c:url value="/productCategories"/>"> <i
							class="fa fa-search fa-3x"></i>
					</a></td>

				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/orderProductStepUps/add">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/order.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/orderProductStepUps/add"/>"> <spring:message
								code="layout.order" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/orderProductStepUps"/>?from=${currentDate}&to=${tomorrow}">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/vendors">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/vendors.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/vendors"/>"> <spring:message
								code="layout.vendors" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/vendorPayments/add">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/addVendorPayment.png" />" alt="" /></td>
					<td><a href="<c:url value="/vendorPayments/add"/>"> <spring:message
								code="layout.addVendorPayment" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/vendorPayments"/>?from=${currentDate}&to=${tomorrow}">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/priceCategories">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/priceCategory.png" />" alt="" /></td>
					<td><a href="<c:url value="/priceCategories"/>"> <spring:message
								code="layout.priceCategory" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/expenses">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/expenses.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/expenses"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.expenses" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/expenseCategories"/>"> <i
							class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/incomes">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/incomes.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/incomes"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.incomes" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/incomeCategories"/>"> <i
							class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/orderPreProducts/add">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/orderPreProduct.png" />" alt="" /></td>
					<td><a href="<c:url value="/orderPreProducts/add"/>"> <spring:message
								code="layout.orderPreProduct" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/orderPreProducts"/>?from=${currentDate}&to=${tomorrow}">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/customerWastedProducts/add">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/customerOrderReturns.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/customerWastedProducts/add"/>">
							<spring:message code="layout.customerReturnWastedProduct" />
					</a></td>
					<td><a class="text-info"
						href="<c:url value="/customerWastedProducts"/>?from=${currentDate}&to=${tomorrow}">
							<i class="fa fa-search fa-3x"></i>
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/customerWastedProducts/wastedProduct">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/wastedProductIcon.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/customerWastedProducts/wastedProduct"/>">
							<spring:message code="layout.wastedProducts" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/accounts">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/account.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/accounts/main"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.account" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/boxAccounting">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/boxAccounting.png" />" alt="" /></td>
					<td><a
						href="<c:url value="/boxAccounting/main"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.boxAccounting" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/netProfit">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/netProfit.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/netProfit/main"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.netProfit" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/users/userManager">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/userManager.png" />" alt="" /></td>
					<td><a href="<c:url value="/users/userManager"/>"> <spring:message
								code="layout.userManager" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/report">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/account.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/report"/>"> <spring:message
								code="layout.report" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/projectStatement">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/projectStatement.png" />" alt="" /></td>
					<td><a href="<c:url value="/report/projectStatement"/>"> <spring:message
								code="layout.projectStatement" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>


		<sec:authorize url="/profit">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/expenseCategories.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/profit"/>"> <spring:message
								code="layout.profit" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/psearch">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/productCategory.png" />" alt="" /></td>
					<td><a href="<c:url value="/psearch"/>"> <spring:message
								code="layout.product" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/venderReturn">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/vReturn.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/venderReturn/view"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.VednorReturn" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/withdrawCategories">

			<table class="card card-body">
				<tr>
					<td><img
						src="<c:url value="/resources/img/withdrawCategories.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/withdrawCategories"/>"> <spring:message
								code="layout.withdrawCategories" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>
		<sec:authorize url="/withdraws">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/withdraws.png" />"
						alt="" /></td>
					<td><a
						href="<c:url value="/withdraws"/>?from=${currentDate}&to=${tomorrow}">
							<spring:message code="layout.withdraws" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>

		<sec:authorize url="/setting">

			<table class="card card-body">
				<tr>
					<td><img src="<c:url value="/resources/img/settings.png" />"
						alt="" /></td>
					<td><a href="<c:url value="/settings"/>"> <spring:message
								code="layout.setting" />
					</a></td>
				</tr>
			</table>

		</sec:authorize>

	</section>
	<section id="main-content">
		<tiles:insertAttribute name="adminBody" />
	</section>

</section>