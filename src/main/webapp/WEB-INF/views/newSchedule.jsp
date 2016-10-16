<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>New Schedule</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link href="<c:url value="/resources/Bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
    <style>
        .wrap {
            width: 50%;
            margin-left: 200px;
        }
    </style>
    <link type="text/css" href="<c:url value="/resources/Timepicker/bootstrap-timepicker.min.css" />" />
    <script type="text/javascript" src="<c:url value="/resources/Timepicker/bootstrap-timepicker.min.js" />" ></script>
</head>
<body>

<div class="navbar navbar-inverse">
    <nav class="navbar-inner">
        <a class="brand">railroad Admin</a>

        <ul class="nav">
            <li class="divider-vertical"></li>
            <li><a href="/railroad/admin/newStation">New Station</a></li>
            <li><a href="/railroad/admin/newTrain">New Train</a></li>
            <li><a href="/railroad/admin/newSchedule">New Schedule</a></li>
            <li><a href="/railroad/admin/passengerList">List of passengers</a></li>
            <li><a href="#logoff" data-toggle="modal">Log off</a></li>
        </ul>
    </nav>
</div>

<div class="wrap">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert" href="#">x</a> ${errorMessage}
        </div>
    </c:if>
</div>

<div class="wrap">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <a class="close" data-dismiss="alert" href="#">x</a> ${successMessage}
        </div>
    </c:if>
</div>

<div class="wrap">
<form method="post" name="form1" action="/railroad/admin/addSchedule">
<strong>Add new schedule</strong>
<div class="form-group">
    <label for="trainGroup">Train Number</label>
    <select size="1" name="trainNumber" id="trainGroup">
        <c:forEach var = "train" items="${trainList}">
            <c:choose>
                <c:when test="${not empty selectedTrain}">
                    <c:choose>
                        <c:when test="${train == selectedTrain}">
                            <option selected value="${selectedTrain}">${selectedTrain}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${train}">${train}</option>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <option value="${train}">${train}</option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>

    <label for="stationGroup">Departure Station</label>
    <select size="1" name="station" id="stationGroup">
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
    </select>

    <label for="depTimeGroup">Departure Time</label>
    <div class="input-group bootstrap-timepicker timepicker" id="depTimeGroup">
        <input id="timepicker1" type="text" class="form-control input-small" name="departureTime">
        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
    </div>

    <button type="submit" class="btn btn-default">Add</button>
</div>
</form>
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
        <a href="/railroad/logoff" class="btn btn-primary" aria-hidden="true">Yes, log off</a>
    </div>
</div>

<script type="text/javascript">
    $('#timepicker1').timepicker({
        minuteStep:1,
        showSeconds:false,
        defaultTime:'current',
        showMeridian:false
    });
</script>

</body>
</html>

