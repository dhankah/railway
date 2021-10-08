<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="view/common/menu.jsp"></jsp:include>
    <jsp:include page="view/common/admin_menu.jsp"></jsp:include>
</head>
<body>
<form method="post" action="controller?action=editRoute">
    <input name="add" style="display: none">
    <h4>from</h4><select name="start_station">
        <c:forEach items="${sessionScope.stations}" var="station">
        <h4>Depart station</h4><option value = ${station.name}>${station.name}</option>
        </c:forEach>
    </select>
    <h4>to</h4><select name="end_station">
        <c:forEach items="${sessionScope.stations}" var="station">
            <h4>Arrival station</h4><option value = ${station.name}>${station.name}</option>
        </c:forEach>
    </select>
    <h4>Departure time</h4><input type="time" name="depart_time">
    <h4>Arrival time</h4><input type="time" name="arrival_time">
    <h4>Price</h4><input type="number" name="price">
    <input type="submit" value="add route">
</form>
<table>
    <tr>
        <td>
            <c:forEach items="${sessionScope.routes}" var="route">
            ${route.startStation.name}-${route.endStation.name}
            ${route.departTime}-${route.arrivalTime}
            ${route.price}
            <a href="controller?action=editRoute&id=${route.id}">Edit </a>
            <a href="controller?action=editRoute&idToDelete=${route.id}">delete</a>
            <br>
        </c:forEach></td>
    </tr>
</table>
</body>
</html>
