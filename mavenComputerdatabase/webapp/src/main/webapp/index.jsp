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
		<div ><spring:message code="index.decisionMsg"/></div>
		<form method="get" action="login">
			<button type="submit"><spring:message code="index.loginButton"/></button>
		</form>
		<form method="get" action="dashboard">
			<button type="submit"><spring:message code="index.dashboardButton"/></button>
		</form>
    </body>
</html>
