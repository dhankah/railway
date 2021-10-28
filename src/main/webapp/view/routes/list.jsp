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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<z:layout pageTitle="Routes">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/routes" onsubmit="return validateRouteForm()">
        <input name="add" style="display: none">
        <h4><fmt:message key="from"/></h4><select name="start_station">
            <c:forEach items="${requestScope.stations}" var="station">
            <h4><fmt:message key="depart_station"/></h4><option value = ${station.name}>${station.name}</option>
            </c:forEach>
        </select>
        <h4><fmt:message key="to"/></h4><select name="end_station">
            <c:forEach items="${requestScope.stations}" var="station">
                <h4><fmt:message key="arrival_station"/></h4><option value = ${station.name}>${station.name}</option>
            </c:forEach>
        </select>
        <h4><fmt:message key="departure_time"/></h4>
        <input type="time" name="depart_time" value="00:00">        <h4><fmt:message key="time"/></h4>
        <input type="number" name="days"> <fmt:message key="days"/>
        <input type="number" name="hours"> <fmt:message key="hours"/>
        <input type="number" name="minutes"> <fmt:message key="minutes"/>

        <h4><fmt:message key="price"/></h4><input type="number" name="price">
        <input type="submit" value="<fmt:message key="add"/>" class="btn btn-primary custom">
    </form>
    <table>
        <tr>
            <td>
                <c:forEach items="${requestScope.routes}" var="route">
                    ${route.startStation.name}-${route.endStation.name}
                    ${route.departTime}-${route.arrivalTime}
                    ${route.price}
                    <a href="${pageContext.request.contextPath}/routes/${route.id}/edit"><fmt:message key="edit"/></a>
                    <form action="${pageContext.request.contextPath}/routes/${route.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" value="<fmt:message key="delete"/>" onclick="return confirm('<fmt:message key="confirm"/>')" class="btn btn-primary danger">
                    </form>
                    <br>
            </c:forEach></td>
        </tr>
    </table>
</z:layout>
<c:if test="${not empty requestScope.pages}">
<%for ( int pageNum = 1; pageNum <= (Integer)request.getAttribute("pages"); pageNum++){ %>
<a href="${pageContext.request.contextPath}/routes/<%=pageNum%>/page"><%=pageNum%></a>
<%}%>
</c:if>