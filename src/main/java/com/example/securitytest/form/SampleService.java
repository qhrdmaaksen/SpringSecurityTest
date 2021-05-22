package com.example.securitytest.form;

import com.example.securitytest.account.Account;
import com.example.securitytest.account.AccountContext;
import com.example.securitytest.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service//Service Class에서 쓰인다. 비즈니스 로직을 수행하는 Class라는 것을 나타내는 용도이다.
public class SampleService {

//    public void dashboard() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//인증된 정보가들어와있음
//        Object principal = authentication.getPrincipal();//object는 임의타입이지만 사실 userdetails타입임 principal=인증한 사용자를나타내는정보로직
//        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//사용자의 권한별로 어디url에접속할수있을지 검사하는 로직
//        Object credentials = authentication.getCredentials();//사용자에대한 자격을 증명하는로직
//        boolean authenticated = authentication.isAuthenticated() ;//isAuthenticated 인증되었는지 bool타입으로 출력
//    }
//    public void dashboard(){
//        //컨트롤러에서 스레드 로컬에 저장한 정보를 get으로 사용자정보를 꺼낼수있음
//        Account account = AccountContext.getAccount();
//        System.out.println("=============");
//        System.out.println(account.getUsername());
//    }
    //METHOD SECURITY  , @Secured @RolesAllowed -메소드 호출 이전에 권한을 확인한다.
    //@Secured("ROLE_USER" , "ROLE_ADMIN")계층 구조 설정 따로 지정하고싶지 않다면으로 지정하면 권한 설정따로안해도됨
    @Secured("ROLE_USER")//USER보안지원, @secured사용시 method안으로 들어가지못하고 인가처리 에러발생
    //@PreAuthorize("hasRole('USER')")//파라미터값을 조회, 비교하는 @
    //@PostAuthorize()//리턴 값이 있는 경우 메소두 호출 이후에 추가적인 인가 확인하는 @
    public void dashboard(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("=============");
        System.out.println(authentication);
        System.out.println(userDetails.getUsername());
    }
    @Async//특정 빈안에있는 메소드를 호출할때 별도의 스레드를 만들어서 비동기적으로 호출을 해줌
    public void asyncService() {
        SecurityLogger.log("Async service");
        System.out.println("Async service is called");
    }
}
