package com.gimnasiolomas.ar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
//@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;
    private LocalDate birthday;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserActivitySchedule> userActivitySchedules = new HashSet<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserPlan> userPlans = new HashSet<>();
    private Role role;

    public User() {
    }

    public User(String name, String lastName, String email, String password, LocalDate birthday) {
        this.name = name;
        this.lastName = lastName;
        this.email = email.toLowerCase();
        this.password = password;
        this.birthday = birthday;
        this.role = Role.USER;
    }
    public void addUserActivity(UserActivitySchedule userActivitySchedule) {
        userActivitySchedule.setUser(this);
        userActivitySchedules.add(userActivitySchedule);
    }
    public void addUserPlan(UserPlan userPlan) {
        userPlan.setUser(this);
        userPlans.add(userPlan);
    }

    public Set<UserPlan> getUserPlans() {
        return userPlans;
    }

    public void setUserPlans(Set<UserPlan> userPlans) {
        this.userPlans = userPlans;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonIgnore
    public Set<UserActivitySchedule> getUserActivitySchedules() {
        return userActivitySchedules;
    }
    public void setUserActivitySchedules(Set<UserActivitySchedule> userActivitySchedules) {
        this.userActivitySchedules = userActivitySchedules;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
