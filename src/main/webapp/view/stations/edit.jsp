<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<z:layout pageTitle="Edit station">
    <form action="${pageContext.request.contextPath}/stations/${requestScope.station.id}" method="post">
        <input type="hidden" name="_method" value="put" />
        <h4>name</h4>
        <input type="text" name = "name" value="${requestScope.station.name}">
        <br>
        <input type="submit">
    </form>
</z:layout>