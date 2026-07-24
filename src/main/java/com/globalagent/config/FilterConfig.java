package com.globalagent.config;

import com.globalagent.security.FirebaseAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<FirebaseAuthFilter> firebaseAuthFilter() {
        FilterRegistrationBean<FirebaseAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new FirebaseAuthFilter());
        registration.addUrlPatterns("/api/auth/user/*", "/api/profile/*", "/api/stats/*");
        registration.setName("firebaseAuthFilter");
        registration.setOrder(1);
        return registration;
    }
}
