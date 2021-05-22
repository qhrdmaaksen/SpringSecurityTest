package com.example.securitytest.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

//Account 에 접근 할 수 있는 어댑터
public class UserAccount extends User {//spring security 가 제공하는 user 상속받음

    private Account account ;

    public UserAccount(Account account){
        super(account.getUsername(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole())));

        this.account = account;//파라미터로 받은 account 세팅
    }
    public Account getAccount(){//account 에 access 할 수 있게 getter 생성
        return account;
    }
}
