package com.eoe.osori.global.advice.error.exception;

import com.eoe.osori.global.advice.error.info.ReceiptErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiptException extends RuntimeException {
    private ReceiptErrorInfo info;
}
