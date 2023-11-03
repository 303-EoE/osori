package com.eoe.osori.domain.review.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eoe.osori.domain.review.domain.ReviewFeed;

@Repository
public interface ReviewFeedRepository extends MongoRepository<ReviewFeed, String> {

	List<ReviewFeed> findAllByStoreDepth1AndStoreDepth2(String storeDepth1, String storeDepth2);

}
