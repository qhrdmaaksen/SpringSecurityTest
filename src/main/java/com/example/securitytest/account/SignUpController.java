package com.example.securitytest.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")//singup을 담당하는 컨트롤러 명시
public class SignUpController {

    @Autowired
    AccountService accountService ;//role 세팅위해 구현

    @GetMapping
    public String signupForm(Model model){// singup을 할 수 있는 form
        System.out.println("!!!!!!!!!!!!!!!!!!!");
        model.addAttribute("account",new Account());//view에 account객체 넘겨줄거임
        return "signup";
    }
    @PostMapping//view에서 post 타입으로 요청이 들어왔을때 처리할 로직
    public String processSignUp(@ModelAttribute Account account){//form 에서 넘어오는 data 는 account 객체에 담겨있다.
        System.out.println("!!!!!!!!!!!!!!!!!!!22");
        account.setRole("USER");//user 역할
        accountService.createNew(account);//accountService로 넘김
        return "redirect:/";//redirect로 / <- prefix로 다시 보냄
    }
}
