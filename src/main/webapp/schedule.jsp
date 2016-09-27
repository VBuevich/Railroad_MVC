<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.ServiceBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>Schedule</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
        <a class="brand">Railroad</a>

        <ul class="nav">
            <li class="divider-vertical"></li>
            <li><a href="RailServlet/dispatcher?page=/schedule.jsp">Shedule</a></li>
            <li><a href="RailServlet/dispatcher?page=/findTrain.jsp">Find a Train</a></li>
            <li><a href="RailServlet/dispatcher?page=/myTickets.jsp">My tickets</a></li>
            <li><a href="RailServlet/logoff">Log off</a></li>
        </ul>
    </nav>
</div>

<div class="wrap">
<form method="post" name="form1" action="RailServlet/StationTrainsServlet">

    <p>Departure Station</p>
    <p><select size="1" width="200px" name="stationName">
        <option disabled>Departure Station</option>
        <c:forEach var = "station" items="${bean.stationList}">
            <option value="${station}">${station}</option>
        </c:forEach>
    </select></p>

    <button type="submit" class="btn btn-default">Get schedule</button>
</form>
</div>

<div class="wrap">
    <c:if test="${not empty message.errorMessage}">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert" href="#">x</a> ${message.errorMessage}
        </div>
    </c:if>
</div>

<c:if test="${not empty bean.scheduleList}">
<div class="wrap">
    <table class="table table-stripped">
        <thead>
        <tr>
            <th>List of trains, departing from <c:out value="${bean.scheduleList[0].stationName}" /></th>
        </tr>
        <tr>
            <th>Train number</th>
            <th>Departure time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bean.scheduleList}" var="sc">
            <tr>
                <td><a href="#" >${sc.trainNumber}</a></td>
                <td><a href="#" >${sc.time}</a></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</c:if>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="Bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
