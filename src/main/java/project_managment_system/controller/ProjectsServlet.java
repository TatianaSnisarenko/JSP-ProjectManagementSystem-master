package project_managment_system.controller;

import project_managment_system.config.DatabaseConnectionManager;
import project_managment_system.dao.entity.*;
import project_managment_system.dao.repositories.one_entity_repositories.*;
import project_managment_system.dao.repositories.relations_repositories.CompaniesProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.CustomersProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.DevelopersProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.RelationsRepository;
import project_managment_system.dto.ProjectTo;
import project_managment_system.service.services.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/projects/*")
public class ProjectsServlet extends HttpServlet {
    private Repository<ProjectDao> projectRepository;
    private Repository<DeveloperDao> developerRepository;
    private Repository<CompanyDao> companyRepository;
    private Repository<CustomerDao> customerRepository;
    private RelationsRepository developersProjectsRepository;
    private RelationsRepository companiesProjectsRepository;
    private RelationsRepository customersProjectsRepository;
    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        this.projectRepository = new ProjectRepository(DatabaseConnectionManager.getDataSource());
        this.developerRepository = new DeveloperRepository(DatabaseConnectionManager.getDataSource());
        this.companyRepository = new CompanyRepository(DatabaseConnectionManager.getDataSource());
        this.customerRepository = new CustomerRepository(DatabaseConnectionManager.getDataSource());
        this.developersProjectsRepository = new DevelopersProjectsRepository(DatabaseConnectionManager.getDataSource());
        this.companiesProjectsRepository = new CompaniesProjectsRepository(DatabaseConnectionManager.getDataSource());
        this.customersProjectsRepository = new CustomersProjectsRepository(DatabaseConnectionManager.getDataSource());
        this.projectService = new ProjectService(projectRepository, developerRepository, companyRepository, customerRepository,
                developersProjectsRepository, companiesProjectsRepository, customersProjectsRepository);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) {
            action = req.getServletPath();
        }

        try {
            switch (action) {
                case "/new":
                    showNewProjectForm(req, resp);
                    break;
                case "/insert":
                    insertProject(req, resp);
                    break;
                case "/delete":
                    deleteProject(req, resp);
                    break;
                case "/edit":
                    showEditFromForProject(req, resp);
                    break;
                case "/update":
                    try {
                        updateProject(req, resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    listProject(req, resp);
                    break;
            }
        } catch (IOException ex) {
            throw new ServletException(ex);
        }
    }

    private void updateProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("idProject"));
        ProjectTo project = projectService.findById(id);
        setAllDataForProjectFromJSP(req, project);
        projectService.update(project);
        resp.sendRedirect("/projects");
    }

    private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("idProject"));
        try {
            projectService.deletedById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        resp.sendRedirect("/projects");
    }

    private void insertProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProjectTo project = new ProjectTo();
        setAllDataForProjectFromJSP(req, project);

        projectService.create(project);
        resp.sendRedirect("/projects");
    }

    private void setAllDataForProjectFromJSP(HttpServletRequest req, ProjectTo project) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double cost = Double.parseDouble(req.getParameter("cost"));
        String choosenDate = req.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(choosenDate, formatter);
        List<DeveloperDao> developers = new ArrayList<>();
        String[] developerIds = req.getParameterValues("developers");
        if (developerIds != null && developerIds.length > 0) {
            developers = Arrays.stream(developerIds).mapToInt(Integer::parseInt)
                    .mapToObj(i -> developerRepository.findById(i))
                    .collect(Collectors.toList());
        }
        List<CompanyDao> companies = new ArrayList<>();
        String[] companyIds = req.getParameterValues("companies");
        if (companyIds != null && companyIds.length > 0) {
            companies = Arrays.stream(companyIds).mapToInt(Integer::parseInt)
                    .mapToObj(i -> companyRepository.findById(i))
                    .collect(Collectors.toList());
        }
        List<CustomerDao> customers = new ArrayList<>();
        String[] customerIds = req.getParameterValues("customers");
        if (customerIds != null && customerIds.length > 0) {
            customers = Arrays.stream(customerIds).mapToInt(Integer::parseInt)
                    .mapToObj(i -> customerRepository.findById(i))
                    .collect(Collectors.toList());
        }
        project.setName(name);
        project.setDescription(description);
        project.setDate(date);
        project.setCost(cost);
        project.setDevelopers(developers);
        project.setCompanies(companies);
        project.setCustomers(customers);
    }

    private void showEditFromForProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idProject"));
        ProjectTo project = projectService.findById(id);
        req.setAttribute("project", project);
        setNeededAttributes(req);
        req.getRequestDispatcher("/view/project-form.jsp").forward(req, resp);
    }

    private void setNeededAttributes(HttpServletRequest req) {
        List<DeveloperDao> allDevelopers = developerRepository.findAll();
        req.setAttribute("allDevelopers", allDevelopers);
        List<CompanyDao> allCompanies = companyRepository.findAll();
        req.setAttribute("allCompanies", allCompanies);
        List<CustomerDao> allCustomers = customerRepository.findAll();
        req.setAttribute("allCustomers", allCustomers);
    }

    private void showNewProjectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setNeededAttributes(req);
        req.getRequestDispatcher("/view/project-form.jsp").forward(req, resp);
    }

    private void listProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectTo> projects = projectService.findAll();
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/view/projects.jsp").forward(req, resp);
    }
}