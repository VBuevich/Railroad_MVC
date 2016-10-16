<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Schedule</title>
    <link href="<c:url value="/resources/Bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
    <style>
        .wrap {

            margin-left: 200px;
        }
    </style>
    <link type="text/css" href="<c:url value="/resources/Timepicker/bootstrap-timepicker.min.css" />" >
    <script type="text/javascript" src="<c:url value="/resources/Timepicker/bootstrap-timepicker.min.js" />"></script>
    <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/SeatChart/jquery.seat-charts.css" />" >
    <!--link rel="stylesheet" type="text/css" href="SeatChart/SeatChart.style.css"-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/SeatChart/Style.css" /> ">
    <script src="<c:url value="/resources/SeatChart/jquery.seat-charts.js" />"></script>

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
<div class="row">
    <div class="span9">
        <form method="post" name="form1" action="/railroad/user/ticketing" >

        <table width=410 cellspacing="0" cellpadding="5">
        <tr>
            <td>
                <div class="form-group">
                    <label for="depTimeStart">Departure Time</label>
                    <div class="input-group bootstrap-timepicker timepicker" id="depTimeStart">
                        <input id="timepicker1" type="text" class="form-control input-small" name="departureTime" value="${departureTime}">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>

                    <label for="depSelectStart">Departure Station</label>
                    <select size="1" name="departureStation" id="depSelectStart">
                    <c:forEach var = "station" items="${stationList}">
                        <c:choose>
                            <c:when test="${not empty selectedStationDeparture}">
                                <c:choose>
                                    <c:when test="${station == selectedStationDeparture}">
                                        <option selected value="${selectedStationDeparture}">${selectedStationDeparture}</option>
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
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="arrTimeStart">Arrival Time</label>
                    <div class="input-group bootstrap-timepicker timepicker" id="arrTimeStart">
                        <input id="timepicker2" type="text" class="form-control input-small" name="arrivalTime" value="${arrivalTime}">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>

                    <label for="arrSelectStart">Arrival Station</label>
                    <select size="1" name="arrivalStation" id="arrSelectStart">
                    <c:forEach var = "station" items="${stationList}">
                        <c:choose>
                            <c:when test="${not empty selectedStationArrival}">
                                <c:choose>
                                    <c:when test="${station == selectedStationArrival}">
                                        <option selected value="${selectedStationArrival}">${selectedStationArrival}</option>
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
                </div>
            </td>
        </tr>
        </table>

        <button type="submit" class="btn btn-default">Find trains</button>
        </form>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                <a class="close" data-dismiss="alert" href="#">x</a> ${errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <a class="close" data-dismiss="alert" href="#">x</a> ${successMessage}
            </div>
        </c:if>

        <c:if test="${not empty offerList}">

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
                <c:forEach var="o" items="${offerList}">
                    <tr>
                            <td><a href="#" >${o.trainNumber}</a></td>
                            <td><a href="#" >${o.departureStation}</a></td>
                            <td><a href="#" >${o.departureTime}</a></td>
                            <td><a href="#" >${o.arrivalStation}</a></td>
                            <td><a href="#" >${o.arrivalTime}</a></td>
                            <td><button type="button" onclick="seatmap('${o.trainNumber}', '${o.departureStation}', '${o.arrivalStation}')" class="btn btn-default">Select seat</button></td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <form method="post" action="/railroad/user/buyTicket">
                <input type="hidden" id="trainNumberForm" name="trainNumber" value="">
                <input type="hidden" id="departureStationForm" name="departureStation" value="">
                <input type="hidden" id="arrivalStationForm" name="arrivalStation" value="">
                <input type="hidden" id="selectedSeatForm" name=selectedSeat value="">
                <input type="hidden" name="departureTime" value="${departureTime}">
                <input type="hidden" name="arrivalTime" value="${arrivalTime}">
                <button type="submit" class="btn btn-success btn-lg" name="seatBuy">Please choose your seat</button>
            </form>

            <c:if test="${not empty trainNumber}">
                <script>
                    window.onload = function() {
                        seatmap('${trainNumber}', '${selectedStationDeparture}', '${selectedStationArrival}')
                    }
                </script>
            </c:if>

        </c:if>
     </div>
    <div class="span2">
        <div class="wrapper">
            <div class="container" id="mapContainer">

                <div class="front-indicator" id="seatmapFront"></div>
                <div id="seat-map"></div>
            </div>
        </div>
    </div>
</div>
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

<script>
    function seatmap(trainNumber, departureStation, arrivalStation) {

        var oldSeatMap = document.getElementById('seat-map');
        oldSeatMap.remove();
        var newMap = document.createElement('div');
        newMap.id = 'seat-map';
        var mapContainer = document.getElementById('mapContainer');
        mapContainer.appendChild(newMap);

        document.getElementById('trainNumberForm').value = trainNumber;
        document.getElementById('departureStationForm').value = departureStation;
        document.getElementById('arrivalStationForm').value = arrivalStation;
        document.getElementById('seatmapFront').innerHTML = 'Seatmap for train #' + trainNumber;

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var responceText = this.response;
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(responceText, "text/xml");
                var swapMap = xmlDoc.getElementsByTagName("row");
                var rowNumber = swapMap.length;
                var swapMapArr = new Array(rowNumber);
                for (var i = 0; i < swapMap.length; i++) {
                    swapMapArr[i] = swapMap[i].innerHTML;
                }
                map(xmlDoc, trainNumber, swapMapArr);
            }
        };
        xhttp.open("GET", "occupiedSeats?trainNumber=" + trainNumber, true);
        xhttp.send();
    }
    function map(xmlDoc, trainNumber, swapMapArr) {
        var i;
        var $cart = $('#selected-seats'),
                $counter = $('#counter'),
                $total = $('#total'),
                sc = $('#seat-map').seatCharts({
                    map: swapMapArr,
                    seats: {
                        f: {
                            price   : 100,
                            classes : 'first-class', //your custom CSS class
                            category: 'First Class'
                        },
                        e: {
                            price   : 40,
                            classes : 'economy-class', //your custom CSS class
                            category: 'Economy Class'
                        }

                    },
                    naming : {
                        top : false,
                        getLabel : function (character, row, column) {
                            var label = row + '_' + column;
                            return label;
                        },
                    },
                    legend : {
                        node : $('#legend'),
                        items : [
                            [ 'f', 'available',   'First Class' ],
                            [ 'e', 'available',   'Economy Class'],
                            [ 'f', 'unavailable', 'Already Booked']
                        ]
                    },
                    click: function () {
                        if (this.status() == 'available') {
                            document.getElementById('selectedSeatForm').value = this.settings.label;
                            var b = document.getElementsByName('seatBuy')[0];
                            b.innerHTML = 'Buy ticket: train number ' + trainNumber + ', seat number ' + this.settings.label;

                            sc.find('selected').each(function () {
                                this.click();
                            });
                            return 'selected';
                        } else if (this.status() == 'selected') {
                            return 'available';
                        } else if (this.status() == 'unavailable') {
                            //seat has been already booked
                            return 'unavailable';
                        } else {
                            return this.style();
                        }
                    }
                });

        //Refreshing seatmap, marking already booked seats

        sc.find('e.unavailable').status('available');

        var x = xmlDoc.getElementsByTagName("unavailable");
        for (i = 0; i <x.length; i++) {
            sc.get([x[i].childNodes[0].nodeValue]).status('unavailable');
        }
    }
</script>

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
