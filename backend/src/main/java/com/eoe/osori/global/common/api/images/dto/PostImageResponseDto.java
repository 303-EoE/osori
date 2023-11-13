package com.eoe.osori.global.common.api.images.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostImageResponseDto {

	List<ImagePathElement> path;

	@Getter
	@Builder
	@AllArgsConstructor
	@RequiredArgsConstructor
	public static class ImagePathElement {

		private String uploadFilePath;
	}

}
