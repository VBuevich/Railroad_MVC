<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.AdminBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>New Train</title>
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
            <li><a href="RailServlet/dispatcher?page=/newStation.jsp">New Station</a></li>
            <li><a href="RailServlet/dispatcher?page=/newTrain.jsp">New Train</a></li>
            <li><a href="RailServlet/dispatcher?page=/newSchedule.jsp">New Schedule</a></li>
            <li><a href="RailServlet/dispatcher?page=/passengerList.jsp">List of passengers</a></li>
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

<div class="wrap">
    <table class="table table-stripped">
        <thead>
        <tr>
            <th>Train list</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bean.trainList}" var="t">
            <tr>
                <td><a href="#">${t}</a></td>
            </tr>
        </c:forEach>
        <form method="post" name="form1" action="RailServlet/addTrain">
            <tr>
                <td><input type="text" name="trainNumber"></td>
                <td><button type="submit" class="btn btn-default">Add new train</button></td>
            </tr>
        </form>
        </tbody>
    </table>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="Bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
