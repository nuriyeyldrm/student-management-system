package dev.proqa.studentmanagementsystem.repository;

import dev.proqa.studentmanagementsystem.entities.Role;
import dev.proqa.studentmanagementsystem.entities.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByUserRole(UserRole userRole);

    Role getByUserRole(UserRole userRole);
}
