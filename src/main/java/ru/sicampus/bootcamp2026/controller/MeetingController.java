package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<MeetingDTO> createMeeting(@RequestBody MeetingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(dto));
    }

    @GetMapping
    public ResponseEntity<List<MeetingDTO>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsByTitle(@PathVariable String title) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByTitle(title));
    }

    @GetMapping("/invited/{id}")
    public ResponseEntity<List<MeetingDTO>> getAllMeetingsByInvitedUserId(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByInvitedUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDTO> updateMeeting(@PathVariable Long id, @RequestBody MeetingDTO dto) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }
}
