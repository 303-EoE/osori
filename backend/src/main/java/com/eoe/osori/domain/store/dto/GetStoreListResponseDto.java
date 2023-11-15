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
	Integer averagePrice;

	public void updateAveragePrice(Integer averagePrice) {
		this.averagePrice = averagePrice;
	}

	public GetStoreListResponseDto() {
		this.stores = new ArrayList<>();
		this.averagePrice = 0;
	}
}
