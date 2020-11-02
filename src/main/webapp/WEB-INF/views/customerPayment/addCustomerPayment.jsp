<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
	var jsonCustomerPayment = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomerPayment}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
</script>

<div ng-controller="appCTRL" ng-init="init()" ng-form name="form">

	<div id="freeze" class="container h-75">
		<div class="row align-items-center h-100">
			<div class="col-6 mx-auto">
				<div class="card customCard">
					<div class="card-header">
						<h4 class="mb-0 text-center text-white">
							<spring:message code="addCustomerPayment.title" />
						</h4>
					</div>
					<div class="card-body">
						<div class="form-group">
							<label for="customer-autoselect"><spring:message
									code="addCustomerPayment.customerName" /></label> <input
								required="required" id="customer-autoselect"
								class="form-control form-control-sm"
								ng-model="cusomerPayment.customer.fullName">
						</div>

						<div class="form-group">
							<label for="customer_phone"><spring:message
									code="addCustomerPayment.phone" /></label> <input required="required"
								readonly="readonly" id="customer_phone"
								class="form-control form-control-sm"
								ng-model="customerPayment.customer.phone">
						</div>
						<fieldset class="customFieldset">
							<div class="form-group">
								<label for="project"><spring:message
										code="addCustomerPayment.project" /></label> <select
									class="form-control form-control-sm" id="project"
									ng-model="customerPayment.customerProject.id"
									ng-options="item.id as item.name for item in customerPayment.customer.customerProjects">
									<option value="">
										<spring:message code="addCustomerPayment.choose" />
									</option>
								</select>
							</div>

							<div class="form-group">
								<label for="totalPayment"><spring:message
										code="addCustomerPayment.totalPayment" /></label> <input required
									name="totalPayment" type="number" id="totalPayment" step="any"
									ng-max="customerPayment.customer.totalLoan" min="0"
									placeholder="{{customerPayment.customer.totalLoan}}"
									ng-model="customerPayment.totalPayment"
									class="form-control form-control-sm">
							</div>

							<div class="form-group">
								<label for="discount"><spring:message
										code="addCustomerPayment.discount" /></label> <input required
									name="discount" type="number" step="any" min="0" id="discount"
									placeholder="{{customerPayment.customer.totalLoan-customerPayment.totalPayment|number}}"
									ng-model="customerPayment.discount"
									class="form-control form-control-sm">

							</div>

							<div class="form-group">
								<label for="loan"><spring:message
										code="addCustomerPayment.remain" /></label> <input
									readonly="readonly" id="loan"
									ng-value="customerPayment.customer.totalLoan-customerPayment.totalPayment-customerPayment.discount|number"
									class="form-control form-control-sm">
							</div>

						</fieldset>

						<div class="form-group">
							<textarea maxlength="200" ng-model="customerPayment.note"
								placeholder="<spring:message code="addCustomerPayment.note"/>"
								class="form-control form-control-sm">
							</textarea>
						</div>

						<div class="text-center">
							<button
								ng-disabled="!form.$valid || (customerPayment.customer.totalLoan-customerPayment.totalPayment-customerPayment.discount)<0"
								class="btn customBtn" ng-click="addCustomerPayment()">
								<i class="fa fa-save"></i>
							</button>
							<button class="btn customBtn" id="refreshBtn"
								onClick="window.location.reload()">
								<i class="fas fa-retweet"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>