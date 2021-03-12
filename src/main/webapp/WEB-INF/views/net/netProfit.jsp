<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
    var csrf = '${_csrf.token}';
    var from = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>';
    var to = '<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';
    var reportTitle = '<fmt:formatDate value="${from}" pattern="yyyy-MM-dd"/>_To_<fmt:formatDate value="${to}" pattern="yyyy-MM-dd"/>';

</script>

<div>

    <h2>
        <spring:message code="netProfit.title"/>
    </h2>
    <hr>

    <div>
        <form class="form-inline" action="<c:url value="/netProfit/main" />">
            <div class="form-group">
                <label for="from"><spring:message code="netProfit.from"/></label>
                <input class="form-control mx-sm-3" id="from" name="from"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${from}" />"/>
            </div>
            <div class="form-group">
                <label for="to"><spring:message code="netProfit.to"/></label>
                <input class="form-control mx-sm-3" id="to" name="to"
                       value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${to}" />"/>
            </div>
            <div class="form-group">
                <button class="btn btn-outline-info">
                    <i class="fa fa-eye"></i>
                </button>
            </div>
        </form>
    </div>

    <hr>

    <div class="form-row justify-content-center">
        <form class="col-2" action="<c:url value="/netProfit/main" />">
            <input id="from_btn" type="hidden" name="from" value=""/>
            <input id="to_btn" type="hidden" name="to" value=""/>
            <div>
                <button class="btn btn-dark w-100"><spring:message code="netProfit.today"/></button>
            </div>
        </form>
        <form class="col-2" action="<c:url value="/netProfit/main" />">
            <input id="from_week" type="hidden" name="from" value=""/>
            <input id="to_week" type="hidden" name="to" value=""/>
            <div>
                <button class="btn btn-dark w-100"><spring:message code="netProfit.week"/></button>
            </div>
        </form>
        <form class="col-2" action="<c:url value="/netProfit/main" />">
            <input id="from_month" type="hidden" name="from" value=""/>
            <input id="to_month" type="hidden" name="to" value=""/>
            <div>
                <button class="btn btn-dark w-100"><spring:message code="netProfit.month"/></button>
            </div>
        </form>
        <form class="col-2" action="<c:url value="/netProfit/main" />">
            <input id="from_year" type="hidden" name="from" value=""/>
            <input id="to_year" type="hidden" name="to" value=""/>
            <div>
                <button class="btn btn-dark w-100"><spring:message code="netProfit.year"/></button>
            </div>
        </form>

    </div>
    <br>

    <c:set var="totalAmount" value="${0}"/>

    <table id="netProfitTable" class="display">
        <thead>
        <tr>
            <td><spring:message code="netProfit.type"/></td>
            <td><spring:message code="netProfit.amount"/></td>
            <td><spring:message code="netProfit.total"/></td>
        </tr>
        </thead>
        <tbody>
        <c:if test="${oldBalance!=0}">
            <tr>
                <td><spring:message code="netProfit.oldBalance"/></td>
                <td><fmt:formatNumber maxFractionDigits="3"
                                      value="${oldBalance+totalAmount}"/></td>
                <td><fmt:formatNumber maxFractionDigits="3"
                                      value="${oldBalance+totalAmount}"/> <c:set
                        var="totalAmount" value="${oldBalance+totalAmount}"/></td>
            </tr>
        </c:if>

        <c:forEach items="${netProfitDS}" var="item">
            <tr>
                <td>${item.type}</td>
                <td><fmt:formatNumber maxFractionDigits="3"
                                      value="${item.amount}"/></td>
                <td><fmt:formatNumber maxFractionDigits="3"
                                      value="${item.amount+totalAmount}"/> <c:set
                        var="totalAmount" value="${item.amount+totalAmount}"/></td>
            </tr>
        </c:forEach>

        </tbody>
        <tfoot>
        <td><spring:message code="netProfit.total"/></td>
        <td></td>
        <td><fmt:formatNumber maxFractionDigits="3"
                              value="${totalAmount}"/></td>
        </tfoot>

    </table>

</div>


<script>
    window.addEventListener('load', function () {
            $("#from").datepicker({
                dateFormat: "yy-mm-dd"
            }).datepicker("setDate", $("#from").val());

            $("#to").datepicker({
                dateFormat: "yy-mm-dd"
            }).datepicker("setDate", $("#to").val());


            $('#netProfitTable').DataTable({
                dom: 'lBfrtip',
                buttons: [
                    {
                        extend: "excel",
                        className: "ml-5 btn btn-sm  btn-outline-info",
                        footer: true,
                        messageTop: reportTitle,
                        filename: reportTitle,
                    }
                ],
                "deferRender": true

            });


            var today = new Date();
            var tommorw = new Date();
            tommorw.setDate(today.getDate() + 1);

            var todday = moment(today).format('YYYY-MM-DD');
            var tommorow = moment(tommorw).format('YYYY-MM-DD');

            var startOfweek = moment().startOf('week').format('YYYY-MM-DD');
            var lastOfweek = moment().startOf('week').add(1, 'week').format('YYYY-MM-DD');

            var startOfMonth = moment().startOf('month').format('YYYY-MM-DD');
            var lastOfMonth = moment().startOf('month').add(1, 'month').format('YYYY-MM-DD');

            var startOfYear = moment().startOf('year').format('YYYY-MM-DD');
            var lastOfYear = moment().startOf('year').add(1, 'years').format('YYYY-MM-DD');


            document.getElementById("from_btn").setAttribute("value", todday);
            document.getElementById("to_btn").setAttribute("value", tommorow);

            document.getElementById("from_week").setAttribute("value", startOfweek);
            document.getElementById("to_week").setAttribute("value", lastOfweek);

            document.getElementById("from_month").setAttribute("value", startOfMonth);
            document.getElementById("to_month").setAttribute("value", lastOfMonth);
            document.getElementById("from_year").setAttribute("value", startOfYear);
            document.getElementById("to_year").setAttribute("value", lastOfYear);
        }
    );

</script>




