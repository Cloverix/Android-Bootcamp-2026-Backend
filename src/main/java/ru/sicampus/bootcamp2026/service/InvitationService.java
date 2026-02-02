package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {
    InvitationDTO createInvitation(InvitationDTO dto);

    InvitationDTO getInvitationById(Long id);

    List<InvitationDTO> getAllInvitations();

    InvitationDTO updateInvitation(Long id, InvitationDTO dto);

    void deleteInvitation(Long id);
}
