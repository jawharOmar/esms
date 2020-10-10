<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<script >
    var returnsJson='<spring:escapeBody  javaScriptEscape="true">${vendorreturn}</spring:escapeBody>';
    var detailJson='<spring:escapeBody  javaScriptEscape="true">${detail}</spring:escapeBody>';
</script>

<div ng-app="myApp" ng-controller="myApp" ng-init="init()">



    <h2>
        <spring:message code="layout.VednorReturn" />

    ${currentDate}
    </h2>
    <table style="width: 200px">

        <tr>
            <td>
                <button class="btn btn-lg btn-outline-success"
                        onClick="window.location.reload()">
                    <i class="fa fa-refresh"></i>
                </button>
            </td>

        </tr>
    </table>
    <hr>
    <div id="freeze">
        <table class="table table-sm cus-table-borderless" ng-form name="form">
            <tbody>


            <tr>
                <td><spring:message code="vendorPaymentPrint.vendorName"/></td>
                <td><select required="required" ng-model="vendorname" ng-change="selectVendor()" class="form-control form-control-sm">
                    <c:forEach items="${Vendors}" var="item">
                        <option value="${item.id}">${item.fullName}</option>
                    </c:forEach>
                </select></td>
            </tr>



            </tbody>
        </table>


        <hr>
        <table>

        </table>

        <table class="table table-bordered table-sm">
            <thead>
            <tr>
                <th>#</th>
                <th><spring:message code="addCustomerOrder.stock" /></th>
                <th><spring:message code="addCustomerOrder.code" /></th>
                <th><spring:message code="addCustomerOrder.name" /></th>
                <th><spring:message code="orderProductStepUpsDetail.quantity" /></th>
                <th><spring:message code="addCustomerOrder.quantity" /></th>
                <th><spring:message code="addCustomerOrder.price" /></th>
                <th><spring:message code="addCustomerOrder.totalPrice" /></th>
                <th><spring:message code="addCustomerOrder.function" /></th>
            </tr>

            <tr>
                <th>&nbsp;</th>
                <th><select class="form-control form-control-sm" ng-model="selectedstock">
                    <c:forEach items="${Stocks}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select></th>
                <th><select ng-disabled="!selectedstock" id="cus_names" ng-change="findproduct()" ng-model="product.id"  class="form-control form-control-sm" placeholder="product">
                    <c:forEach items="${Products}" var="item">
                        <option value="${item.id}">${item.name} - ${item.code}</option>
                    </c:forEach>
                </select>
             </th>
                <th><input  required tabindex="2" id="productName"
                           class="form-control form-control-sm" ng-model="product.name"
                           readonly></th>
                <th><input disabled
                           tabindex="3" type="number" class="form-control form-control-sm"
                           ng-value="productDetail.amount"></th>
                <th><input
                          tabindex="3" type="number"
                           required class="form-control form-control-sm"
                           ng-model="detail.qty" ng-max="{{productDetail.amount}}"></th>
                <th><input tabindex="4" type="number" step="any"
                           class="form-control form-control-sm" ng-model="lastprice">
                  </th>
                <th>
                    <div class="form-row">
                        <div class="col-11">
                            <input  tabindex="5" type="number"
                                   step="any" class="form-control form-control-sm"
                                   ng-value="lastprice*detail.qty">
                        </div>
                    </div>


                </th>

                <th>
                    <button tabindex="7" ng-disabled="!detail.product"
                            class="btn btn-sm btn-primary"
                            ng-click="addItem()">
                        <i class="fa fa-plus"></i>
                    </button>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="item in venderReturns.venderReturnsDetail">
                <td>{{$index+1}}</td>
                <td>{{item.stock.name}}</td>
                <td>{{item.product.code}}</td>
                <td>{{item.product.name}}</td>
                <td colspan="2">{{item.qty}}</td>
                <td>{{item.amount}}</td>
                <td>{{item.amount*item.qty|number}}</td>
                <td width="80px">
                    <button class="btn btn-sm btn-outline-danger"
                            ng-click="deleteProductStepUp($index)">
                        <i class="fa fa-times"></i>
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
                               ng-value="totalPrice()|number : 3"
                               class="form-control form-control-sm" readonly>
                </tr>




            </table>
        </div>
        <button
                class="btn btn-sm btn-success" ng-disabled="!venderReturns.venderReturnsDetail.length>0 || !venderReturns.vendor" ng-click="saveVenderReturn()">
            <i class="fa fa-save"></i>
        </button>

    </div>
</div>

