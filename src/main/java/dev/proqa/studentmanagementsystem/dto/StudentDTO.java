package dev.proqa.studentmanagementsystem.dto;

import dev.proqa.studentmanagementsystem.entities.Department;
import dev.proqa.studentmanagementsystem.entities.Student;
import dev.proqa.studentmanagementsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    private Long studentID;

    private LocalDateTime enrollTime;

    private UserDTO userId;

    private String departmentName;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.studentID = student.getStudentID();
        this.enrollTime = student.getEnrollTime();
        this.userId = new UserDTO(student.getUserId());
        this.departmentName = student.getDepartmentId().getName();
    }
}
