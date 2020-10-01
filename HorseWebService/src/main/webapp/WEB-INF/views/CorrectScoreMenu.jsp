<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" %>
<html>
<head>
        <meta name="viewport" content="width=device-width, initial-scale=1" >
	<title>波膽報表</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
    <h1>
            波膽報表
    </h1>
    <table class="tg">
        <tr><th width="400">賽果</th></tr>
        <tr><td><a href="<c:url value='/report/correctScore/00:00' />" >0:0</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/01:01' />" >1:1</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/02:02' />" >2:2</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/03:03' />" >3:3</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/01:00' />" >1:0</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/02:00' />" >2:0</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/03:00' />" >3:0</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/04:00' />" >4:0</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/05:00' />" >5:0</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/02:01' />" >2:1</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/03:01' />" >3:1</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/04:01' />" >4:1</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/05:01' />" >5:1</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/03:02' />" >3:2</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/04:02' />" >4:2</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/05:02' />" >5:2</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/00:01' />" >0:1</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/00:02' />" >0:2</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/00:03' />" >0:3</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/00:04' />" >0:4</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/00:05' />" >0:5</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/01:02' />" >1:2</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/01:03' />" >1:3</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/01:04' />" >1:4</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/01:05' />" >1:5</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/02:03' />" >2:3</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/02:04' />" >2:4</a></td></tr>
        <tr><td><a href="<c:url value='/report/correctScore/02:05' />" >2:5</a></td></tr>
    </table>
</body>
</html>