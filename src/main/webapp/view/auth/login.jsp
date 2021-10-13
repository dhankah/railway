<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<p>Message: ${sessionScope.errorMessage}</p>
<c:remove var="errorMessage" scope="session" />
<div class = "form">
    <h1>log in</h1>
    <br>
    <form method="post" action="${pageContext.request.contextPath}/auth/login">
        <h4>login</h4><input type="text" name="login"/><br>
        <h4>password</h4><input type= "password" name="password"/><br>
        <input type="submit" value="log in"/>
    </form>
    <a href="${pageContext.request.contextPath}/auth/register">I am not yet signed up</a>
</div>
</body>
</html>
