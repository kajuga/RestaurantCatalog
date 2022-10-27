package org.example.employee;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;


@SpringBootTest(
        properties = {"spring.main.allow-bean-definition-overriding=true"}
)
@ContextConfiguration(classes = {AbstractIT.TestConfig.class})
@Testcontainers
@ActiveProfiles("it-test")
public class AbstractIT {


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.3")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("admin");

    static {
        postgreSQLContainer.start();
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName(postgreSQLContainer.getDriverClassName())
                    .url(postgreSQLContainer.getJdbcUrl())
                    .password(postgreSQLContainer.getPassword())
                    .username(postgreSQLContainer.getUsername())
                    .build();


        }
    }


}