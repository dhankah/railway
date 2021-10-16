<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<z:layout pageTitle="Cabinet">
<div class="col-md-8">
    <div class="card mb-3">
        <div class="card-body">
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Login</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.login}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">First name</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.details.firstName}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">Last name</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.details.lastName}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-sm-3">
                    <h6 class="mb-0">e-mail</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                        ${sessionScope.user.details.email}
                </div>
            </div>
            <hr>

            <div class="row">
                <div class="col-sm-12">
                    <a class="btn btn-info " href="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}/edit">Edit</a>
                </div>
                <div class="col-sm-12">
                    <a class="btn btn-info " href="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}/change_password">Change password</a>
                </div>
                <div class="col-sm-12">
                    <form action="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" class="btn btn-danger" value="Delete" onclick="return confirm('Are you sure?')">
                    </form>
                </div>
            </div>
        </div>
    </div>
</z:layout>
