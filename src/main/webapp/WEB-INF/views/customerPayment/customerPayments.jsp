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
        <spring:message code="customerPayments.title"/>
    </h2>
    <hr>
    <div>
        <form class="form-inline" action="<c:url value="/customerPayments" />">
            <div class="form-group">
                <label for="from"><spring:message code="customerOrders.from"/></label>
                <input readonly class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>
            </div>
            <div class="form-group">
                <label for="to"><spring:message code="customerOrders.to"/></label>
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
    <hr>

    <div>

        <table id="customerPaymentsTable" class="display nowrap">
            <thead>
            <tr>
                <th>#</th>
                <td><spring:message code="adminCustomers.fullName" /></td>
                <th><spring:message code="customerPayments.totalPayment"/></th>
                <th><spring:message code="customerPayments.discount"/></th>
                <th><spring:message code="customerPayments.project"/></th>
                <th><spring:message code="customerPayments.time"/></th>
                <th><spring:message code="customerPayments.function"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="sumTotalPayment" value="${0}"/>
            <c:set var="sumTotalDiscount" value="${0}"/>
            <c:forEach items="${customerPayments}" var="item">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.getCustomer().fullName}</td>
                    <td>${item.totalPayment}
                            <c:set var="sumTotalPayment"
                                   value="${sumTotalPayment+item.totalPayment}"/>
                    <td>
                        <c:if test="${item.discount==null}">
                            0
                        </c:if>
                        ${item.discount}
                        <c:set var="sumTotalDiscount"
                               value="${sumTotalDiscount+item.discount}"/>
                    </td>
                    <td>${item.customerProject.name}</td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                        value="${item.time}"/></td>
                    <td>
                        <div>

                            <a class="btn btn-info" target="_blank"
                               href="<c:url value="/customerPayments/" />${item.id}/print">
                                <i class="fa fa-print"></i>
                            </a>
                            <button class="btn btn-danger"
                                    onclick="deleteCustomerPayment(${item.id})">
                                <i class="fa fa-times"></i>
                            </button>

                        </div>
                    </td>
                </tr>
            </c:forEach>
            <tr class="text-info">
                <td><spring:message code="customerPayments.total"/></td>
                <td>&nbsp;</td>
                <td><fmt:formatNumber value="${sumTotalPayment}"
                                      maxFractionDigits="3"/></td>
                <td><fmt:formatNumber value="${sumTotalDiscount}"
                                      maxFractionDigits="3"/></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>

            </tbody>

            <tfoot>
            <tr>
                <th>#</th>
                <th><spring:message code="adminCustomers.fullName"/></th>
                <th><spring:message code="customerPayments.totalPayment"/></th>
                <th><spring:message code="customerPayments.discount"/></th>
                <th><spring:message code="customerPayments.project"/></th>
                <th><spring:message code="customerPayments.time"/></th>
                <th><spring:message code="customerPayments.function"/></th>
            </tr>
            </tfoot>

        </table>

    </div>

</div>





