package com.epam.dto.response;

import com.epam.dto.DepartmentDto;

import java.util.Collection;

public class DepartmentsResponse {

    private Collection<DepartmentDto> departments;

    public DepartmentsResponse(Collection<DepartmentDto> departments) {
        this.departments = departments;
    }

    public Collection<DepartmentDto> getDepartments() {
        return departments;
    }

    public void setDepartments(Collection<DepartmentDto> departments) {
        this.departments = departments;
    }
}
