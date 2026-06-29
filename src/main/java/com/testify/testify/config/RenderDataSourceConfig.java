package com.testify.config;

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
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String database = System.getenv("DB_DATABASE");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // Build the JDBC URL manually
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database + "?ssl=true&sslmode=require";

        System.out.println("Connecting to: " + url.replaceAll(password, "****"));

        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}