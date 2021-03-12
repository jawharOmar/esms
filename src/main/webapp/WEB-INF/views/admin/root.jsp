<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../version.jsp" />


<jsp:useBean id="now" class="java.util.Date" />
<c:set var="tomorrow"
	value="<%=new Date(new Date().getTime() + 60 * 60 * 24 * 1000)%>" />

<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />
<fmt:formatDate var="tomorrow" value="${tomorrow}" pattern="yyyy-MM-dd" />

<section id="admin-body">

	<section id="main-content">
		<tiles:insertAttribute name="adminBody" />
	</section>

</section>