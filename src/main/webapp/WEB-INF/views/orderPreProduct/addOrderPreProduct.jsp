<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
	var jsonOrderPreProduct = '<spring:escapeBody  javaScriptEscape="true">${jsonOrderPreProduct}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
</script>

<div ng-app="app" ng-controller="appCTRL" ng-init="init()">
	<h2>
		<spring:message code="addOrderPreProduct.title" />
	</h2>
	<hr>
	<button class="btn btn-lg btn-outline-success"
		onClick="window.location.reload()">
		<i class="fa fa-refresh"></i>
	</button>

	<div id="freeze">

		<table class="table table-sm cus-table-borderless">
			<tbody ng-form="orderProductStepUpForm">
				<tr>
					<td><spring:message code="addOrderPreProduct.companyName" /></td>
					<td><input ng-model="orderPreProduct.companyName"
						class="form-control  form-control-sm"></td>
				</tr>

				<tr>
					<td><spring:message code="addOrderPreProduct.companyAddress" /></td>
					<td><input ng-model="orderPreProduct.companyAddress"
						class="form-control  form-control-sm"></td>
				</tr>

				<tr>
					<td><spring:message code="addOrderPreProduct.phone" /></td>
					<td><input ng-model="orderPreProduct.phone"
						class="form-control  form-control-sm"></td>
				</tr>
				<tr>
					<td><spring:message code="addOrderPreProduct.reference" /></td>
					<td><input ng-model="orderPreProduct.reference"
						class="form-control  form-control-sm"></td>
				</tr>
			</tbody>
		</table>

		<div class="border-top">
			<table id="detail-table" class="table table-bordered">

				<thead>
					<tr>
						<td  width="5%">#</td>
						<td width="10%"><spring:message code="addOrderPreProduct.image" /></td>
						<td width="5%"><spring:message
								code="addOrderProductStepUp.pCode" /></td>
						<td><spring:message
								code="addOrderPreProduct.pName" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.weight" /></td>
						<td width="3%"><spring:message
								code="addOrderPreProduct.packetQuantity" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.packetSize" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.quantity" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.secondUnitPrice" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.secondPrice" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.unitPrice" /></td>
						<td width="5%"><spring:message
								code="addOrderPreProduct.price" /></td>
						<td><spring:message code="addOrderPreProduct.note" /></td>
						<td>&nbsp;</td>
					</tr>
					<tr ng-form="newPreProductForm">
						<td>#</td>
						<td><input id="fileSelect" type="file"
							accept="image/x-png,image/gif,image/jpeg,image/jpg"
							class="form-control form-control-sm" name="file" /></td>
						<td><input ng-model="preProduct.code"
							class="form-control form-control-sm"></td>
						<td><input ng-model="preProduct.name"
							class="form-control form-control-sm"></td>
						<td><input ng-model="preProduct.weight"
							class="form-control form-control-sm"></td>
						<td width="7%"><input type="number" min="0"
							ng-model="preProduct.packetQuantity"
							class="form-control form-control-sm"></td>
						<td><input type="number" min="0"
							ng-model="preProduct.packetSize"
							class="form-control form-control-sm"></td>
						<td><input required type="number" min="1"
							ng-model="preProduct.quantity"
							class="form-control form-control-sm"></td>
						<td><input required type="number" step="any"
							ng-model="secondUnitPrice" class="form-control form-control-sm"></td>
						<td><input readonly disabled type="number" step="any"
							ng-model="preProduct.secondPrice"
							class="form-control form-control-sm"></td>
						<td><input required type="number" step="any"
							ng-model="unitPrice" class="form-control form-control-sm"></td>
						<td><input disabled required type="number" step="any"
							ng-model="preProduct.price" class="form-control form-control-sm"></td>
						<td><input ng-model="preProduct.note"
							class="form-control form-control-sm"></td>
						<td>
							<button ng-disabled="newPreProductForm.$invalid"
								class="btn btn-success bnt-sm rounded-circle"
								ng-click="addPreProduct()">
								<i class="fa fa-plus"></i>
							</button>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in orderPreProduct.preProducts">
						<td>{{$index+1}}</td>
						<td><show-image file="files[$index]"> </show-image></td>
						<td>{{item.code}}</td>
						<td>{{item.name}}</td>
						<td>{{item.weight}}</td>
						<td>{{item.packetQuantity}}</td>
						<td>{{item.packetSize}}</td>
						<td>{{item.quantity}}</td>
						<td>{{item.secondPrice/item.quantity|number}}</td>
						<td>{{item.secondPrice}}</td>
						<td>{{item.price/item.quantity|number}}</td>
						<td>{{item.price}}</td>
						<td>{{item.note}}</td>
						<td>
							<button class="btn btn-danger btn-sm rounded-circle"
								ng-click="deletePreProduct($index)">
								<i class="fa fa-times"></i>
							</button>
						</td>
					</tr>
				</tbody>
			</table>

		</div>

		<div class="border-top pt-2" ng-form name="form">
			<div>
				<table>
					<tr>
						<td><spring:message code="addOrderPreProduct.totalPrice" /></td>
						<td><input ng-value="totalPrice()|number"
							style="width: 200px"
							class="d-inline-block form-control form-control-sm"
							disabled="disabled"></td>
					</tr>

					<tr>
						<td><spring:message
								code="addOrderPreProduct.totalSecondPrice" /></td>
						<td><input ng-value="totalSecondPrice()|number"
							style="width: 200px"
							class="d-inline-block form-control form-control-sm"
							disabled="disabled"></td>
					</tr>

					<tr>
						<td><spring:message
								code="addOrderPreProduct.totalSecondPayment" /></td>
						<td><input name="totalSecondPayment" type="number"
							ng-max="totalSecondPrice()|number" step="any"
							ng-model="orderPreProduct.totalSecondPayment"
							style="width: 200px"
							class="d-inline-block form-control form-control-sm"></td>
					</tr>

					<tr>
						<td><spring:message
								code="addOrderPreProduct.totalSecondRemain" /></td>
						<td><input
							ng-value="totalSecondPrice()-orderPreProduct.totalSecondPayment|number"
							style="width: 200px"
							class="d-inline-block form-control form-control-sm"
							disabled="disabled"></td>
					</tr>

				</table>
			</div>
			<button
				ng-disabled="!form.$valid || !orderPreProduct.preProducts.length>0"
				class="btn btn-success" ng-click="saveOrderPreProduct()">
				<i class="fa fa-plus"></i>
			</button>
		</div>
	</div>

</div>