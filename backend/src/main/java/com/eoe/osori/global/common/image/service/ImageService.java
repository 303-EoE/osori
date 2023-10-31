package com.eoe.osori.global.common.image.service;

import com.eoe.osori.global.common.image.dto.PostImageRemovalRequestDto;
import com.eoe.osori.global.common.image.dto.PostImageUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    /**
     * S3에 파일 업로드
     */
    public PostImageUploadResponseDto uploadFiles(List<MultipartFile> multipartFiles);

    /**
     * S3에 업로드된 파일 삭제
     */
    public void deleteFiles(PostImageRemovalRequestDto postImageRemovalRequestDto);

    /**
     * UUID 파일명 반환
     */
    public String getUuidFileName(String fileName);

    /**
     * 년/월/일 폴더명 반환
     */
    public String getFolderName();
}
