package com.example.securitytest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//securityconfig 와 비교해서 한곳은 전부허용 한곳은 인증필수로 설정 후 상충할때 어떤 결과가나오는지 테스트
@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)//강제우선순위
public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/account/**")
                .authorizeRequests()//인가
                .anyRequest().permitAll();//기타등등 전부허용 , 인증이필요없음
        http.formLogin();
        http.httpBasic();
    }
}
