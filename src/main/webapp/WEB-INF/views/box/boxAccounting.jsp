<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    var csrf = '${_csrf.token}';
    var from = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>';
    var to = '<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
    var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>

<style>
    .dt-buttons {
        float: left;
    }

    #main-content {
        overflow: hidden;
    }
</style>
<div ng-app="boxApp" ng-controller="appCTRL" ng-init="init()">
    <h2>
        <spring:message code="boxAccounting.title"/> <c:if test="${actionType!=null}">: ${actionType} </c:if>
    </h2>
    <hr>

    <div class="row">
        <div class="col-lg-4 col-md-6 col-sm-4">
            <form action="<c:url value="/boxAccounting"/>${dateFilterUrl}">
                <c:if test="${actionType!=null}">
                    <input class="form-control" type="hidden" name="actionTypeId" value="${actionTypeId}"/>
                </c:if>
                <table>
                    <tr>
                        <td class="text-left"><spring:message
                                code="boxAccounting.from"/></td>
                        <td><input class="form-control" id="from" name="from"
                                   value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/></td>
                    </tr>

                    <tr>
                        <td class="text-left"><spring:message code="boxAccounting.to"/></td>
                        <td><input class="form-control" id="to" name="to"
                                   value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${to}" />"/></td>
                    </tr>
                    <tr>
                        <td>
                            <button class="btn btn-outline-info">
                                <i class="fa fa-eye"></i>
                            </button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <%--
          1-vendorOrders (typeId 1)
          2-vendorPayment (typeId 2)
          3-vendorReturn (typeId 3)
          4-customerOrders (typeId 4)
          5-customerPayment (typeId 5)
          6-customerReturn (typeId 6)
          7-expenses (typeId 7)
          8-incomes (typeId 8)
          9-withdraws (typeId 9)
          10-loan (typeId 10)
    --%>
        <div class="col-lg-4 col-md-6 col-sm-4 ml-auto">
            <div class="col">
                <select class="form-control" ng-model="actionTypeId" ng-change="filterByAction()">
                    <option value="" selected><spring:message code="boxAccounting.select"/></option>
                    <option value="all"><spring:message code="boxAccounting.all"/></option>
                    <option value="1"><spring:message code="boxAccounting.vendorOrders"/></option>
                    <option value="2"><spring:message code="boxAccounting.vendorPayment"/></option>
                    <option value="3"><spring:message code="boxAccounting.vendorReturn"/></option>
                    <option value="4"><spring:message code="boxAccounting.customerOrders"/></option>
                    <option value="5"><spring:message code="boxAccounting.customerPayment"/></option>
                    <option value="6"><spring:message code="boxAccounting.customerReturn"/></option>
                    <option value="7"><spring:message code="boxAccounting.expenses"/></option>
                    <option value="8"><spring:message code="boxAccounting.incomes"/></option>
                    <option value="9"><spring:message code="boxAccounting.withdraws"/></option>
                </select>
            </div>
        </div>

    </div>
