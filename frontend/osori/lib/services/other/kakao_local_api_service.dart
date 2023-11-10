import 'dart:convert';

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
    List<KakaoStoreModel> storeInstances = [];
    final url = Uri.parse(
        '$baseUrl/search/keyword.json?query=$keyword&x=$x&y=$y&radius=2000');
    final response = await http.get(url, headers: headers);
    if (response.statusCode == 200) {
      final stores = jsonDecode(response.body)['documents'];
      for (var store in stores) {
        storeInstances.add(KakaoStoreModel.fromJson(store));
      }
      return storeInstances;
    }
    throw Error();
  }

  static Future<KakaoStoreModel?> getStoreDesciptionByKeyword(
      String keyword, String x, String y, String id) async {
    KakaoStoreModel? storeInstance;
    final url = Uri.parse(
        '$baseUrl/search/keyword.json?query=$keyword&x=$x&y=$y&radius=10000');
    final response = await http.get(url, headers: headers);

    if (response.statusCode == 200) {
      final stores = jsonDecode(response.body)['documents'];
      for (var store in stores) {
        if (store['id'] == id) {
          // id가 일치하는 가게 리턴
          storeInstance = KakaoStoreModel.fromJson(store);
        }
      }
      return storeInstance;
    }

    throw Error();
  }

  static Future<Map<String, String>?> getDepthByPosition(
      String x, String y) async {
    final url = Uri.parse('$baseUrl/geo/coord2address.json?x=$x&y=$y');
    final response = await http.get(url, headers: headers);

    if (response.statusCode == 200) {
      final addresses = jsonDecode(response.body)['documents'][0]['address'];
      return {
        'depth1': addresses['region_1depth_name'],
        'depth2': addresses['region_2depth_name'],
      };
    }
    throw Error();
  }
}
