package kopo.delivery.config;

import jakarta.servlet.http.HttpServletResponse;
import kopo.delivery.dto.CustomUserDetails;
import kopo.delivery.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //private final CustomOAuth2UserService customOAuth2UserService;

   /* public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 경로에 대해 접근 허용
                )
                .formLogin(form -> form
                        .loginPage("/login")                // 커스텀 로그인 페이지 설정
                        .loginProcessingUrl("/login/complete")  // 로그인 프로세스 처리 URL
                        .defaultSuccessUrl("/")              // 로그인 성공 후 리디렉션할 URL
                        .failureUrl("/login?error=true")     // 로그인 실패 시 리디렉션할 URL
                        .successHandler((request, response, authentication) -> {
                            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                            User user = userDetails.getUser();

                            request.getSession().setAttribute("user", user);
                            request.getSession().setAttribute("userID", userDetails.getUsername()); // 사용자 ID 저장
                            request.getSession().setAttribute("userName", userDetails.getUserName()); // 사용자 이름 저장

                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"status\":\"success\", \"message\":\"" + userDetails.getUserName() + "님, 로그인 성공\"}");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"status\":\"fail\", \"message\":\"아이디나 비밀번호가 맞지 않습니다.\"}");
                        })
                        .permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        //.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/"); // 로그인 성공 후 리디렉트할 URL
                        })
                )
                .csrf(AbstractHttpConfigurer::disable);

        http
                .logout(logout -> logout
                        .logoutUrl("/logout")                  // 로그아웃 URL 설정
                        .logoutSuccessUrl("/")                 // 로그아웃 성공 시 리디렉트할 URL
                        .invalidateHttpSession(true)           // 세션 무효화
                        .deleteCookies("JSESSIONID")           // 세션 쿠키 삭제
                );

        http
                .sessionManagement(auth -> auth
                        .maximumSessions(2) // 중복 로그인 설정
                        .maxSessionsPreventsLogin(true));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String id = defaultOAuth2User.getAttributes().get("id").toString();
            String body = """
                    {"id":"%s"}
                    """.formatted(id);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            PrintWriter writer = response.getWriter();
            writer.println(body);
            writer.flush();
        });
    }
}
