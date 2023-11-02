package com.eoe.osori.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoe.osori.domain.review.domain.LikeReview;

@Repository
public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

	Boolean existsByReviewIdAndMemberId(Long reviewId, Long memberId);
	void deleteByReviewIdAndMemberId(Long reviewId, Long memberId);

}
