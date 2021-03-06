<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

    <sf:form id="add-withdraw-form" method="POST" commandName="withdraw"
             onsubmit="addWithdraw(event)">

        <table>
            <tbody>

            <tr>
                <td class="text-left"><spring:message code="addWithdraw.name" /></td>
                <td><sf:input class="form-control" path="name" /></td>
                <td><sf:errors path="name" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message code="addWithdraw.balance" /></td>
                <td>
                    <input type="number" step="any" class="form-control" readonly value="${accountBalance}"></td>
                <td></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message code="addWithdraw.amount" /></td>
                <td><sf:input type="number" step="any" class="form-control" required="required" min="1"
                              path="amount" max="${accountBalance}" /></td>
                <td><sf:errors path="amount" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message code="addWithdraw.note" /></td>
                <td><sf:textarea class="form-control" path="note" /></td>
                <td><sf:errors path="note" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message
                        code="addWithdraw.withdrawCategory" /></td>
                <td><select class="form-control" name="withdrawCategory[id]" required="required">
                    <option value=""><spring:message
                            code="addWithdraw.choose" /></option>
                    <c:forEach items="${withdrawCategories}" var="item">
                        <c:if test="${withdraw.withdrawCategory.id==item.id}">
                            <option selected="selected" value="${item.id}">${item.name}</option>
                        </c:if>
                        <c:if test="${withdraw.withdrawCategory.id!=item.id}">
                            <option value="${item.id}">${item.name}</option>
                        </c:if>
                    </c:forEach>

                </select></td>

                <td><sf:errors path="withdrawCategory" /></td>
            </tr>

            <tr>
                <td><button class="btn btn-success">
                    <i class="fa fa-plus"></i>
                </button></td>
            </tr>

            </tbody>

        </table>

    </sf:form>

</div>


<script>
    var csrf = '${_csrf.token}';
    function addWithdraw(event) {
        event.preventDefault();
        console.log("addWithdraw->fired");

        var data = $("#add-withdraw-form").serializeJSON();
        console.log("data=", data);
        $.ajax({
            type : "POST",
            url : "<c:url value="/withdraws/add"/>",
            headers : {
                'X-CSRF-TOKEN' : csrf
            },
            data : JSON.stringify(data),
            contentType : "application/json",
            success : function(response) {
                $("#modal-body").html(response);
                $("#modal").modal("show");
            },
            error : function(response) {
                $("#modal-body").html(response.responseText);
                $("#modal").modal("show");
            }
        });
    }
</script>