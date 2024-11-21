package com.example.newsfeed_project.newsfeed.repository;

import com.example.newsfeed_project.newsfeed.entity.Newsfeed;
import com.example.newsfeed_project.newsfeed.entity.NewsfeedLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedLikeRepository extends JpaRepository<NewsfeedLike, Long> {

//  Optional<NewsfeedLike> findByNewsfeedIdAndMemberId(Long newsfeedId, Long memberId);

  @Query(value = "SELECT n.id, n.member_id, n.newsfeed_id "
      + "FROM newsfeed_like n "
      + "WHERE n.newsfeed_id = :newsfeedId AND n.member_id = :memberId", nativeQuery = true)
  NewsfeedLike findByNewsfeedIdAndMemberId(@Param("newsfeedId") Long newsfeedId,@Param("memberId") Long memberId);

  long countByNewsfeedId(Long id);

  void deleteByNewsfeedId(Long id);
}
