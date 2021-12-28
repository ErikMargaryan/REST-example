package com.epam.dto.mapper;

import com.epam.dto.DepartmentDto;
import com.epam.model.Department;

public class DepartmentMapper {

  private DepartmentMapper() {
    throw new UnsupportedOperationException();
  }

  public static Department toEntity(DepartmentDto dto) {
    var department = new Department();
    department.setId(dto.getId());
    department.setDepartmentName(dto.getDepartmentName());
    department.setDepartmentHeadName(dto.getDepartmentHeadName());

    return department;
  }

  public static DepartmentDto toDto(Department department) {
    var dto = new DepartmentDto();
    dto.setId(department.getId());
    dto.setDepartmentName(department.getDepartmentName());
    dto.setDepartmentHeadName(department.getDepartmentHeadName());

    return dto;
  }
}
