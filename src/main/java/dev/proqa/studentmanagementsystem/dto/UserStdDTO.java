package dev.proqa.studentmanagementsystem.dto;

import dev.proqa.studentmanagementsystem.entities.Student;
import dev.proqa.studentmanagementsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserStdDTO {

    private Long studentID;

    private LocalDateTime enrollTime;

    private UserDTO user;

    private String departmentName;

    public UserStdDTO(User user, Student student) {
        this.studentID = student.getStudentID();
        this.enrollTime = student.getEnrollTime();
        this.user = new UserDTO(user);
        this.departmentName = student.getDepartmentId().getName();
    }
}


