<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/addEditValidation.js"></script>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add.title"/></h1>
                    <form id="pcInfoForm" action="addComputer" method="GET">
                     <input type="hidden" name="add" id="add" value="true" >
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="add.pcName"/></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="<spring:message code="add.pcName"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="add.introD"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<spring:message code="add.introD"/>">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="add.discoD"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="<spring:message code="add.discoD"/>">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="add.company"/></label>
                                <c:choose>
	                                <c:when test="${compList != null}">
	                                <select class="form-control" id="companyId" name="companyId">
	                                	<option selected value="noCompName">--</option>
	                                	<c:forEach var="comp" items="${compList}">
	                                		<option value="${comp.compId}"><c:out value="${comp.name}"/></option>
	                                    </c:forEach>
	                                </select>
	                                </c:when>
	                                <c:otherwise>
	                                	<c:redirect url="addComputer" />
	                                </c:otherwise>
                                </c:choose>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<spring:message code="add.submit"/> class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="add.cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body> 
</html>
