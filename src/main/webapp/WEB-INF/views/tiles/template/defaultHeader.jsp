<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<jsp:include page="../../version.jsp" />

<div>




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

		
	</nav>
	<div style="display: none"></div>


</div>