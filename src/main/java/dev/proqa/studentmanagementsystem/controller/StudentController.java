package dev.proqa.studentmanagementsystem.controller;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.entities.Department;
import dev.proqa.studentmanagementsystem.entities.Student;
import dev.proqa.studentmanagementsystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping(path = "/student")
public class StudentController {

    public StudentService studentService;

    @GetMapping("/info")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentDTO> findByUserId(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("id");

        return new ResponseEntity<>(studentService.findByUserId(userId), HttpStatus.OK);

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> createStudent(@RequestParam(value = "userId") Long userId,
                                                              @RequestParam(value = "departmentId") Long departmentId,
                                                              @Valid @RequestBody Student student) {

        studentService.create(student, userId, departmentId);

        Map<String, Boolean> map = new HashMap<>();

        map.put("Department added successfully", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
