import 'dart:convert';
import 'dart:io';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:http_parser/http_parser.dart';
import 'package:osori/widgets/common/token_manager.dart';

class MemberService {
  static const String baseUrl = 'https://osori.co.kr/members';

  static Future<Map<String, dynamic>?> getMemberProfile(int memberId) async {
    try {
      var dio = Dio();
      final url = '$baseUrl?member_id=$memberId';
      final response = await dio.get(url);
      debugPrint('response : ${response.data['data']}');
      await TokenManager.renewUserInfo({
        'id': response.data['data']['id'],
        'nickname': response.data['data']['nickname'],
        'profileImageUrl': response.data['data']['profileImageUrl'],
      });
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
      return null;
    }
  }

  static Future<int> updateProfile(
      String? nickname, File? image, bool useDefault) async {
    try {
      final result = await TokenManager.verifyToken();
      if (result == 'login need') {
        debugPrint('문제가 있어');
        return -1;
      }
      final memberId = await TokenManager.readUserId();
      const url = baseUrl;
      var dio = Dio();
      debugPrint(memberId);
      debugPrint(nickname);
      debugPrint(image?.path);
      debugPrint('$useDefault');
      var formData = FormData.fromMap({
        if (image != null && !useDefault)
          'profileImage': MultipartFile.fromFileSync(image.path),
        'patchMemberRequestDto': MultipartFile.fromString(
            jsonEncode({
              'memberId': memberId,
              'isDefaultImage': useDefault,
              if (nickname != null) 'nickname': nickname,
            }),
            contentType: MediaType.parse('application/json'))
      });
      final response = await dio.patch(url, data: formData);
      return response.statusCode!;
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      return -1;
    }
  }
}
