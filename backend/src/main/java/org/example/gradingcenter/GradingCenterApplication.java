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
            if (roleRepository.findByAuthority(Roles.ADMIN).isPresent()) return;
            Role adminRole = roleRepository.save(new Role(Roles.ADMIN));
            roleRepository.save(new Role(Roles.HEADMASTER));
            roleRepository.save(new Role(Roles.PARENT));
            roleRepository.save(new Role(Roles.STUDENT));
            roleRepository.save(new Role(Roles.TEACHER));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            User admin = new User("adminFirstName", "adminLastName",
                    "admin", passwordEncode.encode("nimda5555"), roles);

            userRepository.save(admin);
        };
    }
//.
}
