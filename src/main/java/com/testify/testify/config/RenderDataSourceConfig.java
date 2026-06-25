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

    // Inject the deconstructed database credentials provided by Render.
    // This is the most robust method, as it avoids URL parsing issues
    // with special characters in passwords.
    @Value("${DB_HOST}")
    private String dbHost;

    @Value("${DB_PORT}")
    private String dbPort;

    @Value("${DB_DATABASE}")
    private String dbName;

    @Value("${DB_USER}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    @Bean
    public DataSource dataSource() {
        // Construct the JDBC URL from the clean, deconstructed parts.
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?ssl=true&sslmode=require",
                dbHost, dbPort, dbName);

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}