package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.ChatErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatException extends RuntimeException{
    private final ChatErrorInfo info;
}
