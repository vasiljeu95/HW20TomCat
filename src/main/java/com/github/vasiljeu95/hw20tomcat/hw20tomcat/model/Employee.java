package com.github.vasiljeu95.hw20tomcat.hw20tomcat.model;

public class Employee {
    private final int id;
    private final String firstName;
    private final String secondName;
    private final int age;
    private final String department;
    private final String position;
    private final int salary;

    public Employee(int id, String firstName, String secondName, int age, String department, String position, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }
}
