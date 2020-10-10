<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    var jsonCustomers = '<spring:escapeBody  javaScriptEscape="true">${jsonCustomers}</spring:escapeBody>';
    var csrf = '${_csrf.token}';
</script>
<div class="container" ng-app="app" ng-controller="appCTRL" ng-init="init()">

    <br>
    <br>
    <div class="row justify-content-center" >
        <div class="card col p-0" ng-form name="form">
            <div class="card-header bg-dark text-white text-center">
                <div class="card-title">
                    <h3><spring:message code="addCustomerPayment.customerName"/></h3>
                </div>
            </div>
            <div class="card-body">

                <div class="form-row justify-content-center">
                    <div class="col">
                        <input required="required" id="customer-autoselect"
                               class="form-control form-control-sm"
                               ng-model="customer.fullName">
                    </div>
                </div>

                <br>

                <div class="form-row justify-content-center">
                    <div class="col">
                        <select class="form-control form-control-sm" ng-model="actionType">
                            <option value="all"><spring:message code="report.all"/></option>
                            <option value="Payment"><spring:message code="report.payment"/></option>
                            <option value="Order"><spring:message code="report.order"/></option>
                            <option value="Return"><spring:message code="report.return"/></option>
                        </select>
                    </div>
                    <div class="col">
                        <select class="form-control form-control-sm" required="required"
                                ng-model="projectId"
                                ng-options="item.id as item.name for item in customer.customerProjects">
                            <option value="">
                                <spring:message code="addCustomerPayment.project"/>
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <button class="btn btn-info form-control" ng-disabled="!form.$valid" ng-click="projectStatement()">
                    <spring:message code="report.search"/>
                </button>
            </div>
        </div>
    </div>

    <hr>

    <table id="accountcheck" class="table table-bordered" style="padding: 0px;">
        <thead class="bg-dark text-white text">
        <tr>
            <th colspan="7" id="tableTitle"><b>${selected.fullName}</b></th>
        </tr>
        <tr>
            <th><spring:message code="statement.type"/></th>
            <th><spring:message code="statement.invoice"/></th>
            <th><spring:message code="statement.time"/></th>
            <th><spring:message code="statement.discount"/></th>
            <th><spring:message code="statement.debit"/></th>
            <th><spring:message code="statement.credit"/></th>
            <th><spring:message code="statement.balance"/></th>

        </tr>


        </thead>

        <c:set var="balance" value="${0}"/>
        <c:set var="totalDebit" value="${0}"/>
        <c:set var="totalCredit" value="${0}"/>
        <c:forEach items="${report}" var="item">

            <c:set var="balance" value="${balance+item.debit-item.credit}"/>
            <c:set var="totalDebit" value="${totalDebit+item.debit}"/>
            <c:set var="totalCredit" value="${totalCredit+item.credit}"/>

            <tr
                    <c:if test="${item.type == 'Order'}">style="background: rgba(41, 125, 194,0.1)"
            </c:if>
                    <c:if test="${item.type == 'Payment'}">style="background: rgba(22, 156, 33,0.1)"
            </c:if>
                    <c:if test="${item.type == 'Return'}">style="background: rgba(143, 156, 22,0.1)"
            </c:if>>

                <c:choose>
                    <c:when test="${isVendor==true}">
                        <spring:url value="../orderProductStepUps/${item.actionId}/print" var="auserActionUrl"/>
                        <spring:url value="../vendorPayments/${item.actionId}/print/" var="payment"/>
                        <spring:url value="../venderReturn/${item.actionId}/print/" var="returnUrl"/>
                    </c:when>
                    <c:otherwise>
                        <spring:url value="../customerOrders/${item.actionId}" var="auserActionUrl"/>
                        <spring:url value="../customerPayments/${item.actionId}/print/" var="payment"/>
                        <spring:url value="../customerOrderReturns/${item.actionId}/print/" var="returnUrl"/>
                        <spring:url value="../customerWastedProducts/${item.actionId}/print/" var="wastedProductUrl"/>
                    </c:otherwise>
                </c:choose>




                <td>

                    <c:if test="${item.note != 'empty'}">
                        <div class="tooltipp">${item.type}
                            <span class="tooltiptext">${item.note}</span>
                        </div>
                    </c:if>
                    <c:if test="${item.note == 'empty'}">
                        ${item.type}
                    </c:if>
                </td>
                <td><c:if test="${item.type == 'Order'}"><a href="${auserActionUrl}" target="_blank">${item.invoice}</a></c:if>
                    <c:if test="${item.type == 'Payment'}"><a href="${payment}" target="_blank">${item.invoice}</a></c:if>
                    <c:if test="${item.type == 'Return'}"><a href="${returnUrl}" target="_blank">${item.invoice}</a></c:if>
                    <c:if test="${item.type == 'Wasted Product'}"><a href="${wastedProductUrl}" target="_blank">${item.invoice}</a></c:if>
                </td>

                <td>${item.time}</td>
                <td><fmt:formatNumber type="number"
                                      maxFractionDigits="3" value="${item.discount}"/></td>
                <td>
                    <fmt:formatNumber type="number"
                                      maxFractionDigits="3" value="${item.debit}"/></td>
                <td><fmt:formatNumber type="number"
                                      maxFractionDigits="3" value="${item.credit}"/></td>
                <td
                        <c:if test="${balance>=0}">style="color: rgba(0, 125, 0,1)"
                </c:if>
                        <c:if test="${balance<0}">style="color: rgba(125, 0, 0,1)"
                </c:if>><fmt:formatNumber type="number"
                                          maxFractionDigits="3" value="${balance}"/></td>

            </tr>
        </c:forEach>
        <tfoot class="bg-dark text-white text">
        <td><spring:message code="accountMain.total"/></td>
        <td colspan="3"></td>
        <td><fmt:formatNumber type="number"
                              maxFractionDigits="3" value="${totalDebit}"/></td>
        </td>
        <td><fmt:formatNumber type="number"
                              maxFractionDigits="3" value="${totalCredit}"/></td>
        </td>
        <td
                <c:if test="${balance>=0}">style="color:green"
        </c:if>
                <c:if test="${balance<0}">style="color:red"
        </c:if>><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${balance}"/></td>
        </tfoot>
    </table>


