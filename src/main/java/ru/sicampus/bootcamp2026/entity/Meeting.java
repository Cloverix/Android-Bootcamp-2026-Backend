package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Invitation> invites;

    @ManyToMany
    @JoinTable(name = "planned_meetings",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> confirmedUsers;       //Users which have accepted invites
}
