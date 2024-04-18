package ru.itone.beeline.database.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseService {

    private static final String DB_PASSWORD = "DB_PASSWORD";
    private static final String DB_USERNAME = "DB_USERNAME";
    private static final String DB_JDBC_URL = "DB_JDBC_URL";

    private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);
    
    private Connection conn;

    private static final String INSERT_QUERY = """
        insert into public."user_web_visit" ("login", "location", "date_time_ts") 
        values (?, ?, ?)
    """;
    
    private static final String DELETE_QUERY = """
        delete from public."user_web_visit" 
        where ctid in (
            select ctid from public."user_web_visit" 
            where "date_time_ts" >= ? and "date_time_ts" < ? 
            limit ?
        )
    """;

    public PreparedStatement getBatchInsertStatement() {
        return createStatement(INSERT_QUERY);
    }

    public PreparedStatement getDeleteStatement() {
        return createStatement(DELETE_QUERY);
    }

    protected PreparedStatement createStatement(String query) {
        try {
            return getConnection().prepareStatement(query);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
    public Connection getConnection() {
        if(conn == null) {
            try {
                String jdbcUrl = System.getenv(DB_JDBC_URL);
                String username = System.getenv(DB_USERNAME);
                String password = System.getenv(DB_PASSWORD);
                log.info("Trying to connect to {} with username = {} and password = {}", 
                    jdbcUrl, username, password != null ? "******" : null);
                conn = DriverManager.getConnection(jdbcUrl, username, password);
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return conn;
    }

}
