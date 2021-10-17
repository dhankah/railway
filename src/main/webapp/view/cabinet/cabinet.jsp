<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="z" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <input type="submit" class="btn btn-primary" value="Cancel purchase" onclick="return confirm('Are you sure?')">
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>

</z:layout>
