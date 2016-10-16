<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/resources/Bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
    <style>
        .well {
            margin-top: 100px;
        }
    </style>
</head>

<body>


<div class="container">
    <div class="row">
        <div class="well span4 offset4">
            <legend>Authorisation</legend>

            <form method="post" action="/railroad/login" accept-charset="UTF-8">

                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                    <div class="alert alert-error">
                        <a class="close" data-dismiss="alert" href="#">x</a>
                            Your login attempt was not successful due to <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                    </div>
                </c:if>
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
                    <input type="text" name="email" class="span4" placeholder="E-Mail">
                    <input type="password" name="pass" class="span4" placeholder="Password">
                <br>
                    <a href="forgotPassword" >Forgot your password?</a><br>
                    <a href="newCustomer" >New customer?</a><br><br>
                <button type="submit" name="submit" class="btn btn-success btn-block">Log in</button>
            </form>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
</body>
</html>

