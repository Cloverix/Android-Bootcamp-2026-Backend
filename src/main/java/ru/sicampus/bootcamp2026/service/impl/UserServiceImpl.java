package ru.sicampus.bootcamp2026.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsersBySurname(String surname) {
        return userRepository.findAllBySurname(surname).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByName(String name) {
        return userRepository.findAllByName(name).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByPatronymic(String patronymic) {
        return userRepository.findAllByPatronymic(patronymic).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersByDepartmentName(String departmentName) {
        return userRepository.findAllByDepartmentName(departmentName).stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        User user = new User();
        user.setSurname(dto.getSurname());
        user.setName(dto.getName());
        user.setPatronymic(dto.getPatronymic());
        user.setDepartmentName(dto.getDepartmentName());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setMessengerLink(dto.getMessengerLink());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPersonalEmail(dto.getPersonalEmail());

        user.setInvites(new ArrayList<>());

        User savedUser = userRepository.save(user);

        List<Invitation> invitations = new ArrayList<>();
        List<Long> invitedMeetingIds = dto.getInvitedMeetingIds();
        if (!(invitedMeetingIds == null)) {
            invitedMeetingIds.forEach(id -> {
                Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
                Invitation newInvitation = new Invitation();
                newInvitation.setInvitedUser(savedUser);
                newInvitation.setMeeting(meeting);
                newInvitation.setAccepted(false);
                invitations.add(newInvitation);
            });
        }

        savedUser.getInvites().addAll(invitations);

        return UserMapper.convertToDto(userRepository.save(savedUser));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setSurname(dto.getSurname());
        user.setName(dto.getName());
        user.setPatronymic(dto.getPatronymic());
        user.setDepartmentName(dto.getDepartmentName());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setMessengerLink(dto.getMessengerLink());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPersonalEmail(dto.getPersonalEmail());

        List<Invitation> updatedInvitations = new ArrayList<>();
        List<Long> updatedInvitedMeetingIds = dto.getInvitedMeetingIds();
        if (!(updatedInvitedMeetingIds == null)) {
            updatedInvitedMeetingIds.forEach(meetingId -> {
                Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
                Invitation newInvitation = new Invitation();
                newInvitation.setInvitedUser(user);
                newInvitation.setMeeting(meeting);
                newInvitation.setAccepted(false);
                updatedInvitations.add(newInvitation);
            });
        }

        user.getInvites().clear();
        user.getInvites().addAll(updatedInvitations);

        return UserMapper.convertToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
