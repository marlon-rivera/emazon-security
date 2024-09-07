package com.emazon.security.adapters.driven.jpa.mysql.entity;

import com.emazon.security.domain.model.RoleEnum;
import com.emazon.security.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, length = Constants.MAX_CHARACTERS_PHONE)
    private String phone;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
