package com.shrayu.dto.auth;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class RegisterRequest {
//
//    @NotBlank(message = "Username is required")
//    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
//    private String username;
//
//    @NotBlank(message = "Email is required")
//    @Email(message = "Invalid email address")
//    private String email;
//
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, max = 100, message = "Password must be at least 8 characters")
//    private String password;
//}

//package com.shrayu.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    @Size(
        min = 2,
        max = 50,
        message = "First name must be between 2 and 50 characters"
    )
    @Pattern(
        regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$",
        message = "First name contains invalid characters"
    )
    private String firstName;


    @NotBlank(message = "Last name is required")
    @Size(
        min = 2,
        max = 50,
        message = "Last name must be between 2 and 50 characters"
    )
    @Pattern(
        regexp = "^[A-Za-z]+(?:[ '-][A-Za-z]+)*$",
        message = "Last name contains invalid characters"
    )
    private String lastName;


    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(
        max = 254,
        message = "Email cannot exceed 254 characters"
    )
    private String email;


    @NotBlank(message = "Password is required")
    @Size(
        min = 8,
        max = 72,
        message = "Password must be between 8 and 72 characters"
    )
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
        message = "Password must contain at least one uppercase letter, "
                + "one lowercase letter, one number and one special character"
    )
    private String password;


    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;


    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^\\+?[1-9]\\d{9,14}$",
        message = "Please provide a valid phone number"
    )
    private String phone;
}

//
//{
//    "email": "shubham@example.com",
//    "password": "Strong@123"
//}