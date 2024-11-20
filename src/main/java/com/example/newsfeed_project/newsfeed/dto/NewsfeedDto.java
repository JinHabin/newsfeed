package com.example.newsfeed_project.newsfeed.dto;

import com.example.newsfeed_project.member.entity.Member;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class NewsfeedDto {

  private long id;

  private Member member;

  private String feedImage;

  private String title;

  private String content;

}
