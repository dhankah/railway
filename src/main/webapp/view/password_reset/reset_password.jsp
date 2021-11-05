<%--
  Created by IntelliJ IDEA.
  User: Dana
  Date: 06.10.2021
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Reset password">
    <form name = "edit" action="${pageContext.request.contextPath}/auth" method="post">
        <div class="mb-3 m-3">
            <input type="hidden" name="_method" value="put" />
            <h4><fmt:message key="enter_new_password"/></h4>
            <input class="form-control custom_input" type="password" name = "password1" required>
            <h4><fmt:message key="reenter_new_password"/></h4>
            <input class="form-control custom_input" type="password" name = "password2" required>
            <input type="submit" value="<fmt:message key="save"/>" class="btn btn-primary custom my-1">
        </div>
    </form>
</z:layout>