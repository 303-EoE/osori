package com.eoe.osori.global.common.api.image;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.eoe.osori.global.common.api.image.dto.PostImageResponseDto;
import com.eoe.osori.global.common.response.EnvelopeResponse;

@FeignClient(name = "images", url = "${api.images}")
public interface ImageApi {
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	EnvelopeResponse<PostImageResponseDto> saveImages(@RequestPart(value = "multipartFiles") List<MultipartFile> multipartFiles);
}