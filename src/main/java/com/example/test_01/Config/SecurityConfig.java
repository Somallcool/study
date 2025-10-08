package com.example.test_01.Config;

import com.example.test_01.Service.CustomAuthenticationSuccessHandler;
import com.example.test_01.Service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1. 비회원 (모두 접근 가능)
                        .requestMatchers("/", "/sign_up", "/signup_save",
                                "/check/**", "/image/**", "/css/**", "/js/**").permitAll()
                        // 2. 관리자 (ADMIN 역할만 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 3. 일반 회원(MEMBER 역할만 접근 가능)
                        .requestMatchers("/member/**").hasRole("MEMBER")
                        // 4. 나머지 모든 요청은 로그인만 하면 접근 가능(인증)
                        // ( 로그인 = ADMIN, MEMBER 모두 해당된다 )
                        .anyRequest().authenticated()
                )
                /* 관리자 외 접근권한 닫기
                1. exceptionHandling : Security가 인증/인가 과정에서 발생하는 예외를 처리하는 방법 설정
                2. accessDeniedPage : 로그인 상태이지만 요청된 리소스에 접근할 권한이 없을 때(403) /login페이지로 이동
                */
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/admin_page")
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/go_login")
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        //.defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
