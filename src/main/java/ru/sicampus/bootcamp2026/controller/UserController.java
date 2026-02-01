package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/surname={surname}")
    public ResponseEntity<List<UserDTO>> getAllUsersBySurname(@PathVariable String surname) {
        return ResponseEntity.ok(userService.getAllUsersBySurname(surname));
    }

    @GetMapping("/name={name}")
    public ResponseEntity<List<UserDTO>> getAllUsersByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.getAllUsersByName(name));
    }

    @GetMapping("/patronymic={patronymic}")
    public ResponseEntity<List<UserDTO>> getAllUsersByPatronymic(@PathVariable String patronymic) {
        return ResponseEntity.ok(userService.getAllUsersByPatronymic(patronymic));
    }

    @GetMapping("/departmentName={departmentName}")
    public ResponseEntity<List<UserDTO>> getAllUsersByDepartmentName(@PathVariable String departmentName) {
        return ResponseEntity.ok(userService.getAllUsersByDepartmentName(departmentName));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
