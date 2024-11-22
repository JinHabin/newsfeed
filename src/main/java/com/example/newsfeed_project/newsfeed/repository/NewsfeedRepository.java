package com.example.newsfeed_project.newsfeed.repository;

import com.example.newsfeed_project.newsfeed.entity.Newsfeed;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {

  @Query(value = "SELECT n.id, n.feed_image, n.title, n.content, n.member_id, n.created_at, n.updated_at "
      + "FROM newsfeed n "
      + "LEFT JOIN newsfeed_like l "
      + "ON n.id = l.newsfeed_id "
      + "GROUP BY n.id "
      + "ORDER BY COUNT(l.id) DESC", nativeQuery = true)
  Page<Newsfeed> findAllOrderByLikes(Pageable pageable);

//  @Query(value = "SELECT n.id, n.feed_image, n.title, n.content, n.member_id, n.created_at, n.updated_at "
//      + "FROM newsfeed n "
//      + "LEFT JOIN newsfeed_like l "
//      + "ON n.id = l.newsfeed_id "
//      + "GROUP BY n.id "
//      + "ORDER BY COUNT(l.id) DESC")
//  Page<Newsfeed> findAllOrderByLikes(Pageable pageable);

}
