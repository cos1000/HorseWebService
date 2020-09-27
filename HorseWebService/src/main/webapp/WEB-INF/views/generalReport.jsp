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
<h1>${title}</h1>
<c:if test="${!empty records}">
	<table class="tg">
            <tr>
                <td>賠率</td>
                <td>記錄總數</td>
                <td>勝出總數</td>
                <td>輸波總數</td>
                <td>勝出比率</td>
                <td>輸波比率</td>
                <td>累計總數</td>
                <td>累計勝出總數</td>
                <td>累計輸波總數</td>
                <td>累計勝出比率</td>
                <td>累計輸波比率</td>
            </tr>
	<c:forEach items="${records}" var="record">
		<tr>
                    <td>${record.description}</td>
                    <td>${record.numberOfRecord}</td>
                    <td>${record.winNumberOfRecord}</td>
                    <td>${record.lostNumberOfRecord}</td>
                    <td>${record.winPercentage}%</td>
                    <td>${record.lostPercentage}%</td>
                    <td>${record.accNumberOfRecord}</td>
                    <td>${record.accWinNumberOfRecord}</td>
                    <td>${record.accLostNumberOfRecord}</td>
                    <td>${record.accWinPercentage}%</td>
                    <td>${record.accLostPercentage}%</td>
		</tr>
	</c:forEach>
            <tr>
                <td>總數</td>
                <td>${numberOfRecord}</td>
                <td>${winNumberOfRecord}</td>
                <td>${lostNumberOfRecord}</td>
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
