import 'package:dio/dio.dart';
import 'package:osori/models/store/store_description_model.dart';
import 'package:osori/models/store/store_model.dart';
import 'package:osori/widgets/common/token_manager.dart';

class StoreService {
  static const String baseUrl = "https://test.osori.co.kr/stores";

  static Future<List<StoreModel>> getNearStores(
      String depth1, String depth2) async {
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
      return storeInstances;
    }
    throw Error();
  }

  static Future<StoreDescriptionModel> getStoreDescription(int storeId) async {
    final token = TokenManager.readAccessToken();
    var dio = Dio();
    dio.options.headers = {"Authorization": token};
    final url = '$baseUrl/detail?store_id=$storeId';
    final response = await dio.get(url);
    if (response.statusCode == 200) {
      return response.data['data'];
    }
    throw Error();
  }
}
