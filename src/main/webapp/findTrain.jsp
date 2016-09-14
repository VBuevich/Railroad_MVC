<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.ServiceBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>Schedule</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="Bootstrap/js/bootstrap.min.js"></script>
    <style>
        .wrap {
            width: 50%;
            margin-left: 200px;
        }
    </style>
    <link type="text/css" href="Timepicker/bootstrap-timepicker.min.css" />
    <script type="text/javascript" src="Timepicker/bootstrap-timepicker.min.js"></script>
</head>
<body>

<div class="navbar navbar-inverse">
    <nav class="navbar-inner">
        <a class="brand">Railroad</a>

        <ul class="nav">
            <li class="divider-vertical"></li>
            <li><a href="/RailServlet/dispatcher?page=/schedule.jsp">Shedule</a></li>
            <li><a href="/RailServlet/dispatcher?page=/findTrain.jsp">Find a Train</a></li>
            <li><a href="/RailServlet/dispatcher?page=/myTickets.jsp">My tickets</a></li>
            <li><a href="RailServlet/logoff">Log off</a></li>
        </ul>
    </nav>
</div>

<div class="wrap">
<form method="post" name="form1" action="/RailServlet/Ticketing" >

    <table width=410 cellspacing="0" cellpadding="5">
        <tr>
            <td>
                <div class="form-group">
                    <label for="depTimeStart">Departure Time</label>
                    <div class="input-group bootstrap-timepicker timepicker" id="depTimeStart">
                        <input id="timepicker1" type="text" class="form-control input-small" name="departureTime">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>

                    <label for="depSelectStart">Departure Station</label>
                    <select size="1" name="departureStation" id="depSelectStart">
                    <c:forEach var = "station" items="${bean.stationList}">
                        <option value="${station}">${station}</option>
                    </c:forEach>
                    </select>
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="arrTimeStart">Arrival Time</label>
                    <div class="input-group bootstrap-timepicker timepicker" id="arrTimeStart">
                        <input id="timepicker2" type="text" class="form-control input-small" name="arrivalTime">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>

                    <label for="arrSelectStart">Arrival Station</label>
                    <select size="1" name="arrivalStation" id="arrSelectStart">
                        <c:forEach var = "station" items="${bean.stationList}">
                            <option value="${station}">${station}</option>
                        </c:forEach>
                    </select>
                </div>
            </td>
        </tr>
    </table>

    <button type="submit" class="btn btn-default">Find trains</button>
</form>
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

<c:if test="${not empty bean.offerList}">
    <div class="wrap">
        <strong> List of trains matching your criteria of search. Consider to buy!</strong>

        <table class="table table-stripped">
            <thead>
            <tr>
                <th>Train number</th>
                <th>Departure station</th>
                <th>Departure time</th>
                <th>Arrival station</th>
                <th>Arrival time</th>
                <th>Buy this ticket</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="o" items="${bean.offerList}">
                <tr>
                    <form method="post" action="/RailServlet/BuyingTicket">
                        <td><a href="#" >${o.trainNumber}</a></td>
                        <td><a href="#" >${o.departureStation}</a></td>
                        <td><a href="#" >${o.departureTime}</a></td>
                        <td><a href="#" >${o.arrivalStation}</a></td>
                        <td><a href="#" >${o.arrivalTime}</a></td>
                        <td><button type="submit" class="btn btn-default">Buy</button></td>
                            <input type="hidden" name="trainNumber" value="${o.trainNumber}">
                            <input type="hidden" name="departureStation" value="${o.departureStation}">
                            <input type="hidden" name="arrivalStation" value="${o.arrivalStation}">
                    </form>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</c:if>

<script type="text/javascript">
    $('#timepicker1').timepicker({
        minuteStep:1,
        showSeconds:false,
        defaultTime:'current',
        showMeridian:false
    });
</script>
<script type="text/javascript">
    $('#timepicker2').timepicker({
        minuteStep:1,
        showSeconds:false,
        defaultTime:'current',
        showMeridian:false
    });
</script>

</body>
</html>
