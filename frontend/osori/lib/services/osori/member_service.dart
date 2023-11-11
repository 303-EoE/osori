import 'dart:convert';
import 'dart:io';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:http_parser/http_parser.dart';
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

  static Future<int> updateProfile(
      String nickname, File? image, bool useDefault) async {
    try {
      final token = await TokenManager.readAccessToken();
      const url = baseUrl;
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      var formData = FormData.fromMap({
        if (image != null)
          'profileImage': MultipartFile.fromFileSync(image.path),
        'dto': MultipartFile.fromString(
            jsonEncode({
              'nickname': nickname,
              'isDefaultImage': useDefault,
            }),
            contentType: MediaType.parse('application/json'))
      });
      final response = await dio.patch(url, data: formData);
      return response.statusCode ?? -1;
    } catch (error) {
      debugPrint(error.toString());
      return -1;
    }
  }
}
