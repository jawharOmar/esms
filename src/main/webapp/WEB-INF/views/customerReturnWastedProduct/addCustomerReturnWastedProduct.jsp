<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
    var jsonCustomers = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomers}</spring:escapeBody>';
    var jsonProducts = '<spring:escapeBody  javaScriptEscape="true">${jsonProducts}</spring:escapeBody>';
    var jsonStocks = '<spring:escapeBody  javaScriptEscape="true">${jsonStocks}</spring:escapeBody>';
    var jsonWastedProduct = '<spring:escapeBody  javaScriptEscape="true">${jsonWastedProduct}</spring:escapeBody>';
    var csrf = '${_csrf.token}';
</script>

<div ng-app="app" ng-controller="appCTRL" ng-init="init()">
    <h2>
        <spring:message code="customerWastedProduct.title"/>
    </h2>
    <hr>
    <button class="btn btn-lg btn-outline-success"
            onClick="window.location.reload()">
        <i class="fa fa-refresh"></i>
    </button>

    <div id="freeze">

        <table class="table table-sm cus-table-borderless" ng-form name="form">
            <tbody>
            <tr>
                <td><spring:message code="addCustomerOrder.customerName"/></td>
                <td><input required="required" id="customer-autoselect"
                           class="form-control form-control-sm"
                           ng-model="wastedProduct.customer.fullName"></td>
            </tr>

            <tr>
                <td><spring:message code="addCustomerOrder.phone"/></td>
                <td><input required="required" readonly="readonly"
                           class="form-control form-control-sm"
                           ng-model="wastedProduct.customer.phone"></td>
            </tr>
            <tr>
                <td><spring:message code="addOrderPreProduct.reference"/></td>
                <td><input class="form-control form-control-sm"
                           ng-model="wastedProduct.reference"></td>
            </tr>

            </tbody>
        </table>

        <div class="border-top">
            <table id="detail-table" class="table table-bordered">
                <thead>
                <tr>
                    <td width="5%">#</td>
                    <td><spring:message
                            code="addOrderProductStepUp.pCode"/></td>
                    <td><spring:message
                            code="addOrderPreProduct.pName"/></td>
                    <td><spring:message
                            code="stock.category"/></td>
                    <td><spring:message
                            code="addOrderPreProduct.quantity"/></td>
                    <td><spring:message
                            code="addOrderPreProduct.price"/></td>
                    <td><spring:message
                            code="addCustomerOrderReturn.stock"/></td>
                    <td><spring:message code="addOrderPreProduct.note"/></td>
                    <td>&nbsp;</td>
                </tr>
                <tr ng-form="newPreProductForm">
                    <td>#</td>
                    <td><input id="autoselect" class="form-control form-control-sm"
                               ng-model="customerWastedProducts.product.code"></td>
                    <td><input ng-model="customerWastedProducts.product.name" readonly
                               class="form-control form-control-sm"></td>
                    <td><input ng-model="customerWastedProducts.product.productCategory.name" readonly
                               class="form-control form-control-sm"></td>
                    <td><input required type="number" min="1"
                               ng-model="customerWastedProducts.quantity"
                               class="form-control form-control-sm"></td>
                    <td><input required type="number" step="any"
                               ng-model="customerWastedProducts.price" class="form-control form-control-sm">
                        <span class="text-info">{{customerWastedProducts.price* customerWastedProducts.quantity|number}}</span>
                    </td>
                    <td><select  required  class="form-control form-control-sm" ng-model="customerWastedProducts.stock"
                                ng-options="item as item.name for item in stocks track by item.id">
                        <option value="{}"></option>
                    </select></td>
                    <td><input ng-model="customerWastedProducts.note"
                               class="form-control form-control-sm"></td>
                    <td>
                        <button ng-disabled="newPreProductForm.$invalid"
                                class="btn btn-success bnt-sm rounded-circle"
                                ng-click="addWastedProduct()">
                            <i class="fa fa-plus"></i>
                        </button>
                    </td>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="item in wastedProduct.customerWastedProducts">
                    <td>{{$index+1}}</td>
                    <td>{{item.product.code}}</td>
                    <td>{{item.product.name}}</td>
                    <td>{{item.product.productCategory.name}}</td>
                    <td>{{item.quantity}}</td>
                    <td>{{item.price}}</td>
                    <td>{{item.stock.name}}</td>
                    <td>{{item.note}}</td>
                    <td>
                        <button class="btn btn-danger btn-sm rounded-circle"
                                ng-click="deleteWastedProduct($index)">
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
                        <td><spring:message code="addOrderPreProduct.totalPrice"/></td>
                        <td><input ng-value="totalPrice()|number"
                                   style="width: 200px"
                                   class="d-inline-block form-control form-control-sm"
                                   disabled="disabled"></td>
                    </tr>
                </table>
            </div>
            <button
                    ng-disabled="!form.$valid || !wastedProduct.customerWastedProducts.length>0 || !wastedProduct.customer.fullName.length>0"
                    class="btn btn-success" ng-click="saveCustomerWastedProduct()">
                <i class="fa fa-plus"></i>
            </button>
        </div>
    </div>

</div>