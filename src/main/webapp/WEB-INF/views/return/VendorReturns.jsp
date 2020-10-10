<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
    var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>
<div ng-app="app" ng-controller="appCTRL">
    <h2>
        <spring:message code="layout.VednorReturn"/>
    </h2>

    <hr>
    <div >
        <form class="form-inline" action="<c:url value="/venderReturn/view"/>">
            <sec:authorize url="/venderReturn/add">
                <div class="form-group mr-1">
                    <a style="text-decoration: none;" class="col-md-auto rounded btn-success p-2"
                       href="<c:url value="/venderReturn/add" />">
                        <spring:message code="layout.add"/> <i class="fa text-white fa-plus-circle"
                                                               aria-hidden="true"></i>
                    </a>
                </div>
            </sec:authorize>
            <div class="form-group">
                <label for="from"><spring:message code="customerOrderReturns.from"/></label>
                <input required readonly class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value="${from}" />"/>
            </div>
            <div class="form-group">
                <label for="to"><spring:message code="customerOrderReturns.to"/></label>
                <input required readonly class="form-control mx-sm-3" id="to" name="to"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd" value="${to}" />"/>

            </div>
            <div class="form-group">
                <button class="btn btn-outline-info mx-sm-3">
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
                <th><spring:message code="customerOrderReturns.time"/></th>
                <th><spring:message code="orderPorductStepUps.vendor"/></th>
                <th><spring:message code="customerOrderReturns.totalPrice"/></th>
                <th class="cus-not-export"><spring:message
                        code="customerOrderReturns.function"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="sumTotalPrice" value="${0}"/>
            <c:forEach items="${verdorReturns}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.time}</td>
                    <td>${item.vendor.fullName}</td>
                    <td><fmt:formatNumber value="${item.amount}"/> <c:set
                            var="sumTotalPrice" value="${sumTotalPrice+item.amount}"/></td>
                    <td>

                        <div>

                            <a class="btn btn-sm btn-outline-info" target="_blank"
                               href="<c:url value="/venderReturn/" />${item.id}/print">
                                <i class="fa fa-print"></i>
                            </a>

                            <sec:authorize url="/venderReturn/delete">

                                <button class="btn btn-danger btn-sm"
                                        ng-click="deletereturn(${item.id})">
                                    <i class="fa fa-times"></i>
                                </button>
                            </sec:authorize>

                        </div>
                    </td>
                </tr>


            </c:forEach>
            <tr class="text-info">
                <td>Total</td>
                <td>&nbsp;</td>
                <td></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${sumTotalPrice}"/></td>
                <td>&nbsp;</td>
            </tr>

            </tbody>
        </table>

    </div>

</div>