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
</script>


<div>

    <h4>
        <spring:message code="incomeCategories.title"/>
    </h4>

    <hr>

    <div>
        <button class="btn btn-success" onclick="getAddingIncomeCategory()">
            <i class="fa fa-plus"></i>
        </button>
    </div>

    <table id="table" class="display nowrap">
        <thead>
        <tr>
            <th><spring:message code="incomeCategories.name"/></th>
            <th class="cus-not-search"><spring:message
                    code="incomeCategories.function"/></th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${incomeCategories}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>
                        <div>
                            <button class="btn btn-danger"
                                    onclick="deleteIncomeCategory(${item.id})">
                                <i class="fa fa-times"></i>
                            </button>
                            <button class="btn btn-warning"
                                    onclick="editingIncomeCategory(${item.id})">
                                <i class="fa fa-edit"></i>
                            </button>
                        </div>
                </td>


            </tr>
        </c:forEach>

        </tbody>

    </table>

</div>





