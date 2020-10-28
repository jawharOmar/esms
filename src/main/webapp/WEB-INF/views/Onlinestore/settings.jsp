<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="from"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<div>
	<c:url value="/settings/add" var="formUrl" />
	<sf:form method="POST" commandName="setting" action="${formUrl}"
		enctype="multipart/form-data">
		<table class="w-100">
			<tbody>

				<tr>
					<td><spring:message code="settings.name" /></td>
					<td><sf:input cssClass="form-control form-control-sm"
							path="name" /></td>
					<td><sf:errors path="name" /></td>
				</tr>

				<tr>
					<td><spring:message code="settings.phone" /></td>
					<td><sf:input cssClass="form-control form-control-sm"
							path="phone" /></td>
					<td><sf:errors path="phone" /></td>
				</tr>

				<tr>
					<td><spring:message code="settings.address" /></td>
					<td><sf:input cssClass="form-control form-control-sm"
							path="address" /></td>
					<td><sf:errors path="address" /></td>
				</tr>

				<tr>
					<td><spring:message code="settings.address" /></td>
					<td><input id="files" type="file" accept=".png"
						class=" form-control
						form-control-sm" name="file" /></td>
				</tr>

				<tr>
					<td>
						<button class="btn btn-sm btn-success">
							<i class="fa fa-plus"></i>
						</button>
					</td>
				</tr>

			</tbody>

		</table>


	</sf:form>

</div>