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

<div class="row">
    <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

    <div class="container">
        <h3 class="text-center">List of Developers</h3>
        <hr>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/developers/new" class="btn btn-success">Add
                New Developer</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>Sex</th>
                <th>Company</th>
                <th>Salary</th>
                <th>Skills</th>
                <th>Projects</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <!--   for (Todo todo: todos) {  -->
            <c:forEach var="developer" items="${developers}">
                <tr>
                    <td><c:out value="${developer.idDeveloper}" /></td>
                    <td><c:out value="${developer.name}" /></td>
                    <td><c:out value="${developer.age}" /></td>
                    <td><c:out value="${developer.sex}" /></td>
                    <td><c:out value="${developer.company}" /></td>
                    <td><c:out value="${developer.salary}" /></td>
                    <td><c:out value="${developer.skills}" /></td>
                    <td><c:out value="${developer.projects}" /></td>
                    <td><a href="<%=request.getContextPath()%>/developers/edit?idDeveloper=<c:out value='${developer.idDeveloper}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="<%=request.getContextPath()%>/developers/delete?idDeveloper=<c:out value='${developer.idDeveloper}' />">Delete</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>

        </table>
    </div>
</div>
</body>
</html>
