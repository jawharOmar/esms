<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    var csrf = '${_csrf.token}';
    var CurrentPage = ${currentPage};
    var ShowPerPage = ${showPerPage};
    var TotalPage = ${totalPage};
    var stockIdCtrl = '${stock.id}';

    var SumTotalCost = '${sumTotalCost}';

    var jsonProductDs = '<spring:escapeBody javaScriptEscape="true">${productDs}</spring:escapeBody>';
    var jsonPriceCategories = '<spring:escapeBody javaScriptEscape="true">${priceCategories}</spring:escapeBody>';

</script>

<div ng-app="stockApp" ng-controller="stockCtrl" ng-init="initStock()">

    <%-- SHOW PER PAGE --%>
    <div class="row align-items-center h-100 mt-5">
        <div class="col mx-auto">
            <div class="card customCard">
                <div class="card-header">
                    <div class="row">
                        <div class="col-2">

                            <select id="stock" onchange="getStock()"
                                    class="form-control form-control-sm rounded-0 border-0">
                                <option value=""><spring:message code="stocks.title"/></option>
                                <c:forEach items="${stocks}" var="item">
                                    <option value="${item.id}" ${item.name == stock.name ? 'selected="selected"' : ''}>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-9">
                            <h4 class="mb-0 text-center text-white">${stock.name}</h4>
                        </div>
                        <div class="col-1">
                            <button id="cus-btn-addstudent" onclick="getAddProduct()"
                                    type="button" class="btn btn-success float-right rounded-0" data-toggle="modal"
                                    data-target="#modal">
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </div>


                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col float-left">
                            <div class="form-inline float-sm-left">
                                <div class="form-group mb-2">
                                    <label for="show_per_page">Show Entry: &nbsp;</label>
                                    <select class="form-control form-control-sm" ng-model="showPerPage"
                                            ng-change="showPerPageAction(showPerPage)" name="showPerPage"
                                            id="show_per_page">
                                        <option ng-value="{{ 10 }}">10</option>
                                        <option ng-value="{{ 25 }}">25</option>
                                        <option ng-value="{{ 50 }}">50</option>
                                        <option ng-value="{{ 100 }}">100</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-inline float-sm-left ml-1">
                                <button class="btn btn-sm btn-outline-info"
                                        ng-click="reloadProducts()">
                                    <i class="fa fa-refresh"></i>
                                </button>

                                <button class="btn btn-sm btn-outline-success ml-1" ng-click="downloadExcel()"><i
                                        class="fa fa-download"></i>
                                </button>
                            </div>

                        </div>
                        <div class="col float-right">
                            <div class="form-inline float-right ml-1">
                                <input type="text" class="form-control form-control-sm"
                                       ng-model="searchByProductCodeNameValue"
                                       ng-change="searchByProductCodeName()" id="searchByProductCodeName"
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

                    <table id="stockTable_new" class="display">
                        <thead>
                        <tr>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id="<spring:message code='stock.code'/>"
                                       ng-model="selectedList['<spring:message code="stock.code"/>']">
                                <label for="<spring:message code='stock.code'/>">
                                    <spring:message code="stock.code"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>

                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id="<spring:message code='stock.name'/>"
                                       ng-model="selectedList['<spring:message code="stock.name"/>']">
                                <label for="<spring:message code='stock.name'/>">
                                    <spring:message code="stock.name"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>

                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id="<spring:message code='stock.category'/>"
                                       ng-model="selectedList['<spring:message code="stock.category"/>']">
                                <label for="<spring:message code='stock.category'/>">
                                    <spring:message code="stock.category"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id="<spring:message code='stock.unitType'/>"
                                       ng-model="selectedList['<spring:message code="stock.unitType"/>']">
                                <label for="<spring:message code='stock.unitType'/>">
                                    <spring:message code="stock.unitType"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id='<spring:message code="stock.stockLevel"/>'
                                       ng-model="selectedList['<spring:message code="stock.stockLevel"/>']">
                                <label for='<spring:message code="stock.stockLevel"/>'>
                                    <spring:message code="stock.stockLevel"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id='<spring:message code="stock.stockPacketLevel"/>'
                                       ng-model="selectedList['<spring:message code="stock.stockPacketLevel"/>']">
                                <label for='<spring:message code="stock.stockPacketLevel"/>'>
                                    <spring:message code="stock.stockPacketLevel"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>

                            <c:if test="${cost_role}">
                                <th>
                                    <input onclick="toggleExport(this)" type="checkbox"
                                           id='<spring:message code="stock.lastPrice"/>'
                                           ng-model="selectedList['<spring:message code="stock.lastPrice"/>']">
                                    <label for='<spring:message code="stock.lastPrice"/>'>
                                        <spring:message code="stock.lastPrice"/>
                                        <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                        <i class="fa fa-check checked" aria-hidden="true"></i>
                                    </label>
                                </th>
                                <th>
                                    <input onclick="toggleExport(this)" type="checkbox"
                                           id='<spring:message code="stock.cost"/>'
                                           ng-model="selectedList['<spring:message code="stock.cost"/>']">
                                    <label for='<spring:message code="stock.cost"/>'>
                                        <spring:message code="stock.cost"/>
                                        <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                        <i class="fa fa-check checked" aria-hidden="true"></i>
                                    </label>
                                </th>
                                <th>
                                    <input onclick="toggleExport(this)" type="checkbox"
                                           id='<spring:message code="stock.totalCost"/>'
                                           ng-model="selectedList['<spring:message code="stock.totalCost"/>']">
                                    <label for='<spring:message code="stock.totalCost"/>'>
                                        <spring:message code="stock.totalCost"/>
                                        <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                        <i class="fa fa-check checked" aria-hidden="true"></i>
                                    </label>
                                </th>
                            </c:if>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id='<spring:message code="stock.packetSize"/>'
                                       ng-model="selectedList['<spring:message code="stock.packetSize"/>']">
                                <label for='<spring:message code="stock.packetSize"/>'>
                                    <spring:message code="stock.packetSize"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id='<spring:message code="stock.minimumStockLevel"/>'
                                       ng-model="selectedList['<spring:message code="stock.minimumStockLevel"/>']">
                                <label for='<spring:message code="stock.minimumStockLevel"/>'>
                                    <spring:message code="stock.minimumStockLevel"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>
                            <th>
                                <input onclick="toggleExport(this)" type="checkbox"
                                       id='<spring:message code="stock.price"/>'
                                       ng-model="selectedList['<spring:message code="stock.price"/>']">
                                <label for='<spring:message code="stock.price"/>'>
                                    <spring:message code="stock.price"/>
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>

                            <th ng-repeat="item in priceCategories">
                                <input id="{{item.name}}"
                                       onclick="toggleExport(this)" type="checkbox" ng-model="selectedList[item.name]"/>
                                <label for="{{item.name}}">
                                    {{item.name}}
                                    <i class="fa fa-times unchecked" aria-hidden="true"></i>
                                    <i class="fa fa-check checked" aria-hidden="true"></i>
                                </label>
                            </th>
                            <th class="cus-not-export" width="62px">
                                <spring:message code="stock.function"/>
                                <i class="fa fa-times unchecked" style="display: inline-block;" aria-hidden="true"></i>
                            </th>
                        </tr>


                        <%--        Search Row--%>
                        <tr>
                            <th colspan="2">
                                <input type="text" class="form-control form-control-sm"
                                       ng-model="searchByProductCodeValue" ng-model-options="{ debounce: 200 }"
                                       ng-change="searchByProductCode()" id="searchByProductCode"
                                       placeholder="<spring:message code="stock.code"/>">
                            </th>
                            <th colspan="3">
                                <input type="text" class="form-control form-control-sm"
                                       ng-model="searchByProductNameValue"
                                       ng-change="searchByProductName()" id="searchByProductName"
                                       placeholder="<spring:message code="stock.name"/>">
                            </th>

                            <th colspan="3">
                                <input type="text" class="form-control form-control-sm"
                                       ng-model="searchByProductCategoryValue"
                                       ng-change="searchByProductCategory()" id="searchByProductCategory"
                                       placeholder="<spring:message code="stock.category"/>">
                            </th>

                            <c:if test="${cost_role}">
                            <th colspan="2">
                                <input type="text" class="form-control form-control-sm" readonly>
                            </th>
                            </c:if>
                            <th colspan="2">
                                <input type="text" class="form-control form-control-sm" readonly>
                            </th>
                            <th ng-if="priceCategories.length>0" colspan="{{priceCategories.length}}">
                                <input type="text" class="form-control form-control-sm" readonly>
                            </th>
                            <c:if test="${cost_role}">
                            <th colspan="1">
                                <input type="text" class="form-control form-control-sm" readonly>
                            </th>
                            </c:if>
                        </thead>
                        <tbody>
                        <tr ng-repeat="productD in productDs">
                            <td>
                                <p style="display: none;">{{productD.code}}</p>
                                <a class="btn btn-sm btn-info" target="_blank"
                                   ng-href="{{contextURL}}/products/printBarcode/{{productD.productId}}">
                                    <i class="fa fa-barcode"></i>
                                </a>
                            </td>
                            <td>{{productD.name}}</td>
                            <td>{{productD.category}}</td>
                            <td>{{productD.unitType}}</td>
                            <td>{{productD.stockLevel | number }} {{productD.unit}}</td>
                            <td>
                                <span class="text-info" ng-if="productD.packetSize!=null">
                                    {{productD.stockLevel/productD.packetSize | number}}
                                </span>
                            </td>

                            <c:if test="${cost_role}">
                                <td>{{productD.lastPrice}}</td>

                                <td>{{productD.cost | number}}</td>
                                <td>
                                    {{productD.cost*productD.stockLevel|number}}
                                </td>
                            </c:if>
                            <td>{{productD.packetSize}}</td>
                            <td>{{productD.minimumStockLevel}}</td>
                            <td>{{productD.price}}</td>
                            <td ng-repeat="itemP in priceCategories">
                                 <span ng-if="itemP.ratio!=null">
                                    {{(productD.cost+productD.cost*itemP.ratio)| number}}
                                </span>
                                <span ng-if="itemP.ratio==null">
                                    <span ng-bind="getSpecialPrice(productD.productPriceCategories,itemP.id)"></span>
                                </span>
                            </td>

                            <td>
                                <div class="cus-table-function-div">
                                    <button class="btn btn-outline-danger btn-sm"
                                            data-product-id="{{productD.productId}}"
                                            onclick="deleteProduct(this)">
                                        <i class="fa fa-times"></i>
                                    </button>
                                    <button class="btn btn-outline-warning btn-sm"
                                            data-product-id="{{productD.productId}}"
                                            onclick="editProduct(this)">
                                        <i class="fa fa-edit"></i>
                                    </button>
                                    <a class="btn btn-sm btn-info" target="_blank"
                                       href="<c:url value="/orderProductStepUps/product/" />{{productD.code}}">
                                        <i class="fa fa-arrow-up"></i>
                                    </a> <a class="btn btn-sm btn-info" target="_blank"
                                            href="<c:url value="/customerOrders/product/" />{{productD.productId}}">
                                    <i class="fa fa-users"></i>
                                </a>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                        <c:if test="${cost_role}">
                            <tfoot>
                            <tr>
                                <th><spring:message code="stock.totalCost"/></th>
                                <th colspan="7"></th>
                                <th>{{sumTotalCost | number:3}}</th>
                                <th colspan="{{priceCategories.length+4}}"></th>
                            </tr>
                            </tfoot>
                        </c:if>

                    </table>

                    <%--    Pagination--%>
                    <br>
                    <ul class="pagination justify-content-center">
                        <li class="page-item" ng-class="(currentPage-1)==0?'disabled':''">
                            <a class="page-link" ng-if="stockId.length==0"
                               ng-href="{{contextURL}}/products/stock?page={{currentPage-1}}&show={{showPerPage}}">Previous</a>
                            <a class="page-link" ng-if="stockId.length!=0"
                               ng-href="{{contextURL}}/products/stock?stockId={{stockId}}&page={{currentPage-1}}&show={{showPerPage}}">Previous</a>
                        </li>
                        <li class="page-item" ng-repeat="i in range " ng-class="i==currentPage?'active':''"
                            ng-if="i<=totalPage">
                            <a class="page-link" ng-if="stockId.length==0"
                               ng-href="{{contextURL}}/products/stock?page={{i}}&show={{showPerPage}}">{{i}}</a>
                            <a class="page-link" ng-if="stockId.length!=0"
                               ng-href="{{contextURL}}/products/stock?stockId={{stockId}}&page={{i}}&show={{showPerPage}}">{{i}}</a>
                        </li>

                        <li class="page-item" ng-class="currentPage+1>totalPage?'disabled':''">
                            <a class="page-link" ng-if="stockId.length==0"
                               ng-href="{{contextURL}}/products/stock?page={{currentPage+1}}&show={{showPerPage}}">Next</a>
                            <a class="page-link" ng-if="stockId.length!=0"
                               ng-href="{{contextURL}}/products/stock?stockId={{stockId}}&page={{currentPage+1}}&show={{showPerPage}}">Next</a>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>

<style>


    .dt > thead > tr > th[class*="sort"]::after {
        display: none
    }

    .dt > thead > tr > th[class*="sort"]::before {
        display: none
    }

    table > thead > tr > th {
        padding: 3px;
    }

    table.dataTable thead .sorting,
    table.dataTable thead .sorting_asc,
    table.dataTable thead .sorting_desc {
        background: none;
    }

    table.dataTable tbody tr td {
        padding: 1px;
    }

</style>


