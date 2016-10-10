<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.AdminBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>New Train</title>
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
        <a class="brand">Railroad Admin</a>

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
<form method="post" name="form1" action="getPassengerList">

    <p>Train number</p>
    <p><select size="1" width="200px" name="trainNumber">
        <option disabled>Train number</option>
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
    </select></p>

    <button type="submit" class="btn btn-default">Get list of passengers</button>
</form>
</div>

<div class="wrap">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert" href="#">x</a> ${errorMessage}
        </div>
    </c:if>
</div>

<c:if test="${not empty passengerList}">
    <div class="wrap">
        <table class="table table-stripped">
            <thead>
            <tr>
                <th>List of passengers</th>
            </tr>
            <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Date of birth</th>
                <th>Seat</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${passengerList}" var="p">
                <tr>
                    <td><a href="#" >${p.name}</a></td>
                    <td><a href="#" >${p.surname}</a></td>
                    <td><a href="#" >${p.dob}</a></td>
                    <td><a href="#" >${p.seat}</a></td>
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
        <a href="logoff" class="btn btn-primary" aria-hidden="true">Yes, log off</a>
    </div>
</div>

</body>
</html>
