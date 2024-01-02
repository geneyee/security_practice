package com.dev.prac.spring0authLogin.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("NAME => {}", clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> body = oAuth2User.getAttributes();
        String email = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(body);
        }

        body.forEach((k, v) -> {
            log.info("-------------------------------");
            log.info(k + " : " + v);
        });

        return oAuth2User;
    }

    private String getKakaoEmail(Map<String, Object> body) {
        Object value = body.get("kakao_account");
        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");
        return email;
    }
}
