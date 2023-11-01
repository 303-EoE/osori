package com.eoe.osori.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoe.osori.domain.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	Boolean existsByKakaoId(String kakaoId);
}
