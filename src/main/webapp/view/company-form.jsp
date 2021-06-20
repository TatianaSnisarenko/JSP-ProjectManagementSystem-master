<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Management System</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="<%=request.getContextPath()%>/" class="navbar-brand"> Managment System </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="/companies"
                   class="nav-link">Companies</a></li>
        </ul>
        <ul class="navbar-nav">
            <li><a href="/customers"
                   class="nav-link">Customers</a></li>
        </ul>
        <ul class="navbar-nav">
            <li><a href="/developers"
                   class="nav-link">Developers</a></li>
        </ul>
        <ul class="navbar-nav">
            <li><a href="/projects"
                   class="nav-link">Projects</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${company != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${company == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${company != null}">
                                Edit Company
                            </c:if>
                            <c:if test="${company == null}">
                                Add New Company
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${company != null}">
                        <input type="hidden" name="idCompany" value="<c:out value='${company.idCompany}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>Company Name</label> <input type="text"
                                                           value="<c:out value='${company.name}' />"
                                                           class="form-control"
                                                           name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Company city</label> <input type="text"
                                                           value="<c:out value='${company.city}' />"
                                                           class="form-control"
                                                           name="city" required="required">
                    </fieldset>
                    <label>Company projects</label><br>
                    <c:if test="${company != null}">
                        <c:set var="companyProjects" value="${company.projects}"/>
                        <c:forEach var="project" items="${allProjects}">
                            <c:if test="${companyProjects.contains(project)}">
                                <input type="checkbox" name="projects" checked
                                       value="${project.idProject}"/>
                                ${project.name}<br>
                            </c:if>
                            <c:if test="${!companyProjects.contains(project)}">
                                <input type="checkbox" name="projects"
                                       value="${project.idProject}"/>
                                ${project.name}<br>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${company == null}">
                        <c:forEach var="project" items="${allProjects}">
                            <input type="checkbox" name="projects"
                                   value="${project.idProject}"/>
                            ${project.name}<br>
                        </c:forEach>
                    </c:if>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>
