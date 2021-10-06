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
    <jsp:include page="view/common/menu.jsp"></jsp:include>
    <jsp:include page="view/common/admin_menu.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>
<h1>WELCOME admin HEYAYEYAEY</h1>
<form method="post" action="controller?action=editStation">
    <input type="text" name ="nameForNew">
    <input type="submit" value="add station">
</form>
<table>
    <tr>
        <td><c:forEach items="${sessionScope.stations}" var="station">
            ${station.name}
            <a href="controller?action=editStation&stationName=${station.name}">Edit</a>
            <a href="controller?action=editStation&stationToDelete=${station.name}"  onclick="return confirm('Are you sure?')">Delete</a>
            <br>
        </c:forEach></td>
    </tr>
</table>

</body>
</html>
