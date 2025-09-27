package com.example.test_01.Repository;

import com.example.test_01.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    @Query(value = "SELECT count(m.id) " +
            "FROM testtable03 m " +
            "WHERE m.id = :id", nativeQuery = true)
    int idcheck(@Param("id") String id);

    @Query(value = "SELECT COUNT(nickname) " +
            "FROM testtable03 " +
            "WHERE nickname = :nickname", nativeQuery = true)
    int nickcheck(@Param("nickname") String nickname);
}
