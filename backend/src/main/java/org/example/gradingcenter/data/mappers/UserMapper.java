package org.example.gradingcenter.data.mappers;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class UserMapper {

    public void mapUserUpdateDtoToUser(final User userFrom, User userTo) {
        userTo.setFirstName(userFrom.getFirstName());
        userTo.setLastName(userFrom.getLastName());
        userTo.setUsername(userFrom.getUsername());
        userTo.setPassword(userFrom.getPassword());
    }

}
