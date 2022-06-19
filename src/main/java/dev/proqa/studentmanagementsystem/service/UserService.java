package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.entities.Role;
import dev.proqa.studentmanagementsystem.entities.User;
import dev.proqa.studentmanagementsystem.entities.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.exception.AuthException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Error: Email is already in use!");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("Error: Username is already in use!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role userRole = roleRepository.findByUserRole(UserRole.ROLE_STUDENT)
                .orElseThrow(() -> new ResolutionException("Error: Role is not found."));

        user.setRole(userRole);
        userRepository.save(user);
    }

    public void Login(String username, String password) throws AuthException {
        try {
            Optional<User> user = userRepository.findByUsername(username);

            if (!BCrypt.checkpw(password, user.get().getPassword()))
                throw new AuthException("Invalid credentials");
        }
        catch (Exception e) {
            throw new AuthException("invalid credentials");
        }
    }
}
