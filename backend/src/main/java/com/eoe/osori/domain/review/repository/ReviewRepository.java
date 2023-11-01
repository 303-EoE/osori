package com.eoe.osori.domain.review.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eoe.osori.domain.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	Boolean existsByStoreIdAndPaidAt(Long storeId, LocalDateTime paidAt);


}
