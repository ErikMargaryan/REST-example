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

/**
 *
 * FROM EXPERIENCE OF INTERN :D
 * IT MAKES ALL REQUESTS, GETS NEEDED RESPONSE, BUT BRRRRR !
 *
 */

@RestController
@RequestMapping("/")
public class ControllerBadExample {

    private final DepartmentService departmentService;

    public ControllerBadExample(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @RequestMapping(value = "GETALLDEPARTMENTSINTHEWORLD")
    public ResponseEntity<DepartmentsResponse> getAllDepartments() {

        List<DepartmentDto> departments = departmentService.getAllDepartments().stream()
                .map(DepartmentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DepartmentsResponse(departments));
    }


    @RequestMapping(value = "GET-department-by-ID/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        var departmentDto =
                departmentService
                        .getDepartmentById(id)
                        .map(DepartmentMapper::toDto)
                        .orElseThrow(() -> new NotFoundException("Not able to find department by id: " + id));

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(departmentDto);
    }


    @RequestMapping(value = "{id}/getEmployeesByDepartmentId")
    public ResponseEntity<EmployeesResponse> getEmployeesByDepartmentId(@PathVariable Long id) {

        List<EmployeeDto> employees = departmentService.getAllEmployeesByDepartmentId(id).stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new EmployeesResponse(employees));
    }


    @RequestMapping(value = "DELETE-METHOD-BY-ID/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @ApiParam(name = "department id", required = true, example = "124") @PathVariable Long id) {
        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "create_department")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto department) {
        var entity = DepartmentMapper.toEntity(department);
        var body =
                DepartmentMapper.toDto(departmentService.createDepartment(entity));

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @RequestMapping(value = "UpdatingDepartmentWithId/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody @Valid DepartmentDto department,
                                                          @PathVariable Long id) {

        var entity = DepartmentMapper.toEntity(department);
        var updatedDepartment =
                departmentService
                        .updateDepartment(entity, id).get();
        var body =
                DepartmentMapper.toDto(updatedDepartment);

        return ResponseEntity.ok(body);
    }
}
