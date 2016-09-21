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

            margin-left: 200px;
        }
    </style>
    <link type="text/css" href="Timepicker/bootstrap-timepicker.min.css" />
    <script type="text/javascript" src="Timepicker/bootstrap-timepicker.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="SeatChart/jquery.seat-charts.css">
    <!--link rel="stylesheet" type="text/css" href="SeatChart/SeatChart.style.css"-->
    <link rel="stylesheet" type="text/css" href="SeatChart/style.css">
    <script src="SeatChart/jquery.seat-charts.js"></script>

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
<div class="row">
    <div class="span9">
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

        <c:if test="${not empty message.errorMessage}">
            <div class="alert alert-error">
                <a class="close" data-dismiss="alert" href="#">x</a> ${message.errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty message.successMessage}">
            <div class="alert alert-success">
                <a class="close" data-dismiss="alert" href="#">x</a> ${message.successMessage}
            </div>
        </c:if>

        <c:if test="${not empty bean.offerList}">

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
                            <td><a href="#" >${o.trainNumber}</a></td>
                            <td><a href="#" >${o.departureStation}</a></td>
                            <td><a href="#" >${o.departureTime}</a></td>
                            <td><a href="#" >${o.arrivalStation}</a></td>
                            <td><a href="#" >${o.arrivalTime}</a></td>
                            <td><button type="button" onclick="loadDoc('${o.trainNumber}', '${o.departureStation}', '${o.arrivalStation}')" class="btn btn-default">Select seat</button></td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <form method="post" action="/RailServlet/BuyingTicket">
                <input type="hidden" id="trainNumberForm" name="trainNumber" value="">
                <input type="hidden" id="departureStationForm" name="departureStation" value="">
                <input type="hidden" id="arrivalStationForm" name="arrivalStation" value="">
                <input type="hidden" id="selectedSeatForm" name=selectedSeat value="">
                <button type="submit" class="btn btn-success btn-lg" name="seatBuy">Please choose your seat</button>
            </form>

        </c:if>
     </div>
    <div class="span2">
        <div class="wrapper">
            <div class="container">
                <div id="seat-map">
                    <div class="front-indicator" id="seatmapFront"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<script>
    function loadDoc(trainNumber, departureStation, arrivalStation) {

        document.getElementById('trainNumberForm').value = trainNumber;
        document.getElementById('departureStationForm').value = departureStation;
        document.getElementById('arrivalStationForm').value = arrivalStation;
        document.getElementById('seatmapFront').innerHTML = 'Seatmap for train #' + trainNumber;

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                map(this, trainNumber);
            }
        };
        xhttp.open("GET", "/RailServlet/occupiedSeats?trainNumber=" + trainNumber, true);
        xhttp.send();
    }
    function map(xml, trainNumber) {
        var i;
        var xmlDoc = xml.responseXML;

        var $cart = $('#selected-seats'),
                $counter = $('#counter'),
                $total = $('#total'),
                sc = $('#seat-map').seatCharts({
                    map: [
                        'f_ff',
                        'f_ff',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee',
                        'e_ee'
                    ],
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

        var seats = ["1_1","1_3","1_4","2_1","2_3","2_4","3_1","3_3","3_4","4_1","4_3","4_4",
                     "5_1","5_3","5_4","6_1","6_3","6_4","7_1","7_3","7_4","8_1","8_3","8_4",
                     "9_1","9_3","9_4","10_1","10_3","10_4","11_1","11_3","11_4","12_1","12_3",
                     "12_4","13_1","13_3","13_4","14_1","14_3","14_4","15_1","15_3","15_4"];

        for (i = 0; i <seats.length; i++) {
            sc.get(seats[i]).status('available');
        }

        var x = xmlDoc.getElementsByTagName("seat_number");
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
