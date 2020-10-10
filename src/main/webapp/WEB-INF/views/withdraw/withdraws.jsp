<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<script>
    var csrf = '${_csrf.token}';
</script>


<div>

    <h4>
        <spring:message code="adminWithdraws.title" />
    </h4>

    <hr>

    <div>
        <form class="form-inline" action="<c:url value="/withdraws" />">
            <div class="form-group">
                <label for="from"><spring:message code="adminWithdraws.from" /></label>
                <input readonly class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>
            </div>
            <div class="form-group">
                <label for="to"><spring:message code="adminWithdraws.to"/></label>
                <input readonly class="form-control mx-sm-3" id="to" name="to"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${to}" />"/>
            </div>
            <div class="form-group">
                <label ><spring:message code="adminWithdraws.withdrawCategory" /></label>
                <select class="form-control mx-sm-3" name="cat">
                    <option value="-1" selected disabled >All</option>
                    <c:forEach items="${category}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <button class="btn btn-outline-info">
                    <i class="fa fa-eye"></i>
                </button>
            </div>

        </form>
    </div>
    <hr>

    <div class="add-new-doctor-div">
        <button class="btn btn-success" onclick="getAddingWithdraw()">
            <i class="fa fa-plus"></i>
        </button>
    </div>

    <table id="table" class="display nowrap">
        <thead>
        <tr>
            <th><spring:message code="adminWithdraws.name" /></th>
            <th><spring:message code="adminWithdraws.amount" /></th>
            <th><spring:message code="adminWithdraws.withdrawCategory" /></th>
            <th><spring:message code="adminWithdraws.time" /></th>
            <th><spring:message code="adminWithdraws.note" /></th>
            <th class="cus-not-search"><spring:message
                    code="adminWithdraws.function" /></th>
        </tr>
        </thead>
        <tbody>


        <c:set var="sumTotalAmount" value="${0}" />

        <c:forEach items="${withdraws}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.amount}<c:set var="sumTotalAmount"
                                         value="${sumTotalAmount+item.amount}" />
                </td>
                <td>${item.withdrawCategory.name}</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                    value="${item.time}" /></td>
                <td class="cus-note-td">${item.note}</td>
                <td>
                    <div>
                        <button class="btn btn-danger"
                                onclick="deleteWithdraw(${item.id})">
                            <i class="fa fa-times"></i>
                        </button>
                        <button class="btn btn-warning"
                                onclick="editingWithdraw(${item.id})">
                            <i class="fa fa-edit"></i>
                        </button>
                    </div>
                </td>


            </tr>
        </c:forEach>

        <tr class="text-info">
            <td><spring:message code="adminWithdraws.total" /></td>

            <td><fmt:formatNumber value="${sumTotalAmount}"
                                  maxFractionDigits="3" /></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>

        </tbody>

        <tfoot>
        <tr>
            <th><spring:message code="adminWithdraws.name" /></th>
            <th><spring:message code="adminWithdraws.amount" /></th>
            <th><spring:message code="adminWithdraws.withdrawCategory" /></th>
            <th><spring:message code="adminWithdraws.time" /></th>
            <th><spring:message code="adminWithdraws.note" /></th>
            <th class="cus-not-search">&nbsp;</th>
        </tr>
        </tfoot>

    </table>

</div>





