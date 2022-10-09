package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.entity.UserActivitySchedule;
import com.gimnasiolomas.ar.restriction.PasswordConstraint;
import com.gimnasiolomas.ar.restriction.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<UserActivityScheduleDTO> userActivitySchedulesDTO = new HashSet<>();


    public UserDto() {
    }
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userActivitySchedulesDTO = user.getUserActivitySchedules().stream().map(UserActivityScheduleDTO::new).collect(Collectors.toSet());
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
    public Set<UserActivityScheduleDTO> getUserActivitySchedulesDTO() {
        return userActivitySchedulesDTO;
    }
}
