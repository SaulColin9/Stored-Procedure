package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

public class App
{
    public static void main( String[] args ) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/socialNetwork";
        String username = "root";
        String password = "";
        Connection connection = DriverManager.getConnection(url, username, password);
        StoreProcedureManager storeProcedureManager = new StoreProcedureManager(connection);
        Random id = new Random();
        storeProcedureManager.createAddUserProcedure();
        storeProcedureManager.invokeAddUserProcedure("Saul", String.format("saul.colin%s@gmail.com", id.nextInt(1000)));
        storeProcedureManager.dropProcedure("AddUser");
        storeProcedureManager.invokePrintProceduresProcedure("socialNetwork");
    }
}
