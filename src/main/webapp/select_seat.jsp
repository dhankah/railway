<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 12.10.2021
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="view/css/style.css">
    <title>Title</title>
</head>
<body>
<form method="post" action="controller?action=buyTicket">
    <table>
        <tr>
            <c:forEach items = "${sessionScope.seats}" var="seat" begin="0" end="8">
                <td>
                  <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                  <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                </td>
            </c:forEach>
        </tr>
        <tr>
        <c:forEach items = "${sessionScope.seats}" var="seat" begin="9" end="17">
            <td>
                <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
            </td>
        </c:forEach>
        </tr>
        <tr>
            <c:forEach items = "${sessionScope.seats}" var="seat" begin="18" end="26">
            <td>
                <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
            </td>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach items = "${sessionScope.seats}" var="seat" begin="27">
                <td>
                    <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                    <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                </td>
            </c:forEach>
        </tr>
    </table>
    <input type="submit">
</form>
</body>
</html>
