<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 03.10.2021
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<z:layout pageTitle="Stations">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/stations" onsubmit="return validateStationForm()">
        <input type="text" name="<fmt:message key="name"/>">
        <input type="submit" value="<fmt:message key="add"/>" class="btn btn-primary custom">

    </form>
    <table>
        <tr>
            <td><c:forEach items="${requestScope.stations}" var="station">
                ${station.name}
                <a href="${pageContext.request.contextPath}/stations/${station.id}/edit"><fmt:message key="edit"/></a>
                <form action="${pageContext.request.contextPath}/stations/${station.id}" method="post">
                    <input type="hidden" name="_method" value="delete" />
                    <input type="submit" value="Delete" onclick="return confirm('<fmt:message key="delete"/>')" class = "btn btn-primary danger">
                </form>
                <br>
            </c:forEach></td>
        </tr>
    </table>

</z:layout>
<c:if test="${not empty requestScope.pages}">
<%for ( int pageNum = 1; pageNum <= (Integer)request.getAttribute("pages"); pageNum++){ %>
<a href="${pageContext.request.contextPath}/stations/<%=pageNum%>/page"><%=pageNum%></a>
<%}%>
</c:if>