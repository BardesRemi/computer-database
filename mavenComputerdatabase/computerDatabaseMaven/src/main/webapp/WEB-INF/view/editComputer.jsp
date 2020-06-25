<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <a class="navbar-brand" href="DashboardServlet"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${pc.pcId}" />
                    </div>
                    <h1>Edit Computer</h1>

                    <form id="pcInfoForm" action="EditComputerServlet" method="POST">
                        <input type="hidden" name="pcId" value="${pc.pcId}" id="pcId"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" value="${pc.name}" placeholder="Computer name" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" value="${pc.introduced}" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" value="${pc.discontinued}" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <c:choose>
	                                <c:when test="${compList != null}">
	                                <select class="form-control" id="companyId" name="companyId">
	                                	<c:if test="${pc.company == null}">
	                                		<option selected value="noCompName">--</option>
	                                	</c:if>
	                                	<c:forEach var="comp" items="${compList}">
	                                		<c:if test="${not empty pc.company && comp.compId == pc.company.compId}">
	                                    		<option selected value="${comp.compId}"><c:out value="${comp.name}"/></option>
	                                    	</c:if>
	                                    	<c:if test="${comp.compId != pc.company.compId}">
	                                    		<option value="${comp.compId}"><c:out value="${comp.name}"/></option>
	                                    	</c:if>
	                                    </c:forEach>
	                                </select>
	                                </c:when>
	                                <c:otherwise>
	                                	<c:redirect url="EditComputerServlet" />
	                                </c:otherwise>
                                </c:choose>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="DashboardServlet" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>