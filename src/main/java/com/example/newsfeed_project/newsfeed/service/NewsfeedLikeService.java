package com.example.newsfeed_project.newsfeed.service;

import com.example.newsfeed_project.newsfeed.dto.LikeResonseDto;
import org.springframework.stereotype.Service;

@Service
public interface NewsfeedLikeService {

  LikeResonseDto addLike(String email, long newsfeedId);

  LikeResonseDto delLike(String email, long newsfeedId);
}
