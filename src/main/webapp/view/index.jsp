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
			<h3 class="text-center">Mamagment System</h3>
			<hr>
			<div class="container text-center">
				<a href="/companies" class="btn btn-success">Show companies</a>
				<a href="/customers" class="btn btn-success">Show customers</a>
                <a href="/developers" class="btn btn-success">Show developers</a>
                <a href="/projects" class="btn btn-success">Show projects</a>
			</div>
		</div>
	</div>
</body>
</html>
