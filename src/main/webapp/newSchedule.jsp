<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.AdminBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>New Schedule</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .wrap {
            width: 50%;
        }
    </style>
</head>
<body>

<div class="navbar navbar-inverse">
    <nav class="navbar-inner">
        <a class="brand">Railroad Admin</a>

        <ul class="nav">
            <li class="divider-vertical"></li>
            <li><a href="/RailServlet/dispatcher?page=/newStation.jsp">New Station</a></li>
            <li><a href="/RailServlet/dispatcher?page=/newTrain.jsp">New Train</a></li>
            <li><a href="/RailServlet/dispatcher?page=/newSchedule.jsp">New Schedule</a></li>
            <li><a href="/RailServlet/dispatcher?page=/passengerList.jsp">List of passengers</a></li>
            <li><a href="RailServlet/logoff">Log off</a></li>
        </ul>
    </nav>
</div>

<div class="wrap">
    <c:if test="${not empty message.errorMessage}">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert" href="#">x</a> ${message.errorMessage}
        </div>
    </c:if>
</div>

<div class="wrap">
    <c:if test="${not empty message.successMessage}">
        <div class="alert alert-success">
            <a class="close" data-dismiss="alert" href="#">x</a> ${message.successMessage}
        </div>
    </c:if>
</div>

<form method="post" name="form1" action="/RailServlet/addSchedule">
<strong>Add new schedule</strong>
<div class="form-group">
    <label for="trainGroup">Departure Station</label>
    <select size="1" name="trainNumber" id="trainGroup">
        <c:forEach var = "train" items="${bean.trainList}">
            <option value="${train}">${train}</option>
        </c:forEach>
    </select>

    <label for="stationGroup">Departure Station</label>
    <select size="1" name="station" id="stationGroup">
        <c:forEach var = "station" items="${bean.stationList}">
            <option value="${station}">${station}</option>
        </c:forEach>
    </select>

    <label for="depTimeGroup">Departure Time</label>
    <input type="text" name="departureTime" class="form-control" id="depTimeGroup">

    <button type="submit" class="btn btn-default">Add</button>
</div>
</form>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="Bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

