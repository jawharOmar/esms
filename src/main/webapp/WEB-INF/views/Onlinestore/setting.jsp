<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="from"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container p-2">
    <div class="bg-dark text-white p-3"><h3 class="p-0">Online Store Management</h3>
    </div>


    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist" >
        <li class="nav-item">
            <a class="nav-link "  href = "<c:url value = "/store"/>">Pending Orders</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href = "<c:url value = "list"/>">Listings</a>
        </li>
        <li class="nav-item">
            <a class="nav-link active" href = "<c:url value = ""/>">Store Setting</a>
        </li>
    </ul>

    <!-- Tab panes -->

    <spring:url value="/store/setting" var="urlaction" />


    <div id="menu2" class="container p-0 tab-pane active"><br>


            <form:form modelAttribute="Setting" action="${urlaction}" method="post" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="store_name">Store Name</label>
                        <form:input path="name" type="text" class="form-control " id="store_name"  placeholder="Store Name"  required="required"/>

                    </div>

                    <div class="col-md-4 mb-3">
                        <label for="phone">Phone Number :</label>
                        <form:input path="phone" type="number" class="form-control " id="phone"  placeholder="Phone"  />

                    </div>

                </div>



                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="store-info">Description</label>
                        <from:textarea class="form-control"  path="description"  />

                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="email">Email</label>
                        <form:input path="email" type="email"  id="email" class="form-control "  placeholder="exmple@domain.com"  ></form:input>

                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="Image-ogo">Logo</label>
                        <input type="file" name="file" id="Image-ogo" class="form-control "  accept="image/png" />

                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="address">Address</label>
                        <form:input path="address" type="text"  id="address" class="form-control "  placeholder="Address"  ></form:input>

                    </div>
                </div>
                <button class="btn btn-info" type="submit">Update</button>
            </form:form>
        </div>

</div>