<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd"/>

<script>
    var csrf = '${_csrf.token}';
    var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
</script>

<div>


    <h2>
        <spring:message code="accountMain.title"/>&nbsp${account.name}&nbsp<span class="text-info"><fmt:formatNumber
            maxFractionDigits="3">${account.balance}</fmt:formatNumber></span>
    </h2>

    <hr>
    <div>
        <form class="form-inline" action="<c:url value="/accounts/main" />">
            <div class="form-group">
                <label for="from"><spring:message code="customerOrders.from"/></label>

                <input class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>
            </div>
            <div class="form-group">
                <label for="to"><spring:message code="customerOrders.to"/></label>
                <input class="form-control mx-sm-3" id="to" name="to"
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


    <c:set var="beforeBalance" value="${0}"/>

    <table id="table" class="display">
        <thead>
        <tr>
            <td><spring:message code="accountMain.transactionType"/></td>
            <td><spring:message code="accountMain.time"/></td>
            <td><spring:message code="accountMain.amount"/></td>
            <td><spring:message code="accountMain.total"/></td>
            <td class="cus-not-export"><spring:message code="accountMain.function"/></td>
        </tr>
        </thead>
        <tbody>
        <c:if test="${oldBalance!=null}">
            <c:set var="beforeBalance" value="${oldBalance}"/>
            <tr>
                <td><spring:message code="accountMain.oldBalance"/></td>
                <td>&nbsp;</td>
                <td><fmt:formatNumber maxFractionDigits="3" value="${oldBalance}"/></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </c:if>
        <c:forEach items="${accountTransactions}" var="item">
            <tr>
                <td><spring:message
                        code="accountMain.${item.accountTransactionType}"/></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
                                    value="${item.time}"/></td>
                <td><fmt:formatNumber maxFractionDigits="3"
                                      value="${item.amount}"/></td>
                <td><fmt:formatNumber maxFractionDigits="3"
                                      value="${item.amount+beforeBalance}"/> <c:set
                        var="beforeBalance" value="${item.amount+beforeBalance}"/></td>
                <td><c:choose>
                    <c:when test="${item.accountTransactionType=='INCOME'}">
                        <button class="btn btn-sm btn-warning"
                                onclick="editingIncome(${item.reference})">
                            <i class="fa fa-edit"></i>
                        </button>
                    </c:when>

                    <c:when test="${item.accountTransactionType=='EXPENSE'}">
                        <button class="btn btn-sm btn-warning"
                                onclick="editingExpense(${item.id})">
                            <i class="fa fa-edit"></i>
                        </button>
                    </c:when>

                    <c:when test="${item.accountTransactionType=='ORDER'}">
                        <a target="_blank"
                           href="<c:url value="/orderProductStepUps/edit/" />${item.reference}"
                           class="btn btn-warning btn-sm"> <i class="fa fa-edit"></i>
                        </a>
                    </c:when>

                    <c:when test="${item.accountTransactionType=='CUSTOMER_ORDER'}">
                        <a target="_blank" class="btn btn-sm btn-outline-info"
                           href="<c:url value="/customerOrders/" />${item.reference}">
                            <i class="fa fa-print"></i>
                        </a>
                    </c:when>

                    <c:when
                            test="${item.accountTransactionType=='CUSTOMER_ORDER_RETURN'}">
                        <a target="_blank" class="btn btn-warning btn-sm"
                           href="<c:url value="/customerOrderReturns/edit/" />${item.reference}">
                            <i class="fa fa-edit"></i>
                        </a>
                    </c:when>

                    <c:when test="${item.accountTransactionType=='VENDOR_PAYMENT'}">
                        <a target="_blank" class="btn btn-sm btn-outline-info"
                           href="<c:url value="/vendorPayments/" />${item.reference}/print">
                            <i class="fa fa-print"></i>
                        </a>
                    </c:when>
                    <c:when test="${item.accountTransactionType=='CUSTOMER_PAYMENT'}">
                        <a target="_blank" class="btn btn-sm btn-outline-info"
                           href="<c:url value="/customerPayments/" />${item.reference}/print">
                            <i class="fa fa-print"></i>
                        </a>
                    </c:when>
                </c:choose></td>
            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>





