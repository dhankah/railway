<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>

<html>
    <head>
        <title>${pageTitle}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/common.css">
        <script src="${pageContext.request.contextPath}/view/js/script.js"></script>

    </head>
    <body>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/select_seat.css">

        <jsp:include page="../../view/common/menu.jsp"/>
    <%--    error--%>
    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert alert-danger d-flex align-items-center" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                 aria-label="Warning:">
                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div>
                    ${sessionScope.errorMessage}
                <c:remove var="errorMessage" scope="session"/>
            </div>
        </div>
    </c:if>

    <%--    success--%>
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success d-flex align-items-center" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                 class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img"
                 aria-label="Warning:">
            </svg>
            <div>
                    ${sessionScope.message}
                <c:remove var="message" scope="session"/>
            </div>
        </div>
    </c:if>
        <jsp:doBody/>
    </body>
</html>