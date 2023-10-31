package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.ImageErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageException extends RuntimeException{
    private final ImageErrorInfo  info;
}
