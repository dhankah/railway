<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 04.10.2021
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <form method="post" action="controller?action=stations">
        <button type="submit">Stations</button>
    </form>
    <form method="post" action="controller?action=routes">
        <button type="submit">Routes</button>
    </form>
    <form method="post" action="controller?action=trains">
        <button type="submit">Trains</button>
    </form>
</div>
</body>
</html>
