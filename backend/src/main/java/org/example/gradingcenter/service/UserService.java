package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getUsers();

    User getUser(long id);

    User createUser(User user);

    User updateUser(User user, long id);

    void deleteUser(long id);
    
}
