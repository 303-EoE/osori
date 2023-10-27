package com.eoe.osori.global.advice.error;

import com.eoe.osori.domain.mattermost.component.NotificationManager;
import com.eoe.osori.global.advice.error.exception.MetaException;
import com.eoe.osori.global.common.response.EnvelopeResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Enumeration;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private NotificationManager notificationManager;

    @ExceptionHandler(MetaException.class)
    public ResponseEntity<EnvelopeResponse<MetaException>> metaExceptionHandler(MetaException exception, HttpServletRequest req) {
        exception.printStackTrace();
        notificationManager.sendNotification(exception, req.getRequestURI(), getParams(req));

        return ResponseEntity.status(exception.getInfo().getStatus())
            .body(EnvelopeResponse.<MetaException>builder()
                .code(exception.getInfo().getCode())
                .message(exception.getInfo().getMessage())
                .build());
    }

    private String getParams(HttpServletRequest req) {
        StringBuilder params = new StringBuilder();
        Enumeration<String> keys = req.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.append("- ").append(key).append(" : ").append(req.getParameter(key)).append('\n');
        }

        return params.toString();
    }
}
