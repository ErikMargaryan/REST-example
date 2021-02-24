package com.epam.service;

import com.epam.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Optional<Employee> updateEmployee(Employee employee, Long id);

    Optional<Employee> getEmployeeById(Long id);

    void deleteEmployee(Long id);

    List<Employee> getAllEmployees();

    List<Employee> getAllEmployeesByPosition(String position);
}
