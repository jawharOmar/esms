<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="version" scope="request"><%=LocalDateTime.now()%></c:set>

<div style="width: 100%;" id="header">
	<table style="direction: ltr;">

		<tr>
			<td width="33"><img style="max-width: 200px; padding: 4px"
				src="<c:url value="/resources/img/starter.jpeg?" />${versionLogo}"
				height="200" alt="" /></td>
			<td width="34%">
				<h2 class="p-1" style="color: #2978B1;text-align: center">${setting.name}</h2> <img
				style="width: 200px;"
				src="<c:url value="/resources/img/logo_red.png?" />${version}" />
			</td>
			<td width="33%"><img style="max-width: 200px; padding: 4px"
				src="<c:url value="/resources/img/dynamo.jpeg?" />${versionLogo}"
				height="200" alt="" /></td>

		</tr>
		<tr>
			<td colspan="3"><p style="text-align: center;">${setting.description}</p></td>
		</tr>
		<tr>
			<td colspan="3">${setting.address}<br> ${setting.phone}
			</td>
		</tr>
	</table>

</div>