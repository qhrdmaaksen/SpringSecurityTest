package com.example.securitytest.common;

import com.example.securitytest.account.Account;
import com.example.securitytest.account.AccountService;
import com.example.securitytest.book.Book;
import com.example.securitytest.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component//개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 Annotation이다. ,
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService ;

    @Autowired
    BookRepository bookRepository ;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    //minwoo - spring
        // minsuck - hibernate 생성
        Account minwoo =  createUser("minwoo");
        Account minsuck =  createUser("minsuck");

        //책 정보 넣기 
        createBook("spring",minwoo);
        createBook("hibernate",minsuck);
    }

    public void createBook(String title , Account minwoo) {
        Book book = new Book() ;
        book.setTitle(title);
        book.setAuthor(minwoo);
        bookRepository.save(book);
    }

    private Account createUser(String usename) {
        //유저 정보 터널만들기
        Account account = new Account() ;
        account.setUsername(usename);
        account.setPassword("123");
        account.setRole("USER");
        return accountService.createNew(account);//디비에 넣기
    }
}
