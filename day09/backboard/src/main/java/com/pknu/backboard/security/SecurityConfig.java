package com.pknu.backboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 스프링시큐리티 핵심파일!!
@Configuration      // 스프링 환경설정 파일 
@EnableWebSecurity  // 스프링시큐리티를 제어 활성화
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 인증되지 않은 모든 페이지 요청을 허락(로그인창 안뜸)
            .authorizeHttpRequests((ahr) -> ahr.requestMatchers("/**")    
                                               .permitAll()
                                               .anyRequest()
                                               .authenticated())
            // h2-console URL은 CSRF에 예외라고 설정
            .csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))  
            // h2-console이 Frame방식(구시대방식)으로 개발되어서 필요한 설정
            .headers((hdr) -> hdr.addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN    
            )))
            // 로그인URL 접근 지정. 로그인페이지 URL과 로그인성공후 페이지 URL 지정
            .formLogin((fl) -> fl.loginPage("/member/signin")
                                 .defaultSuccessUrl("/"))
            // 로그아웃URL 지정.
            .logout((lo) -> lo.logoutUrl("/member/signout")
                              .logoutSuccessUrl("/")
                              .invalidateHttpSession(true))
        ;  // ;을 분리해놓으면 chain method 추가시 간편함
            
        return http.build();
    }

}
