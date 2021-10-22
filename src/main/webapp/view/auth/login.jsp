<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 02.10.2021
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<z:layout pageTitle="Login">
    <main class="col-lg-3 col-md-4 col-sm-6 form-signin m-3 m-sm-auto">
        <form method="post" action="${pageContext.request.contextPath}/auth/login">
            <h1 class="h3 mb-3 fw-normal">Please log in</h1>

            <div class="form-floating">
                <input type="text" class="form-control" id="input" name="login">
                <label for="input">Login</label>
            </div>
            <div class="form-floating mt-2">
                <input type="password" class="form-control" id="password" name="password">
                <label for="password">Password</label>
            </div>

            <button class="btn btn-lg btn-primary mt-2 w-100 custom" type="submit">Log in</button>
        </form>
        <a href="${pageContext.request.contextPath}/auth/register">I am not yet signed up</a>
    </main>

</z:layout>