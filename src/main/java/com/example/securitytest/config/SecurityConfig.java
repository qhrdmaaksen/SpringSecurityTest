package com.example.securitytest.config;

import com.example.securitytest.account.AccountService;
import com.example.securitytest.common.LoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//SecurityConfig 는 하나의 거대한 시큐리티필터체인이라고생각해도됨
@Configuration//어노테이션기반 환경구성을 돕는다
@EnableWebSecurity//웹 보안을 활성화하며, context의 WebSecurityConfigurerAdapter 확장하여 클래스를 설정하는방법을씀
@Order(Ordered.HIGHEST_PRECEDENCE -50)//anotherSecurityConfig와 충돌 테스트하기위한 강제우선순위 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    public SecurityExpressionHandler expressionHandler(){//보안 식 처리기,AccessDecisionVoter가 사용하는 expressionHandler
//        public AccessDecisionVoter accessDecisionVoter(){//접속 결정관리자 함수
        //AccessDecisionManager 는 AccessDecisionVoter 을 사용하고 AccessDecisionVoter는 setExpressionHandler 을 사용
        //RoleHierarchyImpl 설정하기
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl() ;//(role=역할,hierarchy=계층,impl=도구)
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");//roleAdmin은 roleUser의 상위에있기에 user가 할 수 있다는건 admin도 가능하다.

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler() ;
        handler.setRoleHierarchy(roleHierarchy);//웹 보안식 처리에(handler) 역할계층에들어온  ROLE_ADMIN > ROLE_USER 식 넣기

//        WebExpressionVoter webExpressionVoter = new WebExpressionVoter() ;
//        webExpressionVoter.setExpressionHandler(handler);//식이들어온 핸들러를 웹표현유권자?에 넣기
//        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);//접속결정에 식을 포함
//                return new AffirmativeBased(voters);//식이들어오면 긍정기반에서 판단하여 숫자 1(부여) or 0(기권) or -1(거부) 리턴
        return handler ;
    }

//    @Autowired
//    AccountService accountService ;
    //요청된 필터들을 실행하고싶지 않을때


    @Override
    public void configure(WebSecurity web) throws Exception {
        //인증 인가가 필요치 않은 page에 필터목록을 거치면 자원의 낭비가 생긴다 .
        //web.ignoring().mvcMatchers("/favicon.ico");//웹에 들어오는 favicon.ico 요청을 무시하겠다.
        //매번 위와같은 코드를 사용하기 싫다면 스프링 부트가 제공하는 requestMatchers사용
        //공통위치에서 정적 리소스에 경로요청을 무시하는 코드//필터목록을 지워준다 .
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                //아래와 같이 명시해줘야함
            .antMatchers("/favicon.ico", "/resources/**", "/error");//로그인할 때 정적리소스를 찾지못하면 에러로 처리됨
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //addFilterBefore LoggingFilter 필터를 WebAsyncManagerIntegrationFilter 보다 앞에둠 제일 처음부터 끝까지 성능 측정
        http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);
        //인증 인가 처리 로직
        http
//                .antMatcher("/account/**")
                .authorizeRequests()//authorizeRequests (인가)승인 요청//이 승인요청을 거치면 모든 필터 목록을 거쳐가야함
            .mvcMatchers("/", "/info", "/account/**","/signup").permitAll()//info page는 인증을 거치지 않도록 모두허가설정
            .mvcMatchers("/admin").hasRole("ADMIN")//admin page 는 ADMIN 권한이있을경우만 접근가능
            .mvcMatchers("/user").hasRole("USER")//user page 는 user 권한이있을경우만 접근가능
                //위에 필터목록지우는걸 추천 아래와같은 코드는 결과는 같지만 파비콘 요청 처리 시간은 더 오래걸림
            //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .anyRequest().authenticated() //기타 등등은 인증만 한다면 접근 가능
            //.anyRequest().anonymous()//익명의 사용자만 접근가능,인증된사용자는 접속불가능
            //.anyRequest().rememberMe()//rememberMe 기능으로 인증을 진행한 사용자에게 접근가능
            //.anyRequest().fullyAuthenticated()//rememberMe 기능으로 인증을 진행한 사용자가 다시한번 인증을 거치도록 설정
            //.anyRequest().denyAll()//아무도 허용하지 않겠다고 설정
            //.anyRequest().hasIpAddress()//특정한 ip주소를 가지고있는 사용자만 접근가능
            .expressionHandler(expressionHandler());

        http.formLogin()//http login filter: login기능 제공
