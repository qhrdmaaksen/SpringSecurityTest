package com.example.securitytest.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    //현재 유저의 책에대한 정보를 가져오는 쿼리
    //@Query 시큐리티가 제공하는 쿼리 @ 안에서 쿼리문 작성 가능
    //?#{principal.account.id} 스프링 expression 안에서 principal 사용할 수 있게 해준다.
    @Query("select b from Book b where b.author.id = ?#{principal.account.id}")
    public List<Book> findCurrentUserBooks();
}
