<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="add-product-container" style="padding: 5px; overflow: auto">

    <sf:form id="add-product-form" method="POST" commandName="product"
             onsubmit="editingProduct(event)">
        <sf:input path="id" type="hidden" />
        <table>
            <tbody>
            <tr>
                <td><img class="cus-avatar"
                         src="<c:url value="/attachedFiles/1/" />${product.attachedFile.id}">
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left"><spring:message code="editProduct.image" /></td>
                <td><input id="files" type="file"
                           accept="image/x-png,image/gif,image/jpeg,image/jpg"
                           class="form-control form-control-sm" name="file" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message code="editProduct.code" /></td>
                <td><sf:input class="form-control" path="code" /></td>
                <td><sf:errors path="code" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message code="editProduct.name" /></td>
                <td><sf:input class="form-control" path="name" /></td>
                <td><sf:errors class="text-wrap" path="name" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message code="editProduct.unit" /></td>
                <td><sf:input class="form-control" path="unit" /></td>
                <td><sf:errors class="text-wrap" path="unit" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message
                        code="editProduct.unitType" /></td>
                <td><select id="productUnit" onchange="changeProductUnit()"
                            class="form-control" name="productUnitType.id">
                    <option value=""><spring:message
                            code="editProduct.choose" /></option>
                    <c:forEach items="${productUnitTypes}" var="item">
                        <c:if test="${product.productUnitType.id==item.id}">
                            <option selected="selected" value="${item.id}">${item.name}</option>
                        </c:if>
                        <c:if test="${product.productUnitType.id!=item.id}">
                            <option value="${item.id}">${item.name}</option>
                        </c:if>
                    </c:forEach>
                </select></td>
                <td><sf:errors path="productUnitType" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message
                        code="editProduct.packetSize" /></td>
                <td><sf:input type="number" class="form-control"
                              path="packetSize" /></td>
                <td><sf:errors path="packetSize" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message
                        code="editProduct.minimumStockLevel" /></td>
                <td><sf:input type="number" class="form-control"
                              path="minimumStockLevel" /></td>
                <td><sf:errors path="minimumStockLevel" /></td>
            </tr>

            <tr>
                <td class="text-left"><spring:message
                        code="editProduct.category" /></td>
                <td><select class="form-control" name="productCategory.id">
                    <option value=""><spring:message
                            code="editProduct.choose" /></option>
                    <c:forEach items="${productCategories}" var="item">
                        <c:if test="${product.productCategory.id==item.id}">
                            <option selected="selected" value="${item.id}">${item.name}</option>
                        </c:if>
                        <c:if test="${product.productCategory.id!=item.id}">
                            <option value="${item.id}">${item.name}</option>
                        </c:if>
                    </c:forEach>

                </select></td>
                <td><sf:errors path="productCategory" /> <sf:errors
                        path="productCategory.id" /></td>
            </tr>

            <tr class="text-info">
                <td class="text-left"><spring:message code="editProduct.price" /></td>
                <td><sf:input type="number" step="any" class="form-control"
                              path="price" /></td>
                <td><sf:errors path="price" /></td>
            </tr>

            <c:forEach items="${product.productPriceCategories}" var="item"
                       varStatus="loop">
                <c:if test="${item.priceCategory.ratio==null}">
                    <tr class="text-info">
                        <td class="text-left">${item.priceCategory.name}</td>
                        <td><input
                                name="productPriceCategories[${loop.index}].priceCategory.id"
                                value="${item.priceCategory.id}" type="hidden"> <input
                                class="form-control"
                                name="productPriceCategories[${loop.index}].price"
                                value="${item.price}" type="number" step="any"></td>
                    </tr>
                </c:if>
            </c:forEach>



            <tr>
                <td><button class="btn btn-warning" type="submit">
                    <i class="fa fa-edit"></i>
                </button></td>
            </tr>

            </tbody>

        </table>

    </sf:form>

</div>


<script>
    var csrf = '${_csrf.token}';

    $(document).on("keypress", "form input", function(event) {
        return event.keyCode != 13;
    });

    function changeProductUnit() {
        console.log("changeProductUnit->fired");
        var unitType = $('#productUnit option:selected').val();
        console.log("unitType=" + unitType);
        if (unitType != 1) {
            $("#packetSize").val("");
            $("#packetSize").prop('disabled', true);
        } else {
            $("#packetSize").prop('disabled', false);
        }
    }
    changeProductUnit();

    function editingProduct(event) {
        console.log("editProduct->fired");
        event.preventDefault();

        var form = $("#add-product-form")[0];
        console.log("form=", form);
        var data = new FormData(form);

        $.ajax({
            type : "POST",
            url : "<c:url value="/products/update"/>",
            headers : {
                'X-CSRF-TOKEN' : csrf
            },
            data : data,
            enctype : 'multipart/form-data',
            processData : false, // Important!
            contentType : false,
            cache : false,
            success : function(response) {
                $("#modal-body").html(response);
            },
            error : function(response) {
                $("#modal-body").html(response.responseText);
            }
        });
    }
</script>