package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("userId") // ★ HTMLの input name="userId" に合わせる
                        .defaultSuccessUrl("/menus/calendar", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable()); // 必要に応じてCSRFを一時無効化（フォームからの送信を確実にするため）

        return http.build();
    }

    // ★ パスワードを問わず、入力されたユーザーIDだけでログインを許可するカスタム認証プロバイダ
    @Bean
    public AuthenticationManager authenticationManager() {
        AbstractUserDetailsAuthenticationProvider provider = new AbstractUserDetailsAuthenticationProvider() {
            @Override
            protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                // パスワードのチェックは何もしない（＝パスワード不要）
            }

            @Override
            protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                // ここで入力されたユーザーIDを元にユーザーオブジェクトを作成する（どんなIDでもログインOKにする場合）
                return User.builder()
                        .username(username)
                        .password("") // パスワードは空でOK
                        .roles("USER")
                        .build();
            }
        };

        return new ProviderManager(Collections.singletonList(provider));
    }
}