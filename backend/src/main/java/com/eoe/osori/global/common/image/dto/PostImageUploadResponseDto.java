package com.eoe.osori.global.common.image.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostImageUploadResponseDto {
    private List<ImagePathElement> path;

    public static PostImageUploadResponseDto from(List<ImagePathElement> path) {
        return PostImageUploadResponseDto.builder()
                .path(path)
                .build();
    }
}
