//package com.eoe.osori.global.advice.config;
//
//import com.eoe.osori.global.common.jwt.ExceptionHandlerFilter;
//import com.eoe.osori.global.common.jwt.JwtTokenFilter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtTokenFilter jwtTokenFilter;
//    private final ExceptionHandlerFilter exceptionHandlerFilter;
//
//    // 허용할 URL
//    private static final String[] PERMIT_URL_ARRAY = {
//            "/",
//            "/auth/**"
//    };
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors((cors) -> cors.disable())
//                .csrf((csrfConfig) -> csrfConfig.disable())
//                .httpBasic((httpBasic) -> httpBasic.disable())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .formLogin((formLogin) -> formLogin.disable())
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                // JwtTokenFilter 에서 발생한 에러를 처리해주기 위해 JwtTokenFilter 전에 exceptionHandlerFilter 설정
//                .addFilterBefore(exceptionHandlerFilter, JwtTokenFilter.class)
//                .authorizeHttpRequests((authorizeRequests) ->
//                        authorizeRequests
//                                .requestMatchers(PERMIT_URL_ARRAY).permitAll()
//                                .anyRequest().authenticated());
//        return http.build();
//    }
//
//}
