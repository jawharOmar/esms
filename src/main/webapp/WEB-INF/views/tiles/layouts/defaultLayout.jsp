<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="versionLogo" scope="request"><%=LocalDateTime.now()%></c:set>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../../version.jsp" />

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="tomorrow"
	value="<%=new Date(new Date().getTime() + 60 * 60 * 24 * 1000)%>" />


<fmt:setLocale value="en_US" scope="session" />

<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<fmt:formatDate var="tomorrow" value="${tomorrow}" pattern="yyyy-MM-dd" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;UTF-8">
<meta charset="UTF-8" />
<title><tiles:getAsString name="title" /></title>

<tiles:importAttribute name="requiredCSSFiles" />
<c:if test="${!empty requiredCSSFiles}">

	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet" />
	<c:forEach var="item" items="${requiredCSSFiles}">
		<c:if
			test="${(pageContext.response.locale=='ar_SY'||pageContext.response.locale=='ar')&& item=='bootstrap'}">
			<c:set var="item" value="bootstrap_rtl" />
		</c:if>
		<c:if
			test="${(pageContext.response.locale=='ar_SY'||pageContext.response.locale=='ar')&& item=='sb-admin-2.min'}">
			<c:set var="item" value="sb-admin-2.min.rtl" />
		</c:if>

		<link href="<c:url value="/resources/css/${item}.css?${version}" />"
			rel="stylesheet" />
	</c:forEach>

</c:if>

<tiles:importAttribute name="rightToLeftCSS" />
<c:if
	test="${pageContext.response.locale=='ar_SY'||pageContext.response.locale=='ar'}">
	<link
		href="<c:url value="/resources/css/rightToLeftCSS.css?${version}" />"
		rel="stylesheet" />
</c:if>


<style type="text/css">
@font-face {
	font-family: "NotoKufiArabicBold";
	src: url("<c:url value='/resources/fonts/NotoKufiArabic-Bold.woff'/>")
		format('woff');
}

@font-face {
	font-family: "NotoKufiArabic";
	src: url("<c:url value='/resources/fonts/NotoKufiArabic.woff'/>")
		format('woff');
}

body {
	font-family: NotoKufiArabic !important;
}
</style>

