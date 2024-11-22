package com.example.newsfeed_project.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NewsfeedRequestDto {

  private String image;

  @NotBlank
  private String title;

  @NotNull
  private String content;
}
