package com.example.newsfeed_project.member.entity;

import com.example.newsfeed_project.common.BaseEntity;
import com.example.newsfeed_project.member.dto.MemberDto;
import com.example.newsfeed_project.member.dto.MemberUpdateRequestDto;
import com.example.newsfeed_project.member.dto.MemberUpdateResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private int age;
    private String image;

    private LocalDateTime deletedAt;

    @Version
    //낙관적 락
    //버전 관리를 통해 동시성 충돌 감지.
    private Integer version;

    public static Member toEntity(MemberDto memberDtO) {
        return Member.builder()
                .name(memberDtO.getName())
                .email(memberDtO.getEmail())
                .password(memberDtO.getPassword())
                .phoneNumber(memberDtO.getPhoneNumber())
                .address(memberDtO.getAddress())
                .age(memberDtO.getAge())
                .image(memberDtO.getImage())
                .deletedAt(memberDtO.getDeletedAt())
                .build();
    }

    public void updatedMember(MemberUpdateRequestDto updatedDto) {
        if (updatedDto.getName() != null) {
            this.name = updatedDto.getName();
        }

        if (updatedDto.getImage() != null) {
            this.image = updatedDto.getImage();
        }

        if (updatedDto.getPhoneNumber() != null) {
            this.phoneNumber = updatedDto.getPhoneNumber();
        }

        if (updatedDto.getAddress() != null) {
            this.address = updatedDto.getAddress();
        }
    }

    public Member withPassword(String password) {
        return Member.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(password) // 변경된 비밀번호
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .age(this.age)
                .image(this.image)
                .deletedAt(this.deletedAt)
                .version(this.version)
                .build();
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void restore() {
        this.deletedAt = null;
    }
}

