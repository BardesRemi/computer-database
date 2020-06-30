<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
            <c:out value="${pcCount}" /> <spring:message code="dashboard.pcFound"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="dashboard" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" value="${search}" placeholder=<spring:message code="dashboard.search"/> />
                        <input type="submit" id="searchsubmit" value=<spring:message code="dashboard.filter"/> class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="dashboard.add"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="deleteComputer" method="GET">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
						<c:set var="currPage" value="${page.actualPageNb}" />
       					<c:set var="pageSize" value="${page.pageSize}" />
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <a href=<c:url value="dashboard">
                  				<c:param name="currPage" value="${currPage}"/>
                  				<c:param name="pageSize" value="${pageSize}"/>
                  				<c:param name="sort" value="pcUp"/>
                  			</c:url>><spring:message code="dashboard.pcName"/></a>
                        </th>
                        <th>
                            <spring:message code="dashboard.introD"/>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="dashboard.discoD"/>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <a href=<c:url value="dashboard">
                  				<c:param name="currPage" value="${currPage}"/>
                  				<c:param name="pageSize" value="${pageSize}"/>
                  				<c:param name="sort" value="companyUp"/>
                  			</c:url>><spring:message code="dashboard.company"/></a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:choose>
                	<c:when test="${not empty pcList}" >
                		<c:forEach var="pc" items="${pcList}">
                			<tr>
                        		<td class="editMode">
                            		<input type="checkbox" name="cb" class="cb" value="${pc.pcId}">
                        		</td>
                       			<td>
                            		<a href=<c:url value="editComputer">
                  								<c:param name="pcId" value="${pc.pcId}"/>
                  								<c:param name="needEdit" value="false"/>
                  							</c:url> onclick="">
                  							<c:out value="${pc.name}"/></a>
                        		</td>
                        		<td><c:out value="${pc.introduced}"/></td>
                        		<td><c:out value="${pc.discontinued}"/></td>
                        		<c:set var="company" value="${pc.company}" />
                        		<c:if test="${company != null}">
                        			<td><c:out value="${company.name}"/></td>
                        		</c:if>
                        		<c:if test="${company == null}">
                        			<td>no company</td>
                        		</c:if>
                    		</tr>
                    	</c:forEach>
                    </c:when>
                    <c:when test="${ pcList == null}">
                    	<!-- request data "pcList" wasn't initialized -->
                    	<c:redirect url="dashboard" />
                    </c:when>
                    <c:otherwise>
                    	<b>No computer found</b>
                    </c:otherwise>
				</c:choose>
               </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
    	<c:set var="currPage" value="${page.actualPageNb}" />
        <c:set var="pageSize" value="${page.pageSize}" />
        <c:set var="maxPages" value="${page.maxPages}"/>
        <div class="container text-center">
            <ul class="pagination">
            <li>
               <a href=
                 <c:url value="dashboard">
                  	<c:param name="currPage" value="0"/>
                  	<c:param name="pageSize" value="${pageSize}"/>
                  	<c:param name="sort" value="${sort}"/>
                 </c:url> 
                    aria-label="Previous">
                 <span aria-hidden="true">&laquo;</span>
               </a>
            </li>
            <c:if test="${currPage > 1}">
				<li>
            		<a href=
                  		<c:url value="dashboard">
                  			<c:param name="currPage" value="${currPage - 2}"/>
                  			<c:param name="pageSize" value="${pageSize}"/>
                  			<c:param name="sort" value="${sort}"/>
                  		</c:url>>
                  		<c:out value="${currPage - 2}"/>
                  	</a>
				</li>
            </c:if>
            <c:if test="${currPage > 0}">
				<li>
            		<a href=
                  		<c:url value="dashboard">
                  			<c:param name="currPage" value="${currPage - 1}"/>
                  			<c:param name="pageSize" value="${pageSize}"/>
                  			<c:param name="sort" value="${sort}"/>
                  		</c:url>>
                  		<c:out value="${currPage - 1}"/>
                  	</a>
				</li>
            </c:if>
            <li>
            	<a href=
                  	<c:url value="dashboard">
                  		<c:param name="currPage" value="${currPage}"/>
                  		<c:param name="pageSize" value="${pageSize}"/>
                  		<c:param name="sort" value="${sort}"/>
                  	</c:url>>
                  	<c:out value="${currPage}"/>
                </a>
			</li>
            <c:if test="${currPage < maxPages}">
				<li>
            		<a href=
                  		<c:url value="dashboard">
                  			<c:param name="currPage" value="${currPage + 1}"/>
                  			<c:param name="pageSize" value="${pageSize}"/>
                  			<c:param name="sort" value="${sort}"/>
                  		</c:url>>
                  		<c:out value="${currPage + 1}"/>
                  	</a>
				</li>
            </c:if>
            <c:if test="${currPage < maxPages - 1}">
				<li>
            		<a href=
                  		<c:url value="dashboard">
                  			<c:param name="currPage" value="${currPage + 2}"/>
                  			<c:param name="pageSize" value="${pageSize}"/>
                  			<c:param name="sort" value="${sort}"/>
                  		</c:url>>
                  		<c:out value="${currPage + 2}"/>
                  	</a>
				</li>
            </c:if>
            <li>
            	<a href=
                  	<c:url value="dashboard">
                  		<c:param name="currPage" value="${maxPages}"/>
                  		<c:param name="pageSize" value="${pageSize}"/>
                  		<c:param name="sort" value="${sort}"/>
                  	</c:url> 
                    	aria-label="Next">
            		<span aria-hidden="true">&raquo;</span>
            	</a>
           	</li>
        </ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
        	<form method="post" action= <c:url value="dashboard">
        				<c:param name="currPage" value="${(pageSize/10)*currPage}"/>
                  		<c:param name="pageSize" value="10"/>
                  		<c:param name="sort" value="${sort}"/>
        				</c:url>>
        		<button type="submit" class="btn btn-default" >10</button>
            	<button type="submit" class="btn btn-default" formaction=<c:url value="dashboard">
        				<c:param name="currPage" value="${(pageSize/50)*currPage}"/>
                  		<c:param name="pageSize" value="50"/>
                  		<c:param name="sort" value="${sort}"/>
        				</c:url>>50</button>
            	<button type="submit" class="btn btn-default" formaction=<c:url value="dashboard">
        				<c:param name="currPage" value="${(pageSize/100)*currPage}"/>
                  		<c:param name="pageSize" value="100"/>
                  		<c:param name="sort" value="${sort}"/>
        				</c:url>>100</button>
        	</form>
            
      </div> 
      </div>
    </footer>
<script src="./js/jquery.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/dashboard.js"></script>

</body>
</html>