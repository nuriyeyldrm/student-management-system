package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.entities.Department;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public void create(Department department) throws BadRequestException {

        departmentRepository.save(department);
    }
}
