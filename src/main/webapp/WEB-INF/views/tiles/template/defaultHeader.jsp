<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<jsp:include page="../../version.jsp" />

<nav id="cus-navbar" class="navbar">

	<table>
		<tr>
			<td>
				<div id="toggle-menu" onclick="toggleMenu()">
					<span></span> <span></span> <span></span>
				</div>
			</td>
			<td><a href="<c:url value="/adminRoot" />"> <img
					src="<c:url value="/resources/img/logo.png" />"
					height="30" alt="" /> <b class="color-primary"><span id="Systemnam"></span></b>
			</a></td>
		</tr>
	</table>

	<form action="<c:url value="/logout" />" method="POST">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}">
		<div>
            <button class="btn btn-success btn-sm" type="button"
                  onclick="RunFile()"  >
                <i class="fa fa-calculator" aria-hidden="true"></i>
            </button>
			<button class="btn btn-warning btn-sm" type="button"
				onclick="$('#password-dialog').dialog();">
				<i class="fa fa-key"></i>
			</button>
            <button class="btn btn-primary btn-sm" type="button"
                    onclick="currencyview()">
                <i class="fa fa-usd"></i>
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

		<form method="post" action="<c:url value="/changePassword" />">
			<table class="w-100">
				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.oldPassword" /></td>
					<td class="cus-rest-td"><input type="password"
						name="oldPassword" class="form-control form-control-sm"></td>
				</tr>

				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.newPassword" /></td>
					<td class="cus-rest-td"><input type="password"
						name="newPassword" class="form-control form-control-sm"></td>
				</tr>
				<tr>
					<td class="cus-fit-td"><spring:message
							code="changePassword.confirmPassword" /></td>
					<td class="cus-rest-td"><input type="password"
						name="confirmPassword" class="form-control form-control-sm"></td>
				</tr>

				<tr>
					<td class="cus-fit-td">
						<button class="btn btn-success btn-sm">
							<i class="fa fa-save"></i>
						</button>
					</td>

				</tr>
			</table>

		</form>
	</div>
</div>


<div style="display: none">
    <div id="Currency-dialog" class="p-1">

        <form method="post" action="<c:url value="/setting/currency" />">
            <table class="w-100">
                <tr>
                    <td class="cus-fit-td">Base Currency</td>
                    <td class="cus-rest-td"><input required id="curbase" type="text"
                                                   name="base" class="form-control form-control-sm"></td>
                </tr>

                <tr>
                    <td class="cus-fit-td">Exchange Currency</td>
                    <td class="cus-rest-td"><input required type="text" id="tocur"
                                                   name="to" class="form-control form-control-sm"></td>
                </tr>
                <tr>
                    <td class="cus-fit-td">Exchange Rate</td>
                    <td class="cus-rest-td"><input required step="any" type="number" id="currate"
                                                   name="rate" class="form-control form-control-sm"></td>
                </tr>
				<tr>
					<td class="cus-fit-td">Exchange Rate (Reverse)</td>
					<td class="cus-rest-td"><input required step="any" type="number" id="curratereverse"
												   name="reverseRate" class="form-control form-control-sm"></td>
				</tr>

                <tr>
                    <td class="cus-fit-td">
                        <button class="btn btn-success btn-sm">
                            <i class="fa fa-save"></i>
                        </button>
                    </td>

                </tr>
            </table>

        </form>
    </div>
</div>