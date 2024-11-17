package org.example.gradingcenter.configuration;

import com.inf.cscb869_pharmacy.service.UserService;
import lombok.AllArgsConstructor;
import org.example.gradingcenter.service.HeadmasterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
@AllArgsConstructor
public class SecurityConfig {

    private final HeadmasterService userService;

    @Bean
    public InMemoryUserDetailsManager userDetailsInMemory() {
        UserDetails user1 = User
                .withUsername("user1")
                .password("$2a$12$VcsUDlmbLDczCbM2Cm5sAetkRVDSyHHzisAig1B.ypT/GW8XrcTq.")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password("$2a$12$BUppzBU81H1M.doHy7GMBegE1SoVSbmM5BH1F/sd5MiVti1EXL6IG")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests
                        (
                                authz -> authz
                        .requestMatchers("/recipes/**").hasAuthority("DOCTOR")
                        .requestMatchers("/medicines/**").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.GET, "/medicines").hasAuthority("CUSTOMER")
                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
        ;
        return http.build();
    }
}
