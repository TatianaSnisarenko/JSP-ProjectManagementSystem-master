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
            <c:if test="${project != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${project == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${project != null}">
                                Edit Project
                            </c:if>
                            <c:if test="${project == null}">
                                Add New Project
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${project != null}">
                        <input type="hidden" name="idProject" value="<c:out value='${project.idProject}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>project Name</label> <input type="text"
                                                           value="<c:out value='${project.name}' />"
                                                           class="form-control"
                                                           name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Project description</label> <input type="text"
                                                                  value="<c:out value='${project.description}' />"
                                                                  class="form-control"
                                                                  name="description" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Project cost</label> <input type="number" step="any" min="0"
                                                           value="<c:out value='${project.cost}' />"
                                                           class="form-control"
                                                           name="cost" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Project date</label> <input type="date"
                                                           value="<c:out value='${project.date}' />"
                                                           class="form-control"
                                                           name="date" required="required">
                    </fieldset>

                    <label>Project developers</label><br>
                    <c:if test="${project != null}">
                        <c:set var="projectDevelopers" value="${project.developers}"/>
                        <c:forEach var="developer" items="${allDevelopers}">
                            <c:if test="${projectDevelopers.contains(developer)}">
                                <input type="checkbox" name="developers" checked
                                       value="${developer.idDeveloper}"/>
                                ${developer.name}<br>
                            </c:if>
                            <c:if test="${!projectDevelopers.contains(developer)}">
                                <input type="checkbox" name="developers"
                                       value="${developer.idDeveloper}"/>
                                ${developer.name}<br>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${project == null}">
                        <c:forEach var="developer" items="${allDevelopers}">
                            <input type="checkbox" name="developers"
                                   value="${developer.idDeveloper}"/>
                            ${developer.name}<br>
                        </c:forEach>
                    </c:if>
                    <br>

                    <label>Project companies</label><br>
                    <c:if test="${project != null}">
                        <c:set var="projectCompanies" value="${project.companies}"/>
                        <c:forEach var="company" items="${allCompanies}">
                            <c:if test="${projectCompanies.contains(company)}">
                                <input type="checkbox" name="companies" checked
                                       value="${company.idCompany}"/>
                                ${company.name}<br>
                            </c:if>
                            <c:if test="${!projectCompanies.contains(company)}">
                                <input type="checkbox" name="companies"
                                       value="${company.idCompany}"/>
                                ${company.name}<br>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${project == null}">
                        <c:forEach var="company" items="${allCompanies}">
                            <input type="checkbox" name="companies"
                                   value="${company.idCompany}"/>
                            ${company.name}<br>
                        </c:forEach>
                    </c:if>
                    <br>

                        <label>Project customers</label><br>
                        <c:if test="${project != null}">
                            <c:set var="projectCustomers" value="${project.customers}"/>
                            <c:forEach var="customer" items="${allCustomers}">
                                <c:if test="${projectCustomers.contains(customer)}">
                                    <input type="checkbox" name="customers" checked
                                           value="${customer.idCustomer}"/>
                                    ${customer.name}<br>
                                </c:if>
                                <c:if test="${!projectCustomers.contains(customer)}">
                                    <input type="checkbox" name="customers"
                                           value="${customer.idCustomer}"/>
                                    ${customer.name}<br>
                                </c:if>
                            </c:forEach>
                        </c:if>

                        <c:if test="${project == null}">
                            <c:forEach var="customer" items="${allCustomers}">
                                <input type="checkbox" name="customers"
                                       value="${customer.idCustomer}"/>
                                ${customer.name}<br>
                            </c:forEach>
                        </c:if>
                        <br>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>
