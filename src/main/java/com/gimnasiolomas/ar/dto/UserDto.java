package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.restriction.UniqueEmail;
import com.gimnasiolomas.ar.restriction.PasswordConstraint;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    @UniqueEmail
    private String email;
    @NotBlank
    @PasswordConstraint
    private String password;

    public UserDto() {
    }
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
