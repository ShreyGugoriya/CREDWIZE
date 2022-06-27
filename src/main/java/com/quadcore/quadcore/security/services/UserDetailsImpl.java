package com.quadcore.quadcore.security.services;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quadcore.quadcore.Entities.Credential;
import com.quadcore.quadcore.Entities.User;
import com.quadcore.quadcore.Enum.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private int id;

    private User user;

    private String username;
    private int employee_id;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserDetailsImpl(int employee_id, int id, User user, String username, String password, Role role) {
        this.employee_id=employee_id;
        this.id=id;
        this.user=user;
        this.username=username;
        this.password=password;
        this.role=role;
    }

    public static UserDetailsImpl build(Credential user) {

        return new UserDetailsImpl(
                user.getUser().getEmployee_id(),
                user.getId(),
                user.getUser(),
                user.getUsername(),
                user.getPassword(),
                user.getRole());
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Role getRole() {
        return role;
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
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
