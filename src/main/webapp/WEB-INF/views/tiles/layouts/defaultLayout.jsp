<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="../../version.jsp" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;UTF-8">
<meta charset="UTF-8" />
<title><tiles:getAsString name="title" /></title>

<tiles:importAttribute name="requiredCSSFiles" />
<c:if test="${!empty requiredCSSFiles}">

	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet">
	<c:forEach var="item" items="${requiredCSSFiles}">
		<c:if
			test="${(pageContext.response.locale=='ar_SY'||pageContext.response.locale=='ar')&& item=='bootstrap'}">
			<c:set var="item" value="bootstrap_rtl" />
		</c:if>
		<link href="<c:url value="/resources/css/${item}.css" />"
			rel="stylesheet"></link>
	</c:forEach>

</c:if>

<tiles:importAttribute name="rightToLeftCSS" />
<c:if
	test="${(pageContext.response.locale=='ar_SY'||pageContext.response.locale=='ar')&&!empty rightToLeftCSS}">

	<link
		href="<c:url value="/resources/css/${rightToLeftCSS}.css?${version}" />"
		rel="stylesheet"></link>
</c:if>


<style type="text/css">
@font-face {
	font-family: "NotoKufiArabicBold";
	src: url("<c:url value='/resources/fonts/NotoKufiArabic-Bold.woff'/>")
		format('woff');
}

@font-face {
	font-family: "NotoKufiArabic";
	src: url("<c:url value='/resources/fonts/NotoKufiArabic.woff'/>")
		format('woff');
}

body {
	font-family: NotoKufiArabic !important;
}
</style>

</head>
<body>

	<div id="app-loading-div">
		<img id="" src="<c:url value="/resources/img/loading.gif" />" />
	</div>

	<section id="view-port">

		<header id="header">
			<tiles:insertAttribute name="header" />
		</header>

		<section id="site-content">
			<tiles:insertAttribute name="body" />

		</section>


		<footer id="footer">
			<tiles:insertAttribute name="footer" />
		</footer>

	</section>

	<tiles:importAttribute name="requiredJSFiles" />
	<c:if test="${!empty requiredJSFiles}">
		<c:forEach var="item" items="${requiredJSFiles}">
			<c:choose>
				<c:when test="${item=='jsLang'}">
					<script type="text/javascript" src="<c:url value='/${item}.js' />"></script>
				</c:when>
				<c:otherwise>
					<script type="text/javascript"
						src="<c:url value='/resources/js/${item}.js' />"></script>
				</c:otherwise>
			</c:choose>

		</c:forEach>
	</c:if>
	
	<script>
		$('#cus_names').select2({
			placeholder : '<spring:message code="report.selectCustomer"/>'
		});
	</script>

	<script type="text/javascript" language="javascript">
		function RunFile() {
			if (document.getElementById("calc-contain").style.display
					.includes("none"))
				document.getElementById("calc-contain").style.display = "block";
			else {
				document.getElementById("calc-contain").style.display = "none";
			}

		}
	</script>
	<c:url var="urll" value="/setting/currency" />
	<script>
		function currencyview() {

			$.get("${urll}", function(data) {
				$("#curbase").val(data.base);
				$("#tocur").val(data.to);
				$("#currate").val(data.rate);
				$("#curratereverse").val(data.reverseRate);
			});
			$('#Currency-dialog').dialog();
		}
	</script>

	<!-- Modal -->
	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLongTitle" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" id="modal-body"></div>
			</div>
		</div>
	</div>

	<style>
.calc_cus {
	background: rgba(0, 0, 0, 0.5);
	width: 100px;
	height: auto;
	position: absolute;
	top: 35px;
	left: 50px;
}
</style>


	<form dir="ltr" class="calc_cus" style="display: none;"
		id="calc-contain" name="calculator">

		<table dir="ltr" id="calculator">
			<tr>
				<td colspan="4"><input class="form-control" type="text"
					name="answer" /></td>
			</tr>
			<tr>
				<td><input class="btn-secondary" type="button" value=" 1 "
					onclick="calculator.answer.value += '1'" /></td>
				<td><input class="btn-secondary" type="button" value=" 2 "
					onclick="calculator.answer.value += '2'" /></td>
				<td><input class="btn-secondary" type="button" value=" 3 "
					onclick="calculator.answer.value += '3'" /></td>
				<td><input class="btn-info" type="button" value=" + "
					onclick="calculator.answer.value += '+'" /></td>
			</tr>

			<tr>
				<td><input class="btn-secondary" type="button" value=" 4 "
					onclick="calculator.answer.value += '4'" /></td>
				<td><input class="btn-secondary" type="button" value=" 5 "
					onclick="calculator.answer.value += '5'" /></td>
				<td><input class="btn-secondary" type="button" value=" 6 "
					onclick="calculator.answer.value += '6'" /></td>
				<td><input class="btn-info" type="button" value=" - "
					onclick="calculator.answer.value += '-'" /></td>
			</tr>


			<tr>
				<td><input class="btn-secondary" type="button" value=" 7 "
					onclick="calculator.answer.value += '7'" /></td>
				<td><input class="btn-secondary" type="button" value=" 8 "
					onclick="calculator.answer.value += '8'" /></td>
				<td><input class="btn-secondary" type="button" value=" 9 "
					onclick="calculator.answer.value += '9'" /></td>
				<td><input class="btn-info" type="button" value=" x "
					onclick="calculator.answer.value += '*'" /></td>
			</tr>


			<tr>
				<td><input class="btn-warning" type="button" value=" c "
					onclick="calculator.answer.value = ''" /></td>
				<td><input class="btn-secondary" type="button" value=" 0 "
					onclick="calculator.answer.value += '0'" /></td>
				<td><input class="btn-success" type="button" value=" = "
					onclick="calculator.answer.value = eval(calculator.answer.value)" />
				</td>
				<td><input class="btn-info" type="button" value=" / "
					onclick="calculator.answer.value += '/'" />
			</tr>


		</table>
	</form>

</body>
</html>