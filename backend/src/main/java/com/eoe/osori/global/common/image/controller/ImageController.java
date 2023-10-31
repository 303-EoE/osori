package com.eoe.osori.global.common.image.controller;


import com.eoe.osori.global.common.image.dto.PostImageRemovalRequestDto;
import com.eoe.osori.global.common.image.dto.PostImageUploadResponseDto;
import com.eoe.osori.global.common.image.service.ImageService;
import com.eoe.osori.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 이미지에 대한 controller
 */
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    /**
     * S3에 이미지 저장
     * @param multipartFiles List<MultipartFile>
     * @return ResponseEntity<EnvelopeResponse<PostImageUploadResponseDto>>
     * @see ImageService
     */
    @PostMapping
    public ResponseEntity<EnvelopeResponse<PostImageUploadResponseDto>> uploadFiles(@RequestPart List<MultipartFile> multipartFiles) {
        System.out.println("upload 시작 부분");
        return ResponseEntity.status((HttpStatus.OK))
                .body(EnvelopeResponse.<PostImageUploadResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .data(imageService.uploadFiles(multipartFiles))
                        .build());
    }

    /**
     * S3에 이미지 삭제
     * @param postImageRemovalRequestDto PostImageRemovalRequestDto
     * @return ResponseEntity<EnvelopeResponse<Void>>
     * @see ImageService
     */
    @PostMapping("/removal")
    public ResponseEntity<EnvelopeResponse<Void>> deleteFile(@RequestBody PostImageRemovalRequestDto postImageRemovalRequestDto) {
        imageService.deleteFiles(postImageRemovalRequestDto);
        return ResponseEntity.status((HttpStatus.OK))
                .body(EnvelopeResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .build());
    }

}
