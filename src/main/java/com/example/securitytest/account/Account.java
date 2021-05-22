package com.example.securitytest.account;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity//@Entity = 실제 DB의 테이블과 매칭될 Class임을 명시한다.즉, 테이블과 링크될 클래스임을 나타낸다.
public class Account {
    //@Id = 해당 테이블의 PK 필드를 나타낸다.
    @Id @GeneratedValue//db에들어갈때 자동으로 생성된 id 값생성
    private Integer id ;

    @Column(unique = true)//username 은 유일
    private String username ;

    private String password ;

    private String role ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
       /* bcrypt = 비밀번호 해시함수로 Niels Provos와 David Mazieres에 의해 만들어졌으며 Blowfish라는 암호에 기반하였다.
        Bcrypt는 조정할 수 있는 해시알고리즘을 써서 패스워드를 저장한다.
        Bcrypt는 패스워드를 해싱할 때 내부적으로 랜덤한 솔트를 생성하기 때문에 같은 문자열에 대해서 다른 인코드된 결과를 반환한다. 하지만 공통된 점은 매번 길이가 60인 String을 만든다.*/
        this.password = passwordEncoder.encode(this.password);
    }
}
