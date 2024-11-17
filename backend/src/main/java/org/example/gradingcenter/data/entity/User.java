package org.example.gradingcenter.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> authorities;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isAccountNonExpired;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isAccountNonLocked;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isCredentialsNonExpired;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isEnabled;

}
