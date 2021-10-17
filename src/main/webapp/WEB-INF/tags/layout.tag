<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>

<html>
    <head>
        <title>${pageTitle}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/bootstrap.min.css">
        <script src="${pageContext.request.contextPath}/view/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="../../view/common/menu.jsp"/>
        <jsp:doBody/>
    </body>
</html>