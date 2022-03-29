package com.github.vasiljeu95.hw20tomcat.hw20tomcat.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBSHelper {
    public static void resultSQLRequest(Connection connection, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                String jsonText = String.format("id: %d, firstName: %s, secondName: %s, age: %d," +
                                " department: %s, position: %s, salary: %d.\n",
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getLong(7));

                writer.print(jsonText);
                writer.flush();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException exception) {
            System.out.println("Something wrong in doGet!");
            exception.printStackTrace();
        }
        writer.close();
    }
}
