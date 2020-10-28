<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<jsp:include page="../../version.jsp" />

<div ng-controller="navbarCTRL" ng-init="init()">




	<nav id="cus-navbar" class="navbar">


		<table>
			<tr>
				<td>
					<div id="toggle-menu" onclick="toggleMenu()">
						<span></span> <span></span> <span></span>
					</div>
				</td>
				<td><a href="<c:url value="/adminRoot" />"> <img
						src="<c:url value="/resources/img/logo.png?" />${version}"
						height="30" alt="" /> <b class="color-primary"><span
							id="Systemnam"></span></b>
				</a></td>
			</tr>
		</table>
		<sec:authorize url="/customerOrders/add">



			<a style="padding: 5px" href="<c:url value="/customerOrders/add"/>">
				<spring:message code="layout.salePoint" />
			</a>

		</sec:authorize>

		<form action="<c:url value="/logout" />" method="POST">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}">
			<div>
				<button class="btn btn-warning btn-sm" type="button"
					onclick="$('#password-dialog').dialog();">
					<i class="fa fa-key"></i>
				</button>

				<span> <sec:authentication property="principal.username" /></span>

				<button class="btn btn-outline-danger btn-sm">
					<i class="fa fa-power-off"></i>
				</button>
			</div>
		</form>
	</nav>
	<div style="display: none">
		<div id="password-dialog" class="p-1">
			<table class="w-100">
				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.show" /></td>
					<td class="cus-rest-td"><input ng-model="show" type="checkbox"></td>
				</tr>
				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.oldPassword" /></td>
					<td class="cus-rest-td"><input
						ng-model="changePassword.oldPassword"
						type="{{show ? 'text':'password'}}" name="oldPassword"
						class="form-control form-control-sm"></td>
				</tr>

				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.newPassword" /></td>
					<td class="cus-rest-td"><input
						ng-model="changePassword.newPassword"
						type="{{show ? 'text': 'password'}}" name="newPassword"
						class="form-control form-control-sm"></td>
				</tr>
				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.confirmPassword" /></td>
					<td class="cus-rest-td"><input
						ng-model="changePassword.confirmPassword"
						type="{{show ? 'text': 'password'}}" name="confirmPassword"
						class="form-control form-control-sm"></td>
				</tr>

				<tr>
					<td class="cus-fit-td">
						<button ng-click="saveChangePassword()"
							ng-disabled="!changePassword.confirmPassword || !changePassword.newPassword || !changePassword.oldPassword || (changePassword.confirmPassword != changePassword.newPassword)"
							class="btn btn-success btn-sm">
							<i class="fa fa-save"></i>
						</button>
					</td>

				</tr>
			</table>

		</div>
	</div>


</div>