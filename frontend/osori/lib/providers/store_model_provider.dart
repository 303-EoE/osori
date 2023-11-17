import 'package:dio/dio.dart';
import 'package:osori/models/store/store_model.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'store_model_provider.g.dart';

const String baseUrl = "https://test.osori.co.kr/store";

@riverpod
Future<List<StoreModel>> storeModel(
    StoreModelRef ref, String depth1, String depth2) async {
  List<StoreModel> storeInstances = [];
  final token = TokenManager.readAccessToken();
  var dio = Dio();
  dio.options.headers = {"Authorization": token};
  final url = '$baseUrl/region?depth1=$depth1&depth2=$depth2';
  final response = await dio.get(url);
  if (response.statusCode == 200) {
    final stores = response.data['data']['reviews'];
    for (var store in stores) {
      storeInstances.add(StoreModel.fromJson(store));
    }
    return storeInstances;
  }
  throw Error();
}
