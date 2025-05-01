package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
public class StoreProcedureManager {
    private final static Logger logger = LoggerFactory.getLogger(StoreProcedureManager.class);
    private final Connection connection;

    public StoreProcedureManager(Connection connection) {
        this.connection = connection;
    }

    public void dropAllProcedures() throws SQLException {
        try (CallableStatement stmt = connection.prepareCall("{CALL DropAllProcedures()}")) {
            stmt.execute();
            logger.info("All stored procedures dropped successfully.");
        }
    }

    public void createAddUserProcedure() throws SQLException {
        String sql = """
                CREATE PROCEDURE AddUser(IN name VARCHAR(255), IN email VARCHAR(255))
                BEGIN
                    INSERT INTO Users (name, email) VALUES (name, email);
                END
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            logger.info("Stored procedure AddUser created successfully.");
        }
    }

    public void invokeAddUserProcedure(String name, String email) throws SQLException {
        try (CallableStatement stmt = connection.prepareCall("{CALL AddUser(?, ?)}")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.execute();
            logger.info("User added successfully using AddUser procedure: {}", name);
        }
    }

    public void invokePrintProceduresProcedure(String dbName) throws SQLException{
        try (CallableStatement stmt = connection.prepareCall("{CALL PrintProcedures(?)}")) {
            stmt.setString(1, dbName);
            stmt.execute();
        }
    }

    public void dropProcedure(String procedure) throws SQLException {
        String sql = String.format("DROP PROCEDURE IF EXISTS %s", procedure);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        logger.info("Procedure {} dropped successfully.", procedure);
    }
}
