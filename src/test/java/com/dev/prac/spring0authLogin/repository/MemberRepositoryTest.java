package com.dev.prac.spring0authLogin.repository;

import com.dev.prac.spring0authLogin.domain.MemberRole;
import com.dev.prac.spring0authLogin.domain.Members;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Disabled
    @DisplayName("일반 회원 등록 테스트")
    @Test
    void insertMembers() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Members member = Members.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@test.com")
                    .build();

            member.addRole(MemberRole.USER); // 0

            if (i >= 90) {
                member.addRole(MemberRole.ADMIN); // 1
            }
            memberRepository.save(member);
        });
    }

    @DisplayName("회원 조회 테스트")
    @Test
    void testRead() {
        Optional<Members> result = memberRepository.getWithRoles("member100");

        Members member = result.orElseThrow();
        log.info("member => {}", member);
        log.info("roleSet => {}", member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info("memberRole.name => {}", memberRole.name()));
        // Enum 클래스에는 .name() 메서드가 정의되어 있어, 모든 enum 타입에서 이 메서드를 호출할 수 있다. .name() 메서드는 enum 상수의 이름을 문자열로 반환하는 역할을 한다.
    }

    @DisplayName("회원 아이디 중복 확인 테스트")
    @Test
    void testDuplicateId() {
        String mid = "member100";
        boolean exist = memberRepository.existsByMid(mid);

        log.info("true / false => {}", exist);
    }

}