class KakaoStoreModel {
  final String id,
      placeName,
      categoryName,
      categoryGroupCode,
      categoryGroupName,
      phone,
      addressName,
      roadAddressName,
      x,
      y,
      placeUrl,
      distance;

  KakaoStoreModel.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        placeName = json['place_name'],
        categoryName = json['category_name'],
        categoryGroupCode = json['category_group_code'],
        categoryGroupName = json['category_group_name'],
        phone = json['phone'],
        addressName = json['address_name'],
        roadAddressName = json['road_address_name'],
        x = json['x'],
        y = json['y'],
        placeUrl = json['place_url'],
        distance = json['distance'];
}
