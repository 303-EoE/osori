import 'dart:convert';
import 'package:osori/models/user/user_info_model.dart';
import 'package:http/http.dart' as http;
import 'package:osori/token_manager.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'user_info_model_provider.g.dart';

const String baseUrl = "https://osori.co.kr/members";

@riverpod
Future<UserInfoModel> userInfoModel(UserInfoModelRef ref) async {
  final token = TokenManager.readAccessToken();
  final Map<String, String> headers = {
    'content-type': "application/json;charset=UTF-8",
    "Authorization": token.toString(),
  };

  final url = Uri.parse('$baseUrl/my-page');
  final response = await http.get(url, headers: headers);

  return UserInfoModel.fromJson(jsonDecode(response.body));
}
