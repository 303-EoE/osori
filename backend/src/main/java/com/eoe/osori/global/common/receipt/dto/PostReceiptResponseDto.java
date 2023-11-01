package com.eoe.osori.global.common.receipt.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostReceiptResponseDto {
    private int totalPrice;
    private LocalDateTime paidAt;

    public static PostReceiptResponseDto of(int totalPrice, LocalDateTime paidAt) {
        return PostReceiptResponseDto.builder()
                .totalPrice(totalPrice)
                .paidAt(paidAt)
                .build();
    }
}
