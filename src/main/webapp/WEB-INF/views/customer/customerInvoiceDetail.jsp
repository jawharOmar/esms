<%@ page
        import="com.joh.esms.domain.model.CustomerInvoiceD.INVOICE_TYPE" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    var csrf = '${_csrf.token}';
</script>

<div>
    <h2>
        <spring:message code="customerInvoiceDetail.title"/>
    </h2>
    <h3 style="color: #00adff;">${customer.fullName }</h3>

    <div>
        <form class="form-inline" action="<c:url value="/customers/" />${customer.id}/invoiceDetail">
            <div class="form-group">
                <label for="from"><spring:message
                        code="customerOrders.from"/></label>
                <input class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>
            </div>
            <div class="form-group">
                <label for="to"><spring:message
                        code="customerOrders.to"/></label>
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

    <div>

        <table id="customerPaymentsTable" class="display">
            <thead>
            <tr>
                <th><spring:message code="customerInvoiceDetail.time"/></th>
                <th><spring:message code="customerInvoiceDetail.invoiceType"/></th>
                <th><spring:message code="customerInvoiceDetail.amount"/></th>
                <th><spring:message code="customerInvoiceDetail.function"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="sumTotalPayment" value="${0}"/>
            <c:set var="sumTotalOrder" value="${0}"/>
            <c:forEach items="${customerInvoiceDs}" var="item">
                <tr>
                    <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
                                        value="${item.time}"/></td>
                    <td><spring:message
                            code="customerInvoiceDetail.${item.invoiceType}"/></td>
                    <td>${item.amount}</td>
                    <td class="cus-not-export"><c:choose>
                        <c:when test="${item.invoiceType=='ORDER'}">
                            <a class="btn btn-sm btn-danger" target="_blank"
                               href="<c:url value="/customerOrders/edit/" />${item.reference}">
                                <i class="fa fa-edit"></i>
                            </a>
                            <c:set var="sumTotalOrder" value="${sumTotalOrder+item.amount}"/>
                        </c:when>
                        <c:when test="${item.invoiceType=='RETURN'}">
                            <a class="btn btn-sm btn-danger" target="_blank"
                               href="<c:url value="/customerOrderReturns/edit/" />${item.reference}">
                                <i class="fa fa-edit"></i>
                            </a>
                            <c:set var="sumTotalOrder" value="${sumTotalOrder+item.amount}"/>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-sm btn-success" target="_blank"
                               href="<c:url value="/customerPayments/" />${item.reference}/print">
                                <i class="fa fa-print"></i>
                            </a>
                            <c:set var="sumTotalPayment"
                                   value="${sumTotalPayment+item.amount}"/>
                        </c:otherwise>
                    </c:choose></td>
                </tr>
            </c:forEach>
            <tr class="text-info">
                <td><spring:message code="customerInvoiceDetail.sumTotalOrder"/></td>
                <td>&nbsp;</td>
                <td><fmt:formatNumber value="${sumTotalOrder}"
                                      maxFractionDigits="3"/></td>
                <td>&nbsp;</td>
            </tr>

            <tr class="text-info">
                <td><spring:message
                        code="customerInvoiceDetail.sumTotalPayment"/></td>
                <td>&nbsp;</td>
                <td><fmt:formatNumber value="${sumTotalPayment}"
                                      maxFractionDigits="3"/></td>
                <td>&nbsp;</td>
            </tr>

            <tr class="text-info">
                <td><spring:message code="customerInvoiceDetail.totalLoan"/></td>
                <td>&nbsp;</td>
                <td><fmt:formatNumber value="${sumTotalOrder-sumTotalPayment}"
                                      maxFractionDigits="3"/></td>
                <td>&nbsp;</td>
            </tr>

            </tbody>
        </table>

    </div>

</div>





