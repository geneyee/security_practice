package com.dev.prac.spring0authLogin.config;

import com.dev.prac.spring0authLogin.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("-----------------------security filter-------------------");

        http
                .csrf((csrf) -> csrf.disable())
                .headers((header) ->
                        header.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login"))// 로그인 form 요청 주소(GET)
//                        .loginProcessingUrl("/member/login")
//                        .defaultSuccessUrl("/index"))
                // 자동 로그인 쿠키
                .rememberMe((remember) -> remember
                        .key("12345678")
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(userDetailsService)
                        .tokenValiditySeconds(60*60*24*30))
                //OAuth2 Client
                .oauth2Login((oauth2) -> oauth2.loginPage("/login"));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("----------------------------web configure----------------------------");
        // 정적자원 스프링시큐리티 적용 제외
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

}
