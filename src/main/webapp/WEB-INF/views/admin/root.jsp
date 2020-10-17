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
			<a href="<c:url value="/adminRoot" />">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/dashboard.png" />"
							alt="" /></td>
						<td><spring:message code="layout.dashboard" /></td>
					</tr>
				</table>
			</a>

		</sec:authorize>

		<sec:authorize url="/customers">
			<a href="<c:url value="/customers"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/customers.png" />"
							alt="" /></td>
						<td><spring:message code="layout.customers" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/customerOrders/add">
			<a href="<c:url value="/customerOrders/add"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/salePoint.png" />"
							alt="" /></td>
						<td><spring:message code="layout.salePoint" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/customerOrders">
			<a
				href="<c:url value="/customerOrders"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/customerOrders.png" />" alt="" /></td>
						<td><spring:message code="layout.customerOrders" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/customerOrderReturns/add">
			<a href="<c:url value="/customerOrderReturns/add"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/addCustomerOrderReturn.png" />"
							alt="" /></td>
						<td><spring:message code="layout.addCustomerOrderReturn" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/customerOrderReturns">
			<a
				href="<c:url value="/customerOrderReturns"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/customerOrderReturns.png" />"
							alt="" /></td>
						<td><spring:message code="layout.customerOrderReturns" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/customerPayments/add">
			<a href="<c:url value="/customerPayments/add"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/addCustomerPayment.png" />"
							alt="" /></td>
						<td><spring:message code="layout.addCustomerPayment" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/customerPayments">
			<a
				href="<c:url value="/customerPayments"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/customerPayments.png" />"
							alt="" /></td>
						<td><spring:message code="layout.customerPayments" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/productCategories">
			<a href="<c:url value="/productCategories"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/productCategory.png" />" alt="" /></td>
						<td><spring:message code="layout.productCategory" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/stocks">
			<a href="<c:url value="/stocks"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/stocks.png" />"
							alt="" /></td>
						<td><spring:message code="layout.stocks" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/stockTransfers">
			<a
				href="<c:url value="/stockTransfers"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/stockTransfers.png" />" alt="" /></td>
						<td><spring:message code="layout.stockTransfers" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/products">
			<a href="<c:url value="/products/stock"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/stock.png" />"
							alt="" /></td>
						<td><spring:message code="layout.stock" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/orderProductStepUps/add">
			<a href="<c:url value="/orderProductStepUps/add"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/order.png" />"
							alt="" /></td>
						<td><spring:message code="layout.order" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/orderProductStepUps">
			<a
				href="<c:url value="/orderProductStepUps"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/viewOrders.png" />" alt="" /></td>
						<td><spring:message code="layout.viewOrders" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/vendors">
			<a href="<c:url value="/vendors"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/vendors.png" />"
							alt="" /></td>
						<td><spring:message code="layout.vendors" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/vendorPayments/add">
			<a href="<c:url value="/vendorPayments/add"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/addVendorPayment.png" />"
							alt="" /></td>
						<td><spring:message code="layout.addVendorPayment" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/vendorPayments">
			<a
				href="<c:url value="/vendorPayments"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/vendorPayments.png" />" alt="" /></td>
						<td><spring:message code="layout.vendorPayments" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/priceCategories">
			<a href="<c:url value="/priceCategories"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/priceCategory.png" />" alt="" /></td>
						<td><spring:message code="layout.priceCategory" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/expenseCategories">
			<a href="<c:url value="/expenseCategories"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/expenseCategories.png" />"
							alt="" /></td>
						<td><spring:message code="layout.expenseCategories" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/expenses">
			<a
				href="<c:url value="/expenses"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/expenses.png" />"
							alt="" /></td>
						<td><spring:message code="layout.expenses" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/incomeCategories">
			<a href="<c:url value="/incomeCategories"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/incomeCategory.png" />" alt="" /></td>
						<td><spring:message code="layout.incomeCategories" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/incomes">
			<a
				href="<c:url value="/incomes"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/incomes.png" />"
							alt="" /></td>
						<td><spring:message code="layout.incomes" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/orderPreProducts/add">
			<a href="<c:url value="/orderPreProducts/add"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/orderPreProduct.png" />" alt="" /></td>
						<td><spring:message code="layout.orderPreProduct" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/orderPreProducts">
			<a
				href="<c:url value="/orderPreProducts"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/orderPreProducts.png" />"
							alt="" /></td>
						<td><spring:message code="layout.orderPreProducts" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/customerWastedProducts/add">
			<a href="<c:url value="/customerWastedProducts/add"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/customerOrderReturns.png" />"
							alt="" /></td>
						<td><spring:message code="layout.customerReturnWastedProduct" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/customerWastedProducts">
			<a
				href="<c:url value="/customerWastedProducts"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/customerReturnWastedProduct.png" />"
							alt="" /></td>
						<td><spring:message
								code="layout.customerReturnWastedProducts" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/customerWastedProducts/wastedProduct">
			<a href="<c:url value="/customerWastedProducts/wastedProduct"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/wastedProductIcon.png" />"
							alt="" /></td>
						<td><spring:message code="layout.wastedProducts" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/accounts">
			<a
				href="<c:url value="/accounts/main"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/account.png" />"
							alt="" /></td>
						<td><spring:message code="layout.account" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/boxAccounting">
			<a
				href="<c:url value="/boxAccounting/main"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/boxAccounting.png" />" alt="" /></td>
						<td><spring:message code="layout.boxAccounting" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/netProfit">
			<a
				href="<c:url value="/netProfit/main"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/netProfit.png" />"
							alt="" /></td>
						<td><spring:message code="layout.netProfit" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/users/userManager">
			<a href="<c:url value="/users/userManager"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/userManager.png" />" alt="" /></td>
						<td><spring:message code="layout.userManager" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/report">
			<a href="<c:url value="/report"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/account.png" />"
							alt="" /></td>
						<td><spring:message code="layout.report" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/projectStatement">
			<a href="<c:url value="/report/projectStatement"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/projectStatement.png" />"
							alt="" /></td>
						<td><spring:message code="layout.projectStatement" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>


		<sec:authorize url="/profit">
			<a href="<c:url value="/profit"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/expenseCategories.png" />"
							alt="" /></td>
						<td><spring:message code="layout.profit" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/psearch">
			<a href="<c:url value="/psearch"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/productCategory.png" />" alt="" /></td>
						<td><spring:message code="layout.product" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/venderReturn">
			<a
				href="<c:url value="/venderReturn/view"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/vReturn.png" />"
							alt="" /></td>
						<td><spring:message code="layout.VednorReturn" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/withdrawCategories">
			<a href="<c:url value="/withdrawCategories"/>">
				<table class="card card-body">
					<tr>
						<td><img
							src="<c:url value="/resources/img/withdrawCategories.png" />"
							alt="" /></td>
						<td><spring:message code="layout.withdrawCategories" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>
		<sec:authorize url="/withdraws">
			<a
				href="<c:url value="/withdraws"/>?from=${currentDate}&to=${tomorrow}">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/withdraws.png" />"
							alt="" /></td>
						<td><spring:message code="layout.withdraws" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

		<sec:authorize url="/setting">
			<a href="<c:url value="/setting"/>">
				<table class="card card-body">
					<tr>
						<td><img src="<c:url value="/resources/img/settings.png" />"
							alt="" /></td>
						<td><spring:message code="layout.setting" /></td>
					</tr>
				</table>
			</a>
		</sec:authorize>

	</section>
	<section id="main-content">
		<tiles:insertAttribute name="adminBody" />
	</section>

</section>