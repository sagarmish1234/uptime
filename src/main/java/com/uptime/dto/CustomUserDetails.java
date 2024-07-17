package com.uptime.dto;

import com.uptime.model.UserInfo;
import com.uptime.model.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    @Getter
    private final UserInfo userInfo;
    Collection<? extends GrantedAuthority> authorities;
    private final boolean verified;

    public CustomUserDetails(UserInfo byUsername) {
        this.username = byUsername.getEmail();
        this.password = byUsername.getPassword();
        this.verified = byUsername.isVerified();
        this.userInfo = byUsername;
        List<GrantedAuthority> auths = new ArrayList<>();

        for (UserRole role : byUsername.getRoles()) {

            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return verified;
    }
}