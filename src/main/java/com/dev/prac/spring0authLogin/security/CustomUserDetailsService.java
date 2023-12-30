package com.dev.prac.spring0authLogin.security;

import com.dev.prac.spring0authLogin.domain.Members;
import com.dev.prac.spring0authLogin.repository.MemberRepository;
import com.dev.prac.spring0authLogin.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername => {}", username);

        Optional<Members> result = memberRepository.getWithRoles(username);

        if (result.isEmpty()) { // 사용자 없으면
            throw new UsernameNotFoundException("username not found");
        }

        // 사용자 있음
        Members member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getEmail(),
                member.isDel(),
                false,
                member.getRoleSet().stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList()));

        log.info("memberSecurityDTO => {}", memberSecurityDTO);

        return memberSecurityDTO;
    }
}
