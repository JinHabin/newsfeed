package com.example.newsfeed_project.newsfeed.repository;

import com.example.newsfeed_project.newsfeed.dto.NewsfeedResponseDto;
import com.example.newsfeed_project.newsfeed.entity.Newsfeed;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {

  List<Newsfeed> findByMemberId(long memberId, Pageable pageable);
}
