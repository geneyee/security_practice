package com.dev.prac.spring0authLogin.service;

import com.dev.prac.spring0authLogin.domain.MemberRole;
import com.dev.prac.spring0authLogin.domain.Members;
import com.dev.prac.spring0authLogin.dto.MemberJoinDTO;
import com.dev.prac.spring0authLogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {
        log.info("처음 서비스에 dto 들어온다 => {}", memberJoinDTO.toString());

        // 회원가입 시 id가 존재하면 예외발생
        String mid = memberJoinDTO.getMid();
        boolean exist = memberRepository.existsByMid(mid);

        if (exist) {
            throw new MidExistException();
        }

        // 회원가입
//        Members member = modelMapper.map(memberJoinDTO, Members.class); // modelMapper로 DTO to ENTITY  DB에 null...
        Members member = memberJoinDTO.toEntity();
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw())); // pw 암호화
        member.addRole(MemberRole.USER); // ROLE_USER

        memberRepository.save(member);
    }
}
