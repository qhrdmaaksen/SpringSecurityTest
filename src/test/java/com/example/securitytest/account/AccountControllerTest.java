package com.example.securitytest.account;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)//junit4 기반의 test
@SpringBootTest
@AutoConfigureMockMvc//자동구성 모의 mvc  명시
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;//웹 애플리케이션을 애플리케이션 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있는 클래스

    @Autowired
    AccountService accountService;//가짜 user사용하기위해 설정

    @Test
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        /*perform = DispathcherServlet에 요청을 의뢰
            MockMvcRequestBuilders를 사용해 설정한 요청 데이터를 perform()의 인수로 전달
            get / post / fileUpload 와 같은 메서드 제공
            perform()에서 반환된 ResultActions() 호출
            실행 결과 검증*/
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//test진행할 메소드에 설정
    @WithUser//@withmockuser 중복사용을 막기위해 withuser인터페이스 추가
    public void index_user() throws Exception {//특정한 유저가 뷰를 조회할때 테스트
        mockMvc.perform(MockMvcRequestBuilders.get("/"))//이 유저로 로그인되었을시 인덱스페이지 확인
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void admin_user() throws Exception {//특정한 유저가 뷰를 조회할때 테스트
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))//이 유저로 로그인되었을시 인덱스페이지 확인
                .andDo(print())
                .andExpect(status().isForbidden());//forbidden 예상
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void admin_admin() throws Exception {//특정한 유저가 뷰를 조회할때 테스트
        mockMvc.perform(MockMvcRequestBuilders.get("/admin").with(user("admin").roles("ADMIN")))//이 유저로 로그인되었을시 인덱스페이지 확인
                .andDo(print())
                .andExpect(status().isOk());// 예상
    }

    @Test
    @Transactional//각각의 테스트가 끝나면 자동으로 롤백되도록 사용
    //porm 로그인 기능이 정상적으로 작동하는지 확인하는 테스트 코드
    public void login_success() throws Exception {
        //test진행을 위한 가짜 유저 생성
        String username = "minwoo";
        String password = "123" ;
        Account user = this.createUser(username, password);
        //user login name minwoo password=123
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());//예상되는 인증
    }
    @Test
    @Transactional
    //porm 로그인 기능이 정상적으로 작동하는지 확인하는 테스트 코드
    public void login_success2() throws Exception {
        //test진행을 위한 가짜 유저 생성
        String username = "minwoo";
        String password = "123" ;
        Account user = this.createUser(username, password);
        //user login name minwoo password=123
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
                .andExpect(authenticated());//예상되는 인증
    }
    @Test
    @Transactional
    //porm 로그인 기능이 정상적으로 작동하는지 확인하는 테스트 코드
    public void login_fail() throws Exception {
        //test진행을 위한 가짜 유저 생성
        String username = "minwoo";
        String password = "123" ;
        Account user = this.createUser(username, password);
        //user login name minwoo password=123
        mockMvc.perform(formLogin().user(user.getUsername()).password("12345"))
                .andExpect(unauthenticated());//예상되는 인증
    }

    public Account createUser(String username , String password) {
        //test진행을 위한 가짜 유저 생성 설정
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        //retrun 될때 createNew에서 비밀번호를 encodePassword로 변환해주기때문에 위에login메소드에 password는 명시해줘야함
        //password에 user.getPassword로 진행하였으니 null 값으로 진행실패했었음
        return accountService.createNew(account);
    }
}