package com.testify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("render")
public class RenderDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        // Build DataSource from individual components
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://" + System.getenv("DB_HOST") + ":" +
                        System.getenv("DB_PORT") + "/" + System.getenv("DB_DATABASE") +
                        "?ssl=true&sslmode=require")
                .username(System.getenv("DB_USER"))
                .password(System.getenv("DB_PASSWORD"))
                .build();
    }
}