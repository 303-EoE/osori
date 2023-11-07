package com.eoe.osori.global.common.api.images;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "images", url = "${api.images}")
public interface ImageApi {

}
