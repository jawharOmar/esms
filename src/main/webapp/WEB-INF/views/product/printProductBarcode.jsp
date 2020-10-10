<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<style type="text/css">

svg{padding: 0.02in}


* {
	margin: 0px;
	padding: 0px;
}

@media print {
	@page {
		size: auto;
		margin: 0;
	}
}

#barcode {
	width: 2.7in;
	height: 1in;
}
</style>

</head>

<div>
	<svg id='barcode'>
</svg>
</div>


<script src="<c:url value="/resources/js/JsBarcode.all.min.js"/>"></script>
<script>
	JsBarcode("#barcode", '${product.code}');
	
	function printing() {
		console.l
		og("print fired");
		window.print();
	}
	printing();
</script>
