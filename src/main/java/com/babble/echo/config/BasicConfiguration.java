package com.babble.echo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class BasicConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/endpoints")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                ).csrf().disable().httpBasic();
        return http.build();
    }

}
