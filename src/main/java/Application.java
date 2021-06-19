import com.zaxxer.hikari.HikariDataSource;
import project_managment_system.config.DatabaseConnectionManager;
import project_managment_system.dao.repositories.one_entity_repositories.*;
import project_managment_system.dao.repositories.relations_repositories.CompaniesProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.CustomersProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.DevelopersProjectsRepository;
import project_managment_system.dao.repositories.relations_repositories.DevelopersSkillsRepository;
import project_managment_system.service.CommandProcessor;
import project_managment_system.service.services.CompanyService;
import project_managment_system.service.services.DeveloperService;
import project_managment_system.service.services.ProjectService;
import project_managment_system.util.CommandUtil;
import project_managment_system.util.DefaultUtil;
import project_managment_system.util.PropertiesConfig;
import view.Console;
import view.View;

public class Application {
    public static void main(String[] args) {
        try {
            PropertiesConfig propertiesConfig = new PropertiesConfig();
            propertiesConfig.loadPropertiesFile("application.properties");
            HikariDataSource dataSource = DatabaseConnectionManager.getDataSource();

            ProjectRepository projectRepository = new ProjectRepository(dataSource);
            CompanyRepository companyRepository = new CompanyRepository(dataSource);
            CustomerRepository customerRepository = new CustomerRepository(dataSource);
            DeveloperRepository developerRepository = new DeveloperRepository(dataSource);
            SkillRepository skillRepository = new SkillRepository(dataSource);
            CompaniesProjectsRepository companiesProjectsRepository = new CompaniesProjectsRepository(dataSource);
            CustomersProjectsRepository customersProjectsRepository = new CustomersProjectsRepository(dataSource);
            DevelopersProjectsRepository developersProjectsRepository = new DevelopersProjectsRepository(dataSource);
            DevelopersSkillsRepository developersSkillsRepository = new DevelopersSkillsRepository(dataSource);

            ProjectService projectService = new ProjectService(projectRepository, developerRepository, companyRepository, customerRepository,
                    developersProjectsRepository, companiesProjectsRepository, customersProjectsRepository);
            CompanyService companyService = new CompanyService(companyRepository, projectRepository, companiesProjectsRepository);
            DeveloperService developerService = new DeveloperService(developerRepository, projectRepository, companyRepository,
                    skillRepository, developersProjectsRepository, developersSkillsRepository);

            CommandUtil commandUtil = new CommandUtil(projectService, companyService, developerService);
            View view = new Console();
            DefaultUtil defaultUtil = new DefaultUtil();

            CommandProcessor commandProcessor = new CommandProcessor(commandUtil, defaultUtil, view);
            commandProcessor.process();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
