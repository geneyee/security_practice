package com.dev.prac.spring0authLogin.service;

import com.dev.prac.spring0authLogin.dto.MemberJoinDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    static class MidExistException extends Exception {
    }

    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
