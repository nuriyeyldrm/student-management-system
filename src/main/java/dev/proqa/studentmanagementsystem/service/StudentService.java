package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.entities.Department;
import dev.proqa.studentmanagementsystem.entities.Student;
import dev.proqa.studentmanagementsystem.entities.User;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.repository.DepartmentRepository;
import dev.proqa.studentmanagementsystem.repository.StudentRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private final static String STD_NOT_FOUND_MSG = "%d you are not university student";

    public StudentDTO findByUserId(Long userId) throws ResourceNotFoundException {
        return studentRepository.findByUserId(new User(userId)).orElseThrow(() ->
                new ResourceNotFoundException(String.format(STD_NOT_FOUND_MSG, userId)));
    }

    public void create(Student student, Long userId, Long departmentId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        Department department = departmentRepository.findById(departmentId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, departmentId)));

        student.setDepartmentId(department);
        student.setUserId(user);

        studentRepository.save(student);
    }
}
