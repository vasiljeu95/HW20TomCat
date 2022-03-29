package com.github.vasiljeu95.hw20tomcat.hw20tomcat.servlet;

import com.github.vasiljeu95.hw20tomcat.hw20tomcat.model.Employee;
import com.github.vasiljeu95.hw20tomcat.hw20tomcat.model.JDBSHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "employeeServlet", value = "/employee-servlet")
public class EmployeeServlet extends HttpServlet {

    public static final String URL = "jdbc:mysql://localhost:3306/myPlant";
    public static final String USER = "root";
    public static final String PASSWORD = "root1234";

    private static final String SQL_INSERT_REQUEST = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static Connection connection;
    private final List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1, "Stepan", "Vasilyeu", 27, "Office", "Engineer", 500));
        employees.add(new Employee(2, "Dmitriy", "Kozlov", 37, "Office", "Sales manager", 700));
        employees.add(new Employee(3, "Ivan", "Savich", 32, "IBS", "Electrician", 500));
        employees.add(new Employee(4, "Oleh", "Vakylchik", 34, "IBS", "Plumber", 500));
        employees.add(new Employee(5, "Nikolay", "Savitski", 33, "IBS", "Fitter", 500));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_REQUEST);
            for (Employee employee : employees) {
                preparedStatement.setLong(1, employee.getId());
                preparedStatement.setString(2, employee.getFirstName());
                preparedStatement.setString(3, employee.getSecondName());
                preparedStatement.setLong(4, employee.getAge());
                preparedStatement.setString(5, employee.getDepartment());
                preparedStatement.setString(6, employee.getPosition());
                preparedStatement.setLong(7, employee.getSalary());
                preparedStatement.execute();
            }
            preparedStatement.close();
            connection.commit();
        } catch (SQLException exception) {
            System.out.println("Something wrong in init!");
            exception.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        JDBSHelper.resultSQLRequest(connection, resp);
    }

    @Override
    public void destroy() {
        try {
            employees.clear();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
