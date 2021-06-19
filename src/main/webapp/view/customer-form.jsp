<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <c:if test="${customer != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${customer == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${customer != null}">
                                Edit Customer
                            </c:if>
                            <c:if test="${customer == null}">
                                Add New Customer
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${customer != null}">
                        <input type="hidden" name="idCustomer" value="<c:out value='${customer.idCustomer}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Customer Name</label> <input type="text"
                                                           value="<c:out value='${customer.name}' />" class="form-control"
                                                           name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Customer city</label> <input type="text"
                                                           value="<c:out value='${customer.city}' />" class="form-control"
                                                           name="city">
                    </fieldset>

                        <label>Customer projects</label><br>
                        <c:if test="${customer != null}">
                            <c:set var="customerProjects" value="${customer.projects}"/>
                            <c:forEach var="project" items="${allProjects}">
                                <c:if test="${customerProjects.contains(project)}">
                                    <input type="checkbox" name="projects" checked
                                           value="${project.idProject}"/>
                                    ${project.name}<br>
                                </c:if>
                                <c:if test="${!customerProjects.contains(project)}">
                                    <input type="checkbox" name="projects"
                                           value="${project.idProject}"/>
                                    ${project.name}<br>
                                </c:if>
                            </c:forEach>
                        </c:if>

                        <c:if test="${customer == null}">
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
