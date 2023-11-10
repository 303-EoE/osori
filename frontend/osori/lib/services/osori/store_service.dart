import 'package:dio/dio.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:osori/widgets/common/token_manager.dart';

class StoreService {
  static const String baseUrl = "https://test.osori.co.kr/stores";

  static Future<int> getStoreId(KakaoStoreModel model) async {
    try {
      final token = await TokenManager.readAccessToken();
      var dio = Dio();
      dio.options.headers = {'Authorization': token};

      const url = baseUrl;
      final response = await dio.post(url, data: {
        "name": model.placeName, // String
        "kakaoId": model.id, // String
        "category": model.categoryName, // String
        "longitude": model.x, // String
        "latitude": model.y, // String
        "roadAddressName": model.roadAddressName, // String
        "addressName": model.addressName, // String
        "phone": model.phone
      });
      if (response.statusCode == 200) {
        print(response);
        return response.data['data']['id'];
      }
      return -1;
    } catch (error) {
      print(error);
      return -1;
    }
  }
}
