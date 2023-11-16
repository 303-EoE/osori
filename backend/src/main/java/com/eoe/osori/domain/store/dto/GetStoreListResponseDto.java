package com.eoe.osori.domain.store.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetStoreListResponseDto {
	List<StoreElement> stores;
	Integer restaurantAveragePrice;
	Integer fitnessCenterAveragePrice;
	Integer nailShopAveragePrice;

	public void updateRestaurantAveragePrice(Integer restaurantAveragePrice) {
		this.restaurantAveragePrice = restaurantAveragePrice;
	}

	public void updateFitnessCenterAveragePrice(Integer fitnessCenterAveragePrice) {
		this.fitnessCenterAveragePrice = fitnessCenterAveragePrice;
	}

	public void updateNailShopAveragePrice(Integer nailShopAveragePrice) {
		this.nailShopAveragePrice = nailShopAveragePrice;
	}

	public GetStoreListResponseDto() {
		this.stores = new ArrayList<>();
		this.restaurantAveragePrice = 0;
		this.fitnessCenterAveragePrice = 0;
		this.nailShopAveragePrice = 0;
	}
}
