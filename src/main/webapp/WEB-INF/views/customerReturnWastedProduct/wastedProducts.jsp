<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .dt-buttons {
        float: left;
    }
</style>

<div>

    <h2>
        <spring:message code="layout.wastedProducts"/>
    </h2>
    <hr>


    <table id="table" class="display">
        <thead>
        <tr>
            <th><spring:message code="stock.code"/></th>
            <th><spring:message code="stock.name"/></th>
            <th><spring:message code="stock.category"/></th>
            <th><spring:message code="addOrderPreProduct.quantity"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${wastedProducts}" var="wastedProducts">
            <tr style="height: 30px;">
                <td>${wastedProducts.code}</td>
                <td>${wastedProducts.name}</td>
                <td>${wastedProducts.category}</td>
                <td>${wastedProducts.quantity}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>

    window.addEventListener('load', function () {
            $('#table').DataTable({
                dom: 'lBfrtip',
                buttons: [
                    {
                        extend: "excel",
                        className: "ml-5 btn btn-sm  btn-outline-info",
                        exportOptions: {
                            columns: ':not(.cus-not-export)'
                        }
                    }
                ],
                "deferRender": true

            });
        }
    );
</script>

<style>


    .dt > thead > tr > th[class*="sort"]::after {
        display: none
    }

    .dt > thead > tr > th[class*="sort"]::before {
        display: none
    }

    table > thead > tr > th {
        padding: 3px;
    }

    table.dataTable thead .sorting,
    table.dataTable thead .sorting_asc,
    table.dataTable thead .sorting_desc {
        background: none;
    }

    table.dataTable tbody tr td {
        padding: 1px;
    }

</style>


