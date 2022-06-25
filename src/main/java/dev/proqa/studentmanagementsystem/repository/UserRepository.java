package dev.proqa.studentmanagementsystem.repository;

import dev.proqa.studentmanagementsystem.dto.AdminDTO;
import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.entities.User;
import dev.proqa.studentmanagementsystem.entities.Role;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

//    @Query("SELECT new dev.proqa.studentmanagementsystem.dto.UserDTO(u) FROM User u WHERE u.id = ?1")
    Optional<UserDTO> findByIdOrderById(Long id);

    Optional<AdminDTO> findByIdOrderByUsername(Long id);

    List<UserDTO> findAllBy();

    List<UserDTO> findByRole(Role role);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.phoneNumber = ?4, u.email = ?5, " +
            "u.username = ?6, u.zipCode = ?7, u.city = ?8, u.country = ?9, u.state = ?10, u.address = ?11 " +
            "WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String phoneNumber, String email,
                String username, String zipCode, String city, String country, String state, String address)
            throws BadRequestException;
}
