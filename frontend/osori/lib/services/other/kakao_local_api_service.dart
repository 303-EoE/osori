import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:http/http.dart' as http;

class KakaoLocalApiService {
  static const String baseUrl = 'https://dapi.kakao.com/v2/local';
  static final String apiKey = dotenv.get('KAKAO_REST_API_KEY');
  static final Map<String, String> headers = {
    'content-type': "application/json;charset=UTF-8",
    "Authorization": 'KakaoAK $apiKey',
  };

  static Future<List<KakaoStoreModel>> getStoresByKeyword(
      String keyword, String x, String y) async {
    try {
      List<KakaoStoreModel> storeInstances = [];
      final url = Uri.parse(
          '$baseUrl/search/keyword.json?query=$keyword&x=$x&y=$y&radius=2000');
      final response = await http.get(url, headers: headers);
      final stores = jsonDecode(response.body)['documents'];
      for (var store in stores) {
        storeInstances.add(KakaoStoreModel.fromJson(store));
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

  static Future<Map<String, String>> getDepthByPosition(
      String x, String y) async {
    try {
      final url = Uri.parse('$baseUrl/geo/coord2address.json?x=$x&y=$y');
      final response = await http.get(url, headers: headers);

      final addresses = jsonDecode(response.body)['documents'][0]['address'];
      return {
        'depth1': addresses['region_1depth_name'],
        'depth2': addresses['region_2depth_name'],
      };
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
