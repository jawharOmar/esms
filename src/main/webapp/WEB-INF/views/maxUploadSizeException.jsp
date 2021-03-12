<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="text-align: center;">
	<h3>
		<spring:message code="maxUploadSizeException.tilte" />
	</h3>
	<div>
		<img src="<c:url value="/resources/img/fat.png" />" alt="" />
	</div>
</div>
