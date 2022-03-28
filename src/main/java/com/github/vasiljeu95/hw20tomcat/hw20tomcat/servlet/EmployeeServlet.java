package com.github.vasiljeu95.hw20tomcat.hw20tomcat.servlet;

import com.github.vasiljeu95.hw20tomcat.hw20tomcat.model.Employee;

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

    private static Connection connection;
    private static PreparedStatement preparedStatement;

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        employees.add(new Employee(1, "Stepan", "Vasilyeu", 27, "Office", "Engineer", 500));
        employees.add(new Employee(2, "Dmitriy", "Kozlov", 37, "Office", "Sales manager", 700));
        employees.add(new Employee(3, "Ivan", "Savich", 32, "IBS", "Electrician", 500));
        employees.add(new Employee(4, "Oleh", "Vakylchik", 34, "IBS", "Plumber", 500));
        employees.add(new Employee(5, "Nikolay", "Savitski", 33, "IBS", "Fitter", 500));

        employees.forEach(employee -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                preparedStatement = connection.prepareStatement(URL);
                preparedStatement.execute("INSERT INTO employee " +
                        "VALUES (" + employee.getId() + ", '" + employee.getFirstName() + "', '" + employee.getSecondName() +
                        "', " + employee.getAge() + ", '" + employee.getDepartment() +
                        "', '" + employee.getPosition() + "', " + employee.getSalary() + ");"
                );
            } catch (SQLException | ClassNotFoundException exception) {
                System.out.println("Something wrong in init!");
                exception.printStackTrace();
            }
        });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            ResultSet resultSet = preparedStatement.executeQuery("select * from employee");
            while (resultSet.next()) {
                String jsonText = String.format("id: %d, firstName: %s, secondName: %s, age: %d," +
                                " department: %s, position: %s, salary: %d.\n",
                        resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getLong(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getLong(7));

                writer.print(jsonText);
                writer.flush();
            }
        } catch (SQLException exception) {
            System.out.println("Something wrong in doGet!");
            exception.printStackTrace();
        }
        writer.close();
    }

    @Override
    public void destroy() {
        try {
            employees.clear();
            connection.close();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
