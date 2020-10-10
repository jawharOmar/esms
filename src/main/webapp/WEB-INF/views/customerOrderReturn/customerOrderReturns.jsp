<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    var csrf = '${_csrf.token}';
    console.log("csrf=" + csrf);
    var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>
<div ng-app="app" ng-controller="appCTRL">
    <h2>
        <spring:message code="customerOrderReturns.title"/>
    </h2>
    <hr>

    <div>
        <form class="form-inline" action="<c:url value="/customerOrderReturns"/>">
            <div class="form-group">
                <a class="btn btn-info mx-sm-3"
                   href="<c:url value="/customerOrderReturns/detail"/>?from=<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>&to=<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>">
                    <i class="fa fa-info"></i>
                </a>
            </div>
            <div class="form-group">
                <label for="from"><spring:message
                        code="customerOrderReturns.from"/></label>
                <input class="form-control mx-sm-3" id="from" name="from" readonly
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>
            </div>

            <div class="form-group">
                <label for="from"><spring:message
                        code="customerOrderReturns.to"/></label>
                <input class="form-control mx-sm-3" id="to" name="to" readonly
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${to}" />"/>
            </div>

            <div class="form-group">
                <button class="btn btn-outline-info">
                    <i class="fa fa-eye"></i>
                </button>
            </div>
        </form>
    </div>

    <hr>

    <div>

        <table id="table" class="display">
            <thead>
            <tr>
                <th>#</th>
                <th><spring:message code="customerOrderReturns.customer"/></th>
                <th><spring:message code="customerOrderReturns.time"/></th>
                <th><spring:message code="customerOrderReturns.reference"/></th>
                <th><spring:message code="customerOrderReturns.totalPrice"/></th>
                <th><spring:message code="layout.customerPayments"/></th>
                <th class="cus-not-export"><spring:message
                        code="customerOrderReturns.function"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="sumTotalPrice" value="${0}"/>
            <c:set var="sumTotalPaid" value="${0}"/>
            <c:forEach items="${customerOrderReturns}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.customer.fullName}</td>
                    <td>${item.time}</td>
                    <td>${item.referecneInvoiceId}</td>
                    <td><fmt:formatNumber value="${item.totalPrice}"/> <c:set
                            var="sumTotalPrice" value="${sumTotalPrice+item.totalPrice}"/></td>
                    <td><fmt:formatNumber value="${item.totalpaid}"/>
                        <c:set var="sumTotalPaid" value="${sumTotalPaid+item.totalpaid}"/></td>
                    <td>

                        <div>

                            <a class="btn btn-sm btn-outline-info" target="_blank"
                               href="<c:url value="/customerOrderReturns/" />${item.id}/print">
                                <i class="fa fa-print"></i>
                            </a>

                            <button class="btn btn-danger btn-sm"
                                    ng-click="deleteCustomerOrderReturn(${item.id})">
                                <i class="fa fa-times"></i>
                            </button>

                            <a
                                    href="<c:url value="/customerOrderReturns/edit/" />${item.id}"
                                    class="btn btn-warning btn-sm"> <i class="fa fa-edit"></i>
                            </a>

                        </div>
                    </td>
                </tr>


            </c:forEach>
            <tr class="text-info">
                <td>Total</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${sumTotalPrice}"/></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${sumTotalPaid}"/></td>
                <td>&nbsp;</td>
            </tr>

            </tbody>
        </table>

    </div>

</div>