<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="from"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<div ng-controller="settingCTRL" ng-init="init()"
	class="container-fluid">
	<div class="row">
		<div class="col-8">
			<c:url value="/settings/add" var="formUrl" />
			<sf:form method="POST" commandName="setting" action="${formUrl}"
				enctype="multipart/form-data">

				<sf:input path="id" type="hidden" />
				<table class="table table-borderless">
					<tbody>

						<tr>
							<td><spring:message code="settings.name" /></td>
							<td><sf:textarea cssClass="form-control form-control-sm"
									path="name" /></td>
							<td><sf:errors path="name" /></td>
						</tr>

						<tr>
							<td><spring:message code="settings.phone" /></td>
							<td><sf:textarea placeholder="0750xxxxxx <br> 0770xxxxxx"
									cssClass="form-control form-control-sm" path="phone" /></td>
							<td><sf:errors path="phone" /></td>
						</tr>

						<tr>
							<td><spring:message code="settings.address" /></td>
							<td><sf:textarea cssClass="form-control form-control-sm"
									path="address" /></td>
							<td><sf:errors path="address" /></td>
						</tr>
						<tr>
							<td><spring:message code="settings.description" /></td>
							<td><sf:textarea cssClass="form-control form-control-sm"
									path="description" /></td>
							<td><sf:errors path="description" /></td>
						</tr>

						<tr>
							<td><spring:message code="settings.printCustomerOrderThermal" /></td>
							<td><sf:checkbox 
									path="printCustomerThermal" value="1" /></td>
							<td><sf:errors path="printCustomerThermal" /></td>
						</tr>

						<tr>
							<td><spring:message code="settings.logo" /></td>
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
	</div>
</div>