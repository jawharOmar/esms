<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />


<script type="text/javascript">
	var jsonCustomers = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomers}</spring:escapeBody>';
	var jsonCustomerOrderReturn = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomerOrderReturn}</spring:escapeBody>';
	var jsonProductDs = '<spring:escapeBody  javaScriptEscape="true">${jsonProductDs}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
	var url = "<c:url value="/attachedFiles" />";
	var jsonStocks = '<spring:escapeBody  javaScriptEscape="true">${jsonStocks}</spring:escapeBody>';
</script>



<div ng-app="app" ng-controller="appCTRL" ng-init="init()">

	<div id="loading-div" if-loading>
		<img id=""
			src="<c:url value="/resources/img/loading.gif?${version}" />" />
	</div>

	<h2>
		<spring:message code="editCustomerOrderReturn.title" />
		${currentDate}
	</h2>
	<div>
		<a target="_blank" class="btn btn-lg btn-outline-info"
			href="<c:url value="/customerOrderReturns/" />{{customerOrderReturn.id}}/print">
			<i class="fa fa-print"></i>
		</a>
		<button class="btn btn-lg btn-outline-warning"
			onClick="window.location.reload()">
			<i class="fa fa-refresh"></i>
		</button>
	</div>

	<table class="table table-sm cus-table-borderless">
		<tbody ng-form="CustomerOrderReturnForm">
			<tr>
				<td><spring:message code="addCustomerOrderReturn.customer" /></td>
				<td><input disabled required
					class="form-control form-control-sm"
					ng-model="customerOrderReturn.customer.fullName" /></td>
			</tr>
			<tr>
				<td title="Most of the time Vendor's Invoice ID"><spring:message
						code="addCustomerOrderReturn.reference" /></td>
				<td><input ng-model="customerOrderReturn.referecneInvoiceId"
					class="form-control  form-control-sm"></td>
			</tr>
			<tr>
				<td><spring:message code="addCustomerOrder.project"/></td>
				<td><select class="form-control form-control-sm"
							ng-model="customerOrderReturn.customerProject.id"
							ng-options="item.id as item.name for item in customerOrderReturn.customer.customerProjects">
					<option value="">
						<spring:message code="addCustomerOrder.choose"/>
					</option>
				</select></td>
			</tr>
		</tbody>
	</table>


	<div class="border-top">
		<table class="table table-bordered">
			<thead>
				<tr>
					<td>#</td>
					<td><spring:message code="addCustomerOrderReturn.image" /></td>
					<td><spring:message code="addCustomerOrderReturn.pName" /></td>
					<td><spring:message code="addCustomerOrderReturn.pCode" /></td>
					<td><spring:message code="addCustomerOrderReturn.pUnitType" /></td>
					<td><spring:message
							code="addCustomerOrderReturn.packetQuantity" /></td>
					<td><spring:message code="addCustomerOrderReturn.quantity" /></td>
					<td><spring:message code="addCustomerOrderReturn.payment" /></td>
					<td><spring:message code="addCustomerOrderReturn.expDate" /></td>
					<td><spring:message code="addCustomerOrderReturn.stock" /></td>
					<td><spring:message code="addCustomerOrderReturn.note" /></td>
					<td>&nbsp;</td>
				</tr>
				<tr ng-form="newProductStepUpForm">
					<td></td>
					<td></td>
					<td><input id="autoselect" ng-change="selectedProduct=null"
						ng-model="productStepUp.product.name"
						class="form-control form-control-sm"></td>
					<td><input ng-blur="getProduct()"
						ng-readonly="selectedProduct" required
						ng-model="productStepUp.product.code"
						class="form-control form-control-sm"></td>
					<td><input readonly="readonly"
						ng-model="productStepUp.product.unitType"
						class="form-control form-control-sm"></td>
					<td><input ng-disabled="!productStepUp.product.packetSize"
						type="number" ng-model="productStepUp.packetQuantity"
						class="form-control form-control-sm"></td>
					<td><input ng-disabled="productStepUp.packetQuantity"
						type="number" min="1" required ng-model="productStepUp.quantity"
						class="form-control form-control-sm">
						{{productStepUp.product.packetSize}}</td>
					<td><input type="number" min="0" required
						ng-model="productStepUp.returnPrice"
						class="form-control form-control-sm"><span
						class="text-info">{{productStepUp.returnPrice*productStepUp.quantity|number}}</span>
					</td>
					<td><input id="newProductStepUpExpirationDate"
						ng-model="productStepUp.expirationDate"
						class="form-control form-control-sm"></td>
					<td><select required class="form-control form-control-sm"
						ng-model="productStepUp.stock" ng-init="productStepUp.stock=stocks[0]"
						ng-options="item as item.name for item in stocks track by item.id">
							<option value=""></option>
					</select></td>
					<td><input ng-model="productStepUp.note"
						class="form-control form-control-sm"></td>
					<td>
						<button ng-disabled="newProductStepUpForm.$invalid"
							class="btn btn-success btn-sm" ng-click="addProductStepUp()">
							<i class="fa fa-plus"></i>
						</button>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in customerOrderReturn.productStepUps">
					<td>{{$index+1}}</td>
					<td><a data-fancybox="gallery"
						href="{{url+'/0/'+item.product.attachedFile.id}}"> <img
							ng-if="item.product.attachedFile" class="cus-avatar"
							src="{{url+'/1/'+item.product.attachedFile.id}}" />
					</a></td>
					<td>{{item.product.name}}</td>
					<td>{{item.product.code}}</td>
					<td>{{item.product.productCategory.name}}</td>
					<td><span ng-if="item.product.packetSize">
							{{item.quantity/item.product.packetSize|number}} </span></td>
					<td>{{item.quantity}}</td>
					<td>{{item.returnPrice|number}}</td>
					<td>{{item.expirationDate}}</td>
					<td>{{item.stock.name}}</td>
					<td>{{item.note}}</td>
					<td width="100px">
						<button class="btn btn-sm btn-outline-danger"
							ng-click="deleteProductStepUp($index)">
							<i class="fa fa-times"></i>
						</button>

						<button class="btn btn-sm btn-outline-warning"
							ng-click="editProductStepUp($index)">
							<i class="fa fa-edit"></i>
						</button>
					</td>
				</tr>
			</tbody>
		</table>

	</div>

	<div class="border-top pt-2">
		<div>
			<table>
				<tr>
					<td><spring:message code="addCustomerOrderReturn.totalPrice" /></td>
					<td><input ng-value="totalPrice()|number" style="width: 200px"
						class="d-inline-block form-control form-control-sm"
						disabled="disabled"></td>
				</tr>
				<tr>
					<td><spring:message code="addCustomerOrder.totalPayment" /></td>
					<td><input type="number" ng-min="0" ng-max="totalPrice()"  style="width: 200px"
							   ng-model="customerOrderReturn.totalpaid"	   step="any" type="number" required
							   class="d-inline-block form-control form-control-sm"></td>
				</tr>
			</table>
		</div>
		<button
			ng-disabled="!customerOrderReturn||!customerOrderReturn.productStepUps.length>0||CustomerOrderReturnForm.$invalid"
			class="btn btn-warning" ng-click="saveCustomerOrderReturn()">
			<i class="fa fa-edit"></i>
		</button>
	</div>

</div>