package com.example.securitytest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;

//커스텀 필터 추가
public class LoggingFilter extends GenericFilterBean {//filter 등록시 제공해주는 resource가 유용하기에  extends GenericFilterBean 해줌

    private Logger logger = LoggerFactory.getLogger(this.getClass());//log남기기

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        //uri를가져와서 stat할때 넣어주면 uri이름이 task이름이된다.
        //해당 uri에 접근할때 얼마나 걸리는지 시간 log 보기
        stopWatch.start(((HttpServletRequest)request).getRequestURI());

        chain.doFilter(request, response);//체인에 요청

        stopWatch.stop();
        logger.info("stopWatch.prettyPrint : {} " , stopWatch.prettyPrint());
    }
}
