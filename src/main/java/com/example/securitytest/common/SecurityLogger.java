package com.example.securitytest.common;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityLogger {

    public static void log(String message){
        System.out.println(message);
        Thread thread = Thread.currentThread();//principal을 찍을때 어떤 thread를 사용하는지 확인하는 코드
        System.out.println("Thread : " + thread.getName());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal : " + principal);
    }
}
