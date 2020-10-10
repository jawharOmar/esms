<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<script type="text/javascript">
	var jsonStocks = '<spring:escapeBody  javaScriptEscape="true">${jsonStocks}</spring:escapeBody>';
	var jsonStockTransfer = '<spring:escapeBody  javaScriptEscape="true">${jsonStockTransfer}</spring:escapeBody>';
	var jsonProducts = '<spring:escapeBody  javaScriptEscape="true">${jsonProducts}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
</script>

<div ng-app="app" ng-controller="appCTRL" ng-init="init()">

	<pre>
	</pre>

	<div id="loading-div" if-loading>
		<img id=""
			src="<c:url value="/resources/img/loading.gif?${version}" />" />
	</div>

	<h2>
		<spring:message code="addStockTransfer.title" />
		${currentDate}
	</h2>

	<table class="w-100">
		<tr>
			<td><spring:message code="addStockTransfer.from" /></td>
			<td><spring:message code="addStockTransfer.to" /></td>
		</tr>
		<tr ng-form="stockTranferForm">
			<td><select required name="from"
				class="form-control form-control-sm" ng-model="stockTransfer.from"
				ng-options="item as item.name for item in stocks track by item.id">
			</select></td>
			<td><select required name="to"
				class="form-control form-control-sm" ng-model="stockTransfer.to"
				ng-options="item as item.name for item in stocks track by item.id">
			</select></td>
		</tr>
	</table>

	<hr>
	<table class="table table-bordered table-sm">
		<thead>
			<tr>
				<th>#</th>
				<th><spring:message code="addStockTransfer.pCode" /></th>
				<th><spring:message code="addStockTransfer.pName" /></th>
				<th><spring:message code="addStockTransfer.amount" /></th>
				<th><spring:message code="addStockTransfer.function" /></th>
			</tr>
			<tr ng-form="addStockLevelDetailForm">
				<td>&nbsp;</td>
				<td><input ng-disabled="!stockTransfer.from.id" required
					name="code" id="autoselect" class="form-control form-control-sm"
					ng-model="product.code" ng-keypress="getProduct($event)"></td>
				<th><input required class="form-control form-control-sm"
					disabled="disabled" ng-model="product.name" name="name"></th>
				<th>

					<div class="form-row">
					<input required type="number"
					class="form-control form-control-sm" ng-min="0"
					placeholder="{{product.stockLevel}}" ng-max="product.stockLevel"
					ng-model="product.quantity" name="amount">
					</div>



				</th>
				<th><button ng-disabled="addStockLevelDetailForm.$invalid"
						ng-click="addStockTransferDetail()" class="btn btn-sm btn-success">
						<i class="fa fa-plus"></i>
					</button></th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="item in stockTransfer.stockTransferDetails">
				<td>{{$index+1}}</td>
				<td>{{item.product.code}}</td>
				<td>{{item.product.name}}</td>
				<td>{{item.amount|number}}</td>
				<td>
					<button class="btn btn-sm btn-outline-danger"
						ng-click="removeStockTransferDetail($index)">
						<i class="fa fa-times"></i>
					</button>
				</td>
			</tr>
		</tbody>
	</table>



	<div class="alert alert-success" id="pk_info" style="display: none">
		<div class="row">
			<div class="col">
				<p><b>{{stockTransfer.from.name}}</b></p>
				<p><b>Product Name : </b>{{product.name}}</p>
				<p><b>Stock Level :</b> {{product.stockLevel}}</p>
				<div ng-if="product.packetSize">
				<p><b>Packet Size :</b> {{product.packetSize}}</p>
				<p><b>Available Packets :</b>{{product.stockLevel/product.packetSize}}</p>
				<div  ng-if="product.stockLevel/product.packetSize>=1">	<b>Transfer By Packet :</b> <input type="checkbox" ng-model="pkt" />
					<input type="number" ng-model="pknum" class="form-control-sm" ng-keyup="product.quantity=product.packetSize*pknum" placeholder="Packet Amount" ng-if="pkt"/>
				</div>
				</div>
			</div>
		</div>

	</div>

	<button ng-click="saveStockTransfer()"
		ng-disabled="!stockTransfer.from.id||!stockTransfer.to.id||stockTransfer.from.id==stockTransfer.to.id||!stockTransfer.stockTransferDetails.length>0"
		class="btn btn-sm btn-success">
		<i class="fa fa-save"></i>
	</button>

</div>