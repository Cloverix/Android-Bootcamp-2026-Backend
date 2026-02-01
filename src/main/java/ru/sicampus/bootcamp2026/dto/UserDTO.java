package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.util.List;

@Data
public class UserDTO {
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private String departmentName;
    private String photoUrl;
    private String messengerLink;
    private String phoneNumber;
    private String personalEmail;
    private List<Long> invitedMeetingIds;      //Ids of meetings to which the user is invited
    private List<Long> plannedMeetingIds;   //Ids of planned meetings
}
