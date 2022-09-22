package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.User;
import lombok.Data;

@Data
public class UserDto {

    private long id;
    private String name;
    private String lastName;
    private String email;
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
