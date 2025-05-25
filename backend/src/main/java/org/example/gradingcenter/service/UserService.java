package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getUsers();

    User getUser(long id);

    User fetchUser(long id);

    User createUser(User user);

    User updateUser(User user, long id);

    void deleteUser(long id);

    List<User> getAllUsers();

    void addRoleToUser(Long id, String role);

    void removeRoleFromUser(Long id, String role);
}
