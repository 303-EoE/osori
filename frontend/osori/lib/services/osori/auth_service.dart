import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:http_parser/http_parser.dart';
import 'package:osori/services/other/social_login_service.dart';
import 'package:osori/widgets/common/token_manager.dart';

class AuthService {
  static const String baseUrl = "https://test.osori.co.kr/auth";
  static const String google = "Google";
  static const String kakao = "Kakao";
  // 로그인/회원가입
  static Future<Map<String, dynamic>> loginWithSocialService(
      String serviceName) async {
    try {
      late String provider;
      late String providerId;

      switch (serviceName) {
        case google:
          provider = google;
          providerId = await SocialLoginService.loginWithGoogle();
          break;
        case kakao:
          provider = kakao;
          providerId = await SocialLoginService.loginWithKakao();
      }
      var dio = Dio();
      const url = baseUrl;
      final response = await dio.post(url, data: {
        'provider': provider,
        'providerId': providerId,
      });
      final json = response.data['data'];
      if (json['nickname'] == null) {
        return {
          'providerId': providerId,
          'nickname': 'null',
        };
      } else {
        await TokenManager.renewAllToken(
            json['accessToken'], json['refreshToken']);
        await getUserInfo(); // 저장소에 회원정보 저장하기
        return {
          'providerId': providerId,
          'nickname': json['nickname'],
        };
      }
    } catch (error) {
      debugPrint('$error');
      return {'nickname': ""};
    }
  }

  // 토큰으로 회원 정보 조회
  static Future<void> getUserInfo() async {
    final token = await TokenManager.readAccessToken();
    var dio = Dio();
    const url = '$baseUrl/info';
    final response = await dio.post(url, data: {
      'accessToken': token,
    });
    if (response.statusCode == 200) {
      TokenManager.renewUserInfo(response.data); // 회원정보 스토리지에 저장
    }
  }

  // 회원정보 신규 등록
  static Future<int> registerUserInfo(
      String providerId, String nickname, File? profileImage) async {
    try {
      var dio = Dio();
      const url = '$baseUrl/profile';
      var formData = FormData.fromMap({
        if (profileImage != null)
          'profileImageUrl': MultipartFile.fromFileSync(profileImage.path),
        'nickname': MultipartFile.fromString(
            jsonEncode({
              'providerId': providerId,
              'nickname': nickname,
            }),
            contentType: MediaType.parse('application/json')),
      });
      final response = await dio.post(url, data: formData);
      return response.statusCode ?? -1;
    } catch (error) {
      debugPrint('$error');
      return -1;
    }
  }
}
