package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MeetingMapper {
    public MeetingDTO convertToDto(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setId(meeting.getId());
        meetingDTO.setTitle(meeting.getTitle());
        meetingDTO.setDate(meeting.getDate());
        meetingDTO.setStartTime(meeting.getStartTime());
        meetingDTO.setEndTime(meeting.getEndTime());

        User creator = meeting.getCreator();
        meetingDTO.setCreatorSurname(creator.getSurname());
        meetingDTO.setCreatorName(creator.getName());
        meetingDTO.setCreatorPatronymic(creator.getPatronymic());

        List<User> invitedUsers = new ArrayList<>();
        List<User> confirmedUsers = new ArrayList<>();
        meeting.getInvites().forEach(invite -> {
            User invitedUser = invite.getInvitedUser();
            invitedUsers.add(invitedUser);
            if (invite.isAccepted()) {
                confirmedUsers.add(invitedUser);
            }
        });
        meetingDTO.setInvitedUsers(invitedUsers);
        meetingDTO.setConfirmedUsers(confirmedUsers);
        return meetingDTO;
    }
}
