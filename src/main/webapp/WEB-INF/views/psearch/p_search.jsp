<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="container-fluid">

    <h4>
        <spring:message code="productRecord.title"/>
    </h4>
    <hr>


    <div>
        <spring:url value="/psearch/find" var="auserActionUrl"/>
        <div class="row justify-content-center">


            <div class="col-10">
                <form action="${auserActionUrl}" method="post">


                    <br>
                    <div class="form-row justify-content-center">
                        <div class="col-3">

                            <select required id="cus_names" class="form-control" name="product">
                                <option></option>
                                <c:forEach items="${products}" var="item">
                                    <option value="${item.id}">${item.name}-${item.code}</option>
                                </c:forEach>

                            </select>
                        </div>

                        <div class="col-3">

                            <select id="stock" class="form-control" name="stocks">
                                <option value="all"><spring:message code="productRecord.stocks"/></option>
                                <c:forEach items="${stocks}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>

                            </select>
                        </div>

                        <div class="col-2">
                            <button class="btn w-100 btn-info">
                                <i class="fas fa-search-dollar"></i> <spring:message code="profit.calculate"/>
                            </button>


                        </div>

                        <div class="col-2">
                            <a onclick="printData()" class="btn btn-secondary text-white w-100">
                                <spring:message code="profit.export"/>
                            </a>


                        </div>
                    </div>


                </form>

            </div>
        </div>


        <br>


    </div>


    <br>
    <br>

    <table id="profittable" class="table table-striped table-bordered dt">

        <thead class="bg-dark text-white">
        <tr>

            <th colspan="8">${selected.name}</th>
        </tr>
        <tr>

            <th><spring:message code="productRecord.invoice"/></th>
            <th><spring:message code="productRecord.type"/></th>
            <th><spring:message code="productRecord.time"/></th>
            <th><spring:message code="productRecord.quantity"/></th>
            <th><spring:message code="productRecord.price"/></th>
            <th><spring:message code="productRecord.stock"/></th>
            <th><spring:message code="productRecord.detail"/></th>
            <th><spring:message code="productRecord.stockLevel"/></th>

        </tr>
        </thead>

        <tbody>

        <c:set var="profitt" value="${0}"/>
        <c:forEach items="${report}" var="item">
            <c:if test="${item.type=='Order'}">
                <c:set var="profitt" value="${profitt+item.QUANTITY}"/>

            </c:if>

            <c:if test="${item.type=='sale'}">
                <c:set var="profitt" value="${profitt-item.QUANTITY}"/>

            </c:if>

            <c:if test="${item.type=='return'}">
                <c:set var="profitt" value="${profitt+item.QUANTITY}"/>

            </c:if>

            <c:if test="${item.type=='Vendor Return'}">
                <c:set var="profitt" value="${profitt-item.QUANTITY}"/>

            </c:if>

            <c:if test="${item.type=='transfer'}">

                <c:if test="${selected_stock!=-1}">

                    <c:if test="${selected_stock==item.stockname}">
                        <c:set var="profitt" value="${profitt+item.QUANTITY}"/>
                    </c:if>
                    <c:if test="${selected_stock!=item.stockname}">
                        <c:set var="profitt" value="${profitt-item.QUANTITY}"/>
                    </c:if>
                </c:if>
            </c:if>


            <tr
                    <c:if test="${item.type=='Order'}">

                        style="background: rgba(0,255,0,0.05)"
                    </c:if>

                    <c:if test="${item.type=='sale'}">

                        style="background: rgba(0,0,255,0.05)"
                    </c:if>

                    <c:if test="${item.type=='transfer'}">

                        style="background: rgba(255,255,0,0.05)"
                    </c:if>

                    <c:if test="${item.type=='Vendor Return'}">

                        style="background: rgba(255,255,255,0.05)"
                    </c:if>
            >
                <td>${item.invoice}</td>
                <td>${item.type}</td>
                <td>${item.time}</td>
                <td>${item.QUANTITY}</td>
                <td>${item.payment}</td>
                <td>${item.stockname}</td>
                <td>${item.detail}</td>
                <td>${profitt}</td>

            </tr>
        </c:forEach>


        </tbody>
        <tfoot class="bg-secondary text-white">
        <tr>
            <td><spring:message code="productRecord.stockLevel"/></td>
            <td colspan="6"></td>
            <td>
                <fmt:formatNumber type="number" maxFractionDigits="0" value="${pval.amount}"/>
                <fmt:formatNumber type="number" maxFractionDigits="0" value="${sumval}"/></td>
        </tr>

        </tfoot>

    </table>

    <br>
    <br>

</div>


<style>
    .table tr td, .table thead tr th {

        padding: 5px;
    }
</style>

<script>

    function printData() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();
        today = yyyy + '-' + mm + '-' + dd;
        var divToPrint = document.getElementById("profittable");
        newWin = window.open("");

        var title = "PRODUCT RECORDS"
        newWin.document.write("<style>*{text-decoration: none;} .tooltiptext{display:none;} a{color: black} table{width:100%;} table tfoot{background:darkgrey} table thead{background: black; color: white;} .title{text-align: center;}  .action{display:none;} table tr td{padding : 5px; text-align: center; border: solid 1px black;}</style>");

        newWin.document.write();

        newWin.document.write("<h3 class=\"title\">" + title + " - " + today + "</h3>");


        newWin.document.write(divToPrint.outerHTML);
        newWin.print();
        newWin.close();
    }</script>

<script>

    function display(element, id) {

        if (element.checked == true) {
            document.getElementById(id).style.display = "block";
        } else {
            document.getElementById(id).style.display = "none";
        }
    }

    window.addEventListener('load', function () {

        $('#profittable').DataTable();
        $('#cus_names').select2({
            placeholder: '<spring:message javaScriptEscape="true" code="productRecord.selectProduct"/>'

        });

    });


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

</style>