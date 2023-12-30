package com.dev.prac.spring0authLogin.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@ToString
@Setter
@Getter
public class MemberSecurityDTO extends User { // User는 UserDetails 인터페이스를 구현한 클래스

    private String mid;
    private String mpd;
    private String email;
    private boolean del;
    private boolean social;

    public MemberSecurityDTO(String username, String password, String email, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpd = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }

}
