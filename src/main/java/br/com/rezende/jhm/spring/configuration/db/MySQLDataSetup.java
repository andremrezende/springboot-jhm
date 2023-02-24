package br.com.rezende.jhm.spring.configuration.db;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class MySQLDataSetup {

    public static final String DB_NAME = "testdb";
    private static final int ORIGINAL_PORT = 3306;

    public static final Set<String> pathScripts = new HashSet<>(
            Arrays.asList(
                    new String[] {
                            "db/mysql/init.sql"
                    }
            )
    );

    @Bean
    @Primary
    @Scope("singleton")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        TestConfigFactory.getConfig().
//        dataSourceBuilder.url(mySQLContainer.getJdbcUrl());
        //TODO change hardcoded port number
//        dataSourceBuilder.url("jdbc:mysql://localhost:" + mySQLContainer.getMappedPort(ORIGINAL_PORT) + "/" + DB_NAME);
        dataSourceBuilder.url("jdbc:tc:mysql:5.7.34://localhost:3306/testdb?TC_INITSCRIPT=db/mysql/init.sql?TC_DAEMON=true");

//        dataSourceBuilder.username(mySQLContainer.getUsername());
//        dataSourceBuilder.password(mySQLContainer.getPassword());
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("dev");
//        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.driverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
        return dataSourceBuilder.build();
    }
//    @Bean
//    @Primary
//    @Scope("singleton")
//    public MySQLContainer mySQLContainer() {
//        MySQLContainer mySqlContainer = CustomMySqlContainer
//                .getInstance(DockerImageName.parse(CustomMySqlContainer.DB_IMAGE)
//                            .asCompatibleSubstituteFor("mysql"), "init.sql", DB_NAME);
//        return mySqlContainer;
//    }
}
