package com.unsw.shopful.security.service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unsw.shopful.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserDetailsImpl implements UserDetails {
    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Set<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        Set<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toSet());

        return new UserDetailsImpl()
            .setId(user.getId())
            .setUsername(user.getUsername())
            .setEmail(user.getEmail())
            .setPassword(user.getPassword())
            .setAuthorities(authorities);
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
		return true;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
