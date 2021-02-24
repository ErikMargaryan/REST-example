package com.epam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_head_name")
    private String departmentHeadName;

    @JsonBackReference
    @OneToMany(mappedBy = "department", orphanRemoval=true)
    private List<Employee> employees;

}
