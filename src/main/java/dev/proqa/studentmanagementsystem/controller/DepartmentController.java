package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.entities.Department;
import dev.proqa.studentmanagementsystem.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping(path = "/department")
public class DepartmentController {

    public DepartmentService departmentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> createDepartment(@Valid @RequestBody Department department) {

        departmentService.create(department);

        Map<String, Boolean> map = new HashMap<>();

        map.put("Department added successfully", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
