package site.metacoding.white.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FileConfig {

    @Bean // 서버 실행시
    public FilterRegistrationBean<HelloFilter> jwtAuthonticationFilter() {
        log.debug("디버그 : 인증 필터 등록");
        FilterRegistrationBean<HelloFilter> bean = new FilterRegistrationBean<>(new HelloFilter());
        bean.addUrlPatterns("/hello"); // hello 로 접근하는 url 에 대해서 필터를 진행

        return bean;
    }
}

@Slf4j
class HelloFilter implements Filter {

    @Override // /hello 요청 시
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getMethod().equals("resp")) {
            log.debug("디버그 : HelloFileter 실행됨");
        } else {
            log.debug("디버그 : POST 요청이 아니기 때문에 실행할 수 없습니다");
        }
    }
}
