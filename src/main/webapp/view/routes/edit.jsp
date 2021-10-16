<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 08.10.2021
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>

<z:layout pageTitle="Edit route">

    <form method="post" action="${pageContext.request.contextPath}/routes/${requestScope.route.id}">
        <input type="hidden" name="_method" value="put" />
        <h4>from</h4>
        <select name="start_station">
        <c:forEach items="${requestScope.stations}" var="station">
            <h4>Depart station</h4>
            <option value = ${station.name}
            <c:if test="${station.id == requestScope.route.startStation.id}"> selected </c:if> >${station.name}</option>
        </c:forEach>
    </select>
        <h4>to</h4><select name="end_station">
        <c:forEach items="${requestScope.stations}" var="station">
            <h4>Arrival station</h4>
            <option value = ${station.name}
                    <c:if test="${station.id == requestScope.route.endStation.id}"> selected </c:if> >${station.name}</option>
        </c:forEach>
    </select>
        <h4>Departure time</h4><input type="time" name="depart_time" value="${requestScope.route.departTime}">
        <h4>Time in way</h4><input type="time" name="time" value="${requestScope.route.time}">
        <h4>Price</h4><input type="number" name="price" value="${requestScope.route.price}">

        <input type="submit" value="Submit">
    </form>
    </z:layout>
