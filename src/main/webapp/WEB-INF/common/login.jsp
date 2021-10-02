<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 02.10.2021
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class = "form">
    <h1>log in</h1>
    <br>
    <form method="post" action="controller?action=login">
        <fmt:message key="login"/><input type = "text" name = "login"/><br>
        <fmt:message key="password"/><input type = "password" name = "password"/><br>
        <input type="submit" value = "log in"/>
    </form>
</div>
</body>
</html>
