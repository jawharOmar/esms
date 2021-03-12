<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="./version.jsp" />


<html lang="en">
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Area</title>


<link href="<c:url value="/resources/css/bootstrap.css?${version}" />"
	rel="stylesheet"></link>


<link
	href="<c:url value="/resources/css/font-awesome.css?${version}" />"
	rel="stylesheet"></link>

<link href="<c:url value="/resources/css/login.css?${version}" />"
	rel="stylesheet"></link>


<link
	href="<c:url value="/resources/css/fontawesome/css/all.min.css?${version}" />"
	rel="stylesheet" type="text/css" />




<c:choose>

	<c:when
		test="${pageContext.response.locale==\"ar\"||pageContext.response.locale==\"ar_SY\"}">
		<link
			href="<c:url value="/resources/css//sb-admin-2.min.rtl.css?${version}" />"
			rel="stylesheet" type="text/css" />
	</c:when>

	<c:otherwise>
		<link
			href="<c:url value="/resources/css//sb-admin-2.min.css?${version}" />"
			rel="stylesheet" type="text/css" />
	</c:otherwise>

</c:choose>

<link href="<c:url value="/resources/css/login.css?${version}" />"
	rel="stylesheet"></link>



<style type="text/css">
@font-face {
	font-family: "NotoKufiArabicBold";
	src:
		url("<c:url value='/resources/fonts/NotoKufiArabic-Bold.woff?${version}'/>")
		format('woff');
}

@font-face {
	font-family: "NotoKufiArabic";
	src:
		url("<c:url value='/resources/fonts/NotoKufiArabic.woff?${version}'/>")
		format('woff');
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
</style>
</c:if>

</head>
<body>
<body class="bg-gradient-primary">

	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-10 col-lg-12 col-md-9">

				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-6" style="display: flex;">

								<img style="width: 441px; height: auto;"
									src="<c:url value="/resources/img/loginLogo.png?${version}" />">
							</div>
							<div class="col-lg-6">
								<div class="p-5">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-4">
											<spring:message code="login.title" />
										</h1>
									</div>

									<c:url var="loginUrl" value="/login" />
									<form class="user" action="${loginUrl}" method="POST">
										<div class="form-group">
											<input name="username" class="form-control form-control-user"
												id="exampleInputEmail" aria-describedby="emailHelp"
												placeholder="<spring:message code="login.userName" />">
										</div>
										<div class="form-group">
											<input type="password" name="password"
												class="form-control form-control-user"
												id="exampleInputPassword"
												placeholder="<spring:message code="login.password" />">
										</div>
										<div class="form-group">
											<div class="custom-control custom-checkbox small">
												<input type="checkbox" class="custom-control-input"
													name="remember-me" id="customCheck"> <label
													class="custom-control-label" for="customCheck"><spring:message
														code="login.rememberMe" /></label>
											</div>
										</div>
										<input class="btn btn-primary btn-user btn-block"
											type="submit" value="<spring:message code="login.login" />">
										<c:if test="${param.error!=null}">
											<spring:message code="login.loginFailed" />
										</c:if>
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
							</div>
						</div>
					</div>
				</div>

			</div>

		</div>

	</div>

	<script type="text/javascript"
		src="<c:url value='/resources/js/jquery.js' />?${version}"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/js/bootstrap.js' />?${version}"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/js/jquery.easing.min.js' />?${version}"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/js/sb-admin-2.min.js' />?${version}"></script>



</body>
</html>