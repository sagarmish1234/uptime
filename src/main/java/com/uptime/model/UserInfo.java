package com.uptime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Getter
@Setter
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    @JsonIgnore
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String company;
    @JsonIgnore
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private Set<UserRole> roles = new HashSet<>();
    @Builder.Default
    @JsonIgnore
    private boolean isVerified = false;


}