<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<z:layout pageTitle="Reset password">
    <form name="edit" action="${pageContext.request.contextPath}/auth/reset_password_email">
        <div class="mb-3 m-3">
            <h4><fmt:message key="email"/></h4>
            <input class="form-control custom_input" type="email" name="email" required>
            <input type="submit" value="<fmt:message key="send_email"/>" class="btn btn-primary custom my-1">
        </div>
    </form>
</z:layout>
