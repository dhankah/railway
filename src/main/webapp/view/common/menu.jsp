<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/view/css/common.css">
<div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
            Rail<b>Way</b>
        </a>

        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
            <li><a href="${pageContext.request.contextPath}/trips" class="nav-link px-2 link-dark">Trips</a></li>
            <c:if test="${sessionScope.user.isAdmin}">
                <li><a href="${pageContext.request.contextPath}/stations" class="nav-link px-2 link-dark">Stations</a></li>
                <li><a href="${pageContext.request.contextPath}/routes" class="nav-link px-2 link-dark">Routes</a></li>
            </c:if>
        </ul>

        <div class="align-items-center col-md-3 d-flex justify-content-end text-end">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/cabinet" class="btn btn-primary mx-2 custom" role="button" >Cabinet</a>
                    <form method="post" action="${pageContext.request.contextPath}/auth/logout" class="m-0">
                        <button type="submit" class="btn btn-primary custom">log out</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/auth/login" class="m-0">
                        <button type="submit" class="btn btn-primary custom">log in</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>
<%--<div>--%>
<%--    <form method="post" action="">--%>
<%--        <button type="submit" name="en">EN</button>--%>
<%--        <button type="submit" name="ua">UA</button>--%>
<%--    </form>--%>

<%--</div>--%>
