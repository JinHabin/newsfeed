package com.example.newsfeedproject.member.repository;
import com.example.newsfeedproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.deletedAt IS NULL")
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Member> findById(@Param("id") Long id);

    // 전체 조회 시 Soft 삭제된 데이터 제외
    @Query("SELECT m FROM Member m WHERE m.deletedAt IS NULL")
    List<Member> findAllActiveMembers();

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findByEmailIncludingDeleted(@Param("email") String email);


}
