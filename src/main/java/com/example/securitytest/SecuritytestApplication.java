package com.example.securitytest;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication// 스프링 부트의 가장 기본적인 설정을 선언해 줍니다.
@EnableAsync//비동기 활성화 어노테이션
public class SecuritytestApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //createDelegatingPasswordEncoder 다양한 패스워드 인코딩 지원
        //패스워드 안전화
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(SecuritytestApplication.class, args);
    }

}
