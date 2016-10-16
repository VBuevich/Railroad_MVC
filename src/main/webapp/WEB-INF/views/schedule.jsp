<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Schedule</title>
    <link href="<c:url value="/resources/Bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <style>
        .wrap {
            width: 50%;
            margin-left: 200px;
        }
    </style>
</head>
<body>

<div class="navbar navbar-inverse">
    <nav class="navbar-inner">
        <a class="brand">railroad</a>

        <ul class="nav">
            <li class="divider-vertical"></li>
            <li><a href="/railroad/user/schedule">Schedule</a></li>
            <li><a href="/railroad/user/findTrain">Find a Train</a></li>
            <li><a href="/railroad/user/myTickets">My tickets</a></li>
            <li><a href="/railroad/user/googleMap">Railroad Map</a></li>
            <li><a href="#logoff" data-toggle="modal">Log off</a></li>
        </ul>
    </nav>
</div>

<div class="wrap">
<form method="post" name="form1" action="/railroad/user/getSchedule">

    <p>Departure Station</p>
    <p><select size="1" width="200px" name="stationName">
        <option disabled>Departure Station</option>
        <c:forEach var = "station" items="${stationList}">
            <c:choose>
                <c:when test="${not empty selectedStation}">
                    <c:choose>
                        <c:when test="${station == selectedStation}">
                            <option selected value="${selectedStation}">${selectedStation}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${station}">${station}</option>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <option value="${station}">${station}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select></p>

    <button type="submit" class="btn btn-default">Get schedule</button>
</form>
</div>

<div class="wrap">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert" href="#">x</a> ${errorMessage}
        </div>
    </c:if>
</div>

<c:if test="${not empty scheduleList}">
<div class="wrap">
    <table class="table table-stripped">
        <thead>
        <tr>
            <th>List of trains, departing from <c:out value="${scheduleList[0].stationName}" /></th>
        </tr>
        <tr>
            <th>Train number</th>
            <th>Departure time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scheduleList}" var="sc">
            <tr>
                <td><a href="#" >${sc.trainNumber}</a></td>
                <td><a href="#" >${sc.time}</a></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</c:if>

<div id="logoff" class="modal hide fade">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4>Log off</h4>
    </div>
    <div class="modal-body">
        <h3>Are you sure you want to log off?</h3>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">No</a>
        <a href="/railroad/logoff" class="btn btn-primary" aria-hidden="true">Yes, log off</a>
    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
</body>
</html>
