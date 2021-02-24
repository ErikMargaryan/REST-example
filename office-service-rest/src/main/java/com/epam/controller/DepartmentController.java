package com.epam.controller;

import com.epam.dto.DepartmentDto;
import com.epam.dto.EmployeeDto;
import com.epam.dto.mapper.DepartmentMapper;
import com.epam.dto.mapper.EmployeeMapper;
import com.epam.dto.response.DepartmentsResponse;
import com.epam.dto.response.EmployeesResponse;
import com.epam.exception.NotFoundException;
import com.epam.service.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api(value = "Department service rest API")
@RestController
@RequestMapping("api/v1/epam/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "Create new Department", response = DepartmentDto.class, tags = "createDepartment")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Created"),
                    @ApiResponse(code = 400, message = "Bad Request")
            })
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto department) {
        var entity = DepartmentMapper.toEntity(department);
        var body =
                DepartmentMapper.toDto(departmentService.createDepartment(entity))
                        .add(linkTo(DepartmentController.class).slash(entity.getId()).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @ApiOperation(value = "Update the Department", response = DepartmentDto.class, tags = "updateDepartment")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 400, message = "Bad Request")
            })
    @PutMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @RequestBody @Valid DepartmentDto department,
            @ApiParam(name = "department id", required = true, example = "124") @PathVariable Long id) {
        var entity = DepartmentMapper.toEntity(department);
        var updatedDepartment =
                departmentService
                        .updateDepartment(entity, id)
                        .orElseThrow(() -> new NotFoundException("Not able to find department by id: " + id));
        var body =
                DepartmentMapper.toDto(updatedDepartment)
                        .add(linkTo(DepartmentController.class).slash(entity.getId()).withSelfRel());

        return ResponseEntity.ok(body);
    }

    @ApiOperation(value = "Get list of departments.", response = DepartmentsResponse.class, tags = "getDepartments")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success|OK"),
            })
    @GetMapping
    public ResponseEntity<DepartmentsResponse> getAllDepartments() {

        List<DepartmentDto> departments = departmentService.getAllDepartments().stream()
                .map(DepartmentMapper::toDto)
                .collect(Collectors.toList());
        departments.forEach(d -> d.add(linkTo(DepartmentController.class).slash(d.getId()).withSelfRel()));

        return ResponseEntity.ok(new DepartmentsResponse(departments));
    }

    @ApiOperation(value = "Get department by ID.", response = DepartmentDto.class, tags = "getDepartmentById")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success|OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found!!!")
            })
    @GetMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(
            @ApiParam(name = "department id", required = true, example = "124") @PathVariable Long id) {
        var departmentDto =
                departmentService
                        .getDepartmentById(id)
                        .map(DepartmentMapper::toDto)
                        .orElseThrow(() -> new NotFoundException("Not able to find department by id: " + id));

        return ResponseEntity.ok(departmentDto.add(linkTo(DepartmentController.class).slash(id + "/employees" ).withRel("employeesInDepartment").withType(String.valueOf(RequestMethod.GET))));
    }

    @ApiOperation(value = "Get all employees of the department by ID.", response = EmployeeDto.class, tags = "getEmployeesByDepartmentId")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success|OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found!!!")
            })
    @GetMapping(value = "{id}/employees")
    public ResponseEntity<EmployeesResponse> getEmployeesByDepartmentId(
            @ApiParam(name = "department id", required = true, example = "124") @PathVariable Long id) {

        List<EmployeeDto> employees = departmentService.getAllEmployeesByDepartmentId(id).stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
        employees.forEach(e -> e.add(linkTo(EmployeeController.class).slash(e.getId()).withSelfRel()));

        return ResponseEntity.ok(new EmployeesResponse(employees));
    }

    @ApiOperation(value = "Delete department by ID", tags = "deleteDepartment")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "Success|OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found!!!")
            })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteDepartment(
            @ApiParam(name = "department id", required = true, example = "124") @PathVariable Long id) {
        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}
