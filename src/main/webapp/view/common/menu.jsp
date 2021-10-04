<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 02.10.2021
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
<div>
    <form method="post" action="">
        <button type="submit" name="en">EN</button>
        <button type="submit" name="ua">UA</button>
    </form>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <form method="post" action="">
                <button type="submit">log out</button>
            </form>
        </c:when>
        <c:otherwise>
            <form method="post" action="controller?action=login">
                <button type="submit">log in</button>

            </form>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
