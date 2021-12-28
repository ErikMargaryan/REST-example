package com.epam.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.EmployeeDto;
import com.epam.dto.mapper.EmployeeMapper;
import com.epam.dto.response.EmployeesResponse;
import com.epam.exception.NotFoundException;
import com.epam.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Employee service rest API")
@RestController
@RequestMapping("api/v1/epam/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @ApiOperation(
      value = "Create new Employee",
      response = EmployeeDto.class,
      tags = "createEmployee")
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad Request")
      })
  @PostMapping
  public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee) {
    var entity = EmployeeMapper.toEntity(employee);
    var body =
        EmployeeMapper.toDto(employeeService.createEmployee(entity))
            .add(linkTo(EmployeeController.class).slash(entity.getId()).withSelfRel());

    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }

  @ApiOperation(
      value = "Update Employee Info",
      response = EmployeeDto.class,
      tags = "updateEmployee")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request")
      })
  @PutMapping(value = "{id}")
  public ResponseEntity<EmployeeDto> updateEmployee(
      @RequestBody @Valid EmployeeDto employee,
      @ApiParam(name = "id", required = true, example = "124") @PathVariable Long id) {
    var entity = EmployeeMapper.toEntity(employee);
    var updatedEmployee =
        employeeService
            .updateEmployee(entity, id)
            .orElseThrow(() -> new NotFoundException("Not able to find employee by id: " + id));
    var body =
        EmployeeMapper.toDto(updatedEmployee)
            .add(linkTo(EmployeeController.class).slash(entity.getId()).withSelfRel());

    return ResponseEntity.ok(body);
  }

  @ApiOperation(
      value = "Get list of employees",
      response = EmployeesResponse.class,
      tags = "getEmployees")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
      })
  @GetMapping
  public ResponseEntity<EmployeesResponse> getAllEmployees(
      @ApiParam(name = "position", example = "Junior Java Developer")
          @RequestParam(value = "position", required = false)
          String position) {
    List<EmployeeDto> employees;
    if (StringUtils.isEmpty(position)) {
      employees =
          employeeService.getAllEmployees().stream()
              .map(EmployeeMapper::toDto)
              .collect(Collectors.toList());
    } else {
      employees =
          employeeService.getAllEmployeesByPosition(position).stream()
              .map(EmployeeMapper::toDto)
              .collect(Collectors.toList());
    }

    employees.forEach(e -> e.add(linkTo(EmployeeController.class).slash(e.getId()).withSelfRel()));

    return ResponseEntity.ok(new EmployeesResponse(employees));
  }

  @ApiOperation(
      value = "Get employee by ID",
      response = EmployeeDto.class,
      tags = "getEmployeeById")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found!!!")
      })
  @GetMapping(value = "{id}")
  public ResponseEntity<EmployeeDto> getEmployeeById(
      @ApiParam(name = "id", required = true, example = "124") @PathVariable Long id) {
    var employeeDto =
        employeeService
            .getEmployeeById(id)
            .map(EmployeeMapper::toDto)
            .orElseThrow(() -> new NotFoundException("Not able to find department by id: " + id));

    return ResponseEntity.ok(employeeDto);
  }

  @ApiOperation(value = "Delete employee by ID", tags = "deleteEmployee")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "Success|OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found!!!")
      })
  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> deleteEmployee(
      @ApiParam(name = "id", required = true, example = "124") @PathVariable Long id) {
    employeeService.deleteEmployee(id);

    return ResponseEntity.noContent().build();
  }
}
