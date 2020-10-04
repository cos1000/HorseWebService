<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" %>
<html>
<head>
        <meta name="viewport" content="width=device-width, initial-scale=1" >
	<title>Jockey Club Report Menu</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
    <h1>
            Jockey Club Report Menu
    </h1>
    <table class="tg">
        <tr><th width="400">名稱</th></tr>
        <tr><td><a href="<c:url value='/report/suggestBuyRecord/Matt_suggest_01/matchIDinofficial' />" >自訂建議 - Matt 01</a></td></tr>
        <!--tr><td><a href="<c:url value='/football' />" >Football</a></td></tr-->
        <!--tr><td><a href="<c:url value='/football/export' />" >Export Football Full List</a></td></tr-->
        <tr><td><a href="<c:url value='/report/goalHigh' />" >入球大的報表</a></td></tr>
        <tr><td><a href="<c:url value='/report/goalLow' />" >入球細的報表</a></td></tr>
        <tr><td><a href="<c:url value='/report/firstGoalHigh' />" >半場入球大的報表</a></td></tr>
        <tr><td><a href="<c:url value='/report/firstGoalLow' />" >半場入球細的報表</a></td></tr>
        <tr><td><a href="<c:url value='/report/homeAwayDrawHome' />" >主客和 - 主</a></td></tr>
        <tr><td><a href="<c:url value='/report/homeAwayDrawAway' />" >主客和 - 客</a></td></tr>
        <tr><td><a href="<c:url value='/report/homeAwayDrawDraw' />" >主客和 - 和</a></td></tr>
        <tr><td><a href="<c:url value='/report/firstHomeAwayDrawHome' />" >半場主客和 - 主</a></td></tr>
        <tr><td><a href="<c:url value='/report/firstHomeAwayDrawAway' />" >半場主客和 - 客</a></td></tr>
        <tr><td><a href="<c:url value='/report/firstHomeAwayDrawDraw' />" >半場主客和 - 和</a></td></tr>
        <tr><td><a href="<c:url value='/report/handicapHomeAwayDrawHome' />" >讓球主客和 - 主</a></td></tr>
        <tr><td><a href="<c:url value='/report/handicapHomeAwayDrawAway' />" >讓球主客和 - 客</a></td></tr>
        <tr><td><a href="<c:url value='/report/handicapHomeAwayDrawDraw' />" >讓球主客和 - 和</a></td></tr>
        <tr><td><a href="<c:url value='/report/handicapHome' />" >讓球 - 主</a></td></tr>
        <tr><td><a href="<c:url value='/report/handicapAway' />" >讓球 - 客</a></td></tr>
        <tr><td><a href="<c:url value='/report/totalGoal' />" >總入球</a></td></tr>
        <tr><td><a href="<c:url value='/correctScoreMenu' />" >波膽</a></td></tr>
    </table>
</body>
</html>