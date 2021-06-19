package project_managment_system.service.services;

import project_managment_system.dao.entity.CompanyDao;
import project_managment_system.dao.entity.DeveloperDao;
import project_managment_system.dao.entity.ProjectDao;
import project_managment_system.dao.entity.SkillDao;
import project_managment_system.dao.repositories.one_entity_repositories.Repository;
import project_managment_system.dao.repositories.relations_repositories.RelationsRepository;
import project_managment_system.dto.DeveloperTo;
import project_managment_system.service.converters.CompanyConverter;
import project_managment_system.service.converters.DeveloperConverter;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    private Repository<DeveloperDao> developerRepository;
    private Repository<ProjectDao> projectRepository;
    private Repository<CompanyDao> companyRepository;
    private Repository<SkillDao> skillRepository;
    private RelationsRepository developersProjectsRepository;
    private RelationsRepository developersSkillRepository;

    public DeveloperService(Repository<DeveloperDao> developerRepository,
                            Repository<ProjectDao> projectRepository,
                            Repository<CompanyDao> companyRepository,
                            Repository<SkillDao> skillRepository,
                            RelationsRepository developersProjectsRepository,
                            RelationsRepository developersSkillRepository) {
        this.developerRepository = developerRepository;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.developersProjectsRepository = developersProjectsRepository;
        this.developersSkillRepository = developersSkillRepository;
    }

    public DeveloperTo create(DeveloperTo developerTo) {
        CompanyDao createdCompanyDao = addCompanyIfNotExists(developerTo);
        DeveloperDao createdDeveloperDao = developerRepository.create(DeveloperConverter.toDeveloperDao(developerTo));
        List<ProjectDao> createdProjects = addProjectsAndRelations(developerTo, createdDeveloperDao);
        List<SkillDao> createdSkills = addSkillsAndRelations(developerTo, createdDeveloperDao);
        createdDeveloperDao.setIdCompany(createdCompanyDao.getIdCompany());
        DeveloperTo createdDeveloper = DeveloperConverter.fromDeveloperDao(createdDeveloperDao);
        /*createdDeveloper.setCompany(createdCompanyDao);
        createdDeveloper.setProjects(createdProjects);
        createdDeveloper.setSkills(createdSkills);*/
        return createdDeveloper;
    }

    public DeveloperTo createConsole(String name, int age, String sex, int companyId, double salary, DeveloperTo defaultDeveloperTo, CompanyService companyService){
        defaultDeveloperTo.setName(name);
        defaultDeveloperTo.setAge(age);
        defaultDeveloperTo.setSex(sex);
        defaultDeveloperTo.setCompany(CompanyConverter.toCompanyDao(companyService.findById(companyId)));
        defaultDeveloperTo.setSalary(salary);
        return create(defaultDeveloperTo);
    }

    public DeveloperTo findById(int developerId) {
        return DeveloperConverter.fromDeveloperDao(developerRepository.findById(developerId));
    }

    public DeveloperTo update(DeveloperTo developerTo) {
        List<ProjectDao> projectsToBeDeleted = getProjectsToBeDeleted(developerTo);
        DeveloperDao developerDao = DeveloperConverter.toDeveloperDao(developerTo);
        DeveloperDao updatedDeveloperDao = developerRepository.update(developerDao);
        List<ProjectDao> updatedProjects = addProjectsAndRelations(developerTo, updatedDeveloperDao);
        projectsToBeDeleted.forEach(projectDao -> developersProjectsRepository.delete(developerTo.getIdDeveloper(), projectDao.getIdProject()));
        updatedProjects = updatedProjects.stream().filter(p -> !projectsToBeDeleted.contains(p)).collect(Collectors.toList());
        List<SkillDao> updatedSkills = addSkillsAndRelations(developerTo, updatedDeveloperDao);
        DeveloperTo updatedDeveloper = DeveloperConverter.fromDeveloperDao(updatedDeveloperDao);
        updatedDeveloper.setProjects(updatedProjects);
        updatedDeveloper.setSkills(updatedSkills);
        return updatedDeveloper;
    }

    public DeveloperTo updateConsole(String name, int age, String sex, int companyId, double salary, int id, CompanyService companyService){
        DeveloperTo developerTo = findById(id);
        developerTo.setName(name);
        developerTo.setAge(age);
        developerTo.setSex(sex);
        developerTo.setCompany(CompanyConverter.toCompanyDao(companyService.findById(companyId)));
        developerTo.setSalary(salary);
        return update(developerTo);
    }

    public DeveloperTo deleteById(int developerId) {
        deleteRelationsWithProjectsAndSkills(developerId);
        return DeveloperConverter.fromDeveloperDao(developerRepository.deleteById(developerId));
    }

    public DeveloperTo deleteByObject(DeveloperTo developerTo) {
        return deleteById(developerTo.getIdDeveloper());
    }

    public List<DeveloperTo> findAll() {
        List<DeveloperDao> allCompaniesDao = developerRepository.findAll();
        return DeveloperConverter.allFromDeveloperDao(allCompaniesDao);
    }

    private List<ProjectDao> addProjectsAndRelations(DeveloperTo developerTo, DeveloperDao createdDeveloperDao) {
        List<ProjectDao> projects = developerTo.getProjects();
        projects = projects.stream().map(p -> projectRepository.create(p)).collect(Collectors.toList());
        projects.forEach(p -> developersProjectsRepository.create(createdDeveloperDao.getIdDeveloper(), p.getIdProject()));
        return projects;
    }

    private List<ProjectDao> getProjectsToBeDeleted(DeveloperTo developerTo) {
        List<ProjectDao> projectsOld = this.findById(developerTo.getIdDeveloper()).getProjects();
        List<ProjectDao> projectsNew = developerTo.getProjects();
        return projectsOld.stream()
                .filter(projectDao -> !projectsNew.contains(projectDao))
                .collect(Collectors.toList());
    }

    private List<SkillDao> addSkillsAndRelations(DeveloperTo developerTo, DeveloperDao createdDeveloperDao) {
        List<SkillDao> skills = developerTo.getSkills();
        skills = skills.stream().map(skillDao -> skillRepository.create(skillDao)).collect(Collectors.toList());
        skills.forEach(skillDao -> developersSkillRepository.create(createdDeveloperDao.getIdDeveloper(), skillDao.getIdSkill()));
        return skills;
    }

    private CompanyDao addCompanyIfNotExists(DeveloperTo developerTo) {
        CompanyDao companyDao = developerTo.getCompany();
        return companyRepository.create(companyDao);
    }

    private void deleteRelationsWithProjectsAndSkills(int developerId) {
        DeveloperTo developerTo = findById(developerId);
        developerTo.getProjects().forEach(projectDao -> developersProjectsRepository.delete(developerId, projectDao.getIdProject()));
        developerTo.getSkills().forEach(skillDao -> developersSkillRepository.delete(developerId, skillDao.getIdSkill()));
    }

    public List<Integer> getListOfValidIndexes() {
        return developerRepository.getListOfValidIndexes();
    }
}
