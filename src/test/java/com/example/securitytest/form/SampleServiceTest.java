package com.example.securitytest.form;

import com.example.securitytest.account.Account;
import com.example.securitytest.account.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService ;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    //@WithMockUser//임의의 인증된 사용자 생성 @
    public void dashboard(){
        // 테스트를 위한 임의 계정 생성, 웹이아닌 데스크탑 에플리케이션같은경우는  사용자
        Account account = new Account();
        account.setRole("ADMIN");//ADMIN 으로 설정시 methodsecurity 에서 설정따로해줘야함
        account.setUsername("minwoo");
        account.setPassword("123");
        accountService.createNew(account);//계정생성

        //accountService 에서 리턴하는 그 객체가(userDetails) principal
        UserDetails userDetails = accountService.loadUserByUsername("minwoo");//유저이름으로읽어오기

        UsernamePasswordAuthenticationToken token = //token 생성하기위해선 credentials필요
                new UsernamePasswordAuthenticationToken(userDetails, "123");

        //토큰이 일치하다면 인증된 authentication 을 securitycontextholer에 넣어줌
        Authentication authentication = authenticationManager.authenticate(token);//토큰넘겨줌

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();
    }
}