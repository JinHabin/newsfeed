package com.example.newsfeedproject.config;


import com.example.newsfeedproject.auth.LoginFilter;
import com.example.newsfeedproject.member.service.MemberService;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    private final MemberService memberService;

    public WebConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter(memberService));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
