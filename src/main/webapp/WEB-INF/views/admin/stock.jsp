<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	var csrf = '${_csrf.token}';
	var CurrentPage = '<spring:escapeBody javaScriptEscape="true">${currentPage}</spring:escapeBody>';
	var ShowPerPage = '<spring:escapeBody javaScriptEscape="true">${showPerPage}</spring:escapeBody>';
	var TotalPage = '<spring:escapeBody javaScriptEscape="true">${totalPage}</spring:escapeBody>';
	var stockIdCtrl = '${stock.id}';
	var SumTotalCost = '${sumTotalCost}';
	var jsonProductDs = '<spring:escapeBody javaScriptEscape="true">${productDs}</spring:escapeBody>';
	var jsonPriceCategories = '<spring:escapeBody javaScriptEscape="true">${priceCategories}</spring:escapeBody>';
</script>

<div ng-controller="stockCtrl" ng-init="initStock()">

	<%-- SHOW PER PAGE --%>
	<div class="align-items-center h-100 mt-5">
		<div class="col mx-auto">
			<div class="card customCard">
				<div class="card-header">
					<div class="row">
						<div class="col-2">

							<select id="stock" onchange="getStock()"
								class="form-control form-control-sm rounded-0 border-0">
								<option value=""><spring:message code="stocks.title" /></option>
								<c:forEach items="${stocks}" var="item">
									<option value="${item.id}"
										${item.name == stock.name ? 'selected="selected"' : ''}>${item.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-9">
							<h4 class="mb-0 text-center text-white">${stock.name}</h4>
						</div>
						<div class="col-1">
							<button id="cus-btn-addstudent" onclick="getAddProduct()"
								type="button" class="btn btn-success float-right rounded-0"
								data-toggle="modal" data-target="#modal">
								<i class="fa fa-plus"></i>
							</button>
						</div>
					</div>


				</div>
				<div class="card-body">
					<div class="">
						<div class="col float-left">
							<div class="form-inline float-sm-left">
								<div class="form-group mb-2">
									<label for="show_per_page">Show Entry: &nbsp;</label> <select
										class="form-control form-control-sm" ng-model="showPerPage"
										ng-change="showPerPageAction(showPerPage)" name="showPerPage"
										id="show_per_page">
										<option ng-value="{{ 10 }}">10</option>
										<option ng-value="{{ 25 }}">25</option>
										<option ng-value="{{ 50 }}">50</option>
										<option ng-value="{{ 100 }}">100</option>
										<option ng-value="{{ 1000 }}">1000</option>
									</select>
								</div>
							</div>
							<div class="form-inline float-sm-left ml-1">
								<button class="btn btn-sm btn-outline-info"
									ng-click="reloadProducts()">
									<i class="fas fa-retweet"></i>
								</button>

								<button class="btn btn-sm btn-outline-success ml-1"
									ng-click="downloadExcel()">
									<i class="fa fa-download"></i>
								</button>
							</div>

						</div>
						<div class="col float-right">
							<div class="form-inline float-right ml-1">
								<input type="text" class="form-control form-control-sm"
									ng-model="searchByProductCodeNameValue"
									ng-change="searchByProductCodeName()"
									id="searchByProductCodeName"
									placeholder="<spring:message code="report.search"/>">
							</div>

							<div class="form-inline float-right ml-1">
								<button ng-click="generalSearch()" class="btn btn-info btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>


					<br>

					<div id="table-div">
						<table id="stockTable_new" class="display compact"
							style="width: 100%">
							<thead>
								<tr>
									<th><spring:message code="stock.code" /></th>
									<th><spring:message code='stock.name' /></th>
									<th><spring:message code="stock.category" /></th>
									<th><spring:message code='stock.unitType' /></th>
									<th><spring:message code="stock.stockLevel" /></th>
									<th><spring:message code="stock.stockPacketLevel" /></th>
									<c:if test="${cost_role}">
										<th><spring:message code="stock.lastPrice" /></th>
										<th><spring:message code="stock.cost" /></th>
										<th><spring:message code="stock.totalCost" /></th>
									</c:if>
									<th><spring:message code="stock.packetSize" /></th>
									<th><spring:message code="stock.minimumStockLevel" /></th>
									<th><spring:message code="stock.price" /></th>
									<th ng-repeat="item in priceCategories">{{item.name}}</th>
									<th><spring:message code="stock.function" /></th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="productD in productDs">
									<td>
										<p style="display: none;">{{productD.code}}</p> <a
										class="btn btn-sm btn-info" target="_blank"
										ng-href="{{contextURL}}/products/printBarcode/{{productD.productId}}">
											<i class="fa fa-barcode"></i>
									</a>
									</td>
									<td>{{productD.name}}</td>
									<td>{{productD.category}}</td>
									<td>{{productD.unitType}}</td>
									<td>{{productD.stockLevel | number }} <span
										class="text-info"> {{productD.unit}} </span>
									</td>
									<td><span class="text-info"
										ng-if="productD.packetSize!=null">
											{{productD.stockLevel/productD.packetSize | number}} </span></td>

									<c:if test="${cost_role}">
										<td>{{productD.lastPrice}}</td>

										<td>{{productD.cost | number}}</td>
										<td>{{productD.cost*productD.stockLevel|number}}</td>
									</c:if>
									<td>{{productD.packetSize}}</td>
									<td>{{productD.minimumStockLevel}}</td>
									<td>{{productD.price}}</td>
									<td ng-repeat="itemP in priceCategories"><span
										ng-if="itemP.ratio!=null">
											{{(productD.cost+productD.cost*itemP.ratio)| number}} </span> <span
										ng-if="itemP.ratio==null"> <span
											ng-bind="getSpecialPrice(productD.productPriceCategories,itemP.id)"></span>
									</span></td>

									<td>
										<div class="dropdown">
											<button class="btn btn-sm btn-secondary dropdown-toggle"
												type="button" id="d${item.id}" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false">
												<spring:message code="function" />
											</button>
											<div class="dropdown-menu" aria-labelledby="d${item.id}">

												<button class="dropdown-item"
													data-product-id="{{productD.productId}}"
													onclick="deleteProduct(this)">
													<spring:message code="delete" />
												</button>

												<button class="dropdown-item"
													data-product-id="{{productD.productId}}"
													onclick="editProduct(this)">
													<spring:message code="edit" />
												</button>

												<a class="dropdown-item" target="_blank"
													href="<c:url value="/orderProductStepUps/product/" />{{productD.code}}">
													<spring:message code="stock.productOrders" />
												</a> <a class="dropdown-item" target="_blank"
													href="<c:url value="/customerOrders/product/" />{{productD.productId}}">
													<spring:message code="stock.productSales" />
												</a>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
							<c:if test="${cost_role}">
								<tfoot>
									<tr>
										<th><spring:message code="stock.totalCost" /></th>
										<th colspan="7"></th>
										<th>{{sumTotalCost | number:3}}</th>
										<th colspan="{{priceCategories.length+4}}"></th>
									</tr>
								</tfoot>
							</c:if>

						</table>

					</div>

					<%--    Pagination--%>
					<br>
					<ul class="pagination justify-content-center">
						<li class="page-item" ng-class="(currentPage-1)==0?'disabled':''">
							<a class="page-link" ng-if="stockId.length==0"
							ng-href="{{contextURL}}/products/stock?page={{currentPage-1}}&show={{showPerPage}}"><spring:message
									code="js.datatable.previous" /></a> <a class="page-link"
							ng-if="stockId.length!=0"
							ng-href="{{contextURL}}/products/stock?stockId={{stockId}}&page={{currentPage-1}}&show={{showPerPage}}"><spring:message
									code="js.datatable.previous" /></a>
						</li>
						<li class="page-item" ng-repeat="i in range "
							ng-class="i==currentPage?'active':''" ng-if="i<=totalPage">
							<a class="page-link" ng-if="stockId.length==0"
							ng-href="{{contextURL}}/products/stock?page={{i}}&show={{showPerPage}}">{{i}}</a>
							<a class="page-link" ng-if="stockId.length!=0"
							ng-href="{{contextURL}}/products/stock?stockId={{stockId}}&page={{i}}&show={{showPerPage}}">{{i}}</a>
						</li>

						<li class="page-item"
							ng-class="currentPage+1>totalPage?'disabled':''"><a
							class="page-link" ng-if="stockId.length==0"
							ng-href="{{contextURL}}/products/stock?page={{currentPage+1}}&show={{showPerPage}}"><spring:message
									code="js.datatable.next" /></a> <a class="page-link"
							ng-if="stockId.length!=0"
							ng-href="{{contextURL}}/products/stock?stockId={{stockId}}&page={{currentPage+1}}&show={{showPerPage}}"><spring:message
									code="js.datatable.next" /></a></li>
					</ul>

				</div>
			</div>
		</div>
	</div>
</div>

<style>
.dt>thead>tr>th[class*="sort"]::after {
	display: none
}

.dt>thead>tr>th[class*="sort"]::before {
	display: none
}

table>thead>tr>th {
	padding: 3px;
}

table.dataTable thead .sorting, table.dataTable thead .sorting_asc,
	table.dataTable thead .sorting_desc {
	background: none;
}

table.dataTable tbody tr td {
	padding: 1px;
}
</style>


