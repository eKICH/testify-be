package com.testify.testify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Creates the production DataSource bean for the 'render' profile.
 * This approach is used to programmatically construct the JDBC URL,
 * bypassing the Spring Boot property override mechanism for SPRING_DATASOURCE_URL.
 */
@Configuration
@Profile("render")
public class RenderDataSourceConfig {

    // Inject the raw URL from Render's environment variable
    @Value("${SPRING_DATASOURCE_URL}")
    private String renderDatabaseUrl;

    @Value("${SPRING_DATASOURCE_USERNAME}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD}")
    private String password;

    @Bean
    public DataSource dataSource() {
        // Construct the full, valid JDBC URL
        String jdbcUrl = "jdbc:" + renderDatabaseUrl + "?ssl=true&sslmode=require";

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}