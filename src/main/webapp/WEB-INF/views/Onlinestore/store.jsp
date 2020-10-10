<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<div class="container p-2">
<div class="bg-dark text-white p-3"><h3 class="p-0">Online Store Management</h3></div>


    <ul class="nav nav-tabs" role="tablist" >
        <li class="nav-item">
            <a class="nav-link active"  href = "<c:url value = "store"/>">Pending Orders</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href = "<c:url value = "store/list"/>">Listings</a>
        </li>
        <li class="nav-item">
            <a class="nav-link " href = "<c:url value = "store/setting"/>">Store Setting</a>
        </li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content p-0">
        <div id="home" class="container tab-pane active p-0"><br>
            <table border="1" class="table table-striped table-bordered ">
                <thead class="bg-dark text-white">
                <tr><th>ID</th>
                    <th>Price</th>
                    <th>Code</th>
                    <th>Customer</th>
                    <th>Time</th>
                    <th>Action</th>

                </tr>

                </thead>
                <tbody>
                <tr><td>1</td>
                <td>120 USD</td>
                    <td>002746</td>
                    <td>Ahmead Omer</td>
                    <td>01/08/2019</td>
                    <td><button class="btn btn-success">Processed</button>
                        <button class="btn btn-danger">Reject</button></td></td></tr>
                <tr><td>1</td>
                    <td>120 USD</td>
                    <td>002746</td>
                    <td>Ahmead Omer</td>
                    <td>01/08/2019</td>

                    <td><button class="btn btn-success">Processed</button> <button class="btn btn-danger">Reject</button></td></tr>
                <tr><td>1</td>
                    <td>120 USD</td>
                    <td>002746</td>
                    <td>Ahmead Omer</td>
                    <td>01/08/2019</td>

                    <td><button class="btn btn-success">Processed</button> <button class="btn btn-danger">Reject</button></td></tr>
                <tr><td>1</td>
                    <td>120 USD</td>
                    <td>002746</td>
                    <td>Ahmead Omer</td>
                    <td>01/08/2019</td>

                    <td><button class="btn btn-success">Processed</button> <button class="btn btn-danger">Reject</button></td></tr>
                </tbody>
            </table></div>
        <div id="menu1" class="container tab-pane fade"><br>
        </div>
        <div id="menu2" class="container tab-pane fade"><br>


            <form>
                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="store_name">Store Name</label>
                        <input type="text" class="form-control " id="store_name"  placeholder="Store Name" required>

                    </div>

                    <div class="col-md-4 mb-3">
                        <label for="phone">Phone Number :</label>
                        <input type="number" class="form-control " id="phone"  placeholder="Phone" required>

                    </div>

                </div>



                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="store-info">Description</label>
                        <textarea  id="store-info" class="form-control "  placeholder="Description"  required></textarea>

                    </div>
                    <div class="col-md-4 mb-3">
                        <label for="email">Email</label>
                        <input type="email"  id="email" class="form-control "  placeholder="exmple@domain.com"  required></input>

                    </div>
                </div>

                <div class="form-row">
                    <div class="col-md-4 mb-3">
                        <label for="Image-ogo">Logo</label>
                        <input type="file"  id="Image-ogo" class="form-control "   required/ >

                    </div>
                </div>
                <button class="btn btn-info" type="submit">Update</button>
            </form>
        </div>
    </div>

</div>

