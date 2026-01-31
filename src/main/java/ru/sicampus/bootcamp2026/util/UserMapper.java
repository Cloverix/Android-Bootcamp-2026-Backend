package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setDepartmentName(user.getDepartmentName());
        userDTO.setPhotoUrl(user.getPhotoUrl());
        userDTO.setMessengerLink(user.getMessengerLink());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPersonalEmail(user.getPersonalEmail());

        List<Meeting> invitedMeetings = new ArrayList<>();
        for (Invitation invite : user.getInvites()) {
            if (!invite.isAccepted()) {
                invitedMeetings.add(invite.getMeeting());
            }
        }
        userDTO.setInvitedMeetings(invitedMeetings);

        userDTO.setPlannedMeetings(user.getPlannedMeetings());
        return userDTO;
    }
}
