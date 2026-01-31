package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.Meeting;

@Data
public class InvitationDTO {
    private long id;
    private Meeting meeting;
}
