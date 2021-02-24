package com.epam.impl;

import com.epam.model.Employee;
import com.epam.repository.EmployeeRepository;
import com.epam.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Optional<Employee> updateEmployee(Employee employee, Long id) {

        return getEmployeeById(id)
                .map(
                        e -> {
                            e.setFirstName(employee.getFirstName());
                            e.setLastName(employee.getLastName());
                            e.setCountry(employee.getCountry());
                            e.setPosition(employee.getPosition());
                            return repository.save(e);
                        }
                );
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteEmployee(Long id) {
        getEmployeeById(id).ifPresent(repository::delete);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public List<Employee> getAllEmployeesByPosition(String position) {
        return repository.findEmployeeByPosition(position);
    }
}
