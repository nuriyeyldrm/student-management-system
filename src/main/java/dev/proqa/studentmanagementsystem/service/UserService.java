package dev.proqa.studentmanagementsystem.service;

import dev.proqa.studentmanagementsystem.dto.*;
import dev.proqa.studentmanagementsystem.dto.enumeration.PagingHeaders;
import dev.proqa.studentmanagementsystem.entities.Role;
import dev.proqa.studentmanagementsystem.entities.Student;
import dev.proqa.studentmanagementsystem.entities.User;
import dev.proqa.studentmanagementsystem.entities.enumeration.UserRole;
import dev.proqa.studentmanagementsystem.exception.AuthException;
import dev.proqa.studentmanagementsystem.exception.BadRequestException;
import dev.proqa.studentmanagementsystem.exception.ConflictException;
import dev.proqa.studentmanagementsystem.exception.ResourceNotFoundException;
import dev.proqa.studentmanagementsystem.repository.RoleRepository;
import dev.proqa.studentmanagementsystem.repository.StudentRepository;
import dev.proqa.studentmanagementsystem.repository.UserPageableRepository;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserPageableRepository userPageableRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";

    public List<UserDTO> fetchAllUsers() {
        return userRepository.findAllBy();
    }

    public List<UserDTO> fetchAllStudents() {
        Role role = roleRepository.getByUserRole(UserRole.ROLE_STUDENT);
        return userRepository.findByRole(role);
    }

    public UserDTO findById(Long id) {
        return userRepository.findByIdOrderById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
    }

    public PagingResponse get(Specification<User> spec, Pageable pageable) {
        Page<User> page = userPageableRepository.findAll(spec, pageable);
        List<User> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(),
                (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    public List<User> get(Specification<User> spec, Sort sort) {
        return userPageableRepository.findAll(spec, sort);
    }

    public PagingResponse get(Specification<User> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<User> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L,
                    0L, 0L, 0L, entities);
        }
    }

    public PagingResponseDTO searchAll(PagingResponse pagingResponse) {
        List<User> elements = pagingResponse.getElements();

        List<UserStdDTO> search = new ArrayList<>();

        for (User u : elements){
            Student student = studentRepository.getByUserId(u);
            search.add(new UserStdDTO(u, student));
        }

        return new PagingResponseDTO(pagingResponse.getCount(),
                pagingResponse.getPageNumber(), pagingResponse.getPageSize(), pagingResponse.getPageOffset(),
                pagingResponse.getPageTotal(), search);
    }

    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) &&
                headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
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
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));

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

    public void addUserAuth(AdminDTO adminDTO) throws BadRequestException {

        boolean emailExist = userRepository.existsByEmail(adminDTO.getEmail());
        boolean usernameExist = userRepository.existsByUsername(adminDTO.getUsername());

        if (emailExist) {
            throw new BadRequestException("Error: Email already in use!");
        }

        if (usernameExist) {
            throw new BadRequestException("Error: Username already in use!");
        }

        String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());
        adminDTO.setPassword(encodedPassword);

        Role role = addRole(adminDTO.getRole());

        User updatedUser = new User(adminDTO.getFirstName(), adminDTO.getLastName(),
                adminDTO.getEmail(), adminDTO.getUsername(), adminDTO.getPassword(),
                adminDTO.getAddress(), adminDTO.getCity(), adminDTO.getState(),
                adminDTO.getZipCode(), adminDTO.getCountry(), adminDTO.getPhoneNumber(),
                role);

        userRepository.save(updatedUser);
    }

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

    public void updatePassword(Long id, String newPassword, String oldPassword) throws BadRequestException {

        User user = userRepository.getById(id);

        if (!BCrypt.hashpw(oldPassword, user.getPassword()).equals(user.getPassword()))
            throw new BadRequestException("password does not match");

        String hashedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public void updateUserAuth(Long id, AdminDTO adminDTO) throws BadRequestException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        boolean emailExist = userRepository.existsByEmail(adminDTO.getEmail());
        boolean usernameExist = userRepository.existsByUsername(adminDTO.getUsername());

        if (emailExist && !adminDTO.getEmail().equals(user.getEmail())) {
            throw new BadRequestException("Error: Email already in use!");
        }

        if (usernameExist && !adminDTO.getUsername().equals(user.getUsername())) {
            throw new BadRequestException("Error: Username already in use!");
        }

        if (adminDTO.getPassword() == null)
            adminDTO.setPassword(user.getPassword());

        else {
            String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());
            adminDTO.setPassword(encodedPassword);
        }

        Role role = addRole(adminDTO.getRole());

        User updatedUser = new User(id, adminDTO.getFirstName(), adminDTO.getLastName(),
                adminDTO.getEmail(), adminDTO.getUsername(), adminDTO.getPassword(),
                adminDTO.getAddress(), adminDTO.getCity(), adminDTO.getState(),
                adminDTO.getZipCode(), adminDTO.getCountry(), adminDTO.getPhoneNumber(),
                role);

        userRepository.save(updatedUser);
    }

    public void removeById(Long id) throws ResourceNotFoundException {
        userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        userRepository.deleteById(id);
    }

    private Role addRole(String role) {

        if (role == null){
            return roleRepository.findByUserRole(UserRole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
        else {
            if ("Administrator".equals(role)) {
                return roleRepository.findByUserRole(UserRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
            else {
                return roleRepository.findByUserRole(UserRole.ROLE_STUDENT)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
        }
    }


}
