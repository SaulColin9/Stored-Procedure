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

    public void createAddPostProcedure() throws SQLException{
        String sql = """
                CREATE PROCEDURE AddPost(IN user_id INT, IN content TEXT)
                BEGIN
                    INSERT INTO Posts(user_id, content) VALUES (user_id, content);
                END
                """;
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
            logger.info("Stored procedure AddPost created successfully.");
        }
    }

    public void createPrintProceduresProcedure() throws SQLException{
        String sql = """
                CREATE PROCEDURE PrintProcedures(IN dbName VARCHAR(255))
                BEGIN
                    SHOW PROCEDURE STATUS WHERE db = dbName;
                END
                """;
        try(Statement statement = connection.createStatement()){
            statement.execute(sql);
            logger.info("Stored procedure PrintProcedures created successfully.");
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

    public void invokePrintProceduresProcedure() throws SQLException{
        String dbName = connection.getCatalog();
        try (CallableStatement stmt = connection.prepareCall("{CALL PrintProcedures(?)}")) {
            stmt.setString(1, dbName);
            boolean notEmpty = stmt.execute();
            if(notEmpty){
                logger.info("List of procedures available:");
                try(ResultSet resultSet = stmt.getResultSet()){
                    while(resultSet.next())
                        logger.info(resultSet.getString(2));
                }
            } else
                logger.info("No procedures available in {}", dbName);
        }
    }

    public void dropProcedure(String procedure) throws SQLException {
        String sql = String.format("DROP PROCEDURE IF EXISTS %s", procedure);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        logger.info("Procedure {} dropped successfully.", procedure);
    }
}
