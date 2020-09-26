<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" %>
<html>
<head>
        <meta name="viewport" content="width=device-width, initial-scale=1" >
	<title>Football</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
    <h1>
            Football
    </h1>
<br>    
<c:if test="${!empty records}">
	<table class="tg">

	<c:forEach items="${records}" var="record">
        <!-- 1 -->
	<tr>
                <th width="120" >1</th>
		<th width="120">比賽編號</th>
                <th width="120">比賽類別</th>
                <th width="120">比賽時間</th>
		<th width="120">主場</th>
		<th width="120">客場</th>
                <th width="120" colspan="3">主客和</th>
                <th width="120" colspan="3">入球大細</th>
                <th width="120" colspan="3">波膽</th>
                <th width="120" colspan="3">半場波膽</th>
                <th width="120" >總入球</th>
                <th width="120" >半全場</th>
	</tr>
        <!-- 2 -->
	<tr>
                <th width="120" >2</th>
		<th width="120" colspan="5"></th>
                <th width="120" colspan="3">半場主客和</th>
                <th width="120" colspan="3">半場入球大細</th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" ></th>
                <th width="120" ></th>
	</tr>
        <!-- 3 -->
	<tr>
                <th width="120" >3</th>
		<th width="120" colspan="5">賽果</th>
                <th width="120" colspan="3">讓球主客和</th>
                <th width="120" colspan="3">角球大細</th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" ></th>
                <th width="120" ></th>
	</tr>
        <!-- 4 -->
	<tr>
                <th width="120" >4</th>
		<th width="120">主客和</th>
                <th width="120">半場主客和</th>
                <th width="120">波膽</th>
		<th width="120">半場波膽</th>
		<th width="120">半全場</th>
                <th width="120" colspan="3">讓球</th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" ></th>
                <th width="120" ></th>
	</tr>
        <!-- 5 -->
	<tr>
                <th width="120" >5</th>
		<th width="120" colspan="5">入球單雙</th>
                <th width="120" colspan="3">第一隊入球</th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" ></th>
                <th width="120" ></th>
	</tr>
        <!-- 6 -->
	<tr>
                <th width="120" >6</th>
		<th width="120" colspan="5"></th>
                <th width="120" colspan="3">單雙</th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" colspan="3"></th>
                <th width="120" ></th>
                <th width="120" ></th>
	</tr>
        <!-- 7 -->
	<tr>
                <th width="120" >7</th>
		<th width="120" colspan="5"></th>
                <th width="120">主</th>
                <th width="120">客</th>
                <th width="120">和</th>
                <th width="120">中間數</th>
                <th width="120">大</th>
                <th width="120">細</th>
                <th width="120">主</th>
                <th width="120">客</th>
                <th width="120">和</th>
                <th width="120">主</th>
                <th width="120">客</th>
                <th width="120">和</th>
                <th width="120" ></th>
                <th width="120" ></th>
	</tr>            
                <!-- 1 -->
		<tr>
                        <td>1</td>
			<td>${record.matchDay} ${record.matchNum}</td>
			<td>${record.league.leagueNameCH}</td>
			<td>${record.matchTime}</td>
			<td>${record.homeTeam.teamNameCH}</td>
			<td>${record.awayTeam.teamNameCH}</td>
                        <c:choose>
                        <c:when test="${!empty record.hadodds}">
			<td>${fn:replace(record.hadodds.h, '100@', '')}</td>
			<td>${fn:replace(record.hadodds.a, '100@', '')}</td>
			<td>${fn:replace(record.hadodds.d, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hilodds and !empty record.hilodds.LINELIST[0]}">
			<td>${fn:replace(record.hilodds.LINELIST[0].LINE, '100@', '')}</td>
			<td>${fn:replace(record.hilodds.LINELIST[0].h, '100@', '')}</td>
			<td>${fn:replace(record.hilodds.LINELIST[0].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>01:00 ${fn:replace(record.crsodds.s0100, '100@', '')}</td>
			<td>00:01 ${fn:replace(record.crsodds.s0001, '100@', '')}</td>
			<td>00:00 ${fn:replace(record.crsodds.s0000, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>01:00 ${fn:replace(record.fcsodds.s0100, '100@', '')}</td>
			<td>00:01 ${fn:replace(record.fcsodds.s0001, '100@', '')}</td>
			<td>00:00 ${fn:replace(record.fcsodds.s0000, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>1球 ${fn:replace(record.ttgodds.p1, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>主主 ${fn:replace(record.hftodds.HH, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 2 -->
		<tr>
                        <td>2</td>
			<td colspan="5"></td>
                        <c:choose>
                        <c:when test="${!empty record.fhaodds}">
			<td>${fn:replace(record.fhaodds.h, '100@', '')}</td>
			<td>${fn:replace(record.fhaodds.a, '100@', '')}</td>
			<td>${fn:replace(record.fhaodds.d, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hilodds and !empty record.hilodds.LINELIST[1]}">
			<td>${fn:replace(record.hilodds.LINELIST[1].LINE, '100@', '')}</td>
			<td>${fn:replace(record.hilodds.LINELIST[1].h, '100@', '')}</td>
			<td>${fn:replace(record.hilodds.LINELIST[1].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>02:00 ${fn:replace(record.crsodds.s0200, '100@', '')}</td>
			<td>00:02 ${fn:replace(record.crsodds.s0002, '100@', '')}</td>
			<td>01:01 ${fn:replace(record.crsodds.s0101, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>02:00 ${fn:replace(record.fcsodds.s0200, '100@', '')}</td>
			<td>00:02 ${fn:replace(record.fcsodds.s0002, '100@', '')}</td>
			<td>01:01 ${fn:replace(record.fcsodds.s0101, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>2球 ${fn:replace(record.ttgodds.p2, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>主客 ${fn:replace(record.hftodds.HA, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 3 -->
		<tr>
                        <td>3</td>
			<td colspan="5"></td>
                        <c:choose>
                        <c:when test="${!empty record.hhaodds}">
			<td>${fn:replace(record.hhaodds.h, '100@', '')} (${record.hhaodds.HG})</td>
			<td>${fn:replace(record.hhaodds.a, '100@', '')} (${record.hhaodds.AG})</td>
			<td>${fn:replace(record.hhaodds.d, '100@', '')}</td> 
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hilodds and !empty record.hilodds.LINELIST[2]}">
			<td>${fn:replace(record.hilodds.LINELIST[2].LINE, '100@', '')}</td>
			<td>${fn:replace(record.hilodds.LINELIST[2].h, '100@', '')}</td>
			<td>${fn:replace(record.hilodds.LINELIST[2].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>02:01 ${fn:replace(record.crsodds.s0201, '100@', '')}</td>
			<td>01:02 ${fn:replace(record.crsodds.s0102, '100@', '')}</td>
			<td>02:02 ${fn:replace(record.crsodds.s0202, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>02:01 ${fn:replace(record.fcsodds.s0201, '100@', '')}</td>
			<td>01:02 ${fn:replace(record.fcsodds.s0102, '100@', '')}</td>
			<td>02:02 ${fn:replace(record.fcsodds.s0202, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>3球 ${fn:replace(record.ttgodds.p3, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>主和 ${fn:replace(record.hftodds.HD, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 4 -->
		<tr>
                        <td>4</td>
                        <c:choose>
                        <c:when test="${!empty record.results}">
			<td>${fn:replace(record.results.HAD, '100@', '')}</td>
			<td>${fn:replace(record.results.FHA, '100@', '')}</td>
			<td>${fn:replace(record.results.CRS, '100@', '')}</td>
			<td>${fn:replace(record.results.FCS, '100@', '')}</td>
			<td>${fn:replace(record.results.TTG, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="5"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hdcodds}">
			<td>${fn:replace(record.hdcodds.h, '100@', '')} (${record.hdcodds.HG})</td>
			<td>${fn:replace(record.hdcodds.a, '100@', '')} (${record.hdcodds.AG})</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fhlodds and !empty record.fhlodds.LINELIST[0]}">
			<td>${fn:replace(record.fhlodds.LINELIST[0].LINE, '100@', '')}</td>
			<td>${fn:replace(record.fhlodds.LINELIST[0].h, '100@', '')}</td>
			<td>${fn:replace(record.fhlodds.LINELIST[0].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>03:00 ${fn:replace(record.crsodds.s0300, '100@', '')}</td>
			<td>00:03 ${fn:replace(record.crsodds.s0003, '100@', '')}</td>
			<td>03:03 ${fn:replace(record.crsodds.s0303, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>03:00 ${fn:replace(record.fcsodds.s0300, '100@', '')}</td>
			<td>00:03 ${fn:replace(record.fcsodds.s0003, '100@', '')}</td>
			<td>03:03 ${fn:replace(record.fcsodds.s0303, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>4球 ${fn:replace(record.ttgodds.p4, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>客主 ${fn:replace(record.hftodds.AH, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 5 -->
		<tr>
                        <td>5</td>
                        <c:choose>
                        <c:when test="${!empty record.results}">
			<td colspan="5">${fn:replace(record.results.OOE, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="5"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ftsodds}">
			<td>${fn:replace(record.ftsodds.h, '100@', '')}</td>
			<td>${fn:replace(record.ftsodds.a, '100@', '')}</td>
			<td>${fn:replace(record.ftsodds.n, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fhlodds and !empty record.fhlodds.LINELIST[1]}">
			<td>${fn:replace(record.fhlodds.LINELIST[1].LINE, '100@', '')}</td>
			<td>${fn:replace(record.fhlodds.LINELIST[1].h, '100@', '')}</td>
			<td>${fn:replace(record.fhlodds.LINELIST[1].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>03:01 ${fn:replace(record.crsodds.s0301, '100@', '')}</td>
			<td>01:03 ${fn:replace(record.crsodds.s0103, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>03:01 ${fn:replace(record.fcsodds.s0301, '100@', '')}</td>
			<td>01:03 ${fn:replace(record.fcsodds.s0103, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>5球 ${fn:replace(record.ttgodds.p5, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>客客 ${fn:replace(record.hftodds.AA, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 6 -->
		<tr>
                        <td>6</td>
			<td colspan="5"></td>
                        <c:choose>
                        <c:when test="${!empty record.ooeodds}">
			<td>${fn:replace(record.ooeodds.o, '100@', '')}</td>
			<td>${fn:replace(record.ooeodds.e, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fhlodds and !empty record.fhlodds.LINELIST[2]}">
			<td>${fn:replace(record.fhlodds.LINELIST[2].LINE, '100@', '')}</td>
			<td>${fn:replace(record.fhlodds.LINELIST[2].h, '100@', '')}</td>
			<td>${fn:replace(record.fhlodds.LINELIST[2].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>03:02 ${fn:replace(record.crsodds.s0302, '100@', '')}</td>
			<td>02:03 ${fn:replace(record.crsodds.s0203, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>03:02 ${fn:replace(record.fcsodds.s0302, '100@', '')}</td>
			<td>02:03 ${fn:replace(record.fcsodds.s0203, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>6球 ${fn:replace(record.ttgodds.p6, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>客和 ${fn:replace(record.hftodds.AD, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 7 -->
		<tr>
                        <td>7</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.chlodds and !empty record.chlodds.LINELIST and !empty record.chlodds.LINELIST[0]}">
			<td>${fn:replace(record.chlodds.LINELIST[0].LINE, '100@', '')}</td>
			<td>${fn:replace(record.chlodds.LINELIST[0].h, '100@', '')}</td>
			<td>${fn:replace(record.chlodds.LINELIST[0].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>04:00 ${fn:replace(record.crsodds.s0400, '100@', '')}</td>
			<td>00:04 ${fn:replace(record.crsodds.s0004, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>04:00 ${fn:replace(record.fcsodds.s0400, '100@', '')}</td>
			<td>00:04 ${fn:replace(record.fcsodds.s0004, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.ttgodds}">
			<td>7球 ${fn:replace(record.ttgodds.m7, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>和主 ${fn:replace(record.hftodds.DH, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 8 -->
		<tr>
                        <td>8</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.chlodds and !empty record.chlodds.LINELIST and !empty record.chlodds.LINELIST[1]}">
			<td>${fn:replace(record.chlodds.LINELIST[1].LINE, '100@', '')}</td>
			<td>${fn:replace(record.chlodds.LINELIST[1].h, '100@', '')}</td>
			<td>${fn:replace(record.chlodds.LINELIST[1].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>04:01 ${fn:replace(record.crsodds.s0401, '100@', '')}</td>
			<td>01:04 ${fn:replace(record.crsodds.s0104, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>04:01 ${fn:replace(record.fcsodds.s0401, '100@', '')}</td>
			<td>01:04 ${fn:replace(record.fcsodds.s0104, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <td></td>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>和客 ${fn:replace(record.hftodds.DA, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 9 -->
		<tr>
                        <td>9</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.chlodds and !empty record.chlodds.LINELIST and !empty record.chlodds.LINELIST[2]}">
			<td>${fn:replace(record.chlodds.LINELIST[2].LINE, '100@', '')}</td>
			<td>${fn:replace(record.chlodds.LINELIST[2].h, '100@', '')}</td>
			<td>${fn:replace(record.chlodds.LINELIST[2].l, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>04:02 ${fn:replace(record.crsodds.s0402, '100@', '')}</td>
			<td>02:04 ${fn:replace(record.crsodds.s0204, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>04:02 ${fn:replace(record.fcsodds.s0402, '100@', '')}</td>
			<td>02:04 ${fn:replace(record.fcsodds.s0204, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <td></td>
                        <c:choose>
                        <c:when test="${!empty record.hftodds}">
			<td>和和 ${fn:replace(record.hftodds.DD, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td></td>
                        </c:otherwise>
                        </c:choose>
		</tr>
                <!-- 10 -->
		<tr>
                        <td>10</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>05:00 ${fn:replace(record.crsodds.s0500, '100@', '')}</td>
			<td>00:05 ${fn:replace(record.crsodds.s0005, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>05:00 ${fn:replace(record.fcsodds.s0500, '100@', '')}</td>
			<td>00:05 ${fn:replace(record.fcsodds.s0005, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <td></td>
                        <td></td>
		</tr>
                <!-- 11 -->
		<tr>
                        <td>11</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>05:01 ${fn:replace(record.crsodds.s0501, '100@', '')}</td>
			<td>01:05 ${fn:replace(record.crsodds.s0105, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>05:01 ${fn:replace(record.fcsodds.s0501, '100@', '')}</td>
			<td>01:05 ${fn:replace(record.fcsodds.s0105, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <td></td>
                        <td></td>
		</tr>
                <!-- 12 -->
		<tr>
                        <td>12</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>05:02 ${fn:replace(record.crsodds.s0502, '100@', '')}</td>
			<td>02:05 ${fn:replace(record.crsodds.s0205, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>05:02 ${fn:replace(record.fcsodds.s0502, '100@', '')}</td>
			<td>02:05 ${fn:replace(record.fcsodds.s0205, '100@', '')}</td>
			<td></td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <td></td>
                        <td></td>
		</tr>
                <!-- 13 -->
		<tr>
                        <td>13</td>
			<td colspan="5"></td>
                        <td colspan="3"></td>
                        <td colspan="3"></td>
                        <c:choose>
                        <c:when test="${!empty record.crsodds}">
			<td>主其他 ${fn:replace(record.crsodds.SM1MH, '100@', '')}</td>
			<td>客其他 ${fn:replace(record.crsodds.SM1MA, '100@', '')}</td>
			<td>和其他 ${fn:replace(record.crsodds.SM1MD, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                        <c:when test="${!empty record.fcsodds}">
			<td>主其他 ${fn:replace(record.fcsodds.SM1MH, '100@', '')}</td>
			<td>客其他 ${fn:replace(record.fcsodds.SM1MA, '100@', '')}</td>
			<td>和其他 ${fn:replace(record.fcsodds.SM1MD, '100@', '')}</td>
                        </c:when>    
                        <c:otherwise>
                        <td colspan="3"></td>
                        </c:otherwise>
                        </c:choose>
                        <td></td>
                        <td></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<c:if test="${!empty message}">
    <h3>${message}</h3>
</c:if>
</body>
</html>

<!--
matchday
matchnum
matchtime
hometeam.teamamech
awayteam.teamamech
home_away_draw.h
home_away_draw.a
home_away_draw.d
first_home_away_draw.h
first_home_away_draw.a
first_home_away_draw.d
handicap_home_away_draw.h hg
handicap_home_away_draw.a ag
handicap_home_away_draw.d
handicap.h hg
handicap.a ag

goal_high_low[0].detail.line
goal_high_low[0].detail.h
goal_high_low[0].detail.l
goal_high_low[1].detail.line
goal_high_low[1].detail.h
goal_high_low[1].detail.l
goal_high_low[2].detail.line
goal_high_low[2].detail.h
goal_high_low[2].detail.l
first_goal_high_low[0].detail.line
first_goal_high_low[0].detail.h
first_goal_high_low[0].detail.l
first_goal_high_low[1].detail.line
first_goal_high_low[1].detail.h
first_goal_high_low[1].detail.l
first_goal_high_low[2].detail.line
first_goal_high_low[2].detail.h
first_goal_high_low[2].detail.l
corner_high_low[0].detail.line
corner_high_low[0].detail.h
corner_high_low[0].detail.l
corner_high_low[1].detail.line
corner_high_low[1].detail.h
corner_high_low[1].detail.l
corner_high_low[2].detail.line
corner_high_low[2].detail.h
corner_high_low[2].detail.l
correct_score.s0100
correct_score.s0200
correct_score.s0201
correct_score.s0300
correct_score.s0301
correct_score.s0302
correct_score.s0400
correct_score.s0401
correct_score.s0402
correct_score.s0500
correct_score.s0501
correct_score.s0502
correct_score.s0000
correct_score.s0101
correct_score.s0202
correct_score.s0303
correct_score.s0001
correct_score.s0002
correct_score.s0102
correct_score.s0003
correct_score.s0103
correct_score.s0203
correct_score.s0004
correct_score.s0104
correct_score.s0204
correct_score.s0005
correct_score.s0105
correct_score.s0205
correct_score.sm1mh
correct_score.sm1ma
correct_score.sm1md

first_correct_score.s0100
first_correct_score.s0200
first_correct_score.s0201
first_correct_score.s0300
first_correct_score.s0301
first_correct_score.s0302
first_correct_score.s0400
first_correct_score.s0401
first_correct_score.s0402
first_correct_score.s0500
first_correct_score.s0501
first_correct_score.s0502
first_correct_score.s0000
first_correct_score.s0101
first_correct_score.s0202
first_correct_score.s0303
first_correct_score.s0001
first_correct_score.s0002
first_correct_score.s0102
first_correct_score.s0003
first_correct_score.s0103
first_correct_score.s0203
first_correct_score.s0004
first_correct_score.s0104
first_correct_score.s0204
first_correct_score.s0005
first_correct_score.s0105
first_correct_score.s0205
first_correct_score.sm1mh
first_correct_score.sm1ma
first_correct_score.sm1md
first_team_to_score.h
first_team_to_score.a
first_team_to_score.n
total_goal.p1
total_goal.p2
total_goal.p3
total_goal.p4
total_goal.p5
total_goal.p6
total_goal.m7
odd_even.o
odd_even.e
half_full_home_away_draw.hh
half_full_home_away_draw.ha
half_full_home_away_draw.hd
half_full_home_away_draw.ah
half_full_home_away_draw.aa
half_full_home_away_draw.ad
half_full_home_away_draw.dh
half_full_home_away_draw.da
half_full_home_away_draw.dd
-->