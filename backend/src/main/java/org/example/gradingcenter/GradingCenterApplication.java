package org.example.gradingcenter;

import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.RoleRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class GradingCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradingCenterApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode) {
        return args -> {
            if (roleRepository.findByAuthority(Roles.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(new Role(Roles.ROLE_ADMIN));
                roleRepository.save(new Role(Roles.ROLE_HEADMASTER));
                roleRepository.save(new Role(Roles.ROLE_PARENT));
                roleRepository.save(new Role(Roles.ROLE_STUDENT));
                roleRepository.save(new Role(Roles.ROLE_TEACHER));
            }

            if (userRepository.findByUsername("admin").isPresent()) return;
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByAuthority(Roles.ROLE_ADMIN).get());
            User admin = new User("adminFirstName", "adminLastName",
                    "admin", passwordEncode.encode("nimda5555"), roles);

            userRepository.save(admin);
        };
    }

}
