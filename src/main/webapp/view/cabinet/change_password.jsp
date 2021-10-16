<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 16.10.2021
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<z:layout pageTitle="Edit profile">
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
    <form method="post" action="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}">
        <input type="hidden" name="_method" value="put" />
    <div class="row mb-3">
        <div class="col-sm-3">
            <h6 class="mb-0">Enter your previous password</h6>
        </div>
        <div class="col-sm-9 text-secondary">
            <input type="password" class="form-control" name="old_password">
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-sm-3">
            <h6 class="mb-0">Enter your new password</h6>
        </div>
        <div class="col-sm-9 text-secondary">
            <input type="password" class="form-control" name="password">
        </div>
    </div>

    <div class="row mb-3">
        <div class="col-sm-3">
            <h6 class="mb-0">Enter your new password again</h6>
        </div>
        <div class="col-sm-9 text-secondary">
            <input type="password" class="form-control" name="re_password">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-9 text-secondary">
            <input type="submit" class="btn btn-primary px-4" value="Save Changes">
        </div>
    </div>
    </form>
</z:layout>