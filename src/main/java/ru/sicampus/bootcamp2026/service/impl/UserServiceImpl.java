package ru.sicampus.bootcamp2026.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

        return UserMapper.convertToDto(userRepository.save(user));
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

        return UserMapper.convertToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
