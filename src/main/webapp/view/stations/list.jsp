<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 03.10.2021
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../common/menu.jsp"></jsp:include>
    <jsp:include page="../common/admin_menu.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>
<h1>WELCOME admin HEYAYEYAEY</h1>
<form method="post" action="stations">
    <input type="text" name="name">
    <input type="submit" value="add station">

</form>
<table>
    <tr>
        <td><c:forEach items="${requestScope.stations}" var="station">
            ${station.name}
            <a href="stations/${station.id}/edit">Edit</a>
            <form action="${pageContext.request.contextPath}/stations/${station.id}" method="post">
                <input type="hidden" name="_method" value="delete" />
                <input type="submit" value="Delete" onclick="return confirm('Are you sure?')">
            </form>
            <br>
        </c:forEach></td>
    </tr>
</table>

</body>
</html>
