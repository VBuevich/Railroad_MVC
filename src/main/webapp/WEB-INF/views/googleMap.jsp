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

<div id="map" style="width:100%;height:500px"></div>

<script>
    function myMap() {
        var mapCanvas = document.getElementById("map");
        var mapOptions = {
            center: {lat: 57.7, lng: 39.3},
            zoom: 6
        }
        var map = new google.maps.Map(mapCanvas, mapOptions);

        var markerA = new google.maps.Marker({
            position: {lat: 59.9, lng: 30.3},
            map: map
        });
        var markerB = new google.maps.Marker({
            position: {lat: 58.8, lng: 32.2},
            map: map
        });
        var markerC = new google.maps.Marker({
            position: {lat: 58.3, lng: 33.3},
            map: map
        });

        var markerD = new google.maps.Marker({
            position: {lat: 57.9, lng: 34.1},
            map: map
        });
        var markerE = new google.maps.Marker({
            position: {lat: 56.9, lng: 35.9},
            map: map
        });
        var markerF = new google.maps.Marker({
            position: {lat: 55.7, lng: 37.6},
            map: map
        });
        var markerG = new google.maps.Marker({
            position: {lat: 55.8, lng: 39.0},
            map: map
        });
        var markerH = new google.maps.Marker({
            position: {lat: 56.2, lng: 40.4},
            map: map
        });
        var markerI = new google.maps.Marker({
            position: {lat: 56.2, lng: 43.4},
            map: map
        });
        var markerJ = new google.maps.Marker({
            position: {lat: 56.3, lng: 43.9},
            map: map
        });
        var markerX = new google.maps.Marker({
            position: {lat: 55.8, lng: 49.1},
            map: map
        });

        var pathCoordinates = [
            {lat: 59.9, lng: 30.3},
            {lat: 58.8, lng: 32.2},
            {lat: 58.3, lng: 33.3},
            {lat: 57.9, lng: 34.1},
            {lat: 56.9, lng: 35.9},
            {lat: 55.7, lng: 37.6},
            {lat: 55.8, lng: 39.0},
            {lat: 56.2, lng: 40.4},
            {lat: 56.2, lng: 43.4},
            {lat: 56.3, lng: 43.9},
            {lat: 55.8, lng: 49.1}
        ];
        var railPath = new google.maps.Polyline({
            path: pathCoordinates,
            geodesic: true,
            strokeColor: '#007F00',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });

        railPath.setMap(map);

        var infowindowA = new google.maps.InfoWindow({
            content: 'Saint Petersburg'
        });
        var infowindowB = new google.maps.InfoWindow({
            content: 'Malaya Vishera'
        });
        var infowindowC = new google.maps.InfoWindow({
            content: 'Okulovka'
        });
        var infowindowD = new google.maps.InfoWindow({
            content: 'Bologoe Moskovskoe'
        });
        var infowindowE = new google.maps.InfoWindow({
            content: 'Tver'
        });
        var infowindowF = new google.maps.InfoWindow({
            content: 'Moscow'
        });
        var infowindowG = new google.maps.InfoWindow({
            content: 'Orekhovo Zuevo'
        });
        var infowindowH = new google.maps.InfoWindow({
            content: 'Vladimir'
        });
        var infowindowI = new google.maps.InfoWindow({
            content: 'Dzerzhinsk'
        });
        var infowindowJ = new google.maps.InfoWindow({
            content: 'Nizhny Novgorod'
        });
        var infowindowX = new google.maps.InfoWindow({
            content: 'Kazan'
        });

        google.maps.event.addListener(markerA, 'click', function() {
            infowindowA.open(map, markerA);
        });
        google.maps.event.addListener(markerB, 'click', function() {
            infowindowB.open(map, markerB);
        });
        google.maps.event.addListener(markerC, 'click', function() {
            infowindowC.open(map, markerC);
        });
        google.maps.event.addListener(markerD, 'click', function() {
            infowindowD.open(map, markerD);
        });
        google.maps.event.addListener(markerE, 'click', function() {
            infowindowE.open(map, markerE);
        });
        google.maps.event.addListener(markerF, 'click', function() {
            infowindowF.open(map, markerF);
        });
        google.maps.event.addListener(markerG, 'click', function() {
            infowindowG.open(map, markerG);
        });
        google.maps.event.addListener(markerH, 'click', function() {
            infowindowH.open(map, markerH);
        });
        google.maps.event.addListener(markerI, 'click', function() {
            infowindowI.open(map, markerI);
        });
        google.maps.event.addListener(markerJ, 'click', function() {
            infowindowJ.open(map, markerJ);
        });
        google.maps.event.addListener(markerX, 'click', function() {
            infowindowX.open(map, markerX);
        });


    }
</script>

<script src="<c:url value="https://maps.googleapis.com/maps/api/js?key=AIzaSyA2Xm2UqQ_AYLg85vP5Y30_yJZyoa8Yqi4&callback=myMap" />"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
</body>
</html>