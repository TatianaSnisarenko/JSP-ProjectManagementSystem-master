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
            <c:if test="${developer != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${developer == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${developer != null}">
                                Edit Developer
                            </c:if>
                            <c:if test="${developer == null}">
                                Add New Developer
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${developer != null}">
                        <input type="hidden" name="idDeveloper" value="<c:out value='${developer.idDeveloper}' />"/>

                    </c:if>

                    <fieldset class="form-group">
                        <label>Developer Name</label> <input type="text"
                                                             value="<c:out value='${developer.name}' />"
                                                             class="form-control"
                                                             name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Developer age</label> <input type="number" min="18"
                                                            value="<c:out value='${developer.age}' />"
                                                            class="form-control"
                                                            name="age" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Developer Salary</label> <input type="number" step="any" min="0"
                                                               value="<c:out value='${developer.salary}' />"
                                                               class="form-control"
                                                               name="salary" required="required">
                    </fieldset>


                    <fieldset class="form-group">
                        <label>Developer Sex</label>
                        <br>
                        <c:if test="${developer != null}">
                            <c:set var="developerSex" value="${developer.sex}"/>
                            <c:set var="male" value="male"/>
                            <c:set var="female" value="female"/>

                            <c:if test="${developerSex.equals(male)}">
                                <input type="radio" name="sex" checked
                                       value="male" required="required"/>
                                male <br>
                                <input type="radio" name="sex"
                                       value="female"/>
                                female <br>
                            </c:if>
                            <c:if test="${developerSex.equals(female)}">
                                <input type="radio" name="sex"
                                       value="male" required="required"/>
                                male <br>
                                <input type="radio" name="sex" checked
                                       value="female"/>
                                female <br>
                            </c:if>

                        </c:if>

                        <c:if test="${developer == null}">
                            <input type="radio" name="sex" required="required"
                                   value="male"/>
                            male <br>
                            <input type="radio" name="sex"
                                   value="female"/>
                            female <br>
                        </c:if>


                    </fieldset>

                    <label>Developer Company</label><br>
                    <c:if test="${developer != null}">
                        <c:set var="developerCompany" value="${developer.company}"/>

                        <c:forEach var="company" items="${allCompanies}">
                            <c:if test="${developerCompany.equals(company)}">
                                <input type="radio" name="company" checked required="required"
                                       value="${company.idCompany}"/>
                                ${company.name} <br>
                            </c:if>
                            <c:if test="${!developerCompany.equals(company)}">
                                <input type="radio" name="company" required="required"
                                       value="${company.idCompany}"/>
                                ${company.name} <br>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${developer == null}">
                        <c:forEach var="company" items="${allCompanies}">
                            <input type="radio" name="company" required="required"
                                   value="${company.idCompany}"/>
                            ${company.name} <br>
                        </c:forEach>
                    </c:if>
                    <br>

                    <label>Developer projects</label><br>
                    <c:if test="${developer != null}">
                        <c:set var="developerProjects" value="${developer.projects}"/>
                        <c:forEach var="project" items="${allProjects}">
                            <c:if test="${developerProjects.contains(project)}">
                                <input type="checkbox" name="projects" checked
                                       value="${project.idProject}"/>
                                ${project.name}<br>
                            </c:if>
                            <c:if test="${!developerProjects.contains(project)}">
                                <input type="checkbox" name="projects"
                                       value="${project.idProject}"/>
                                ${project.name}<br>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${developer == null}">
                        <c:forEach var="project" items="${allProjects}">

                            <input type="checkbox" name="projects"
                                   value="${project.idProject}"/>
                            ${project.name}<br>
                        </c:forEach>
                    </c:if>
                    <br>
                    <label>Developer skills</label><br>
                    <c:if test="${developer != null}">
                        <c:set var="developerSkills" value="${developer.skills}"/>
                        <c:forEach var="skill" items="${allSkills}">
                            <c:if test="${developerSkills.contains(skill)}">
                                <input type="checkbox" name="skills" checked
                                       value="${skill.idSkill}"/>
                                ${skill.language} ${skill.level}<br>
                            </c:if>
                            <c:if test="${!developerSkills.contains(skill)}">
                                <input type="checkbox" name="skills"
                                       value="${skill.idSkill}"/>
                                ${skill.language} ${skill.level}<br>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${developer == null}">
                        <c:forEach var="skill" items="${allSkills}">
                            <input type="checkbox" name="skills"
                                   value="${skill.idSkill}"/>
                            ${skill.language} ${skill.level}<br>
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
