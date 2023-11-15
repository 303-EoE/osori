package com.eoe.osori.global.common.jwt;

import com.eoe.osori.global.advice.error.exception.AuthException;
import com.eoe.osori.global.advice.error.info.AuthErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtTokenFilter @ExceptionHandler로 관리가 되지 않기 때문에 JwtTokenFilter 전에
 * Exception을 핸들링하는 filter를 만들어 주어야한다
 *
 */
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        }
        // JwtTokenFilter에서 모든 exception을 AuthException으로 발생시켜준다.
        catch (AuthException e) {
            setErrorResponse(response, e.getInfo());
        }
    }

    // 이후 발생한 Exception에 따라서 status, code, message를 넣고 프론트에 보내준다.
    private void setErrorResponse(
            HttpServletResponse response,
            AuthErrorInfo errorCode
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus().value());
        // 한글이 깨지지 않게 하기 위해서 추가한다.
        response.setContentType("application/json; charset=UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class ErrorResponse {
        private final Integer code;
        private final String message;
    }
}