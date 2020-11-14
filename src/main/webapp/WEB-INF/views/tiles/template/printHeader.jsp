<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="version" scope="request"><%=LocalDateTime.now()%></c:set>

<div style="width: 100%" id="header">
	<table>

		<tr>
			<td width="33">${setting.phone}</td>
			<td width="34%">
				<h2 class="p-1">${setting.name}</h2> <img style="width: 200px;"
				src="<c:url value="/resources/img/logo.png?" />${version}" />
				<p>${setting.description}</p>
			</td>
			<td width="33%">${setting.address}</td>
		</tr>
	</table>

</div>