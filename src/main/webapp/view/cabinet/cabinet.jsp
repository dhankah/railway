<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<z:layout pageTitle="Cabinet">
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
                    <a class="btn btn-info custom" href="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}/edit">Edit</a>
                    <a class="btn btn-info custom" href="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}/change_password">Change password</a>
                    <form action="${pageContext.request.contextPath}/cabinet/${sessionScope.user.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" value="Delete your profile" class="btn btn-danger my-1 danger" onclick="return confirm('Are you sure?')">
                    </form>
            </div>
        </div>
    </div>
    <div class="card-group">
        <c:forEach items="${requestScope.tickets}" var="ticket">
            <div class="card" style="width: 100px;">
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Passenger name: ${ticket.user.details.firstName} ${ticket.user.details.lastName}</li>
                        <li class="list-group-item">Train number: ${ticket.trip.route.id}</li>
                        <li class="list-group-item">Depart station: ${ticket.trip.route.startStation.name}</li>
                        <li class="list-group-item">Depart station: ${ticket.trip.route.startStation.name}</li>
                        <li class="list-group-item">Departure: ${ticket.trip.departDate} ${ticket.trip.route.departTime}</li>
                        <li class="list-group-item">Arrival: ${ticket.trip.arrivalDate} ${ticket.trip.route.arrivalTime}</li>
                    </ul>
                    <form class="m-3" action="${pageContext.request.contextPath}/ticket/${ticket.id}" method="post">
                        <input type="hidden" name="_method" value="delete" />
                        <input type="submit" class="btn btn-primary custom" value="Cancel purchase" onclick="return confirm('Are you sure?')">
                    </form>
                    <a href="${pageContext.request.contextPath}/download/${ticket.id}" class="btn btn-primary custom">Download ticket</a>
                </div>
            </div>
        </c:forEach>
    </div>


</z:layout>
