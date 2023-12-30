package com.dev.prac.spring0authLogin.dto;

import com.dev.prac.spring0authLogin.domain.Members;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberJoinDTO {

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

    public MemberJoinDTO() {

    }

    public Members toEntity() {
        Members member = Members.builder()
                .mid(this.mid)
                .mpw(this.mpw)
                .email(this.email)
                .del(this.del)
                .social(this.social)
                .build();
        return member;
    }

}
