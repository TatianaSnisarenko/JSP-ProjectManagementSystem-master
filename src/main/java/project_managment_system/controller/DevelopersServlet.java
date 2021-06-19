package project_managment_system.controller;

import project_managment_system.config.DatabaseConnectionManager;
import project_managment_system.dao.entity.CompanyDao;
import project_managment_system.dao.entity.DeveloperDao;
import project_managment_system.dao.entity.ProjectDao;
import project_managment_system.dao.entity.SkillDao;
import project_managment_system.dao.repositories.one_entity_repositories.*;
import project_managment_system.dao.repositories.relations_repositories.DevelopersProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.DevelopersSkillsRepository;
import project_managment_system.dao.repositories.relations_repositories.RelationsRepository;
import project_managment_system.dto.DeveloperTo;
import project_managment_system.service.services.DeveloperService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/developers/*")
public class DevelopersServlet extends HttpServlet {
    private Repository<DeveloperDao> developerRepository;
    private Repository<ProjectDao> projectRepository;
    private Repository<CompanyDao> companyRepository;
    private SkillRepository skillRepository;
    private RelationsRepository developersProjectsRepository;
    private RelationsRepository developersSkillRepository;
    private DeveloperService developerService;

    @Override
    public void init() throws ServletException {
        this.developerRepository = new DeveloperRepository(DatabaseConnectionManager.getDataSource());
        this.projectRepository = new ProjectRepository(DatabaseConnectionManager.getDataSource());
        this.companyRepository = new CompanyRepository(DatabaseConnectionManager.getDataSource());
        this.skillRepository = new SkillRepository(DatabaseConnectionManager.getDataSource());
        this.developersProjectsRepository = new DevelopersProjectsRepository(DatabaseConnectionManager.getDataSource());
        this.developersSkillRepository = new DevelopersSkillsRepository(DatabaseConnectionManager.getDataSource());
        this.developerService = new DeveloperService(developerRepository, projectRepository, companyRepository, skillRepository,
                developersProjectsRepository, developersSkillRepository);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getPathInfo();
        if(action == null){
            action = req.getServletPath();
        }

        try {
            switch (action) {
                case "/new":
                    showNewDeveloperForm(req, resp);
                    break;
                case "/insert":
                    insertDeveloper(req, resp);
                    break;
                case "/delete":
                    deleteDeveloper(req, resp);
                    break;
                case "/edit":
                    showEditFromForDeveloper(req, resp);
                    break;
                case "/update":
                    try {
                        updateDeveloper(req, resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    listDeveloper(req, resp);
                    break;
            }
        }catch (IOException ex){
            throw new ServletException(ex);
        }
    }

    private void updateDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("idDeveloper"));
        DeveloperTo developer = developerService.findById(id);
        setAllDataForDeveloperFromJSP(req, developer);
        developerService.update(developer);
        resp.sendRedirect("/developers");
    }

    private void setAllDataForDeveloperFromJSP(HttpServletRequest req, DeveloperTo developer) {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String sex = req.getParameter("sex");
        Double salary = Double.parseDouble(req.getParameter("salary"));
        int idCompany = Integer.parseInt(req.getParameter("company"));
        CompanyDao company = companyRepository.findById(idCompany);
        String[] projectsIds = req.getParameterValues("projects");
        List<ProjectDao> projects = Arrays.stream(projectsIds).mapToInt(Integer::parseInt).mapToObj(i -> projectRepository.findById(i))
                .collect(Collectors.toList());
        String[] skillsIds = req.getParameterValues("skills");
        List<SkillDao> skills = Arrays.stream(skillsIds).mapToInt(Integer::parseInt).mapToObj(i -> skillRepository.findById(i))
                .collect(Collectors.toList());
        developer.setName(name);
        developer.setAge(age);
        developer.setSex(sex);
        developer.setSalary(salary);
        developer.setProjects(projects);
        developer.setCompany(company);
        developer.setSkills(skills);
    }

    private void deleteDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("idDeveloper"));
        try {
            developerService.deleteById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        resp.sendRedirect("/developers");
    }

    private void insertDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DeveloperTo developer = new DeveloperTo();
        setAllDataForDeveloperFromJSP(req, developer);
        developerService.create(developer);
        resp.sendRedirect("/developers");
    }

    private void showEditFromForDeveloper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idDeveloper"));
        DeveloperTo developer = developerService.findById(id);
        req.setAttribute("developer", developer);
        setNeededAttributes(req);
        req.getRequestDispatcher("/view/developer-form.jsp").forward(req, resp);
    }

    private void setNeededAttributes(HttpServletRequest req) {
        List<ProjectDao> allProjects = projectRepository.findAll();
        req.setAttribute("allProjects", allProjects);
        List<CompanyDao> allCompanies = companyRepository.findAll();
        req.setAttribute("allCompanies", allCompanies);
        List<SkillDao> allSkills = skillRepository.allSkillsList();
        req.setAttribute("allSkills", allSkills);
    }

    private void showNewDeveloperForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setNeededAttributes(req);
        req.getRequestDispatcher("/view/developer-form.jsp"). forward(req, resp);
    }

    private void listDeveloper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DeveloperTo> developers = developerService.findAll();
        req.setAttribute("developers", developers);
        req.getRequestDispatcher("/view/developers.jsp").forward(req, resp);
    }
}

