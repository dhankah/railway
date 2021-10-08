<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 03.10.2021
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="view/common/menu.jsp"></jsp:include>
    <jsp:include page="view/common/client_menu.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>
<h1>Dear CLIENT AHAHAHAHAHA</h1>
<form action="controller?action=findTrains" method="post">
    <h4>from</h4>
    <select name="start_station">
        <c:forEach items="${sessionScope.stations}" var="station">
            <h4>Depart station</h4><option value = ${station.name}>${station.name}</option>
        </c:forEach>
    </select>
    <h4>to</h4>
    <select name="end_station">
        <c:forEach items="${sessionScope.stations}" var="station">
            <h4>Arrival station</h4><option value = ${station.name}>${station.name}</option>
        </c:forEach>
    </select>
    <h4>Departure date</h4><input type="date" name="depart_date">
    <input type="submit" value="Find!">
</form>
</body>
</html>
