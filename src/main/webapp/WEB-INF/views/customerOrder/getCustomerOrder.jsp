<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<style>
    .last {
        border-bottom: solid 1px rgba(0, 0, 0, 0.8);
    }

    @media screen {
        header, footer {
            display: none;
        }
    }

    @media print {
        header, footer {
            display: block;
        }
    }
</style>
<c:if test="${pageContext.response.locale==\"ar\"||pageContext.response.locale==\"ar_SY\"}">
    <style type="text/css">
        * {
            direction: rtl;
            text-align: right !important;
        }
    </style>
</c:if>
<c:if test="${pageContext.response.locale==\"en\"}">
    <style type="text/css">
        * {
            direction: ltr;
            text-align: left !important;
        }
    </style>
</c:if>
<div class="cus-print-container">

    <table style="width: 400px;">

        <tr>
            <td><spring:message code="getCustomerOrder.time"/></td>
            <td><fmt:formatDate pattern="yyyy-MM-dd  hh:mm:ss"
                                value="${customerOrder.orderTime}"/></td>
        </tr>

        <tr>
            <td><spring:message code="getCustomerOrder.invoiceId"/></td>
            <td>${customerOrder.invoiceId}</td>
        </tr>
        <tr>
            <td><spring:message code="getCustomerOrder.customerName"/></td>
            <td>${customerOrder.customer.fullName}</td>
        </tr>

        <c:if test="${customerOrder.receivedBy!=null}">
            <tr>
                <td><spring:message code="getCustomerOrder.receivedBy"/></td>
                <td>${customerOrder.receivedBy}</td>
            </tr>
        </c:if>

    </table>

    <hr>
    <div id="order">
        <table class="cus-center" style="width: 100%">

            <thead>
            <tr>
                <th>#</th>
                <th><spring:message code="getCustomerOrder.image"/></th>
                <th><spring:message code="getCustomerOrder.name"/></th>
                <th style="text-align: center;"><spring:message
                        code="getCustomerOrder.quantity"/></th>
                <th style="text-align: center;"><spring:message
                        code="getCustomerOrder.packetQuantity"/></th>
                <th><spring:message code="getCustomerOrder.price"/></th>
                <th><spring:message code="getCustomerOrder.total"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="packet" value="0"/>
            <c:set var="itemqty" value="0"/>

            <c:forEach items="${customerOrder.customerOrderDetails}" var="item"
                       varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td><c:if test="${item.product.attachedFile!=null}">
                        <img class="cus-avatar"
                             src="<c:url value="/attachedFiles/1/" />${item.product.attachedFile.id}">
                    </c:if></td>
                    <td>${item.productName}</td>
                    <td style="text-align: center;">${item.quantity}&nbsp;${item.product.unit}

                        <c:set var="itemqty" value="${itemqty+item.quantity}"/>
                    </td>
                    <td style="text-align: center;"><c:if
                            test="${item.quantity>=item.product.packetSize}">
                        <fmt:formatNumber maxFractionDigits="3">
                            ${item.quantity/item.product.packetSize}
                        </fmt:formatNumber>
                        <c:set var="packet" value="${packet+item.quantity/item.product.packetSize}"/>

                    </c:if></td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                          value="${item.price}"/></td>

                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                          value="${item.price*item.quantity}"/>

                    </td>

                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td style="text-align: center" colspan="3"><spring:message code="getCustomerOrder.total"/></td>

                <td style="text-align: center">${itemqty}</td>
                <td style="text-align: center"><fmt:formatNumber maxFractionDigits="3">
                    ${packet}
                </fmt:formatNumber></td>
                <td></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${customerOrder.totalPrice}"/></td>
            </tr>
            </tfoot>
        </table>


    </div>


    <hr>
    <table border="1" cellpadding="5" style="font-weight:bolder; width: 400px;">

        <tr>
            <td><spring:message code="getCustomerOrder.totalPrice"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                  value="${customerOrder.totalPrice}"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                  value="${customerOrder.totalPrice * currencyType.rate}"/> ${currencyType.to}</td>
        </tr>

        <c:if test="${customerOrder.discount!=null}">
            <tr>
                <td><spring:message code="getCustomerOrder.discount"/></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${customerOrder.discount}"/> - ${customerOrder.note} </td>

                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${customerOrder.discount * currencyType.rate}"/> ${currencyType.to}</td>
            </tr>
        </c:if>

        <tr>
            <td><spring:message code="getCustomerOrder.totalPayment"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                  value="${customerOrder.totalPayment}"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                  value="${customerOrder.totalPayment * currencyType.rate}"/> ${currencyType.to}</td>
        </tr>

        <tr>
            <td><spring:message code="getCustomerOrder.remain"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                  value="${customerOrder.totalPrice-customerOrder.discount-customerOrder.totalPayment}"/></td>
            <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                  value="${(customerOrder.totalPrice-customerOrder.discount-customerOrder.totalPayment) * currencyType.rate}"/> ${currencyType.to}</td>
        </tr>

        <c:if test="${showLoan}">
            <tr>
                <td><spring:message code="getCustomerOrder.oldLoan"/></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${customerOrder.customer.totalLoan-(customerOrder.totalPrice-customerOrder.discount-customerOrder.totalPayment)}"/></td>
                <td></td>
            </tr>
            <tr>
                <td><spring:message code="getCustomerOrder.totalLoan"/></td>
                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                      value="${customerOrder.customer.totalLoan}"/></td>
                <td></td>
            </tr>
        </c:if>

    </table>


</div>

<script type="text/javascript">
    function printing() {
        console.log("print fired");
        window.print();
    }

    printing();
</script>


