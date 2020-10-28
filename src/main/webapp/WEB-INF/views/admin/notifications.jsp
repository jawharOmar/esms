<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spirng" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script>
	var numofMin = ${minProducts};
	var numofexp = ${ExpiredProducts};
	
	var expiration = '<spring:message  javaScriptEscape="true" code="dashboard.expiration" />';
	var expirationMessage = '<spring:message  javaScriptEscape="true" code="dashboard.expirationMessage" />';

	var goodsAmount = '<spring:message  javaScriptEscape="true" code="dashboard.goodsAmount" />';
	var goodsAmountMessage = '<spring:message  javaScriptEscape="true" code="dashboard.goodsAmountMessage" />';
</script>

<div class="container">
	<div class="row">
		<div class="col-md-12 col-lg-12">
			<div class="card bg-cyan p-2 bg-success text-white">
				<div class="box  text-center">
					<h1 class="font-light text-white">
						<i style="font-size: 50px" class="material-icons large"> </i>
					</h1>
					<h6 class="text-white">
						<spring:message code="dashboard.balance" />
					</h6>
				</div>
				<div class="card-body text-white text-center">
					<fmt:formatNumber type="number" maxFractionDigits="3"
						value="${account.balance} " />
				</div>

			</div>
		</div>
	</div>

	<br>
	<div class="row" p-0>


		<div class="col-md-6 col-lg-4">
			<div class="card bg-green p-0 bg-warning text-dark">
				<div class="box p-2  text-center">
					<h1 class="font-light text-white">
						<i style="font-size: 50px" class="material-icons large"> </i>
					</h1>
					<h6 class="text-white">
						<spring:message code="dashboard.totalSold" />
					</h6>

					<p class="text-white">
						<fmt:formatNumber type="number" maxFractionDigits="0"
							value="${cus.qty} " />
					</p>
				</div>
				<div class="card-body bg-dark text-white text-center">
					<spring:message code="dashboard.totalPrice" />
					<fmt:formatNumber type="number" maxFractionDigits="3"
						value="${cus.price} " />
				</div>



			</div>
		</div>

		<div class="col-md-6 col-lg-4">
			<div class="card bg-danger text-white">
				<div class="box p-2  text-center">
					<h1 class="font-light text-white">
						<i style="font-size: 50px" class="material-icons large"> </i>
					</h1>
					<h6 class="text-white">
						<spring:message code="dashboard.amountReceived" />
					</h6>
					<p class="text-white">
						<fmt:formatNumber type="number" maxFractionDigits="3"
							value="${cus.payment} " />

					</p>
				</div>
				<div class="card-body bg-dark text-white text-center">
					<spring:message code="dashboard.loanAmount" />
					<fmt:formatNumber type="number" maxFractionDigits="3"
						value="${cus.loan} " />
				</div>

			</div>
		</div>


		<div class="col-md-6 col-lg-4">
			<div class="card bg-info text-white">
				<div class="box p-2  text-center">
					<h1 class="font-light text-white">
						<i style="font-size: 50px" class="material-icons large"> </i>
					</h1>
					<h6 class="text-white">
						<spring:message code="dashboard.expenses" />
					</h6>
					<p class="text-white">
						<fmt:formatNumber type="number" maxFractionDigits="3"
							value="${cus.expense} " />
					</p>

				</div>
				<div class="card-body bg-dark text-white text-center">
					<spring:message code="dashboard.paymentAmount" />
					<fmt:formatNumber type="number" maxFractionDigits="3"
						value="${cus.paid} " />
				</div>

			</div>
		</div>



	</div>

</div>


<div id="tablemins" style="display: none;">
	<table class="w-100 border" id="tablemins-table">
		<thead>
			<tr>
				<th><spring:message code="addCustomerOrder.quantity" /></th>
				<th><spring:message code="editProduct.minimumStockLevel" /></th>
				<th><spring:message code="addProduct.name" /></th>
				<th><spring:message code="addProduct.code" /></th>

			</tr>
		</thead>
	</table>
</div>

<div id="Expiredmins" style="display: none;">
	<table class="w-100 border" id="Expiredmins-table">
		<thead>
			<tr>
				<th><spring:message code="addOrderProductStepUp.expDate" /></th>
				<th><spring:message code="addProduct.name" /></th>
				<th><spring:message code="addProduct.code" /></th>
			</tr>
		</thead>
	</table>
</div>


