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
    <title>Title</title>
</head>
<body>
<h1>WELCOME admin HEYAYEYAEY</h1>
<table>
    <tr>
        <td><c:forEach items="${sessionScope.stations}" var="station">
            ${station.name} <br>
        </c:forEach></td>
    </tr>
</table>

</body>
</html>
