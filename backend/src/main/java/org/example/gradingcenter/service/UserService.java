package org.example.gradingcenter.service;

import org.example.gradingcenter.data.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getUsers();

    User getUser(long id);

    User createUser(User User);

    User updateUser(User User, long id);

    void deleteUser(long id);
    
}
