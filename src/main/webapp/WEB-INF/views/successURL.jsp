<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<table style="width: 100%">
    <tr>
        <td style="text-align: center"><span><spring:message
                code="success.message" /> </span></td>
    </tr>
</table>

<script>
    var url = '<spring:escapeBody  javaScriptEscape="true">${url}</spring:escapeBody>';
    setTimeout(function() {
        window.location.href = $$ContextURL +"/"+ url;
    }, 1500);
</script>