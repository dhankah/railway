<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 03.10.2021
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<z:layout pageTitle="Stations">
    <form name="edit" method="post" action="${pageContext.request.contextPath}/stations" onsubmit="return validateStationForm()">
        <input type="text" name="name">
        <input type="submit" value="add station">

    </form>
    <table>
        <tr>
            <td><c:forEach items="${requestScope.stations}" var="station">
                ${station.name}
                <a href="${pageContext.request.contextPath}/stations/${station.id}/edit">Edit</a>
                <form action="${pageContext.request.contextPath}/stations/${station.id}" method="post">
                    <input type="hidden" name="_method" value="delete" />
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure?')">
                </form>
                <br>
            </c:forEach></td>
        </tr>
    </table>

</z:layout>