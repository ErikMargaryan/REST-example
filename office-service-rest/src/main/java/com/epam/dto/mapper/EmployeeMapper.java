package com.epam.dto.mapper;

import com.epam.dto.EmployeeDto;
import com.epam.model.Employee;

public class EmployeeMapper {

    private EmployeeMapper() {
        throw new UnsupportedOperationException();
    }

    public static Employee toEntity(EmployeeDto dto) {
        var employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setCountry(dto.getCountry());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());

        return employee;
    }

    public static EmployeeDto toDto(Employee employee) {
        var dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setCountry(employee.getCountry());
        dto.setDepartment(employee.getDepartment());
        dto.setPosition(employee.getPosition());

        return dto;
    }
}
