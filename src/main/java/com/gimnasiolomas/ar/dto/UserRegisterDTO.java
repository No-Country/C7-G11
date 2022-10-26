package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.restriction.PasswordConstraint;
import com.gimnasiolomas.ar.restriction.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
@Data
public class UserRegisterDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Email
    @UniqueEmail
    @NotBlank
    private String email;
    @NotBlank
    @PasswordConstraint
    private String password;
    @NotNull
    @Past
    private LocalDate birthday;

    public UserRegisterDTO(){}
}
