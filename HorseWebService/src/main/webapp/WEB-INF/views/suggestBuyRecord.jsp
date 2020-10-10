<%-- 
    Document   : goalHigh
    Created on : 2020年9月26日, 上午09:05:46
    Author     : matt_
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" >
        <title>${title}</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
    </head>
    <body>
<h1>${title}</h1><br>
<h3><a href="<c:url value='/report/suggestBuyRecord/${category}/matchIDinofficial' />" >以比賽次序排序</a></h3><br>
<h3><a href="<c:url value='/report/suggestBuyRecord/${category}/subcategory' />" >以建議種類排序</a></h3><br>
<h3><a href="<c:url value='/report/suggestBuyRecord/${category}/noResult' />" >只顯示未有結果的建議</a></h3><br>
<c:if test="${!empty records}">
	<table class="tg">
            <tr>
                <td>比賽編號</td>
                <td>建議種類</td>
                <td>主隊</td>
                <td>客隊</td>
                <td>規則</td>
                <td>賠率($)</td>
                <td>賽果</td>
                <td>回報($)</td>
                <td>累計回報($)</td>
            </tr>
	<c:forEach items="${records}" var="record">
		<tr>
                    <td>${record.matchIDinofficial}</td>
                    <td>${record.subCategory}</td>
                    <td>${record.homeTeam}</td>
                    <td>${record.awayTeam}</td>
                    <td>${record.rule}</td>
                    <td>${record.odds}</td>
                    <td>${record.matchResult}</td>
                    <td>${record.winPrice}</td>
                    <td>${record.accWinPrice}</td>
		</tr>
	</c:forEach>
            <tr>
                <td>總數</td>
                <td>${numberOfRecord}</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
	</table>
</c:if>
    </body>
</html>
