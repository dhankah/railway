<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 08.10.2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${requestScope.route.departTime}
${requestScope.route.id}
<form method="post" action="controller?action=editRoute">
    <input name="editId" style="display: none" value=${sessionScope.route.id}>
    <h4>from</h4>
    <select name="start_station">
    <c:forEach items="${sessionScope.stations}" var="station">
        <h4>Depart station</h4>
        <option value = ${station.name}
        <c:if test="${station.id == sessionScope.route.startStation.id}"> selected </c:if> >${station.name}</option>
    </c:forEach>
</select>
    <h4>to</h4><select name="end_station">
    <c:forEach items="${sessionScope.stations}" var="station">
        <h4>Arrival station</h4>
        <option value = ${station.name}
                <c:if test="${station.id == sessionScope.route.endStation.id}"> selected </c:if> >${station.name}</option>
    </c:forEach>
</select>
    <h4>Departure time</h4><input type="time" name="depart_time" value="${sessionScope.route.departTime}">
    <h4>Arrival time</h4><input type="time" name="arrival_time" value="${sessionScope.route.arrivalTime}">
    <h4>Price</h4><input type="number" name="price" value="${sessionScope.route.price}">
    <input type="submit" value="Submit">
</form>
</body>
</html>
