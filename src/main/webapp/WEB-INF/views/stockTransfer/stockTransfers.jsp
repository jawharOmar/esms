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
        <spring:message code="stockTransfers.title"/>
    </h4>
    <hr>
    <div>
        <form class="form-inline" action="<c:url value="/stockTransfers" />">
            <div class="form-group">
                <label for="from"><spring:message code="stockTransfers.from"/></label>
                <input readonly class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd"
         value = "${from}" />"/>

            </div>
            <div class="form-group">
                <label for="to"><spring:message code="stockTransfers.to"/></label>
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
        <a class="btn btn-success" href="<c:url value="stockTransfers/add" />">
            <i class="fa fa-plus"></i>
        </a>
    </div>

    <table id="table" class="display nowrap">
        <thead>
        <tr>
            <th><spring:message code="stockTransfers.time"/></th>
            <th><spring:message code="stockTransfers.from"/></th>
            <th><spring:message code="stockTransfers.to"/></th>
            <th class="cus-not-search"><spring:message
                    code="stockTransfers.function"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${stockTransfers}" var="item">
            <tr>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
                                    value="${item.time}"/></td>
                <td>${item.from.name}</td>
                <td>${item.to.name}</td>
                <td>
                    <div>
                        <button class="btn btn-danger"
                                onclick="deleteStockTransfer(${item.id})">
                            <i class="fa fa-times"></i>
                        </button>
                        <a class="btn btn-warning"
                           href="<c:url value="stockTransfers/edit/"/>${item.id}"> <i
                                class="fa fa-edit"></i>
                        </a> <a class="btn btn-info"
                                href="<c:url value="stockTransfers/"/>${item.id}/print"> <i
                            class="fa fa-print"></i>
                    </a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>





