<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link href="<c:url value="/resources/Bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/Bootstrap/js/bootstrap.min.js" /> "></script>
    <link href="<c:url value="/resources/Datepicker/css/bootstrap-datepicker.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/Datepicker/js/bootstrap-datepicker.min.js" /> "></script>
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
            <legend>Registration</legend>

            <form method="post" action="newUser" accept-charset="UTF-8" id="form">

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-error">
                        <a class="close" data-dismiss="alert" href="#">x</a> ${errorMessage}
                    </div>
                </c:if>

                <input type="text" name="name" class="span4" placeholder="Name" value="${name}" >
                <input type="text" name="surname" class="span4" placeholder="Surname" value="${surname}">
                <div id="sandbox-container">
                    <input type="text" name="dob" class="span4" placeholder="Date of birth (YYYY-MM-DD)" value="${dob}">
                </div>
                <input type="text" name="email" class="span4" placeholder="E-Mail" value="${email}">
                <input type="password" name="pass1" class="span4" placeholder="Password" value="${pass1}">
                <input type="password" name="pass2" class="span4" placeholder="Password repeat" value="${pass2}">
                <input type="text" name="secret" class="span4" placeholder="Secret phrase" value="${secret}">
                <br><br>
                    <a href="login" >Back to login page</a><br>
                    <a href="forgotPassword" >Forgot your password?</a><br><br>
                <button type="submit" name="submit" class="btn btn-success btn-block">Register</button>
            </form>

        </div>
    </div>
</div>

<script type="text/javascript">
   $('#sandbox-container input').datepicker({
       format: 'yyyy-mm-dd',
       startView: 2
   });
</script>

</body>
</html>

