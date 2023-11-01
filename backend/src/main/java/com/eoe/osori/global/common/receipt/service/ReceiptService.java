package com.eoe.osori.global.common.receipt.service;

import com.eoe.osori.global.common.receipt.dto.PostReceiptResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface ReceiptService {
    PostReceiptResponseDto getReceiptInfo(MultipartFile multipartFile);
}
