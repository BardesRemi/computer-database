<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login Hub</title>
	</head>
	<body>
		<c:if test="${error}">
		<div ><spring:message code="login.errorMsg"/></div>
		</c:if>
		<c:if test="${logout}">
		<div ><spring:message code="login.logoutMsg"/></div>
		</c:if>
        <form action="loginAttempt" method="get">
            <div><label> <spring:message code="login.username"/> <input type="text" name="username"/> </label></div>
            <div><label> <spring:message code="login.password"/> <input type="password" name="password"/> </label></div>
            <div><input type="submit" value="Sign In"/></div>
        </form>
    </body>
</html>