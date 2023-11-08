import 'dart:convert';
import 'package:osori/models/store/store_review_summary_model.dart';
import 'package:osori/token_manager.dart';
import 'package:http/http.dart' as http;
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'store_review_summary_model_provider.g.dart';

const String baseUrl = "https://test.osori.co.kr/reviews/store";

@riverpod
Future<List<StoreReviewSummaryModel>> storeReviewSummaryModel(
    StoreReviewSummaryModelRef ref, String storeId) async {
  List<StoreReviewSummaryModel> reviewInstances = [];
  final token = TokenManager.readAccessToken();
  final Map<String, String> headers = {
    'content-type': "application/json;charset=UTF-8",
    "Authorization": token.toString(),
  };
  final url = Uri.parse('$baseUrl?store_id=$storeId');
  final response = await http.get(url, headers: headers);
  if (response.statusCode == 200) {
    final reviews = jsonDecode(response.body)['data']['reviews'];
    for (var review in reviews) {
      reviewInstances.add(StoreReviewSummaryModel.fromJson(review));
    }
    return reviewInstances;
  }
  throw Error();
}
