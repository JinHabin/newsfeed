package com.example.newsfeed_project.newsfeed.repository;

import com.example.newsfeed_project.newsfeed.entity.NewsfeedLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedLikeRepository extends JpaRepository<NewsfeedLike, Long> {

  NewsfeedLike findByNewsfeedIdAndMember_Email(Long id, String email);

}
