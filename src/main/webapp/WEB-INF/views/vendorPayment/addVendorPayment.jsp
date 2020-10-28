<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
	var jsonVendors = '<spring:escapeBody  javaScriptEscape="true">${jsonVendors}</spring:escapeBody>';
	var jsonVendorPayment = '<spring:escapeBody  javaScriptEscape="true">${jsonVendorPayment}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
</script>

<div ng-controller="appCTRL" ng-init="init()" ng-form name="form">

	<div id="freeze" class="container h-75">
		<div class="row align-items-center h-100">
			<div class="col-6 mx-auto">
				<div class="card customCard">
					<div class="card-header">
						<h4 class="mb-0 text-center text-white">
							<spring:message code="addVendorPayment.title" />
						</h4>
					</div>
					<div class="card-body">
						<div class="form-group">
							<label for="vendor-autoselect"><spring:message
									code="addVendorPayment.vendorName" /></label> <input
								required="required" id="vendor-autoselect"
								class="form-control form-control-sm"
								ng-model="vendorPayment.vendor.fullName">
						</div>
						<div class="form-group">
							<label for="vender_phone"><spring:message
									code="addVendorPayment.phone" /></label> <input required="required"
								readonly="readonly" id="vender_phone"
								class="form-control form-control-sm"
								ng-model="vendorPayment.vendor.phone">
						</div>
						<fieldset class="customFieldset">
							<div class="form-group">
								<label for="totalPayment"><spring:message
										code="addVendorPayment.totalPayment" /></label> <input required
									name="totalPayment" type="number" id="totalPayment" step="any"
									ng-max="vendorPayment.vendor.totalLoan" min="0"
									placeholder="{{vendorPayment.vendor.totalLoan}}"
									ng-model="vendorPayment.totalPayment"
									class="form-control form-control-sm">
							</div>
							<div class="form-group">
								<label for="discount"><spring:message
										code="addVendorPayment.discount" /></label> <input required
									name="discount" type="number" step="any" min="0" id="discount"
									placeholder="{{vendorPayment.vendor.totalLoan-vendorPayment.totalPayment|number}}"
									ng-model="vendorPayment.discount"
									class="form-control form-control-sm">
							</div>
							<div class="form-group">
								<label for="loan"><spring:message
										code="addVendorPayment.remain" /></label> <input readonly="readonly"
									id="loan"
									ng-value="vendorPayment.vendor.totalLoan-vendorPayment.totalPayment-vendorPayment.discount|number"
									class="form-control form-control-sm">
							</div>
						</fieldset>

						<div class="form-group">
							<textarea maxlength="200" ng-model="vendorPayment.note"
								placeholder="<spring:message code="addVendorPayment.note"/>"
								class="form-control"></textarea>
						</div>
						<div class="text-center">
							<button
								ng-disabled="!form.$valid || (vendorPayment.vendor.totalLoan-vendorPayment.totalPayment-vendorPayment.discount)<0"
								class="btn customBtn" ng-click="addVendorPayment()">
								<i class="fa fa-save"></i>
							</button>
							<button class="btn customBtn" id="refreshBtn"
								onClick="window.location.reload()">
								<i class="fa fa-refresh"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>