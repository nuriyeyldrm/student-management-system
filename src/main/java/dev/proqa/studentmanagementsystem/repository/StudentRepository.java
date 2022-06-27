package dev.proqa.studentmanagementsystem.repository;

import dev.proqa.studentmanagementsystem.dto.StudentDTO;
import dev.proqa.studentmanagementsystem.entities.Student;
import dev.proqa.studentmanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<StudentDTO> findByUserId(User user);
}