//                .usernameParameter("my-username")//로그인페이지 파라미터 변경
//                .passwordParameter("my-password")
                    .loginPage("/login")//로그인페이지 커스터마이징
                    .permitAll();//permitAll 설정 필수 없으면 에러남

        http.rememberMe()
//                .alwaysRemember()//form에서 파라미터를 넘겨주지 않더라도 쿠키를 기억하겠다.
//                .tokenValiditySeconds()//세션 만료 시간 설정
                .userDetailsService(accountService)
                .key("remember-me-sample");

        http.httpBasic();// http.httpBasic 인증 filter :

        //http logout filter: logout 기능 제공
        http.logout().logoutSuccessUrl("/");//로그아웃 성공 후 이동할 url

        // TODO ExceptionTranslatorFilter -> FilterSecurityInterceptor (AccessDecisionManager,AffirmativeBased)
        // TODO AuthenticationException -> AuthenticationEntryPoint 인증예외발생시 인증 진입점(login page)로 이동시킴
        // TODO AccessDeniedException -> AccessDeniedHandler 접속거부예외발생시 접속거부처리자가 처리함

        http.exceptionHandling()
                //.accessDeniedPage("/access-denied");//엑세스 거부 페이지 설정하기
                .accessDeniedHandler(new AccessDeniedHandler() {//서버쪽에 log남기기 위한 설정
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        String username = principal.getUsername(); //username 확인
                        System.out.println(username + " is denied to access " + request.getRequestURI());//누가 어딘가(url)에 접근하려다 실패했을때 로그남기기 설정
                        response.sendRedirect("/access-denied");
                    }
                });

        //http.sessionManagement()
                //sessionCreationPolicy세션생성정책은 비저장,저장하지않기때문에 인증이필요한 page는 계속해서 인증요구
                //form 기반 인증 지원할땐 session 사용하는게 좋음 .
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS);//세션생성전략에는 4가지가있음
//                .sessionFixation()
//                .changeSessionId()
        //.invalidSessionUrl("/login")//유효하지않은 세션이 접근한다면 리다이렉트 시킬 url
//                .maximumSessions(1)
//
//                .maxSessionsPreventsLogin(false);//true로 추가 로그인못하게하고싶을때 사용,false사용시 기존에 로그인되어있는 세션 만료시킴
        //.expiredUrl()//만료가되었을때 어디로 리다이렉트시킬 url

        //인증이안된사용자를 anonymousAuthentication 라는 객체로만들어서 인증된 정보를 securityContextHolder에 넣어줌
        //http.anonymous();

        //async 로 만들어진 스레드에서 시큐리티컨텍스트를 사용해서 공유할수있다.
        //상속받은 스레드는 부모 스레드안에서 스레드가 생성되고 공유가능함
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
    //AuthenticationManager 을 bean 노출 시키기
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(accountService);
//    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //AuthenticationManagerBuilder 스프링 시큐리티 인증에대한 지원 설정하는 몇가지 메소드를 가지고있음
//        auth.inMemoryAuthentication()//메소드로 활성화 및 설정이 가능하고 선택적으로 인메모리 사용자 저장소에 값을 채울 수 있음
//                //db에 들어가는 값들
//                //유저정보 채우기, noop은 기본 스프링 패스워드 인코더 ( noop 은 암호화를 하지않음을 의미 )
//                .withUser("minwoo").password("{noop}123").roles("USER").and()
//                //prefix로 noop이 붙으면 뒤에 암호화된 password를 찾아서 값 비교하여 일치하면 인증완료
//                .withUser("admin").password("{noop}!@#").roles("ADMIN");
//    }
}
