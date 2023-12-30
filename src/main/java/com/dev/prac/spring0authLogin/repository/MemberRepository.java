package com.dev.prac.spring0authLogin.repository;

import com.dev.prac.spring0authLogin.domain.Members;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    // 일반 회원 조회
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Members m where m.mid = :mid and m.social = false")
    Optional<Members> getWithRoles(String mid);

    // mid(회원가입, 로그인 시 id) 존재 여부 있으면 true, 없으면 false
    boolean existsByMid(String mid);
}
