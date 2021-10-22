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

    <form name="edit" method="post" action="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}" onsubmit="return validateEditPasswordForm">
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
            <input type="submit" class="btn btn-primary px-4 custom" value="Save Changes">
        </div>
    </div>
    </form>
</z:layout>