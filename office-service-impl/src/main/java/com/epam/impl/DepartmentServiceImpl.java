package com.epam.impl;

import com.epam.model.Department;
import com.epam.model.Employee;
import com.epam.repository.DepartmentRepository;
import com.epam.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Department createDepartment(Department department) {
        return repository.save(department);
    }

    @Override
    public Optional<Department> updateDepartment(Department department, Long id) {

        return getDepartmentById(id)
                .map(
                        d -> {
                            d.setDepartmentName(department.getDepartmentName());
                            d.setDepartmentHeadName(department.getDepartmentHeadName());

                            return repository.save(d);
                        });
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteDepartment(Long id) {
        getDepartmentById(id).ifPresent(repository::delete);
    }

    @Override
    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    @Override
    public List<Employee> getAllEmployeesByDepartmentId(Long id) {
        return getDepartmentById(id).get().getEmployees();
    }
}
