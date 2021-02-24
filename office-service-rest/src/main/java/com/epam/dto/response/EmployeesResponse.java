package com.epam.dto.response;

import com.epam.dto.EmployeeDto;

import java.util.Collection;

public class EmployeesResponse {

    private Collection<EmployeeDto> employees;

    public EmployeesResponse(Collection<EmployeeDto> employees) {
        this.employees = employees;
    }

    public Collection<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<EmployeeDto> employees) {
        this.employees = employees;
    }
}
