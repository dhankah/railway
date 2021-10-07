<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 07.10.2021
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>sign up</h1>
<br>
<form method="post" action="controller?action=signup">
    <h4>login</h4><input type = "text" name = "loginSignUp"/><br>
    <h4>password</h4><input type = "password" name = "passwordSignUp"/><br>
    <h4>first name</h4><input type = "text" name = "fname"/><br>
    <h4>last name</h4><input type = "text" name = "lname"/><br>
    <h4>email</h4><input type = "text" name = "email"/><br>
    <input type="submit" value = "sign up"/>
</form>
</body>
</html>
