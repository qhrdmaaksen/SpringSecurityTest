package com.example.securitytest.account;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Retention 어노테이션으로 어느 시점까지 어노테이션의 메모리를 가져갈 지 설정
//RetentionPolicy의 값을 넘겨주는 것으로 어노테이션의 메모리 보유 범위가 결정
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "minwoo" , roles = "USER")
public @interface WithUser {
}
