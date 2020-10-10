<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="container-fluid">

    <h4>
        <spring:message code="profit.title"/>
    </h4>
    <hr>


    <div>
        <spring:url value="/profit/calc" var="auserActionUrl"/>
        <div class="row justify-content-center">


            <div class="col-10">
                <form action="${auserActionUrl}" method="post">


                    <div class="form-row justify-content-center">
                        <div class="col-md-5">
                            <input require id="from_date" placeholder='<spring:message code="customerOrders.from"/>'
                                   class="form-control" name="from"/>
                        </div>
                        <div class="col-md-5">
                            <input id='to_date' placeholder=
                            <spring:message code="customerOrders.to"/> name="to" class="form-control" name="to"/>
                        </div>
                    </div>

                    <br>
                    <div class="form-row justify-content-center">
                        <div class="col-6">

                            <select id="cus_names" class="form-control" name="Customer">
                                <option value="all"><spring:message code="profit.allCustomers"/></option>
                                <c:forEach items="${customers}" var="item">
                                    <option value="${item.id}">${item.fullName}-${item.phone}</option>
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
        <div class="form-row justify-content-center">


            <form class="col-2" action="${auserActionUrl}" method="post">
                <input type="hidden" value="all" name="Customer">
                <input id="from_btn" type="hidden" name="from" value=""/>
                <input id="to_btn" type="hidden" name="to" value=""/>
                <div>
                    <button class="btn btn-dark w-100"><spring:message code="profit.today"/></button>
                </div>
            </form>
            <form class="col-2" action="${auserActionUrl}" method="post">
                <input type="hidden" value="all" name="Customer">
                <input id="from_week" type="hidden" name="from" value=""/>
                <input id="to_week" type="hidden" name="to" value=""/>
                <div>
                    <button class="btn btn-dark w-100"><spring:message code="profit.week"/></button>
                </div>
            </form>
            <form class="col-2" action="${auserActionUrl}" method="post">
                <input type="hidden" value="all" name="Customer">
                <input id="from_month" type="hidden" name="from" value=""/>
                <input id="to_month" type="hidden" name="to" value=""/>
                <div>
                    <button class="btn btn-dark w-100"><spring:message code="profit.month"/></button>
                </div>
            </form>
            <form class="col-2" action="${auserActionUrl}" method="post">
                <input type="hidden" value="all" name="Customer">
                <input id="from_year" type="hidden" name="from" value=""/>
                <input id="to_year" type="hidden" name="to" value=""/>
                <div>
                    <button class="btn btn-dark w-100"><spring:message code="profit.year"/></button>
                </div>
            </form>

        </div>

    </div>


    <br>
    <br>

    <table id="profittable" class="table table-striped table-bordered dt">

        <thead class="bg-dark text-white">
        <tr>
            <th><spring:message code="profit.invoice"/></th>
            <th><spring:message code="profit.customer"/></th>
            <th><spring:message code="profit.cost"/></th>
            <th><spring:message code="profit.price"/></th>
            <th><spring:message code="profit.discount"/></th>
            <th><spring:message code="profit.time"/></th>
            <th><spring:message code="profit.profit"/></th>

        </tr>
        </thead>

        <tbody>

        <c:set var="profitt" value="${0}"/>
        <c:set var="totalCost" value="${0}"/>
        <c:set var="totalPrice" value="${0}"/>
        <c:set var="totalDiscount" value="${0}"/>
        <c:forEach items="${profit}" var="item">
            <tr>

                <c:set var="profitt" value="${profitt+item.profit}"/>
                <c:set var="totalCost" value="${totalCost+item.cost}"/>
                <c:set var="totalPrice" value="${totalPrice+item.price}"/>
                <c:set var="totalDiscount" value="${totalDiscount+item.discount}"/>
                <td><a href="../customerOrders/${item.id}" target="_blank">${item.id}</a></td>
                <td>${item.name}</td>
                <td><fmt:formatNumber type="number"
                                      maxFractionDigits="3" value="${item.cost}"/></td>
                <td>${item.price}</td>
                <td>${item.discount}</td>
                <td>${item.time}</td>
                <td><fmt:formatNumber type="number"
                                      maxFractionDigits="3" value="${item.profit}"/></td>
            </tr>
        </c:forEach>


        </tbody>
        <tfoot class="bg-secondary text-white">
        <tr>
            <td><spring:message code="profit.totalProfit"/></td>
            <td>
                <c:set var="profitt" value="${profitt-discount}"/>
                <spring:message code="profit.paymentDiscount"/> ${discount}</td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${totalCost}"/></td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${totalPrice}"/></td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${totalDiscount}"/></td>
            <td></td>
            <td><fmt:formatNumber type="number"
                                  maxFractionDigits="3" value="${profitt}"/></td>
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

        var title = "ربح"
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
            placeholder: "All Customer"

        });
        $('#from_date').datepicker({startDate: '-3d'});
        $('#to_date').datepicker({startDate: '-3d'});

        var today = new Date();
        var tommorw = new Date();
        tommorw.setDate(today.getDate() + 1);

        var todday = moment(today).format('L');
        var tommorow = moment(tommorw).format('L');

        var startOfweek = moment().startOf('week').format('L');
        var lastOfweek = moment().startOf('week').add(1, 'week').format('L');

        var startOfMonth = moment().startOf('month').format('L');
        var lastOfMonth = moment().startOf('month').add(1, 'month').format('L');

        var startOfYear = moment().startOf('year').format('L');
        var lastOfYear = moment().startOf('year').add(1, 'years').format('L');


        document.getElementById("from_btn").setAttribute("value", todday);
        document.getElementById("to_btn").setAttribute("value", tommorow);

        document.getElementById("from_week").setAttribute("value", startOfweek);
        document.getElementById("to_week").setAttribute("value", lastOfweek);

        document.getElementById("from_month").setAttribute("value", startOfMonth);
        document.getElementById("to_month").setAttribute("value", lastOfMonth);
        document.getElementById("from_year").setAttribute("value", startOfYear);
        document.getElementById("to_year").setAttribute("value", lastOfYear);


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