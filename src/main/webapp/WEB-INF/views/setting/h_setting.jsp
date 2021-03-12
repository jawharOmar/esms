<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div ng-app="app" ng-controller="appCTRL" ng-init="init()">
    <style>
        label {
            margin-bottom: 0.1rem;
        }

        /*    card */
        .customCard .card-header {
            background-color: #01c851;
        }

        #refreshBtn {
            background-color: #45b5ff;
        }
    </style>

    <spring:url value="/setting/add" var="auserActionUrl"/>

    <sf:form id="add-setting-form" method="POST" commandName="setting" enctype="multipart/form-data"
             action="${auserActionUrl}">


    <div id="freeze" class="container h-75">
        <div class="row align-items-center h-100">
            <div class="col-6 mx-auto">
                <div class="card customCard">
                    <div class="card-header">
                        <h4 class="mb-0 text-center text-white"><spring:message code="addOrderPreProduct.title"/></h4>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label><spring:message code="addOrderPreProduct.companyName"/></label>
                            <form:input path="name"
                                        class="form-control  form-control-sm"></form:input>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="orderPreProducts.companyPhone"/></label>
                            <form:input path="phone"
                                        class="form-control  form-control-sm"></form:input>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="addOrderPreProduct.companyAddress"/></label>
                            <form:input path="address"
                                        class="form-control  form-control-sm"></form:input>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="orderPreProducts.companyEmail"/></label>
                            <form:input path="email"
                                        class="form-control  form-control-sm"></form:input>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="orderPreProducts.companyDescription"/></label>
                            <form:input path="description"
                                        class="form-control  form-control-sm"></form:input>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="layout.imageLogo"/></label>
                            <input type="file" name="files"/>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="layout.HeaderImage"/></label>
                            <input type="file" name="files"/>
                        </div>
                        <div class="form-group">
                            <label><spring:message code="layout.FooterImage"/></label>
                            <input type="file" name="files"/>
                        </div>

                        <div class="text-center">
                            <button class="btn customBtn" id="refreshBtn">
                                <i class="fa fa-edit"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </sf:form>

    <script>
        window.onload = function () {
            var success = '${success}';
            if (success) {


                toastr.options = {
                    "closeButton": true,
                    "debug": false,
                    "newestOnTop": false,
                    "progressBar": true,
                    "positionClass": "toast-top-center",
                    "preventDuplicates": false,
                    "onclick": null,
                    "showDuration": "300",
                    "hideDuration": "1000",
                    "timeOut": "5000",
                    "extendedTimeOut": "1000",
                    "showEasing": "swing",
                    "hideEasing": "linear",
                    "showMethod": "fadeIn",
                    "hideMethod": "fadeOut"
                };

                toastr["success"]('${success}');
            }
        }
    </script>