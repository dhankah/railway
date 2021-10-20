<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<z:layout pageTitle="Select seat">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/select_seat.css">
<form method="post" action="${pageContext.request.contextPath}/ticket" onsubmit="return validateSeatForm()">
    <table>
        <tr>
            <c:forEach items = "${requestScope.seats}" var="seat" begin="0" end="8">
                <td>
                  <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                  <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                </td>
            </c:forEach>
        </tr>
        <tr>
        <c:forEach items = "${requestScope.seats}" var="seat" begin="9" end="17">
            <td>
                <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
            </td>
        </c:forEach>
        </tr>
        <tr>
            <c:forEach items = "${requestScope.seats}" var="seat" begin="18" end="26">
            <td>
                <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
            </td>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach items = "${requestScope.seats}" var="seat" begin="27">
                <td>
                    <input id="${seat.number}" class="seat-select"  <c:if test="${seat.occupied == true}"> disabled </c:if>type="radio" value="${seat.number}" name="number" />
                    <label for="${seat.number}" <c:if test="${seat.occupied == true}"> class="occupied"</c:if>>${seat.number}</label>
                </td>
            </c:forEach>
        </tr>
    </table>
    <input type="hidden" name="trip" value="${requestScope.trip.id}">
    <input type="submit"  class="btn btn-primary custom" value="Purchase">
</form>
</z:layout>