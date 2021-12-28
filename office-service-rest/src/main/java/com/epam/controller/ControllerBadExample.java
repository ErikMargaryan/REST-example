package com.epam.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.DepartmentDto;
import com.epam.dto.EmployeeDto;
import com.epam.dto.mapper.DepartmentMapper;
import com.epam.dto.mapper.EmployeeMapper;
import com.epam.dto.response.DepartmentsResponse;
import com.epam.dto.response.EmployeesResponse;
import com.epam.exception.NotFoundException;
import com.epam.service.DepartmentService;

import io.swagger.annotations.ApiParam;

/** FROM EXPERIENCE OF INTERN :D IT MAKES ALL REQUESTS, GETS NEEDED RESPONSE, BUT BRRRRR ! */
@RestController
@RequestMapping("/")
public class ControllerBadExample {

  private final DepartmentService departmentService;

  public ControllerBadExample(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping(value = "GETALLDEPARTMENTSINTHEWORLD")
  public ResponseEntity<DepartmentsResponse> getAllDepartments() {

    List<DepartmentDto> departments =
        departmentService.getAllDepartments().stream()
            .map(DepartmentMapper::toDto)
            .collect(Collectors.toList());

    return ResponseEntity.ok(new DepartmentsResponse(departments));
  }

  @GetMapping(value = "GET-department-by-ID/{depId}")
  public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long depId) {
    var departmentDto =
        departmentService
            .getDepartmentById(depId)
            .map(DepartmentMapper::toDto)
            .orElseThrow(
                () -> new NotFoundException("Not able to find department by depId: " + depId));

    return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(departmentDto);
  }

  @GetMapping(value = "{id}/getEmployeesByDepartmentId")
  public ResponseEntity<EmployeesResponse> getEmployeesByDepartmentId(@PathVariable Long id) {

    List<EmployeeDto> employees =
        departmentService.getAllEmployeesByDepartmentId(id).stream()
            .map(EmployeeMapper::toDto)
            .collect(Collectors.toList());

    return ResponseEntity.ok(new EmployeesResponse(employees));
  }

  @GetMapping(value = "DELETE-METHOD-BY-ID/{depId}")
  public ResponseEntity<Void> deleteDepartment(
      @ApiParam(name = "depId", required = true, example = "124") @PathVariable
          Long depId) {
    departmentService.deleteDepartment(depId);

    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "create_department")
  public ResponseEntity<DepartmentDto> createDepartment(
      @RequestBody @Valid DepartmentDto department) {
    var entity = DepartmentMapper.toEntity(department);
    var body = DepartmentMapper.toDto(departmentService.createDepartment(entity));

    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }

  @GetMapping(value = "UpdatingDepartmentWithId/{id}")
  public ResponseEntity<DepartmentDto> updateDepartment(
      @RequestBody @Valid DepartmentDto department, @PathVariable Long id) {

    var entity = DepartmentMapper.toEntity(department);
    var updatedDepartment = departmentService.updateDepartment(entity, id).get();
    var body = DepartmentMapper.toDto(updatedDepartment);

    return ResponseEntity.ok(body);
  }
}
