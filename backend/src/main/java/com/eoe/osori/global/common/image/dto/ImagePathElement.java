package com.eoe.osori.global.common.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagePathElement {
    private String uploadFilePath;

    public static ImagePathElement from(String uploadFilePath) {
        return ImagePathElement.builder()
                .uploadFilePath(uploadFilePath)
                .build();
    }
}
