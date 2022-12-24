package com.barogo.common.security;

import com.barogo.common.filter.handler.JwtAuthenticationFailureHandler;
import com.barogo.common.filter.jwt.JwtAuthenticationFilter;
import com.barogo.common.filter.jwt.JwtAuthorizationFilter;
import com.barogo.common.properties.JwtProperties;
import com.barogo.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    private final JwtProperties jwtProperties;

    private final BarogoAuthProvider barogoAuthProvider;

    private static final String [] AUTH_WHITELIST = {
            "/webjars/**",
            "/h2-console/**",
            "/join",
            "/login",
            "/users/login/**",
            "/users",
            "/users/token",


    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthorizationFilter())
                .addFilter(jwtAuthenticationFilter())
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProperties, barogoAuthProvider);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationFilter;
    }

    @Bean
    JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtProperties);
    }
}
