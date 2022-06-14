package dev.proqa.studentmanagementsystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 15)
    @NotNull(message = "Enter your first name")
    @Column(nullable = false, length = 15)
    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    @Column(nullable = false, length = 15)
    private String lastName;

    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 150)
    @NotNull(message = "Please enter your email")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Size(min = 8, max = 20)
    @NotNull(message = "Please enter your username")
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Pattern(regexp="^[a-zA-Z0-9]{4}",message="length must be 3")
    @Size(min = 4, max = 60, message = "Please enter min 4 characters")
    @NotNull(message = "Please enter your password")
    @Column(nullable = false, length = 120)
    private String password;

    @Size(max = 250)
    @NotNull(message = "Please enter your address")
    @Column(nullable = false, length = 250)
    private String address;

    @Size(max = 20)
    @NotNull(message = "Please enter your city")
    @Column(nullable = false, length = 20)
    private String city;

    @Size(max = 20)
    @NotNull(message = "Please select your state")
    @Column(nullable = false, length = 20)
    private String state;

    @Size(max = 5)
    @NotNull(message = "Please enter your zip code")
    @Column(nullable = false, length = 5)
    private String zipCode;

    @Size(max = 20)
    @NotNull(message = "Please select your country")
    @Column(nullable = false, length = 20)
    private String country;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    @Size(min = 14, max= 14, message = "Phone number should be exact 10 characters")
    @NotNull(message = "Please enter your phone number")
    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
