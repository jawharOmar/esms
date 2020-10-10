<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="./version.jsp"/>


<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Area</title>
	<link rel="icon" type="image/jpg" href="<c:url value="/resources/img/twekl.jpg" />">


	<link href="<c:url value="/resources/css/bootstrap.css?${version}" />"
          rel="stylesheet"></link>


    <link
            href="<c:url value="/resources/css/font-awesome.css?${version}" />"
            rel="stylesheet"></link>

    <link href="<c:url value="/resources/css/login.css?${version}" />"
          rel="stylesheet"></link>

    <style type="text/css">
        @font-face {
            font-family: "NotoKufiArabicBold";
            src: url("<c:url value='/resources/fonts/NotoKufiArabic-Bold.woff?${version}'/>") format('woff');
        }

        @font-face {
            font-family: "NotoKufiArabic";
            src: url("<c:url value='/resources/fonts/NotoKufiArabic.woff?${version}'/>") format('woff');
        }

        body {
            font-family: NotoKufiArabic !important;
        }

        td {
            text-align: left;
        }

        .center-td {
            text-align: center;
        }
    </style>
    <c:if
            test="${pageContext.response.locale==\"ar\"||pageContext.response.locale==\"ar_SY\"}">
        <style type="text/css">
            * {
                direction: rtl;
            }

            td {
                text-align: right;
            }

            table {
                width: 80%;
            }
        </style>
    </c:if>

</head>
<body>
<div id="cus-login-contianer" class="card">
    <c:url var="loginUrl" value="/login"/>
    <form action="${loginUrl}" method="POST">
        <table>
            <tr>
                <td>&nbsp;</td>
                <td class="center-td"><i class="fa fa-user cus-user-icon"></i></td>
            </tr>
            <tr>
                <td><spring:message code="login.userName"/></td>
                <td><input class="form-control form-control-sm"
                           name="username"/></td>
            </tr>
            <tr>
                <td><spring:message code="login.password"/></td>
                <td><input class="form-control form-control-sm"
                           name="password" type="password"></td>
            </tr>
            <tr>
                <td><spring:message code="login.rememberMe"/></td>
                <td><input type="checkbox" name="remember-me"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td class="center-td" id="cus-submit-td"><input
                        id="cus-login-btn" class="btn btn-outline-success" type="submit"
                        value="<spring:message code="login.login" />"></td>
            </tr>
            <c:if test="${param.error!=null}">
                <tr>
                    <td>&nbsp;</td>
                    <td class="text-danger"><spring:message
                            code="login.loginFailed"/></td>
                </tr>
            </c:if>
        </table>
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}">
    </form>


    <table id="lang-table">
        <tr>
            <td><a href="?lang=en"> <img
                    src="<c:url value="/resources/img/english.png?${version}" />"
                    height="50" alt="">
            </a></td>
            <td><a href="?lang=ar"> <img
                    src="<c:url value="/resources/img/arabic.png?${version}" />"
                    height="50" alt="">
            </a></td>
            <td><a href="?lang=ar_SY"> <img
                    src="<c:url value="/resources/img/kurdish.png?${version}" />"
                    height="50" alt="">
            </a></td>
        </tr>
    </table>

</div>


</body>
</html>