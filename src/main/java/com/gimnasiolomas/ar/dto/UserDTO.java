package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.restriction.PasswordConstraint;
import com.gimnasiolomas.ar.restriction.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

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
    private Set<UserPlanDTO> userPlansDTO = new HashSet<>();


    public UserDTO() {
    }
    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userActivitySchedulesDTO = user.getUserActivitySchedules().stream().map(UserActivityScheduleDTO::new).collect(Collectors.toSet());
        this.userPlansDTO = user.getUserPlans().stream().map(UserPlanDTO::new).collect(Collectors.toSet());
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
    public Set<UserActivityScheduleDTO> getUserActivitySchedulesDTO() {
        return userActivitySchedulesDTO;
    }
    public Set<UserPlanDTO> getUserPlansDTO() {
        return userPlansDTO;
    }
}
