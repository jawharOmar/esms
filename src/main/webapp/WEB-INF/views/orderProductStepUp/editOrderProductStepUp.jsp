<%@page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd"/>


<script type="text/javascript">
    var jsonVendors = '<spring:escapeBody  javaScriptEscape="true">${jsonVendors}</spring:escapeBody>';
    var jsonOrderProductStepUp = '<spring:escapeBody  javaScriptEscape="true">${jsonOrderProductStepUp}</spring:escapeBody>';
    var jsonProductDs = '<spring:escapeBody  javaScriptEscape="true">${jsonProductDs}</spring:escapeBody>';
    var jsonStocks = '<spring:escapeBody  javaScriptEscape="true">${jsonStocks}</spring:escapeBody>';
    var csrf = '${_csrf.token}';
    var url = "<c:url value="/attachedFiles" />";
    var urlCustomerOrder = '<c:url value="/customerOrders/productStepUp/"/>';
</script>


<div ng-app="app" ng-controller="appCTRL" ng-init="init()">

    <div id="loading-div" if-loading>
        <img id=""
             src="<c:url value="/resources/img/loading.gif?${version}" />"/>
    </div>

    <h2>
        <spring:message code="editOrderProductStepUp.title"/>
        ${currentDate}
    </h2>
    <div>
        <button class="btn btn-lg btn-outline-warning"
                onClick="window.location.reload()">
            <i class="fas fa-retweet"></i>
        </button>
    </div>

    <table class="table table-sm cus-table-borderless">
        <tbody ng-form="orderProductStepUpForm">
        <tr>
            <td><spring:message code="addOrderProductStepUp.title"/></td>
            <td><select required class="form-control form-control-sm"
                        ng-model="orderProductStepUp.vendor.id"
                        ng-options="item.id as item.fullName for item in vendors">
                <option value=""></option>
            </select></td>
        </tr>
        <tr>
            <td title="Most of the time Vendor's Invoice ID"><spring:message
                    code="addOrderProductStepUp.reference"/></td>
            <td><input ng-model="orderProductStepUp.referecneInvoiceId"
                       class="form-control  form-control-sm"></td>
        </tr>
        </tbody>
    </table>


    <div class="border-top">
        <table class="table table-bordered">
            <thead>
            <tr>
                <td>#</td>
                <td><spring:message code="addOrderProductStepUp.image"/></td>
                <td><spring:message code="addOrderProductStepUp.pName"/></td>
                <td><spring:message code="addOrderProductStepUp.pCode"/></td>
                <td><spring:message code="addOrderProductStepUp.pUnitType"/></td>
                <td><spring:message
                        code="addOrderProductStepUp.packetQuantity"/></td>
                <td><spring:message code="addOrderProductStepUp.quantity"/></td>
                <td><spring:message code="editOrderProductStepUp.soldQuantity"/></td>
                <td><spring:message code="addOrderProductStepUp.payment"/></td>
                <td><spring:message code="addOrderProductStepUp.expDate"/></td>
                <td><spring:message code="addOrderProductStepUp.stock"/></td>
                <td><spring:message code="addOrderProductStepUp.note"/></td>
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
                           type="number" min="0" step="any"
                           ng-model="productStepUp.packetQuantity"
                           class="form-control form-control-sm"></td>
                <td><input min="0" step="any"
                           ng-disabled="productStepUp.packetQuantity" type="number" required
                           ng-model="productStepUp.quantity"
                           class="form-control form-control-sm">
                    {{productStepUp.product.packetSize}}
                </td>
                <td></td>
                <td><input type="number" min="0" required
                           ng-model="productStepUp.paymentAmount"
                           class="form-control form-control-sm"><span
                        class="text-info">{{productStepUp.paymentAmount*productStepUp.quantity|number}}</span>
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

            <tr ng-repeat="item in orderProductStepUp.productStepUps">
                <td>{{$index+1}}</td>
                <td><a data-fancybox="gallery"
                       href="{{url+'/0/'+item.product.attachedFile.id}}"> <img
                        ng-if="item.product.attachedFile" class="cus-avatar"
                        src="{{url+'/1/'+item.product.attachedFile.id}}"/>
                </a></td>
                <td>{{item.product.name}}</td>
                <td>{{item.product.code}}</td>
                <td>{{item.product.productCategory.name}}</td>
                <td><span ng-if="item.product.packetSize">
							{{item.quantity/item.product.packetSize|number}} </span></td>
                <td>{{item.quantity}}</td>
                <td>{{item.soldQuantity}} <a target="_blank"
                                             class="btn btn-info" href="{{urlCustomerOrder+item.id}}"> <i
                        class="fa fa-info"></i>
                </a>
                </td>
                <td>{{item.paymentAmount|number}}</td>
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
                    <td><spring:message code="addOrderProductStepUp.totalPrice"/></td>
                    <td><input style="width: 200px" class="d-inline-block form-control form-control-sm"
                               ng-value="checkTotalPrice=totalPrice()|number"
                               ng-model="checkTotalPrice"
                               disabled="disabled">
                        <button class="btn btn-sm btn-info" ng-click="setTotalPayment()">
                            <i class="fa fa-arrow-down"></i>
                        </button>
                    </td>
                </tr>


                <tr>
                    <td><spring:message code="addOrderProductStepUp.discount"/>
                        <input type="checkbox" ng-model="selectRatio"> <spring:message
                                code="addOrderProductStepUp.ratio"/> :
                    </td>
                    <td>
                        <div class="input-group input-group-sm col-lg-3 col-md-3 col-sm-4 float-left mb-1" style="margin-left: -15px;">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="ng-sm">$</span>
                            </div>
                            <input min="0" type="number" step="any" ng-disabled="selectRatio || totalPrice()<=0"
                                   ng-change="calculateTotalPayment()"
                                   ng-required="required" ng-model="orderProductStepUp.discount" max="totalPrice()"
                                   class="d-inline-block form-control form-control-sm"/>
                        </div>
                        <div class="input-group input-group-sm col-lg-3 col-md-3 col-sm-4 float-left mb-1">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="sizing-sm">%(0.1)</span>
                            </div>
                            <input min="0" type="number" step="any" ng-max="1" ng-change="calculateDiscount()"
                                   ng-disabled="!selectRatio || totalPrice()<=0"
                                   ng-value="!selectRatio?discountRatio=0:0"
                                   ng-required="required" ng-model="discountRatio"
                                   class="d-inline-block form-control form-control-sm"/>
                        </div>

                    </td>
                </tr>


                <tr>
                    <td><spring:message code="addOrderProductStepUp.totalPayment"/></td>
                    <td colspan="2"><input name="totalPayment" type="number"
                                           step="any" ng-model="orderProductStepUp.totalPayment"
                                           style="width: 200px"
                                           class="d-inline-block form-control form-control-sm"></td>
                </tr>
            </table>
        </div>
        <button
                ng-disabled="orderProductStepUp.totalPayment>paymentValidation ||!orderProductStepUp||!orderProductStepUp.productStepUps.length>0||orderProductStepUpForm.$invalid"
                class="btn btn-warning" ng-click="saveOrderProductStepUp()">
            <i class="fa fa-edit"></i>
        </button>
    </div>

</div>