package org.example.gradingcenter.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.service.UserService;
import org.example.gradingcenter.util.RSAKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final RSAKeyProperties keys;

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/.well-known/**").permitAll(); // Chrome PWA probes
                    auth.requestMatchers("/error").permitAll(); // Spring’s error page
                    auth.requestMatchers("/auth/**", "/css/**", "/js/**", "/assets/**").permitAll();

                    auth.requestMatchers("/schools").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/schools/filter").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/schools/edit-school/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/schools/update/*").hasAnyAuthority(Roles.ROLE_ADMIN.name());
                    auth.requestMatchers("/schools/delete/*").hasAnyAuthority(Roles.ROLE_ADMIN.name());

                    auth.requestMatchers("/headmasters").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/headmasters/filter").hasAnyAuthority(Roles.ROLE_ADMIN.name());
                    auth.requestMatchers("/headmasters/edit-headmaster/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/headmasters/update/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/headmasters/delete/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());

                    auth.requestMatchers("/teachers").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/teachers/filter").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/teachers/edit-teacher/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/teachers/update/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name());
                    auth.requestMatchers("/teachers/*/subjects/add").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name());
                    auth.requestMatchers("/teachers/*/subjects/delete").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name());
                    auth.requestMatchers("/teachers/delete/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name());

                    auth.requestMatchers("/parents").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_PARENT.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/parents/filter").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/parents/edit-parent/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_PARENT.name(), Roles.ROLE_HEADMASTER.name());
                    auth.requestMatchers("/parents/update/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_PARENT.name());
                    auth.requestMatchers("/parents/*/children/add").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_PARENT.name());
                    auth.requestMatchers("/parents/*/children/remove/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_PARENT.name());
                    auth.requestMatchers("/parents/delete/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_PARENT.name());

                    auth.requestMatchers("/students").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name(), Roles.ROLE_PARENT.name(), Roles.ROLE_TEACHER.name(), Roles.ROLE_STUDENT.name());
                    auth.requestMatchers("/students/filter").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name(), Roles.ROLE_PARENT.name(), Roles.ROLE_TEACHER.name());
                    auth.requestMatchers("/students/edit-student/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_HEADMASTER.name(), Roles.ROLE_PARENT.name(), Roles.ROLE_TEACHER.name(), Roles.ROLE_STUDENT.name());
                    auth.requestMatchers("/students/update/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_STUDENT.name());
                    auth.requestMatchers("/students/*/marks/add").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name());
                    auth.requestMatchers("/students/*/marks/remove").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_TEACHER.name());
                    auth.requestMatchers("/students/delete/*").hasAnyAuthority(Roles.ROLE_ADMIN.name(), Roles.ROLE_STUDENT.name());

                    auth.requestMatchers("/").permitAll();
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                )
                .formLogin(fl -> fl
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter(){
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return jwtConverter;
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(){
//        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
//    }
//
//    @Bean
//    public JwtEncoder jwtEncoder(){
//        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
//        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwks);
//    }

}
