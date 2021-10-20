<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>

<z:layout pageTitle="Routes">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/routes" onsubmit="return validateRouteForm()">
        <input name="add" style="display: none">
        <h4>from</h4><select name="start_station">
            <c:forEach items="${requestScope.stations}" var="station">
            <h4>Depart station</h4><option value = ${station.name}>${station.name}</option>
            </c:forEach>
        </select>
        <h4>to</h4><select name="end_station">
            <c:forEach items="${requestScope.stations}" var="station">
                <h4>Arrival station</h4><option value = ${station.name}>${station.name}</option>
            </c:forEach>
        </select>
        <h4>Departure time</h4><input type="time" name="depart_time" value="${requestScope.time}">
        <h4>Time in way</h4>

        <input type="number" name="days"> days
        <input type="number" name="hours"> hours
        <input type="number" name="minutes"> minutes

        <h4>Price</h4><input type="number" name="price">
        <input type="submit" value="add route">
    </form>
    <table>
        <tr>
            <td>
                <c:forEach items="${requestScope.routes}" var="route">
                    ${route.startStation.name}-${route.endStation.name}
                    ${route.departTime}-${route.arrivalTime}
                    ${route.price}
                    <a href="${pageContext.request.contextPath}/routes/${route.id}/edit">Edit</a>
                    <form action="${pageContext.request.contextPath}/routes/${route.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" value="Delete" onclick="return confirm('Are you sure?')">
                    </form>
                    <br>
            </c:forEach></td>
        </tr>
    </table>
</z:layout>
