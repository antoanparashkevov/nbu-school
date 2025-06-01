package org.example.gradingcenter.service.impl;

import lombok.AllArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.users.UserOutDto;
import org.example.gradingcenter.data.entity.Role;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.User;
import org.example.gradingcenter.data.repository.RoleRepository;
import org.example.gradingcenter.data.repository.UserRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.UserService;
import org.example.gradingcenter.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username: " + username));
    }

    @Override
    public List<UserOutDto> getUsers() {
        return userRepository.findAll().stream().map(MapperUtil::entityToDto).collect(Collectors.toList());
    }

    @Override
    public User getUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
    }

    @Override
    public User fetchUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
    }

    @Override
    public User createUser(User user) {
        Optional<User> duplicatedUser = userRepository.findByUsername(user.getUsername());
        if (duplicatedUser.isPresent()) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User User, long id) {
        return this.userRepository.findById(id)
                .map(User1 -> {
                    mapperConfig.getModelMapper().map(User, User1);
                    return this.userRepository.save(User1);
                }).orElseGet(() ->
                        this.userRepository.save(User)
                );
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private final RoleRepository roleRepository;

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Roles roleEnum = Roles.valueOf(roleName.toUpperCase());

        Role role = roleRepository.findByAuthority(roleEnum)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (!user.getAuthorities().contains(role)) {
            user.getAuthorities().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Roles roleEnum = Roles.valueOf(roleName.toUpperCase());

        Role role = roleRepository.findByAuthority(roleEnum)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (user.getAuthorities().contains(role)) {
            user.getAuthorities().remove(role);
            userRepository.save(user);
        }
    }

}
