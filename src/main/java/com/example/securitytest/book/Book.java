package com.example.securitytest.book;

import com.example.securitytest.account.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity//실제 DB의 테이블과 매칭될 Class임을 명시한다.즉, 테이블과 링크될 클래스임을 나타낸다.
public class Book {

    @Id @GeneratedValue//db에들어갈때 자동으로 생성된 id 값생성
    private Integer id ;

    private String title ;

    @ManyToOne//N:1 관계라고 보면 됩니다.
    private Account author ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }
}
