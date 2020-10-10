<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<script type="text/javascript">
	var jsonRoles = '<spring:escapeBody  javaScriptEscape="true">${jsonRoles}</spring:escapeBody>';
	var jsonUsers = '<spring:escapeBody  javaScriptEscape="true">${jsonUsers}</spring:escapeBody>';
	var csrf = '${_csrf.token}';
</script>

<div ng-app="app" ng-controller="appCTRL" ng-init="init()">

	<pre>
	</pre>

	<div id="loading-div" if-loading>
		<img id=""
			src="<c:url value="/resources/img/loading.gif?${version}" />" />
	</div>

	<h2>
		<spring:message code="userManager.title" />
	</h2>

	<ul class="nav nav-tabs" id="usersTab" role="tablist">
		<li class="nav-item"><a class="nav-link active" data-toggle="tab"
			href="#users"><spring:message code="userManager.users" /></a></li>
		<li class="nav-item"><a class="nav-link" data-toggle="tab"
			href="#addUser">{{user.id?user.userName:'<spring:message
					code="userManager.addUser" />'}}
		</a></li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane active" id="users">
			<div class="py-2">
				<table class="table table-bordered">
					<tr>
						<th>#</th>
						<th><spring:message code="userManager.userName" /></th>
						<th><spring:message code="userManager.enabled" /></th>
						<th><spring:message code="userManager.function" /></th>
					</tr>
					<tr ng-repeat="item in users">
						<td>{{$index+1}}</td>
						<td>{{item.userName}}</td>
						<td><span
							ng-class="{'text-danger':!item.enabled,'text-success':item.enabled}">
								<i class="fa fa-circle"></i>
						</span></td>
						<td>
							<button class="btn btn-sm btn-warning"
								ng-click="getEditingUser(item)">
								<i class="fa fa-edit"></i>
							</button>
						</td>
					</tr>

				</table>
			</div>
		</div>


		<div class="tab-pane" id="addUser">
			<div class="py-2" ng-form="defineUserForm">
				<div class="row">
					<div class="col-lg-4 ">
						<fieldset>
							<legend><spring:message code="userManager.info"/></legend>
							<div class="form-group">
								<label><spring:message code="userManager.userName"/></label>
								<input required name="userName" ng-model="user.userName"
									   class="form-control form-control-sm">
							</div>
							<div class="form-group">
								<label><spring:message code="userManager.password"/></label>
								<input name="password" ng-required="!user.id"
									   ng-model="user.password" class="form-control form-control-sm">
							</div>
							<div class="form-group">
								<label class="checkboxLabel"
									   ng-class="user.enabled?'selectedLabel':'unSelectedLabel'" for="userEnable">
									<spring:message code="userManager.enabled"/></label>
								<input type="checkbox" id="userEnable" ng-model="user.enabled" style="display: none;"
									   class="form-control form-control-sm">
							</div>

						</fieldset>
					</div>


					<div class="col-lg-8" id="roles">
						<fieldset>
							<legend><spring:message code="userManager.role"/></legend>

							<div class="form-group" ng-repeat="item in userRoles|orderBy:'role.id'">
								<label class="checkboxLabel" for="{{item.role.id}}"
									   ng-class="item.hasRole?'selectedLabel':'unSelectedLabel'">{{item.role.name}}</label>
								<input type="checkbox" id="{{item.role.id}}" ng-model="item.hasRole"
									   style="display: none;">

							</div>
						</fieldset>
					</div>
				</div>
				<div class="text-center">
					<button ng-if="!user.id" class="btn btn-success col-lg-1 col-md-2 col-sm-3"
							ng-disabled="defineUserForm.$invalid" ng-click="addUser()">
						<i class="fa fa-save"></i>
					</button>

					<div ng-if="user.id">
						<button class="btn btn-warning col-lg-1 col-md-2 col-sm-3" ng-click="updateUser()"
								ng-disabled="defineUserForm.$invalid">
							<i class="fa fa-edit"></i>
						</button>
						<button class="btn btn-danger col-lg-1 col-md-2 col-sm-3" ng-click="cancleEdit()">
							<i class="fa fa-close"></i>
						</button>
					</div>
				</div>

			</div>
		</div>
	</div>

</div>