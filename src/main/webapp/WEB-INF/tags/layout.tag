<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>

<html>
    <head>
        <title>${pageTitle}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/common.css">
        <script src="${pageContext.request.contextPath}/view/js/script.js"></script>

    </head>
    <body>
        <jsp:include page="../../view/common/menu.jsp"/>
        <jsp:doBody/>
    </body>
</html>