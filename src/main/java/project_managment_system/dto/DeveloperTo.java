package project_managment_system.dto;

import project_managment_system.dao.entity.CompanyDao;
import project_managment_system.dao.entity.ProjectDao;
import project_managment_system.dao.entity.SkillDao;

import java.util.List;
import java.util.Objects;

public class DeveloperTo {
    private int idDeveloper;
    private String name;
    private int age;
    private String sex;
    private CompanyDao company;
    private double salary;
    private List<SkillDao> skills;
    private List<ProjectDao> projects;

    public int getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(int idDeveloper) {
        this.idDeveloper = idDeveloper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public CompanyDao getCompany() {
        return company;
    }

    public void setCompany(CompanyDao company) {
        this.company = company;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<SkillDao> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDao> skills) {
        this.skills = skills;
    }

    public List<ProjectDao> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDao> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "DeveloperTo{" +
                "idDeveloper=" + idDeveloper +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", company=" + company +
                ", salary=" + salary +
                ", skills=" + skills +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperTo that = (DeveloperTo) o;
        return age == that.age && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name) && Objects.equals(sex, that.sex) && Objects.equals(company, that.company) && Objects.equals(skills, that.skills) && Objects.equals(projects, that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex, company, salary, skills, projects);
    }
}
