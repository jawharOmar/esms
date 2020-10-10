<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="from"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<br>
<div class="container p-1">
    <spring:url value="/store/list/edit/${list.id}" var="urlaction" />

    <form:form modelAttribute="list" action="${urlaction}" method="post" enctype="multipart/form-data">

<div class="bg-dark text-white p-1 align-middle"><h6 class="p-0">Online Store Management - New Item Listing</h6>
    </div>
<br>
    <div class="row">

        <div class="col-md-12">

            <select disabled name="product" class="form-control"  >
                <option value="${list.product.id}" selected>${list.product.name}</option>
            </select>

        </div>

    </div>
<br>
    <div class="row">

        <div class="col-md-12">

            <form:input path="Title"  class="form-control" type="text" placeholder="List Title" ></form:input>

        </div>

    </div>
        <br>
        <div class="row">

            <div class="col-md-12">

                <form:input path="Brand" class="form-control" type="text" placeholder="Brand" ></form:input>

            </div>

        </div>
<br>
    <div class="row">

        <div class="col-md-12">

            <form:textarea path="Description"  class="form-control" placeholder="Description"></form:textarea>

        </div>

    </div>
    <br>


    <div  class="form-control align-middle border">

        <div class="col-12">
            <div id="ph_row" class="row d-flex align-items-center">

                <c:forEach items="${list.photos}" var="item">
        <div class="col-md-auto m-3 border align-middle" style=" padding:5px; cursor:pointer;border-radius: 5px;" >
            <spring:url value="/resources/img/list/${item.path}" var="imgurl" />
            <img alt="img" src="${imgurl}" width="100" href="100">

        </div>
                </c:forEach>


                <div class="col-md-auto m-3 border align-middle" style=" padding:5px; cursor:pointer;border-radius: 5px;" >
                    <label id="file0label" style="cursor:pointer;" class="text-center" for="file0"><p>Add Photo</p><i style="font-size:20px; color:#695DD5;" class="fa fa-plus-square" aria-hidden="true"></i></label>
                    <input id="file0" name="file" onchange="loadPhoto(this)"  type="file" style="display:none;" >
                </div>
            </div>
        </div>

    </div>
<br>

    <div class="row">
        <div class="col-12">
            <form:textarea path="Details" id="Editor" class="form control w-100"></form:textarea>
        </div>
    </div>
    <br>

    <button type="submit" class="btn btn-success">Publish</button>
</form:form>
</div>


<script>
    let i=1;
   function loadPhoto(input) {
    let id=   input.getAttribute("id");

       if (input.files && input.files[0]) {
           let reader = new FileReader();

           reader.onload = function (e) {
               document.getElementById(id+"label").innerHTML='<img alt="img" src="'+e.target.result+'" width="100" height="100"/>';
           };

           reader.readAsDataURL(input.files[0]);
       }

       let div=document.createElement("div");

       div.innerHTML='<div class="col-md-auto m-3 border align-middle" style=" padding:5px; cursor:pointer;border-radius: 5px;" >\n' +
           '            <label id="file'+i+'label" style="cursor:pointer;" class="text-center" for="file'+i+'"><p>Add Photo</p><i style="font-size:20px; color:#695DD5;" class="fa fa-plus-square" aria-hidden="true"></i></label>\n' +
           '            <input id="file'+i+'" name="file" onchange="loadPhoto(this)"  type="file" style="display:none;" >\n' +
           '        </div>';
       document.getElementById("ph_row").appendChild(div);
        i+=1;
   }



</script>

<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function(event) {

        $('#Editor').richText();

        });
</script>