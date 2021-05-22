package com.example.securitytest.form;

import com.example.securitytest.account.Account;
import com.example.securitytest.account.AccountRepository;
import com.example.securitytest.book.BookRepository;
import com.example.securitytest.common.CurrentUser;
import com.example.securitytest.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller//@Controller는 주로 View를 반환하기 위해 사용합니다.
public class SampleController {

    @Autowired
    SampleService sampleService;// sampleservice 주입받음

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BookRepository bookRepository ;

    @GetMapping("/")
//@GetMapping = @RequestMapping(Method=RequestMethod.GET)과 같다. @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping 등 도 있다.
    //@AuthenticationPrincipal / controller 에서는 principal 에 해당하는 UserAccount 객체를 받을수있다.
    //public String index(Model model, @AuthenticationPrincipal UserAccount userAccount) {

    public String index(Model model,@CurrentUser Account account) {
        //자바principal 인터페이스 구현체를 스프링 시큐리티가 인증된사용자가있다면 인증된사용자에대한 정보를 principal이라는 인터페이스타입으로
        //스프링 엠브이시 핸들러에 파라미터로 받아서 사용할수있게 해준다. 접근하는 유저에대한 정보를 확인할수있따.
        if (account == null) {//principal없는경우
            model.addAttribute("message", "Hello Spring Security");
        } else {//principal있는경우
            //userAccount.getAccount().getUsername();//account 에 접근해서 정보를 가져올수있게 설정도 가능하다.
            model.addAttribute("message", "Hello " + account.getUsername());
        }
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello " + principal.getName());//principal에 담긴 사용자 정보 id출력
        //AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));//사용자 정보를 가져와서 스레드로컬에 넣어주기
        sampleService.dashboard();//sampleservice클래스의 dashboard 메소드 호출
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin," + principal.getName());
        return "admin";
    }
    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "Hello USER," + principal.getName());
        //유저의 책에대한 정보를 보여주고싶을때 설정
        model.addAttribute("books" , bookRepository.findCurrentUserBooks());
        return "user";
    }

    @GetMapping("/async-handler")//async-handler호출
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                SecurityLogger.log("Callable");
                return "Async Handler";
            }
        };
    }

    @GetMapping("/async-service")//async-service호출
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Handler";
    }

}
