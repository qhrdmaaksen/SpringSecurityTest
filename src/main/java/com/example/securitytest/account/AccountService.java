package com.example.securitytest.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service//account 와 관련되있는 비즈니스 로직을 처리해주거나 , account와 관련된일을하는 구현체
public class AccountService implements UserDetailsService {
    //이 자리에는 편의상 쓰면됨

    @Autowired
    PasswordEncoder passwordEncoder ;

    @Autowired
    AccountRepository accountRepository;

    ////UserDetail 유저정보를 data에서 받아와서 유저네임에 해당하는 유저정보를 data에서 가져와 UserDetails 타입으로 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);//accountRepository에서 유저정보를 꺼내옴
        if ( account == null){
            throw new UsernameNotFoundException(username);
        }
        //UserDetailsService 에서 return 한 이 객체의 type 이 principal 임
//        return User.builder()//스프링에서 User 클래스 제공
//                .username(account.getUsername())
//                .password(account.getPassword())
//                .roles(account.getRole())
//                .build();//UserDetails 타입의 객체 사용가능한게만듦

        //Account 에 접근할 어댑터 설정으로인한 return
        return new UserAccount(account);//accountRepository 에서 읽어온 유저 리턴
    }

    public Account createNew(Account account) {

        account.encodePassword(passwordEncoder);//비밀번호 암호화
        return  this.accountRepository.save(account);//저장
    }
}
