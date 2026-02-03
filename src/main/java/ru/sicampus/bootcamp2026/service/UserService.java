package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getAllUsersBySurname(String surname);

    List<UserDTO> getAllUsersByName(String name);

    List<UserDTO> getAllUsersByPatronymic(String patronymic);

    List<UserDTO> getAllUsersByDepartmentName(String departmentName);

    UserDTO createUser(UserRegisterDTO dto);

    UserDTO updateUser(Long id, UserDTO dto);

    void deleteUser(Long id);
}
