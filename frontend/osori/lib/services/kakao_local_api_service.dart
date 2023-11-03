import 'dart:convert';

import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:http/http.dart' as http;

class KakaoLocalApiService {
  static const String baseUrl = 'https://dapi.kakao.com/v2/local/search';
  static final String apiKey = dotenv.get('KAKAO_LOCAL_REST_API_KEY');
  static final Map<String, String> headers = {
    'content-type': "application/json;charset=UTF-8",
    "Authorization": 'KakaoAK $apiKey',
  };

  static Future<List<KakaoStoreModel>> getStoresByKeyword(
      String keyword, String x, String y) async {
    List<KakaoStoreModel> storeInstances = [];
    final url =
        Uri.parse('$baseUrl/keyword.json?query=$keyword&x=$x&y=$y&radius=2000');
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
}
