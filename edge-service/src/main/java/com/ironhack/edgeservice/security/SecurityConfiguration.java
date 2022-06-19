package com.ironhack.edgeservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/new-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/show-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/show-opportunities/**").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.PATCH,"/convert/**").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.POST,"/new-lead").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.GET,"/show-leads").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.GET,"/lead/**").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.PUT,"/close-lost-opportunity/**").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.PUT,"/close-won-opportunity/**").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.GET,"/report/closed-won-by-city").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-lost-by-city").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/open-by-city").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-won-by-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-lost-by-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/open-by-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-won-by-product").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-lost-by-product").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/open-by-product").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-won-by-country").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-lost-by-country").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/open-by-country").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-won-by-industry").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/closed-lost-by-industry").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/open-by-industry").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/opportunity-by-product").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/opportunity-by-country").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/opportunity-by-city").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/opportunity-by-industry").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/opportunity-by-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/leads-by-sales").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/mean-opps-account").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/median-opps-account").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/max-opps-account").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/report/min-opps-account").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/employee-count/mean").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/employee-count/median").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/employee-count/max").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/employee-count/min").hasRole("DIRECTOR")
                .mvcMatchers(HttpMethod.GET,"/opportunity-quantity/mean").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.GET,"/opportunity-quantity/median").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.GET,"/opportunity-quantity/max").hasRole("SALESREPRESENTATIVE")
                .mvcMatchers(HttpMethod.GET,"/opportunity-quantity/min").hasRole("SALESREPRESENTATIVE")
                .anyRequest().permitAll();
    }
}
