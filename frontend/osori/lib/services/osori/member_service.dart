import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:http/http.dart' as http;

class MemberService {
  static const String baseUrl = 'https://test.osori.co.kr/members';

  static Future<Map<String, dynamic>?> getMyProfile() async {
    try {
      final token = await TokenManager.readAccessToken();
      final Map<String, String> headers = {
        'content-type': "application/json;charset=UTF-8",
        "Authorization": token.toString(),
      };
      final url = Uri.parse('$baseUrl/my-page');
      final response = await http.get(url, headers: headers);
      return jsonDecode(response.body);
    } catch (error) {
      return null;
    }
  }

  static Future<int?> updateProfile(
      String nickname, String? image, bool useDefault) async {
    try {
      final token = await TokenManager.readAccessToken();
      // final Map<String, String> headers = {
      //   'content-type': 'multipart/form-data;charset=UTF-8',
      //   'Authorization': token.toString(),
      // };
      // final url = Uri.parse(baseUrl);
      const url = baseUrl;
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      var formData = FormData.fromMap({
        'nickname': nickname,
        'profileImage': await MultipartFile.fromFile(image!),
        'isDefaultImage': useDefault,
      });
      final response = await dio.patch(url, data: formData);
      print(response.statusCode);
      return response.statusCode;
    } catch (error) {
      debugPrint(error.toString());
      return null;
    }
  }
}
