package com.se1858.G5.LandAuction.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                System.out.println("Connection to the SQL Server database is successful!");
            } else {
                System.out.println("Failed to make connection to SQL Server!");
            }
        } catch (Exception e) {
            System.err.println("Error occurred while connecting to SQL Server: " + e.getMessage());
        }
    }
}
