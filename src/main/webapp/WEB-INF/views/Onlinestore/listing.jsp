<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="from"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
.my-table tr th{
    font-size: 15px;
    padding: 10px;
}
    .my-table tr td {
        padding: 8px;
    }
    .mybtn{ cursor: pointer;
        font-size: 14px;
        margin: 2px;
        padding: 2px;

    }
.mybtn:hover{ text-decoration: none;

}
</style>

<div class="container p-2">
    <div class="bg-dark text-white p-3"><h3 class="p-0">Online Store Management - Listing</h3>
    </div>

    <spring:url value="/store/list/add" var="urladd" />
    <spring:url value="/store/list/edit/" var="urledit" />


    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist" >
        <li class="nav-item">
            <a class="nav-link "  href = "<c:url value = "/store"/>">Pending Orders</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href = "<c:url value = "list"/>">Listings</a>
        </li>
        <li class="nav-item">
            <a class="nav-link " href = "<c:url value = "setting"/>">Store Setting</a>
        </li>
    </ul>

    <!-- Tab panes -->

    <spring:url value="/store/setting" var="urlaction" />


    <div  class="container p-0 tab-pane active"><br>

<div class="row">
    <div class="col-md-3">
        <a href="${urladd}" class="btn btn-success">Add</a>

    </div>


</div>
<br>
        <div class="row">
            <div class="col-12">

                <table class="w-100 my-table table-bordered table-striped">

                    <thead class="bg-dark text-white">
                    <tr>
                    <th>Title</th>
                    <th>Time</th>
                        <th>Status</th>
                        <th>Brand</th>
                    <th class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${lists}" var="item">
                        <tr>
                            <td>${item.title}</td>
                            <td>${item.time}</td>
                            <td>${item.active}</td>
                            <td >${item.brand}</td>

                            <td class="text-white text-center">
                                <div class="row justify-content-center">
                                    <a href="${urledit}${item.id}" class="col-md-5 col-sm-9 text-center col-lg-3 mybtn btn-warning">Edit</a>
                                    <a class="col-md-5 col-sm-9  text-center col-lg-3 btn-danger mybtn">
                                         Delete </a>
                                    <a class="col-md-5 col-sm-9 text-center btn-info  col-lg-3 mybtn">
                                        Detail </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
    
</div>

