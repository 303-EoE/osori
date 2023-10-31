package com.eoe.osori.global.common.image.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostImageRemovalRequestDto {
    private List<String> imageUrls;
}
