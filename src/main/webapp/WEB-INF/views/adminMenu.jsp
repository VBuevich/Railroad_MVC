<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Admin Menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link href="<c:url value="/resources/Bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
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
        <a class="brand">railroad Admin</a>

        <ul class="nav">
            <li class="divider-vertical"></li>
            <li><a href="newStation">New Station</a></li>
            <li><a href="newTrain">New Train</a></li>
            <li><a href="newSchedule">New Schedule</a></li>
            <li><a href="passengerList">List of passengers</a></li>
            <li><a href="#logoff" data-toggle="modal">Log off</a></li>
        </ul>
    </nav>
</div>

<div class="wrap">
    <strong>Admin role granted for Mr. ${name} ${surname}</strong><br>
    <strong>Please use navigation bar for options.</strong>

    <a href="getStatistics" class="btn btn-default" >Get statistics of ticketing</a>
</div>

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
        <a href="logoff" class="btn btn-primary" aria-hidden="true">Yes, log off</a>
    </div>
</div>

</body>
</html>
