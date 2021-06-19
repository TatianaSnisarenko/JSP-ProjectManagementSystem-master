package project_managment_system.dao.repositories.one_entity_repositories;

import com.zaxxer.hikari.HikariDataSource;
import project_managment_system.dao.entity.SkillDao;
import project_managment_system.service.converters.SkillConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository implements Repository<SkillDao> {
    private final HikariDataSource dataSource;

    public SkillRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SkillDao deleteById(int idSkill) {
        String query = "delete from skills where id_skill=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idSkill);
            SkillDao deletedSkillDao = findById(idSkill);
            preparedStatement.executeUpdate();
            return deletedSkillDao;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public SkillDao deleteByObject(SkillDao skillDao) {
        return deleteById(skillDao.getIdSkill());
    }

    @Override
    public SkillDao update(SkillDao skillDao) {
        String query = "update skills set language =?::language_choice, level=?::level_choice where id_skill=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(3, skillDao.getIdSkill());
            setValuesAndExecutePreparedStatement(skillDao, preparedStatement);
            return findById(skillDao.getIdSkill());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public SkillDao create(SkillDao skillDao) {
        if (!exists(skillDao)) {
            String query = "insert into skills (language, level) VALUES (?::language_choice,?::level_choice)";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                setValuesAndExecutePreparedStatement(skillDao, preparedStatement);
                skillDao.setIdSkill(getLastSkillIndex());
                return skillDao;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            skillDao.setIdSkill(getIdForExistingSkill(skillDao));
            return skillDao;
        }
    }

    @Override
    public SkillDao findById(int skillId) {
        String query = "select id_skill, language , level from skills where id_skill=?";
        SkillDao skillDao = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, skillId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                skillDao = SkillConverter.toSkillDao(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return skillDao;
    }

    @Override
    public List<SkillDao> findAll() {
        String query = "select id_skill, language, level from skills";
        List<SkillDao> allSkillDaos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allSkillDaos.add(SkillConverter.toSkillDao(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allSkillDaos;
    }

    @Override
    public List<Integer> getListOfValidIndexes() {
        String query = "select id_skill from skills order by id_skill";
        List<Integer> indexes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                indexes.add(resultSet.getInt("id_skill"));
            }
            return indexes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return indexes;
        }
    }

    public List<SkillDao> allSkillsList(){
        List<SkillDao> allSkillsList = new ArrayList<>();
        SkillDao javaJunior = new SkillDao();
        javaJunior.setLanguage("Java");
        javaJunior.setLevel("Junior");
        allSkillsList.add(javaJunior);
        SkillDao javaMiddle = new SkillDao();
        javaMiddle.setLanguage("Java");
        javaMiddle.setLevel("Middle");
        allSkillsList.add(javaMiddle);
        SkillDao javaSenior = new SkillDao();
        javaSenior.setLanguage("Java");
        javaSenior.setLevel("Senior");
        allSkillsList.add(javaSenior);
        SkillDao cPluPlusJunior = new SkillDao();
        cPluPlusJunior.setLanguage("C++");
        cPluPlusJunior.setLevel("Junior");
        allSkillsList.add(cPluPlusJunior);
        SkillDao cPluPlusMiddle = new SkillDao();
        cPluPlusMiddle.setLanguage("C++");
        cPluPlusMiddle.setLevel("Middle");
        allSkillsList.add(cPluPlusMiddle);
        SkillDao cPluPlusSenior = new SkillDao();
        cPluPlusSenior.setLanguage("C++");
        cPluPlusSenior.setLevel("Senior");
        allSkillsList.add(cPluPlusSenior);
        SkillDao cSharpJunior = new SkillDao();
        cSharpJunior.setLanguage("C#");
        cSharpJunior.setLevel("Junior");
        allSkillsList.add(cSharpJunior);
        SkillDao cSharpMiddle = new SkillDao();
        cSharpMiddle.setLanguage("C#");
        cSharpMiddle.setLevel("Middle");
        allSkillsList.add(cSharpMiddle);
        SkillDao cSharpSenior = new SkillDao();
        cSharpSenior.setLanguage("C#");
        cSharpSenior.setLevel("Senior");
        allSkillsList.add(cSharpSenior);
        SkillDao jsJunior = new SkillDao();
        jsJunior.setLanguage("JS");
        jsJunior.setLevel("Junior");
        allSkillsList.add(jsJunior);
        SkillDao jsMiddle = new SkillDao();
        jsMiddle.setLanguage("JS");
        jsMiddle.setLevel("Middle");
        allSkillsList.add(jsMiddle);
        SkillDao jsSenior = new SkillDao();
        jsSenior.setLanguage("JS");
        jsSenior.setLevel("Senior");
        allSkillsList.add(jsSenior);
        allSkillsList.forEach(this::create);
        return allSkillsList;
    }

    private boolean exists(SkillDao skillDao) {
        return findAll().stream()
                .anyMatch(s -> s.equals(skillDao));
    }

    private int getIdForExistingSkill(SkillDao skillDao) {
        return findAll().stream()
                .filter(s -> s.equals(skillDao))
                .findFirst().orElse(new SkillDao()).getIdSkill();
    }

    private void setValuesAndExecutePreparedStatement(SkillDao skillDao, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, skillDao.getLanguage());
        preparedStatement.setString(2, skillDao.getLevel());
        preparedStatement.executeUpdate();
    }

    private int getLastSkillIndex() {
        String query = "select id_skill from skills order by id_skill desc limit 1";
        int index = -1;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                index = resultSet.getInt("id_skill");
            }
            return index;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return index;
        }
    }
}
