package com.epam.service;

import com.epam.model.Department;
import com.epam.model.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Department createDepartment(Department department);

    Optional<Department> updateDepartment(Department department, Long id);

    Optional<Department> getDepartmentById(Long id);

    void deleteDepartment(Long id);

    List<Department> getAllDepartments();

    List<Employee> getAllEmployeesByDepartmentId(Long id);
}
