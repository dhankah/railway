<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="controller?action=editStation" method="post">
    <h4>name</h4>
    <input name="prevName" style="display: none" value="${sessionScope.station.name}">
    <input type="text" name = "name" value=${sessionScope.station.name}>
    <br>
    <input type="submit">
</form>
</body>
</html>
