package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.UserDTO;
import dev.proqa.studentmanagementsystem.entities.Role;
import dev.proqa.studentmanagementsystem.entities.User;
import dev.proqa.studentmanagementsystem.entities.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.exception.AuthException;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";

    // TODO: fetchAllUsers
    public List<UserDTO> fetchAllUsers() {
        return userRepository.findAllBy();
    }

    public UserDTO findById(Long id) {
        return userRepository.findByIdOrderById(id).orElseThrow(() ->
                new ResolutionException(String.format(USER_NOT_FOUND_MSG, id)));
    }

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

    // TODO: add user by admin, admin can add role

    public void updateUser(Long id, UserDTO userDTO) throws BadRequestException {

        boolean emailExist = userRepository.existsByEmail(userDTO.getEmail());
        boolean usernameExist = userRepository.existsByUsername(userDTO.getUsername());

        User user = userRepository.findById(id).get();

        if (emailExist && !userDTO.getEmail().equals(user.getEmail())) {
            throw new BadRequestException("Error: Email already in use!");
        }

        if (usernameExist && !userDTO.getUsername().equals(user.getUsername())) {
            throw new BadRequestException("Error: Username already in use!");
        }

        userRepository.update(id, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(),
                userDTO.getEmail(), user.getUsername(), userDTO.getZipCode(), userDTO.getCity(),
                userDTO.getCountry(), userDTO.getState(), userDTO.getAddress());

//        user.setFirstName(userDTO.getFirstName());

//        userRepository.save(user);

//        User u = new User(id, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(),
//                userDTO.getEmail(), user.getUsername(), userDTO.getZipCode(), userDTO.getCity(),
//                userDTO.getCountry(), userDTO.getState(), userDTO.getAddress(), user.getPassword());  not use

    }
}
