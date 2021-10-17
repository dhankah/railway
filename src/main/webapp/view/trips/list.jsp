<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 03.10.2021
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<z:layout pageTitle="Trips">
    <form action="${pageContext.request.contextPath}/trips">
        <h4>from</h4>
        <select name="depart_station">
            <c:forEach items="${requestScope.stations}" var="station">
                <h4>Depart station</h4><option value = ${station.name}>${station.name}</option>
            </c:forEach>
        </select>
        <h4>to</h4>
        <select name="arrival_station">
            <c:forEach items="${requestScope.stations}" var="station">
                <h4>Arrival station</h4><option value = ${station.name}>${station.name}</option>
            </c:forEach>
        </select>
        <h4>Departure date</h4><input type="date" name="depart_date">
        <input type="submit" value="Find!">
    </form>

    <c:if test="${null != requestScope.trips}">
        <h3>These are the trains we have found for you</h3>
        <c:forEach items="${requestScope.trips}" var="trip">
            number
            ${trip.route.id}
            from
            ${trip.route.startStation.name}
            ${trip.route.departTime}
            ${trip.departDate}
            to
            ${trip.route.endStation.name}
            ${trip.route.arrivalTime}
            ${trip.arrivalDate}
            time in way
            ${trip.route.day} days
            ${trip.route.hour} hours
            ${trip.route.minute} minutes
            price
            ${trip.route.price}
            available places
            ${trip.availablePlaces}
            <c:if test="${not empty sessionScope.user && !sessionScope.user.isAdmin}">
                <a href="${pageContext.request.contextPath}/trips/${trip.id}/choose">Choose seat</a>
            </c:if>
        </c:forEach>
    </c:if>
</z:layout>