</div>
<hr>
<table id="box_accounting" class="table table-bordered" style="padding: 0px;">
    <thead class="bg-dark text-white text">
    <tr>
        <th><spring:message code="boxAccounting.fullName"/></th>
        <th><spring:message code="boxAccounting.type"/></th>
        <th><spring:message code="boxAccounting.note"/></th>
        <th><spring:message code="boxAccounting.invoice"/></th>
        <th><spring:message code="boxAccounting.time"/></th>
        <th><spring:message code="boxAccounting.discount"/></th>
        <th><spring:message code="boxAccounting.income"/></th>
        <th><spring:message code="boxAccounting.expense"/></th>
        <th><spring:message code="boxAccounting.balance"/></th>

    </tr>

    </thead>

    <c:set var="balance" value="${0}"/>
    <c:set var="totalIncome" value="${0}"/>
    <c:set var="totalExpense" value="${0}"/>
    <c:if test="${incExpValues!=null}">
        <tr>
            <td></td>
            <td><spring:message code="accountMain.oldBalance"/></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>
                <fmt:formatNumber maxFractionDigits="3">${incExpValues.incomes}</fmt:formatNumber>
                <c:set var="totalIncome" value="${totalIncome+incExpValues.incomes}"/>
            </td>
            <td>
                <fmt:formatNumber maxFractionDigits="3">${incExpValues.expenses}</fmt:formatNumber>
                <c:set var="totalExpense" value="${totalExpense+incExpValues.expenses}"/>

                <c:set var="balance" value="${balance+incExpValues.incomes-incExpValues.expenses}"/>
            </td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${balance}"/>
            </td>
        </tr>
    </c:if>
    <c:forEach items="${boxAccounting}" var="item">

        <c:set var="balance" value="${balance+item.income-item.expense}"/>
        <c:set var="totalIncome" value="${totalIncome+item.income}"/>
        <c:set var="totalExpense" value="${totalExpense+item.expense}"/>

        <tr
                <c:if test="${item.typeId == 1}">style="background: #83cacd"</c:if>
                <c:if test="${item.typeId == 2}">style="background: #2fc1e5"</c:if>
                <c:if test="${item.typeId == 3}">style="background:#dffede;"</c:if>
                <c:if test="${item.typeId == 4}">style="background: #4aaeec;" </c:if>
                <c:if test="${item.typeId == 5}">style="background: #5cb85c;"</c:if>
                <c:if test="${item.typeId == 6}">style="background: #8be18c;"</c:if>
                <c:if test="${item.typeId == 10}">style="background: #ffb4b4;"</c:if>
        >

            <c:choose>
                <c:when test="${item.typeId == 1 || item.typeId ==2|| item.typeId ==3 }">
                    <spring:url value="../orderProductStepUps/${item.actionId}/print" var="auserActionUrl"/>
                    <spring:url value="../vendorPayments/${item.actionId}/print/" var="payment"/>
                    <spring:url value="../venderReturn/${item.actionId}/print/" var="returnUrl"/>
                </c:when>
                <c:otherwise>
                    <spring:url value="../customerOrders/${item.actionId}" var="auserActionUrl"/>
                    <spring:url value="../customerPayments/${item.actionId}/print/" var="payment"/>
                    <spring:url value="../customerOrderReturns/${item.actionId}/print/" var="returnUrl"/>
                </c:otherwise>
            </c:choose>
            <td>
                    ${item.fullName}
            </td>
            <td>
                    ${item.type}
            </td>
            <td>
                <c:if test="${item.note != 'empty'}">
                    ${item.note}
                </c:if>
            </td>
            <td>
                <c:if test="${item.typeId == 1 || item.typeId == 4}"><a
                        href="${auserActionUrl}" target="_blank">${item.invoice}</a></c:if>
                <c:if test="${item.typeId == 2 || item.typeId == 5}"><a
                        href="${payment}" target="_blank">${item.invoice}</a></c:if>
                <c:if test="${item.typeId == 3 || item.typeId == 6}"><a
                        href="${returnUrl}" target="_blank">${item.invoice}</a></c:if>
            </td>

            <td>${item.time}</td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${item.discount}"/></td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${item.income}"/></td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${item.expense}"/></td>
            <td
                    <c:if test="${balance>=0}">style="color: black"
            </c:if>
                    <c:if test="${balance<0}">style="color: rgba(125, 0, 0,1)"
            </c:if>><fmt:formatNumber type="number"
                                      maxFractionDigits="3" value="${balance}"/></td>
        </tr>
    </c:forEach>
    <tfoot class="bg-dark text-white text">
    <td><spring:message code="boxAccounting.total"/></td>
    <td colspan="3"></td>
    <td>
        <c:if test="${wastedCost!=null}">
            <spring:message code="boxAccounting.wastedCost"/> : <span style="color: yellow">${wastedCost}</span>
        </c:if>
    </td>
    <td></td>
    <td><fmt:formatNumber type="number"
                          maxFractionDigits="3" value="${totalIncome}"/></td>
    </td>
    <td><fmt:formatNumber type="number"
                          maxFractionDigits="3" value="${totalExpense}"/></td>
    </td>
    <td
            <c:if test="${balance>=0}">style="color:lawngreen"</c:if>
            <c:if test="${balance<0}">style="color:red"</c:if>>
        <c:if test="${wastedCost!=null}">
            <fmt:formatNumber type="number"
                              maxFractionDigits="3" value="${balance-wastedCost}"/>
        </c:if>
        <c:if test="${wastedCost==null}">
            <fmt:formatNumber type="number"
                              maxFractionDigits="3" value="${balance}"/>
        </c:if>
    </td>
    </tfoot>
</table>

</div>


<style>
    .table tr td, .table thead tr th {
        padding: 5px;
    }
</style>

