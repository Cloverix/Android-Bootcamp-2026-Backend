package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.User;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
public class MeetingDTO {
    private long id;
    private String title;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String creatorSurname;
    private String creatorName;
    private String creatorPatronymic;
    private List<User> invitedUsers;
    private List<User> confirmedUsers;
}
