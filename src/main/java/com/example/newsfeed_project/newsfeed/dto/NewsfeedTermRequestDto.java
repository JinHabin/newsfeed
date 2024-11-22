package com.example.newsfeed_project.newsfeed.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class NewsfeedTermRequestDto {

  @NotNull(message = "시작일자는 필수값입니다.")
  private LocalDate startDateTime;

  private LocalDate endDateTime;

  public NewsfeedTermRequestDto(LocalDate startDateTime, LocalDate endDateTime) {
    this.startDateTime = startDateTime;
    this.endDateTime = (endDateTime != null) ? endDateTime : LocalDate.now();
  }
}
