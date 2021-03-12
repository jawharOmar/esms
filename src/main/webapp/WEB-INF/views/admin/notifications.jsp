<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script>
	var jsonCustomerOrderLabel = '<spring:escapeBody  javaScriptEscape="true">${customerOrderLabel}</spring:escapeBody>';
	var jsonCustomerOrderData = '<spring:escapeBody  javaScriptEscape="true">${customerOrderData}</spring:escapeBody>';

	var customerOrderLabel = JSON.parse(jsonCustomerOrderLabel);
	var customerOrderData = JSON.parse(jsonCustomerOrderData);

	var jsonSalesLabel = '<spring:escapeBody  javaScriptEscape="true">${salesLabel}</spring:escapeBody>';
	var jsonSalesData = '<spring:escapeBody  javaScriptEscape="true">${salesData}</spring:escapeBody>';

	var salesLabel = [
			'<spring:message code="notifications.salesLabel.${salesLabel[0]}" />',
			'<spring:message code="notifications.salesLabel.${salesLabel[1]}" />',
			'<spring:message code="notifications.salesLabel.${salesLabel[2]}" />',
			'<spring:message code="notifications.salesLabel.${salesLabel[3]}" />' ];
	var salesData = JSON.parse(jsonSalesData);
</script>

<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">
			<spring:message code="dashboard.title" />
		</h1>
	</div>

	<!-- Content Row -->
	<div class="row">

		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-primary shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-primary text-uppercase mb-1">
								<spring:message code="dashabord.accountBalance" />
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								<fmt:formatNumber maxFractionDigits="3">
								${dashboardBalanceD.account}
								</fmt:formatNumber>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-box fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-success shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-success text-uppercase mb-1">
								<spring:message code="dashabord.expenseLastMonthBalance" />
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								<fmt:formatNumber maxFractionDigits="3">
								${dashboardBalanceD.expense}
								</fmt:formatNumber>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-trash fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-info shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-info text-uppercase mb-1">
								<spring:message code="dashabord.totalVendorLoanBalance" />
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								<fmt:formatNumber maxFractionDigits="3">
								${dashboardBalanceD.vendorLoan}
								</fmt:formatNumber>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-industry fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-xl-3 col-md-6 mb-4">
			<div class="card border-left-warning shadow h-100 py-2">
				<div class="card-body">
					<div class="row no-gutters align-items-center">
						<div class="col mr-2">
							<div
								class="text-xs font-weight-bold text-warning text-uppercase mb-1">
								<spring:message code="dashabord.totalCustomerLoanBalance" />
							</div>
							<div class="h5 mb-0 font-weight-bold text-gray-800">
								<fmt:formatNumber maxFractionDigits="3">
								${dashboardBalanceD.customerLoan}
								</fmt:formatNumber>
							</div>
						</div>
						<div class="col-auto">
							<i class="fas fa-user fa-2x text-gray-300"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div class="row">

		<!-- Area Chart -->
		<div class="col-xl-8 col-lg-7">
			<div class="card shadow mb-4">
				<!-- Card Header - Dropdown -->
				<div
					class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">
						<spring:message code="dashboard.customerOrder" />
					</h6>
					<div class="dropdown no-arrow">
						<a class="dropdown-toggle" href="#" role="button"
							id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i
							class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
						</a>
					</div>
				</div>
				<!-- Card Body -->
				<div class="card-body">
					<div class="chart-area">
						<canvas id="myAreaChart"></canvas>
					</div>
				</div>
			</div>
		</div>

		<!-- Pie Chart -->
		<div class="col-xl-4 col-lg-5">
			<div class="card shadow mb-5">
				<!-- Card Header - Dropdown -->
				<div
					class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">
						<spring:message code="dashboard.salesSummary" />
					</h6>
					<div class="dropdown no-arrow">
						<a class="dropdown-toggle" href="#" role="button"
							id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i
							class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
						</a>
					</div>
				</div>
				<!-- Card Body -->
				<div class="card-body">
					<div class="chart-pie pt-4 pb-2">
						<canvas id="myPieChart"></canvas>
					</div>
					<div class="mt-4 text-center small">
						<span class="mr-2"> <i class="fas fa-circle text-primary"></i>
							<span id="saleLabelOrder"></span>
						</span> <span class="mr-2"> <i class="fas fa-circle text-success"></i>
							<span id="saleLabelPayment"></span>
						</span> <span class="mr-2"> <i class="fas fa-circle text-info"></i>
							<span id="saleLabelLoan"></span>
						</span> <span class="mr-2"> <i class="fas fa-circle text-danger"></i>
							<span id="saleLabelReturn"></span>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">

		<div class="col-lg-6 mb-4">

			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">
						<spring:message code="dashboard.outOfStockProducts" />
					</h6>
				</div>
				<div class="card-body">
					<table class="table table-bordered table-sm">
						<thead class="thead-dark">
							<tr>
								<th><spring:message code="dashboard.minimumLevelCode" /></th>
								<th><spring:message code="dashboard.minimumLevelName" /></th>
								<th><spring:message
										code="dashboard.minimumLevelMinimumLevel" /></th>
								<th><spring:message code="dashboard.minimumLevelLevel" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${minimumStockLevelProducts}">
								<tr>
									<td>${item.productCode }</td>
									<td>${item.productName}</td>
									<td><fmt:formatNumber maxFractionDigits="0">
										${item.minimumStockLevel}
										</fmt:formatNumber></td>
									<td><fmt:formatNumber maxFractionDigits="0">
										${item.currentStockLevel}
										</fmt:formatNumber></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="col-lg-6 mb-4">
			<div class="card shadow mb-4">
				<div class="card-header">
					<h6 class="m-0 font-weight-bold text-danger">
						<spring:message code="dashboard.expiredProducts" />
					</h6>
				</div>
				<div class="card-body">
					<table class="table table-bordered table-sm">
						<thead class="thead-dark">
							<tr>
								<th><spring:message code="dashboard.minimumLevelCode" /></th>
								<th><spring:message code="dashboard.minimumLevelName" /></th>
								<th><spring:message code="dashboard.minimumLevelLevel" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${dashboardExpiredProductDs}">
								<tr>
									<td>${item.productCode }</td>
									<td>${item.productName}</td>
									<td><fmt:formatNumber maxFractionDigits="0">
										${item.amount}
										</fmt:formatNumber></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>


	</div>