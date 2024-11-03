package org.example.gradingcenter.data.mappers;

import lombok.NoArgsConstructor;
import org.example.gradingcenter.data.entity.Headmaster;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class HeadmasterMapper {

    public void mapHeadmasterUpdateDtoToHeadmaster(final Headmaster headmasterFrom, Headmaster headmasterTo) {
        headmasterTo.setSchool(headmasterFrom.getSchool());
        headmasterTo.setFirstName(headmasterFrom.getFirstName());
        headmasterTo.setLastName(headmasterFrom.getLastName());
        headmasterTo.setUsername(headmasterFrom.getUsername());
        headmasterTo.setPassword(headmasterFrom.getPassword());
    }

}
