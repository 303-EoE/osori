import 'dart:convert';
import 'dart:io';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:http_parser/http_parser.dart';
import 'package:osori/widgets/common/token_manager.dart';

class MemberService {
  static const String baseUrl = 'https://test.osori.co.kr/members';

  static Future<Map<String, dynamic>?> getMemberProfile(String memberId) async {
    try {
      final token = await TokenManager.readAccessToken();
      var dio = Dio();
      dio.options.headers = {"Authorization": 'Bearer $token'};
      final url = '$baseUrl/member_id=$memberId';
      final response = await dio.get(url);
      debugPrint(response.data['data']);
      return response.data['data'];
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
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
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
    }
  }
}
