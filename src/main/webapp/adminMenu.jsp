<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.AdminBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>Admin Menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
    <strong>Admin role granted for Mr. <c:out value="${bean.user.name}"></c:out> <c:out value="${bean.user.surname}"></c:out></strong><br>
    <string>Please use navigation bar for options.</string>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="Bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
