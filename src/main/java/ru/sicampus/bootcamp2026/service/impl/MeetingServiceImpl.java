package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public MeetingDTO createMeeting(MeetingDTO dto) {
        Meeting meeting = new Meeting();

        Optional<User> creator = userRepository.findById(dto.getCreatorId());
        if (creator.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        meeting.setCreator(creator.get());

        meeting.setTitle(dto.getTitle());
        meeting.setDate(dto.getDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());

        List<Invitation> invitations = new ArrayList<>();
        List<Long> inviteIds = dto.getInvitedUserIds();
        inviteIds.forEach(id -> {
            Optional<Invitation> invitation = invitationRepository.findById(id);
            if (invitation.isEmpty()) {
                throw new InvitationNotFoundException("Invitation not found");
            }
            invitations.add(invitation.get());
        });
        meeting.setInvites(invitations);

        List<User> confirmedUsers = new ArrayList<>();
        List<Long> confirmedIds = dto.getConfirmedUserIds();
        confirmedIds.forEach(id -> {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                throw new UserNotFoundException("User not found");
            }
            confirmedUsers.add(user.get());
        });
        meeting.setConfirmedUsers(confirmedUsers);

        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public List<MeetingDTO> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getAllMeetingsByTitle(String title) {
        return meetingRepository.findAllByTitle(title).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingDTO> getAllMeetingsByInvitedUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<Invitation> invites = invitationRepository.findAllByInvitedUser(user.get());
        List<MeetingDTO> meetings = new ArrayList<>();
        invites.forEach(invite -> {
            if (!invite.isAccepted()) {
                meetings.add(MeetingMapper.convertToDto(invite.getMeeting()));
            }
        });

        return meetings;
    }

    @Override
    public List<MeetingDTO> getAllMeetingsByConfirmedUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<Meeting> allMeetings = meetingRepository.findAll();
        List<MeetingDTO> meetingDTOS = new ArrayList<>();
        allMeetings.forEach(meeting -> {
            if (meeting.getConfirmedUsers().contains(user.get())) {
                meetingDTOS.add(MeetingMapper.convertToDto(meeting));
            }
        });

        return meetingDTOS;
    }

    @Override
    public MeetingDTO updateMeeting(Long id, MeetingDTO dto) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));

        User creator = userRepository.findById(dto.getCreatorId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        meeting.setCreator(creator);

        meeting.setTitle(dto.getTitle());
        meeting.setDate(dto.getDate());
        meeting.setStartTime(dto.getStartTime());
        meeting.setEndTime(dto.getEndTime());

        List<Invitation> invites = new ArrayList<>();
        dto.getInvitedUserIds().forEach(inviteId -> {
            invites.add(invitationRepository.findById(inviteId).orElseThrow(() -> new InvitationNotFoundException("Invitation not found")));
        });
        meeting.setInvites(invites);

        List<User> confirmedUsers = new ArrayList<>();
        dto.getConfirmedUserIds().forEach(confirmedId -> {
            confirmedUsers.add(userRepository.findById(confirmedId).orElseThrow(() -> new UserNotFoundException("User not found")));
        });
        meeting.setConfirmedUsers(confirmedUsers);

        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