</head>
<body ng-app="app" id="page-top" ng-controller="appCTRL"
	ng-init="init()">

	<!-- Page Wrapper -->


	<div id="wrapper">

		<!-- Sidebar -->
		<div id="navbar-div" class="toggled">
			<ul
				class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
				id="accordionSidebar">
				<div
					class="sidebar-brand d-flex align-items-center justify-content-center">
					<div class="sidebar-brand-icon rotate-n-15">
						<img style="max-width: 50px;"
							src="<c:url value="/resources/img/logo.png?" />${versionLogo}"
							height="30" alt="" />
					</div>
					<div class="sidebar-brand-text mx-3">Goran</div>
				</div>
				<hr class="sidebar-divider my-0">
				<sec:authorize url="/adminRoot">
					<li class="nav-item"><a class="nav-link"
						href="<c:url value="/adminRoot" />"> <i
							class="fas fa-fw fa-tachometer-alt"></i> <span><spring:message
									code="layout.dashboard" /></span>
					</a></li>
				</sec:authorize>
				<hr class="sidebar-divider" />
				<li class="nav-item"></li>

				<li class="nav-item"><a class="nav-link collapsed" href="#"
					data-toggle="collapse" data-target="#collapseTwo"
					aria-expanded="true" aria-controls="collapseTwo"> <i
						class="fas fa-fw fa-cog"></i> <span><spring:message
								code="layout.salePoint" /></span>
				</a>
					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
						data-parent="#accordionSidebar">
						<div class="py-2 collapse-inner rounded">
							<a class="collapse-item" href="<c:url value="/customers" />">
								<spring:message code="layout.customers" />
							</a>

							<sec:authorize url="/customerOrders/add">
								<a class="collapse-item"
									href="<c:url value="/customerOrders/add"/>"><spring:message
										code="layout.add" /></a>
								<a class="collapse-item"
									href="<c:url value="/customerOrders"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.list" /></a>
							</sec:authorize>
							<sec:authorize url="/priceCategories">
								<a class="collapse-item"
									href="<c:url value="/priceCategories"/>"><spring:message
										code="layout.priceCategory" /></a>
							</sec:authorize>
						</div>
					</div></li>

				<li class="nav-item"><a class="nav-link collapsed" href="#"
					data-toggle="collapse" data-target="#c2" aria-expanded="true"
					aria-controls="c2"> <i class="fas fa-fw fa-cog"></i> <span><spring:message
								code="layout.customerTransactions" /></span>
				</a>
					<div id="c2" class="collapse" aria-labelledby="headingTwo"
						data-parent="#accordionSidebar">
						<div class=" py-2 collapse-inner rounded">
							<sec:authorize url="/customerPayments/add">
								<a class="collapse-item"
									href="<c:url value="/customerPayments/add"/>"><spring:message
										code="layout.addCustomerPayment" /></a>
								<a class="collapse-item"
									href="<c:url value="/customerPayments"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.listCustomerPayment" />
								</a>
							</sec:authorize>
							<sec:authorize url="/customerOrderReturns/add">
								<a class="collapse-item"
									href="<c:url value="/customerOrderReturns/add"/>"><spring:message
										code="layout.addCustomerReturn" /></a>
								<a class="collapse-item"
									href="<c:url value="/customerOrderReturns"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.listCustomerReturn" /></a>
							</sec:authorize>
							<sec:authorize url="/customerReturnWastedProducts/add">
								<a class="collapse-item"
									href="<c:url value="/customerReturnWastedProducts/add"/>"><spring:message
										code="layout.addCustomerOrderReturnWastedProduct" /></a>
								<a class="collapse-item"
									href="<c:url value="/customerReturnWastedProducts"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.customerReturnWastedProducts" /></a>
							</sec:authorize>
						</div>
					</div></li>

				<li class="nav-item"><a class="nav-link collapsed" href="#"
					data-toggle="collapse" data-target="#c4" aria-expanded="true"
					aria-controls="c4"> <i class="fas fa-fw fa-cog"></i> <span>
							<spring:message code="layout.stocks" />
					</span>
				</a>
					<div id="c4" class="collapse" aria-labelledby="headingTwo"
						data-parent="#accordionSidebar">
						<div class=" py-2 collapse-inner rounded">
							<sec:authorize url="/products">
								<a class="collapse-item" href="<c:url value="/products/stock"/>">
									<span><spring:message code="layout.productInStock" /></span>
								</a>
							</sec:authorize>
							<a class="collapse-item"
								href="<c:url value="/productCategories"/>"><span><spring:message
										code="layout.productCategory" /></span> </a>
							<sec:authorize url="/stockTransfers">
								<a class="collapse-item"
									href="<c:url value="/stockTransfers"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.stockTransfers" /></a>
							</sec:authorize>
							<sec:authorize url="/stocks">
								<a class="collapse-item" href="<c:url value="/stocks"/>"> <spring:message
										code="layout.list" /></a>
							</sec:authorize>
						</div>
					</div></li>

				<li class="nav-item"><a class="nav-link collapsed" href="#"
					data-toggle="collapse" data-target="#c5" aria-expanded="true"
					aria-controls="c5"> <i class="fas fa-fw fa-cog"></i> <span>
							<spring:message code="layout.vendorOrder" />
					</span>
				</a>
					<div id="c5" class="collapse totop" aria-labelledby="headingTwo"
						data-parent="#accordionSidebar">
						<div class=" py-2 collapse-inner rounded">
							<sec:authorize url="/vendors">
								<a class="collapse-item" href="<c:url value="/vendors"/>"><spring:message
										code="layout.vendors" /></a>
							</sec:authorize>
							<sec:authorize url="/orderProductStepUps/add">
								<a class="collapse-item"
									href="<c:url value="/orderProductStepUps/add"/>"><spring:message
										code="layout.add" /></a>
								<a class="collapse-item"
									href="<c:url value="/orderProductStepUps"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.list" />
								</a>
							</sec:authorize>
							<sec:authorize url="/vendorPayments/add">
								<a class="collapse-item"
									href="<c:url value="/vendorPayments/add"/>"> <spring:message
										code="layout.addVendorPayment" />
								</a>
								<a class="collapse-item"
									href="<c:url value="/vendorPayments"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.vendorPayments" />
								</a>
							</sec:authorize>
							<sec:authorize url="/orderPreProducts/add">
								<a class="collapse-item"
									href="<c:url value="/orderPreProducts/add"/>"><spring:message
										code="layout.addOrderPreProduct" /></a>
								<a class="collapse-item"
									href="<c:url value="/orderPreProducts"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.orderPreProducts" /></a>

							</sec:authorize>
							<sec:authorize url="/venderReturn">
								<a class="collapse-item"
									href="<c:url value="/venderReturns/view"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.VednorReturn" /></a>
							</sec:authorize>
						</div>
					</div></li>
				<li class="nav-item"><a class="nav-link collapsed" href="#"
					data-toggle="collapse" data-target="#c8" aria-expanded="true"
					aria-controls="c8"> <i class="fas fa-fw fa-cog"></i> <span>
							<spring:message code="layout.utility" />
					</span>
				</a>
					<div id="c8" class="collapse totop" aria-labelledby="headingTwo"
						data-parent="#accordionSidebar">
						<div class=" py-2 collapse-inner rounded">

							<sec:authorize url="/accounts">
								<a class="collapse-item"
									href="<c:url value="/accounts/main"/>?from=${currentDate}&to=${tomorrow}"><spring:message
										code="layout.account" /></a>

							</sec:authorize>
							<sec:authorize url="/boxAccounting">
								<a class="collapse-item"
									href="<c:url value="/boxAccounting/main"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.boxAccounting" />
								</a>

							</sec:authorize>
							<sec:authorize url="/netProfit">
								<a class="collapse-item"
									href="<c:url value="/netProfit/main"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.netProfit" />
								</a>
							</sec:authorize>
							<sec:authorize url="/users/userManager">
								<a class="collapse-item"
									href="<c:url value="/users/userManager"/>"> <spring:message
										code="layout.userManager" />
								</a>
							</sec:authorize>
							<sec:authorize url="/report">
								<a class="collapse-item" href="<c:url value="/report"/>"> <spring:message
										code="layout.report" />
								</a>
							</sec:authorize>
							<sec:authorize url="/projectStatement">
								<a class="collapse-item"
									href="<c:url value="/report/projectStatement"/>"> <spring:message
										code="layout.projectStatement" />
								</a>

							</sec:authorize>

							<sec:authorize url="/profit">
								<a class="collapse-item" href="<c:url value="/profit"/>"> <spring:message
										code="layout.profit" />
								</a>
							</sec:authorize>
							<sec:authorize url="/incomes">
								<a class="collapse-item"
									href="<c:url value="/incomes"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.incomes" />
								</a>
							</sec:authorize>
							<sec:authorize url="/expenses">
								<a class="collapse-item"
									href="<c:url value="/expenses"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.expenses" />
								</a>
							</sec:authorize>
							<sec:authorize url="/withdraws">
								<a class="collapse-item"
									href="<c:url value="/withdraws"/>?from=${currentDate}&to=${tomorrow}">
									<spring:message code="layout.withdraws" />
								</a>
							</sec:authorize>
							<sec:authorize url="/setting">
								<a class="collapse-item" href="<c:url value="/settings/add"/>">
									<spring:message code="layout.setting" />
								</a>
							</sec:authorize>

						</div>
					</div></li>


				<!-- Divider -->
				<hr class="sidebar-divider d-none d-md-block">

				<!-- Sidebar Toggler (Sidebar) -->
				<div class="text-center d-none d-md-inline">
					<button class="rounded-circle border-0" id="sidebarToggle"></button>
				</div>

			</ul>
		</div>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">


						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small"> <sec:authorize
										access="isAuthenticated()">
										<sec:authentication property="principal.username" />
									</sec:authorize>
							</span>
						</a> <!-- Dropdown - User Information -->


							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<button type="button" class="dropdown-item" data-toggle="modal"
									data-target="#passwordModal">
									<i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
									Change password
								</button>
								<a class="dropdown-item" href="?lang=ar_SY">کوردی</a> <a
									class="dropdown-item" href="?lang=en">English</a> <a
									class="dropdown-item" href="?lang=ar">عربی</a>
								<div class="dropdown-divider"></div>

								<form action="<c:url value="/logout" />" method="POST">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}">
									<div>
										<button class="dropdown-item">
											<i class="fa fa-power-off"></i>
										</button>
									</div>
								</form>
							</div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->


				<section id="site-content">
					<div class="container-fluid">
						<tiles:insertAttribute name="body" />
					</div>
				</section>


				<!-- /.container-fluid -->



				<div id="app-loading-div">
					<img id="" src="<c:url value="/resources/img/loading.gif" />" />
				</div>


				<tiles:importAttribute name="requiredJSFiles" />
				<c:if test="${!empty requiredJSFiles}">
					<c:forEach var="item" items="${requiredJSFiles}">
						<c:choose>
							<c:when test="${item=='jsLang'}">
								<script type="text/javascript"
									src="<c:url value='/${item}.js' />?${version}"></script>
							</c:when>
							<c:otherwise>
								<script type="text/javascript"
									src="<c:url value='/resources/js/${item}.js' />?${version}"></script>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</c:if>


				<!-- Modal -->
				<div class="modal fade" id="modal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLongTitle" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body" id="modal-body"></div>
						</div>
					</div>
				</div>



				<!-- Change password -->

				<div class="modal fade" id="passwordModal" tabindex="-1"
					role="dialog" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">

								<div id="password-dialog" class="p-1">
									<table class="w-100">
										<tr>
											<td class="cus-fit-td"><spring:message
													code="changePassword.show" /></td>
											<td class="cus-rest-td"><input ng-model="show"
												type="checkbox"></td>
										</tr>
										<tr>
											<td class="cus-fit-td"><spring:message
													code="changePassword.oldPassword" /></td>
											<td class="cus-rest-td"><input
												ng-model="changePassword.oldPassword"
												type="{{show ? 'text':'password'}}" name="oldPassword"
												class="form-control form-control-sm"></td>
										</tr>

										<tr>
											<td class="cus-fit-td"><spring:message
													code="changePassword.newPassword" /></td>
											<td class="cus-rest-td"><input
												ng-model="changePassword.newPassword"
												type="{{show ? 'text': 'password'}}" name="newPassword"
												class="form-control form-control-sm"></td>
										</tr>
										<tr>
											<td class="cus-fit-td"><spring:message
													code="changePassword.confirmPassword" /></td>
											<td class="cus-rest-td"><input
												ng-model="changePassword.confirmPassword"
												type="{{show ? 'text': 'password'}}" name="confirmPassword"
												class="form-control form-control-sm"></td>
										</tr>

										<tr>
											<td class="cus-fit-td">
												<button ng-click="saveChangePassword()"
													ng-disabled="!changePassword.confirmPassword || !changePassword.newPassword || !changePassword.oldPassword || (changePassword.confirmPassword != changePassword.newPassword)"
													class="btn btn-success btn-sm">
													<i class="fa fa-save"></i>
												</button>
											</td>

										</tr>
									</table>
								</div>

							</div>
						</div>
					</div>
				</div>

				<div id="alerts-div">
					<div class="alert alert-success" id="cus-success-alert"
						role="alert" style="text-align: center; display: none;">
						<spring:message code="layout.success" />
					</div>

					<div class="alert alert-danger" id="cus-failed-alert" role="alert"
						style="text-align: center; display: none;">
						<spring:message code="layout.failed" />
					</div>
				</div>
</body>
</html>