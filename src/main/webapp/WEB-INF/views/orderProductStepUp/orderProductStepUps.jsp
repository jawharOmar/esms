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
<div ng-app="productStepUps" ng-controller="productStepUps">


    <h2>
        <spring:message code="orderPorductStepUps.title"/>
    </h2>
    <hr>
    <c:if test="${from!=null&&to!=null}">
        <div>
            <form class="form-inline" action="<c:url value="/orderProductStepUps"/>">
                <div class="form-group">
                    <a class="btn btn-info mx-sm-3"
                       href="<c:url value="/orderProductStepUps/detail"/>?from=<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>&to=<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>">
                        <i class="fa fa-info"></i>
                    </a>
                </div>
                <div class="form-group">
                    <label for="from"><spring:message code="orderPreProducts.from"/></label>
                    <input readonly class="form-control mx-sm-3" id="from" name="from"
                           value="<fmt:formatDate pattern = "yyyy-MM-dd"
                           value = "${from}" />"/>
                </div>
                <div class="form-group">
                    <label for="to"><spring:message code="orderPreProducts.to"/></label>
                    <input readonly class="form-control mx-sm-3" id="to" name="to"
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
    </c:if>
    <hr>


    <div>

        <table id="productStepUpsTable" class="display">
            <thead>
            <tr>
                <th>#</th>
                <th><spring:message code="orderPorductStepUps.vendor"/></th>
                <th><spring:message code="orderPorductStepUps.time"/></th>
                <th><spring:message code="orderPorductStepUps.reference"/></th>
                <th><spring:message code="orderPorductStepUps.totalPrice"/></th>
                <th><spring:message code="orderPorductStepUps.discount"/></th>
                <th><spring:message code="orderPorductStepUps.totalPayment"/></th>
                <th class="cus-not-export"><spring:message
                        code="orderPorductStepUps.function"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="sumTotalPayment" value="${0}"/>
            <c:set var="sumTotalDiscount" value="${0}"/>
            <c:set var="sumTotalPrice" value="${0}"/>
            <c:forEach items="${orderProductStepUps}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.vendor.fullName}</td>
                    <td>${item.orderTime}</td>
                    <td>${item.referecneInvoiceId}</td>
                    <td><fmt:formatNumber value="${item.totalPrice}"/> <c:set
                            var="sumTotalPrice" value="${sumTotalPrice+item.totalPrice}"/></td>
                    <td><fmt:formatNumber value="${item.discount}"/> <c:set
                            var="sumTotalDiscount" value="${sumTotalDiscount+item.discount}"/></td>
                    <td><fmt:formatNumber value="${item.totalPayment}"/> <c:set
                            var="sumTotalPayment"
                            value="${sumTotalPayment+item.totalPayment}"/></td>
                    <td>
                        <div>

                            <a class="btn btn-sm btn-outline-info" target="_blank"
                               href="<c:url value="/orderProductStepUps/" />${item.id}/print">
                                <i class="fa fa-print"></i>
                            </a>

                            <button class="btn btn-danger btn-sm"
                                    ng-click="deleteOrderProductStepUp(${item.id})">
                                <i class="fa fa-times"></i>
                            </button>

                            <a href="<c:url value="/orderProductStepUps/edit/" />${item.id}"
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
                                      value="${sumTotalDiscount}"/></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${sumTotalPayment}"/></td>
                <td>&nbsp;</td>
            </tr>

            </tbody>
        </table>

    </div>

</div>