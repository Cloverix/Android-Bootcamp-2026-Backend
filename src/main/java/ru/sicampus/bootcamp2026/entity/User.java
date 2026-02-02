package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "department")
    private String departmentName;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "messenger_link", unique = true)
    private String messengerLink;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "personal_email", unique = true)
    private String personalEmail;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RegisteredUser registrationData;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> createdMeetings;

    @OneToMany(mappedBy = "invitedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invites;
}
