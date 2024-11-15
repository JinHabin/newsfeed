package com.example.scheduler_jpa.entity;

import com.example.scheduler_jpa.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false, length = 500)
    private String title;

    @Column(name = "contents",nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "updateAt", nullable = false)
    private LocalDateTime updateAt;

    //@OneToMany(mappedBy = "schedule")
    //private List<Comment> commentList = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    @Builder
    public Schedule(String title, String contents, String password) {
        this.title = title;
        this.contents = contents;
        this.password = password;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}