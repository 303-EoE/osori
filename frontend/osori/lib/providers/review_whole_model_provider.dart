import 'package:dio/dio.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'review_whole_model_provider.g.dart';

const String baseUrl = "https://test.osori.co.kr/reviews";

@riverpod
Future<List<ReviewWholeModel>> reviewWholeLocalModel(
    ReviewWholeLocalModelRef ref, String depth1, String depth2) async {
  List<ReviewWholeModel> reviewInstances = [];
  final token = TokenManager.readAccessToken();
  var dio = Dio();
  dio.options.headers = {"Authorization": token};
  final url = '$baseUrl/region?depth1=$depth1&depth2=$depth2';
  final response = await dio.get(url);
  if (response.statusCode == 200) {
    final reviews = response.data['data']['reviews'];
    for (var review in reviews) {
      reviewInstances.add(ReviewWholeModel.fromJson(review));
    }
    return reviewInstances;
  }
  throw Error();
}

@riverpod
Future<List<ReviewWholeModel>> reviewWholeMyModel(
    ReviewWholeMyModelRef ref) async {
  List<ReviewWholeModel> reviewInstances = [];
  final token = TokenManager.readAccessToken();
  var dio = Dio();
  dio.options.headers = {"Authorization": token};
  const url = '$baseUrl/my-review';
  final response = await dio.get(url);
  if (response.statusCode == 200) {
    final reviews = response.data['data']['reviews'];
    for (var review in reviews) {
      reviewInstances.add(ReviewWholeModel.fromJson(review));
    }
    return reviewInstances;
  }
  throw Error();
}

@riverpod
Future<List<ReviewWholeModel>> reviewWholeLikedModel(
    ReviewWholeLikedModelRef ref) async {
  List<ReviewWholeModel> reviewInstances = [];
  final token = TokenManager.readAccessToken();
  var dio = Dio();
  dio.options.headers = {"Authorization": token};
  const url = '$baseUrl/like';
  final response = await dio.get(url);
  if (response.statusCode == 200) {
    final reviews = response.data['data']['reviews'];
    for (var review in reviews) {
      reviewInstances.add(ReviewWholeModel.fromJson(review));
    }
    return reviewInstances;
  }
  throw Error();
}
