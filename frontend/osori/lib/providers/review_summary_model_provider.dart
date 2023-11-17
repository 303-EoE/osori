import 'dart:convert';
import 'package:osori/models/review/review_summary_model.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:http/http.dart' as http;
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'review_summary_model_provider.g.dart';

const String baseUrl = "https://osori.co.kr/reviews/store";

@riverpod
Future<List<ReviewSummaryModel>> reviewSummaryModel(
    ReviewSummaryModelRef ref, String storeId) async {
  List<ReviewSummaryModel> reviewInstances = [];
  final token = TokenManager.readAccessToken();
  final Map<String, String> headers = {
    "Authorization": "Bearer $token",
  };
  final url = Uri.parse('$baseUrl?store_id=$storeId');
  final response = await http.get(url, headers: headers);
  if (response.statusCode == 200) {
    final reviews =
        jsonDecode(utf8.decode(response.bodyBytes))['data']['reviews'];
    for (var review in reviews) {
      reviewInstances.add(ReviewSummaryModel.fromJson(review));
    }
    return reviewInstances;
  }
  throw Error();
}
