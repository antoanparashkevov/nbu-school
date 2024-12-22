package org.example.gradingcenter.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(nullable = false)
    private Roles authority;

    @ManyToMany
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority.name();
    }
}