</div>


<style>
    .table tr td, .table thead tr th {

        padding: 5px;
    }
</style>

<script>

    window.onload = function () {
        $('#accountcheck').DataTable({
            searching: false,
            dom: 'lBfrtip',
            buttons: [{
                extend: 'excel',
                className: "btn btn-sm float-right btn-outline-info",
                title: function () {
                    var today = new Date();
                    var dd = String(today.getDate()).padStart(2, '0');
                    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
                    var yyyy = today.getFullYear();
                    today = yyyy + '-' + mm + '-' + dd;
                    var title = "کشف المشروع";
                    return title+" - "+today;
                },
                messageTop: function () {
                    return '${selected.fullName}';
                },
                footer: true,
            }
            ]
        });

        // for Print button title and message
        // return "<h3 class=\"title text-center\">" + title + " - " + today + "</h3>";
        <%--return '<h6 class="text-center">${selected.fullName}</h6>';--%>

    };



</script>

<style>

    .tooltipp {
        position: relative;
        display: inline-block;
        border-bottom: 1px dotted black; /* If you want dots under the hoverable text */
    }

    /* Tooltip text */
    .tooltipp .tooltiptext {
        visibility: hidden;
        width: 120px;
        background-color: #555;
        color: #fff;
        text-align: center;
        padding: 5px 0;
        border-radius: 6px;

        /* Position the tooltip text */
        position: absolute;
        z-index: 1;
        bottom: 125%;
        left: 50%;
        margin-left: -60px;

        /* Fade in tooltip */
        opacity: 0;
        transition: opacity 0.3s;
    }

    /* Tooltip arrow */
    .tooltipp .tooltiptext::after {
        content: "";
        position: absolute;
        top: 100%;
        left: 50%;
        margin-left: -5px;
        border-width: 5px;
        border-style: solid;
        border-color: #555 transparent transparent transparent;
    }

    /* Show the tooltip text when you mouse over the tooltip container */
    .tooltipp:hover .tooltiptext {
        visibility: visible;
        opacity: 1;
    }
</style>