package org.example.gradingcenter.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles authority;

    @JsonBackReference
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public String getAuthorityName() {
        return authority.toString();
    }

    public Role(Roles authority) {
        this.authority = authority;
    }
}
