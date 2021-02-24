package com.epam.dto;

import com.epam.model.Employee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel(description = "Represents the info which is required to create a Department")
@Getter
@Setter
public class DepartmentDto extends RepresentationModel<DepartmentDto> {

    @ApiModelProperty(value = "departmentId")
    private Long id;

    @ApiModelProperty(value = "departmentName")
    @NotBlank(message = "Department should have name")
    private String departmentName;

    @ApiModelProperty(value = "departmentHeadName")
    @NotBlank(message = "Department is not a department without its head")
    private String departmentHeadName;

    @ApiModelProperty(value = "employees")
    private List<Employee> employees;
}
