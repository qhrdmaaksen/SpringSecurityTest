package com.example.securitytest.common;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//런타임까지 정보를 유지하겠다.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)//이 annotation 을 어디에 붙일수 있는지 설정

//@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")  Account account) {
//익명의 사용자가 아닐 경우에 account를 가져오게 설정
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {
}
