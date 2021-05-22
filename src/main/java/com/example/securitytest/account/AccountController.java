package com.example.securitytest.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController//Spring에서 Controller 중 View로 응답하지 않는, Controller를 의미한다. method의 반환 결과를 JSON 형태로 반환한다.
public class AccountController {

 /*   @Autowired
//    @Autowired = 속성(field), setter method, constructor(생성자)에서 사용하며 Type에 따라 알아서 Bean을 주입 해준다. 객체에 대한 의존성을 주입시킨다.
//    Annotation을 사용할 시, 스프링이 자동적으로 값을 할당한다. Controller 클래스에서 DAO나 Service에 관한 객체들을 주입 시킬 때 많이 사용한다.
    AccountRepository accountRepository ;*/

    @Autowired
    AccountService accountService ;
    //url path에들어있는 3개의 값을 조합해서 객체로 만들어서 Account에 바인딩을 받음
    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account){
        //바인딩을 받은 account 를 createNew 에  accountService 로 반환하면 json으로 보여준다 RestController이기때문에
        return accountService.createNew(account);
    }
}
