<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<script type="text/javascript">
	var jsonCustomerOrder = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomerOrder}</spring:escapeBody>';
	var jsonStocks = '<spring:escapeBody  javaScriptEscape="true">${jsonStocks}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
	var url = "<c:url value="/attachedFiles" />";
</script>

<div ng-app="app" ng-controller="addCustomerOrder" ng-init="init()">


	<div id="loading-div" if-loading>
		<img id=""
			src="<c:url value="/resources/img/loading.gif?${version}" />" />
	</div>

	<h2>
		<spring:message code="editCustomerOrder.title" />
		${currentDate}
	</h2>

	<div class="container-fluid">
		<div class="row">
			<div class="col-2">
				<label class="checkboxLabel"
					ng-class="showLoan?'selectedLabel':'unSelectedLabel'"
					for="showLoan"> <spring:message
						code="addCustomerOrder.showLoan" />
				</label> <input type="checkbox" id="showLoan" value="false"
					ng-model="showLoan" style="display: none;">
			</div>
			<div class="col-3">
				<div class="form-inline">
					<div class="form-group mr-1">
						<a target="_blank" class="btn btn-outline-info"
							href="<c:url value="/customerOrders/" />{{customerOrder.id}}/{{showLoan}}">
							<i class="fa fa-print"></i>
						</a>
					</div>
					<div class="form-group mr-1">
						<button title="<spring:message code="addCustomerOrder.refresh" />"
							class="btn btn-outline-success"
							onClick="window.location.reload()">
							<i class="fas fa-retweet"></i>
						</button>
					</div>
					<div class="form-group">
						<input title="<spring:message code="addCustomerOrder.search" />"
							class="form-control form-control" ng-model="invoiceId"
							ng-enter="getCustomerOrder()" type="number">
					</div>
				</div>

			</div>
		</div>
		<hr>
		<div id="freeze">
			<div style="width: 800px">
				<table class="table table-sm cus-table-borderless" ng-form
					name="form">
					<tbody>

						<tr>
							<td><spring:message code="editCustomerOrder.customerName" /></td>
							<td><input readonly="readonly"
								class="form-control form-control-sm"
								ng-model="customerOrder.customer.fullName"></td>
						</tr>

						<tr>
							<td><spring:message code="addCustomerOrder.receivedBy" /></td>
							<td><input class="form-control form-control-sm"
								ng-model="customerOrder.receivedBy"></td>
						</tr>
						<tr>
							<td><spring:message code="addCustomerOrder.phone" /></td>
							<td><input required="required" readonly="readonly"
								class="form-control form-control-sm"
								ng-model="customerOrder.customer.phone"></td>
						</tr>
						<tr>
							<td><spring:message code="addCustomerOrder.project" /></td>
							<td><select class="form-control form-control-sm"
								ng-model="customerOrder.customerProject.id"
								ng-options="item.id as item.name for item in customerOrder.customer.customerProjects">
									<option value="">
										<spring:message code="addCustomerOrder.choose" />
									</option>
							</select></td>
						</tr>
					</tbody>
				</table>
			</div>

			<hr>
			<table>
				<tr>
					<td style="white-space: nowrap;"><spring:message
							code="addCustomerOrder.stock" /></td>
					<td width="99%" class="py-2"><select
						class="form-control form-control-sm" ng-model="selectedStock"
						ng-init="selectedStock=stocks[0]"
						ng-options="item as item.name for item in stocks track by item.id">
							<option value=""></option>
					</select></td>
				</tr>
			</table>

			<table id="productDetailTable" class="table table-bordered table-sm">
				<tbody>
					<tr>
						<th>#</th>
						<th><spring:message code="addCustomerOrder.image" /></th>
						<th><spring:message code="addCustomerOrder.stock" /></th>
						<th><spring:message code="addCustomerOrder.code" /></th>
						<th><spring:message code="addCustomerOrder.name" /></th>
						<th><spring:message code="addCustomerOrder.packetQuantity" /></th>
						<th><spring:message code="addCustomerOrder.quantity" /></th>
						<th><spring:message code="addCustomerOrder.price" /></th>
						<th><spring:message code="addCustomerOrder.totalPrice" /></th>
						<th><spring:message code="addCustomerOrder.unitType" /></th>
						<th><spring:message code="addCustomerOrder.function" /></th>
					</tr>

					<tr ng-form name="addProduct">
						<th>&nbsp;</th>
						<th>&nbsp;</th>
						<th><select ng-disabled="selectedStock!=null" required
							class="form-control form-control-sm" ng-model="product.stock"
							ng-options="item as item.name for item in stocks track by item.id">
								<option value="{}"></option>
						</select></th>
						<th>
							<div class="input-group">
								<div class="input-group-prepend">
									<button class="btn btn-info btn-sm" ng-if="product.productId"
										ng-click="getProductCustomerOrderDetail()">
										<i class="fa fa-info"></i>
									</button>
								</div>
								<input ng-disabled="!product.stock.id" tabindex="1"
									ng-change="searchByProductCodeOrName()"
									ng-enter="getProduct(product.code)" id="autoselect"
									class="form-control form-control-sm" ng-model="product.code">
							</div>
						</th>
						<th><input required tabindex="2" id="productName"
							ng-keypress="getKeys($event)"
							class="form-control form-control-sm" ng-model="product.name"
							readonly></th>
						<th><input ng-disabled="!product.packetSize" tabindex="3"
							type="number" class="form-control form-control-sm"
							ng-model="product.packetQuantity"></th>
						<th><input
							ng-disabled="product.packetQuantity||priceToWeigth" min="0"
							max="{{product.stockLevel}}" tabindex="3" type="number" required
							class="form-control form-control-sm" ng-model="product.quantity"
							placeholder={{product.stockLevel}}>{{product.packetSize}}
							<small class="text-success" ng-if="showCost">{{product.cost}}
						</small></th>
						<th><input tabindex="4" type="number" step="any"
							class="form-control form-control-sm" ng-model="product.price"
							title="<spring:message code="addCustomerOrder.lastPrice" /> :{{product.lastPrice}}"></th>
						<th>
							<div class="form-row">
								<div class="col-1 d-inline-flex">
									<input type="checkbox" ng-model="priceToWeigth"> <span
										ng-hide="priceToWeigth">
										{{product.price*+product.quantity|number}} </span>
								</div>
								<div class="col-11">
									<input ng-show="priceToWeigth" tabindex="5" type="number"
										step="any" class="form-control form-control-sm"
										ng-model="productTotalPrice">
								</div>
							</div>


						</th>
						<th><input tabindex="5" class="form-control form-control-sm"
							ng-model="product.unitType" readonly></th>
						<th>
							<button tabindex="6" ng-disabled="addProduct.$invalid"
								class="btn btn-sm btn-primary"
								ng-click="addCustomerOrderDetail()">
								<i class="fa fa-plus"></i>
							</button>
						</th>

					</tr>
				</tbody>
				<tbody>
					<tr ng-repeat="item in customerOrder.customerOrderDetails">
						<td>{{$index+1}}</td>
						<td><a data-fancybox="gallery"
							href="{{url+'/0/'+item.product.attachedFile.id}}"> <img
								ng-if="item.product.attachedFile" class="cus-avatar"
								src="{{url+'/1/'+item.product.attachedFile.id}}" />
						</a></td>
						<td>{{item.stock.name}}</td>
						<td>{{item.productCode}}</td>
						<td>{{item.productName}}</td>
						<td><span ng-if="item.product.packetSize">
								{{item.quantity/item.product.packetSize|number}} </span></td>
						<td>{{item.quantity}}</td>
						<td>{{item.price}}</td>
						<td>{{item.price*item.quantity|number}}</td>
						<td>&nbsp;</td>
						<td width="80px">
							<button class="btn btn-sm btn-outline-danger"
								ng-click="removeCustomerOrderDetail($index)">
								<i class="fa fa-times"></i>
							</button>

							<button class="btn btn-sm btn-outline-warning"
								ng-click="editCustomerOrderDetail($index)">
								<i class="fa fa-edit"></i>
							</button>
						</td>
					</tr>
				</tbody>
			</table>
			<hr>
			<div>
				<table style="width: 800px">
					<tr>
						<td><spring:message code="addCustomerOrder.totalPrice" /></td>
						<td><input id="totalPriceInput"
							ng-value="checkTotalPrice=totalPrice()|number : 3"
							ng-model="checkTotalPrice" class="form-control form-control-sm"
							readonly>
							<button class="btn btn-sm btn-info" ng-click="setTotalPayment()"
								ng-disabled="discountRatio==null">
								<i class="fa fa-arrow-down"></i>
							</button></td>
					</tr>
					<tr>
						<td><spring:message code="addCustomerOrder.discount" /> <input
							type="checkbox" ng-model="selectRatio"> <spring:message
								code="addCustomerOrder.ratio" /> :</td>
						<td>
							<div class="row">

								<div class="input-group input-group-sm col-6 float-left mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="sizing-sm">$</span>
									</div>
									<input min="0" type="number" step="any"
										ng-disabled="selectRatio || totalPrice()<=0"
										ng-change="calculateTotalPayment()" ng-required="required"
										ng-model="customerOrder.discount" ng-max="totalPrice()"
										class="form-control form-control-sm" />
								</div>
								<div class="input-group input-group-sm col-6 float-left mb-1">
									<div class="input-group-prepend">
										<span class="input-group-text" id="sizinddg-sm">%</span>
									</div>
									<input min="0" type="number" step="1" ng-max="100"
										ng-change="calculateDiscount()"
										ng-disabled="!selectRatio || totalPrice()<=0"
										ng-value="!selectRatio?discountRatio=0:0"
										ng-required="required" ng-model="discountRatio"
										class="form-control form-control-sm" />
								</div>

							</div>
							<div class="row">
								<div class="col-6">
									<input ng-model="customerOrder.note"
										placeholder="<spring:message code="addCustomerOrder.note" />"
										class="form-control form-control-sm">

								</div>
							</div>
						</td>

					</tr>
					<tr>
						<td><spring:message code="addCustomerOrder.totalPayment" /></td>
						<td><input required="required" step="any" type="number"
							ng-model="customerOrder.totalPayment"
							class="form-control form-control-sm"></td>
					</tr>


				</table>
			</div>

			<button
				ng-disabled="!form.$valid || customerOrder.totalPayment>paymentValidation  || customerOrder.customerOrderDetails.length==0 || discountRatio<0"
				class="btn btn-sm btn-warning" ng-click="addCustomerOrder()">
				<i class="fa fa-edit"></i>
			</button>
		</div>

	</div>
</div>