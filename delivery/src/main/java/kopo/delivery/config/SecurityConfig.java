package kopo.delivery.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/", "/login", "/about").permitAll() // 특정 경로들을 모든 사용자에게 오픈
                        //.requestMatchers("/admin").hasRole("ADMIN")           // ADMIN 역할만 접근 가능
                        //.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // ADMIN, USER 역할만 접근 가능
                        .anyRequest().permitAll() // 모든 경로에 대해 접근 허용
                )
                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")               // 커스텀 로그인 페이지 설정
                        .loginProcessingUrl("/login/complete")  // 로그인 프로세스 처리 URL
                        .defaultSuccessUrl("/")            // 로그인 성공 후 리디렉션할 URL
                        .failureUrl("/login?error=true")   // 로그인 실패 시 리디렉션할 URL
                        .permitAll()
                )
                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // OAuth2 로그인 시 커스텀 로그인 페이지
                        .defaultSuccessUrl("/") // OAuth2 로그인 성공 후 리디렉션할 URL
                )
                .csrf((auth) -> auth.disable());

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(2) //중복로그인 설정
                        .maxSessionsPreventsLogin(true));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
