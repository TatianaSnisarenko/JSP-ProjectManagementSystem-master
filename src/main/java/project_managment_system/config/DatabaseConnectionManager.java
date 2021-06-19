package project_managment_system.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import project_managment_system.util.PropertiesConfig;

import java.util.Objects;

public class DatabaseConnectionManager {
    private static HikariDataSource ds;

    private DatabaseConnectionManager() {
    }

    public static void init() {
        try {
            PropertiesConfig propertiesLoader = new PropertiesConfig();
            propertiesLoader.loadPropertiesFile("application.properties");
            initDataSource(propertiesLoader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized HikariDataSource getDataSource() {
        if (Objects.isNull(ds)) {
            init();
            return ds;
        }
        return ds;
    }

    private static void initDataSource(PropertiesConfig propertiesLoader) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format("jdbc:postgresql://%s/%s", propertiesLoader.getProperty("host"),
                    propertiesLoader.getProperty("database.name")));
            config.setUsername(propertiesLoader.getProperty("username"));
            config.setPassword(propertiesLoader.getProperty("password"));
            config.setMaximumPoolSize(10);
            config.setIdleTimeout(10_000);
            config.setConnectionTimeout(10_000);
            config.setDriverClassName(propertiesLoader.getProperty("managment.driver"));
            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
