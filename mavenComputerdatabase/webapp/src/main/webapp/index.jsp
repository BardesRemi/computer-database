<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Welcoming Hub</title>
	</head>
	<body>
		<h2><spring:message code="login.title"/></h2>
		<form method="get" action="dashboard">
			<label for="username"><spring:message code="login.username"/></label>
			<input type="text" class="form-control" id="username" name="username" placeholder="<spring:message code="login.username"/>" required>
			
			<label for="password"><spring:message code="login.password"/></label>
			<input type="text" class="form-control" id="password" name="password" placeholder="<spring:message code="password"/>" required>              
		
			<input type="submit" class="form-control" id="confirmation" name="confirmation" value="<spring:message code="login.validate"/>">
		</form>
	</body>
</html>
