<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>

    <form:form id="edit-customer-payment-form" method="POST"
               modelAttribute="customerPayment" onsubmit="editCustomerPayment(event)">
        <form:input type="hidden" path="id"/>

        <table>
            <tbody>

            <tr>
				<td class="text-left"><spring:message
						code="addCustomerPayment.totalPayment"/></td>
				<td><sf:input type="number" step="any" class="form-control"
							  path="totalPayment"/></td>
				<td><sf:errors path="totalPayment"/></td>
			</tr>
			<tr>
				<td class="text-left"><spring:message
						code="addCustomerPayment.discount"/></td>
				<td><sf:input type="number" step="any" class="form-control"
							  path="discount"/></td>
				<td><sf:errors path="discount"/></td>
			</tr>

            <tr>
                <td class="text-left"><spring:message
                        code="addCustomerPayment.project"/></td>
                <td><sf:select id="productUnit" class="form-control" path="customerProject">
                    <option selected disabled
                            value="${customerPayment.customerProject}">${customerPayment.customerProject.name}
                        <spring:message
                                code="addCustomerPayment.choose"/></option>
                </sf:select></td>

                <td><sf:errors path="customerProject"/></td>
            </tr>

            <tr>
                <td>
                    <button class="btn btn-warning">
                        <i class="fa fa-edit"></i>
                    </button>
                </td>
            </tr>

            </tbody>

        </table>

    </form:form>

</div>


<script>
    var csrf = '${_csrf.token}';

    function editCustomerPayment(event) {
        event.preventDefault();
        console.log("editCustomerPayment->fired");

        var data = $("#edit-customer-payment-form").serializeJSON();
        console.log("data=", data);
        $
            .ajax({

                type: "POST",
                url: "<c:url value="/customerPayments/customerpayment/edit/"/>${customerPayment.id}",
                headers: {
                    'X-CSRF-TOKEN': csrf
                },
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function (response) {
                    $("#modal-body").html(response);
                    $("#modal").modal("show");
                },
                error: function (response) {
                    $("#modal-body").html(response.responseText);
                    $("#modal").modal("show");
                }
            });
    }
</script>