import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:osori/models/store/store_description_model.dart';
import 'package:osori/models/store/store_model.dart';
import 'package:osori/widgets/common/token_manager.dart';

class StoreService {
  static const String baseUrl = "https://test.osori.co.kr/stores";

  static Future<List<StoreModel>> getNearStores(
      String depth1, String depth2) async {
    try {
      List<StoreModel> storeInstances = [];
      final token = await TokenManager.readAccessToken();
      var dio = Dio();
      dio.options.headers = {"Authorization": token};
      final url = '$baseUrl/region?depth1=$depth1&depth2=$depth2';
      final response = await dio.get(url);
      if (response.statusCode == 200) {
        final stores = response.data['data']['stores'];
        for (var store in stores) {
          storeInstances.add(StoreModel.fromJson(store));
        }
      }
      return storeInstances;
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
    }
  }

  static Future<int> registerStore(KakaoStoreModel model) async {
    try {
      final token = await TokenManager.readAccessToken();
      var dio = Dio();
      dio.options.headers = {"Authorization": token};
      const url = baseUrl;
      final response = await dio.post(url, data: {
        "name": model.placeName,
        "kakaoId": model.id,
        "category": model.categoryName,
        "longitude": model.x,
        "latitude": model.y,
        "roadAddressName": model.roadAddressName,
        "addressName": model.addressName,
        "phone": model.phone,
      });
      return response.data['data']['id'];
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
    }
  }

  static Future<StoreDescriptionModel?> getStoreDescription(int storeId) async {
    try {
      final token = TokenManager.readAccessToken();
      var dio = Dio();
      dio.options.headers = {"Authorization": token};
      final url = '$baseUrl/detail?store_id=$storeId';
      final response = await dio.get(url);
      return response.data['data'];
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
    }
  }
}
