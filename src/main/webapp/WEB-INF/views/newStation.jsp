<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>New Station</title>
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
        <div class="span3">
            <table class="table table-stripped">
                <thead>
                    <tr>
                        <th>Station name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${stationList}" var="s">
                    <tr>
                        <td><a href="#" >${s}</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="span4">
            <form method="post" name="form1" action="/railroad/admin/addStation">
                <input type="text" name="stationName" placeholder="New station name">
                <button type="submit" class="btn btn-default">Add new station</button>
            </form>
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

</body>
</html>