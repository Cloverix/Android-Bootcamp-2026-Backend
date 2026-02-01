package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@RequestBody InvitationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationService.createInvitation(dto));
    }

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
        return ResponseEntity.ok(invitationService.getAllInvitations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationDTO> updateInvitation(@PathVariable Long id, @RequestBody InvitationDTO dto) {
        return ResponseEntity.ok(invitationService.updateInvitation(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}
