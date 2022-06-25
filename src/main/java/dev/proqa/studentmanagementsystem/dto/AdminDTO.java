package dev.proqa.studentmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class AdminDTO {

    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    private String lastName;

    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 150)
    @NotNull(message = "Please enter your email")
    private String email;

    @Size(min = 8, max = 20)
    @NotNull(message = "Please enter your username")
    private String username;

    @Pattern(regexp="^[a-zA-Z0-9]{4}")
    @Size(min = 4, max = 60, message = "Please enter min 4 characters")
    @NotNull(message = "Please enter your password")
    private String password;

    @Size(max = 250)
    @NotNull(message = "Please enter your address")
    private String address;

    @Size(max = 20)
    @NotNull(message = "Please enter your city")
    private String city;

    @Size(max = 20)
    @NotNull(message = "Please select your state")
    private String state;

    @Size(max = 5)
    @NotNull(message = "Please enter your zip code")
    private String zipCode;

    @Size(max = 20)
    @NotNull(message = "Please select your country")
    private String country;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    @Size(min = 10, max= 14, message = "Phone number should be exact 10 characters")
    @NotNull(message = "Please enter your phone number")
    private String phoneNumber;

    private String role;
}
