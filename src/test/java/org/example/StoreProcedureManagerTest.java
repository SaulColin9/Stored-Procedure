package org.example;

import com.mysql.cj.util.EscapeTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StoreProcedureManagerTest {
    private StoreProcedureManager storeProcedureManager;

    @BeforeEach
    void setup(){
        String url = "jdbc:mysql://localhost:3306/socialNetworkTest";
        String username = "root";
        String password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            storeProcedureManager = new StoreProcedureManager(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void createAndDropAddUserProcedure() throws SQLException {
        // create 'AddUser' procedure in socialNetworkTest data base
        storeProcedureManager.createAddUserProcedure();

        // drop 'AddUser' procedure
        storeProcedureManager.dropProcedure("AddUser");
    }

    @Test
    void createAndDropAddPostProcedure() throws SQLException {
        // create 'AddPost' procedure in socialNetworkTest data base
        storeProcedureManager.createAddPostProcedure();

        // drop 'AddPost' procedure
        storeProcedureManager.dropProcedure("AddPost");

    }

    @Test
    void createAndPrintProcedures() throws SQLException {
        storeProcedureManager.createAddPostProcedure();
        storeProcedureManager.createAddUserProcedure();

        // print to console available procedures
        storeProcedureManager.invokePrintProceduresProcedure();

    }
}