package com.eoe.osori.global.common.redis.repository;

import org.springframework.data.repository.CrudRepository;

import com.eoe.osori.global.common.redis.domain.StoreInfo;

public interface StoreInfoRedisRepository extends CrudRepository<StoreInfo, Long> {
}
