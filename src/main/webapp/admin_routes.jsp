<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="view/common/menu.jsp"></jsp:include>
    <jsp:include page="view/common/admin_menu.jsp"></jsp:include>
</head>
<body>

<table>
    <tr>
        <td><c:forEach items="${sessionScope.routes}" var="route">


        </c:forEach></td>
    </tr>
</table>
</body>
</html>
