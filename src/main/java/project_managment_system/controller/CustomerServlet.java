package project_managment_system.controller;

import project_managment_system.config.DatabaseConnectionManager;
import project_managment_system.dao.entity.CustomerDao;
import project_managment_system.dao.entity.ProjectDao;
import project_managment_system.dao.repositories.one_entity_repositories.CustomerRepository;
import project_managment_system.dao.repositories.one_entity_repositories.ProjectRepository;
import project_managment_system.dao.repositories.one_entity_repositories.Repository;
import project_managment_system.dao.repositories.relations_repositories.CustomersProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.RelationsRepository;
import project_managment_system.dto.CustomerTo;
import project_managment_system.service.services.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/customers/*")
public class CustomerServlet extends HttpServlet {
    private Repository<CustomerDao> customerRepository;
    private Repository<ProjectDao> projectRepository;
    private RelationsRepository customersProjectsRepository;
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        this.customerRepository = new CustomerRepository(DatabaseConnectionManager.getDataSource());
        this.projectRepository = new ProjectRepository(DatabaseConnectionManager.getDataSource());
        this.customersProjectsRepository = new CustomersProjectsRepository(DatabaseConnectionManager.getDataSource());
        this.customerService= new CustomerService(customerRepository, projectRepository, customersProjectsRepository);
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
                    showNewCustomerForm(req, resp);
                    break;
                case "/insert":
                    insertCustomer(req, resp);
                    break;
                case "/delete":
                    deleteCustomer(req, resp);
                    break;
                case "/edit":
                    showEditFromForCustomer(req, resp);
                    break;
                case "/update":
                    try {
                        updateCustomer(req, resp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    listCustomer(req, resp);
                    break;
            }
        }catch (IOException ex){
            throw new ServletException(ex);
        }
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("idCustomer"));
        CustomerTo customer = customerService.findById(id);
        setAllDataForCustomerFromJSP(req, customer);
        customerService.update(customer);
        resp.sendRedirect("/customers");
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("idCustomer"));
        try {
            customerService.deleteById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        resp.sendRedirect("/customers");
    }

    private void insertCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CustomerTo customer = new CustomerTo();
        setAllDataForCustomerFromJSP(req, customer);
        customerService.create(customer);
        resp.sendRedirect("/customers");
    }

    private void setAllDataForCustomerFromJSP(HttpServletRequest req, CustomerTo customer) {
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        String[] projectsIds = req.getParameterValues("projects");
        List<ProjectDao> projects = Arrays.stream(projectsIds).mapToInt(Integer::parseInt).mapToObj(i -> projectRepository.findById(i))
                .collect(Collectors.toList());

        customer.setName(name);
        customer.setCity(city);
        customer.setProjects(projects);
    }

    private void showEditFromForCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("idCustomer"));
        CustomerTo customer = customerService.findById(id);
        req.setAttribute("customer", customer);
        List<ProjectDao> allProjects = projectRepository.findAll();
        req.setAttribute("allProjects", allProjects);
        req.getRequestDispatcher("/view/customer-form.jsp").forward(req, resp);
    }

    private void showNewCustomerForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectDao> allProjects = projectRepository.findAll();
        req.setAttribute("allProjects", allProjects);
        req.getRequestDispatcher("/view/customer-form.jsp"). forward(req, resp);
    }

    private void listCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CustomerTo> customers = customerService.findAll();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/view/customers.jsp").forward(req, resp);
    }
}
