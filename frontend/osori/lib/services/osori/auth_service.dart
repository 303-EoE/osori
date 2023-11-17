import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:http_parser/http_parser.dart';
import 'package:osori/services/other/social_login_service.dart';
import 'package:osori/widgets/common/token_manager.dart';

class AuthService {
  static const String baseUrl = "https://osori.co.kr/auth";
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
      const url = '$baseUrl/login';
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
        // 재로그인 완료!
        await TokenManager.renewAllToken(
            json['accessToken'], json['refreshToken']);
        await getUserInfo(json['accessToken']); // 저장소에 회원정보 저장하기
        return {
          'nickname': json['nickname'],
        };
      }
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      return {'nickname': ""};
    }
  }

  // 토큰으로 회원 정보 조회
  static Future<Map<String, dynamic>?> getUserInfo(String? token) async {
    try {
      var dio = Dio();
      token ??= await TokenManager.readAccessToken();
      dio.options.headers = {"Authorization": 'Bearer $token'};
      const url = '$baseUrl/info';
      final response = await dio.get(url);
      TokenManager.renewUserInfo(response.data['data']); // 회원정보 스토리지에 저장
      return {
        'id': response.data['data']['id'],
        'nickname': response.data['data']['nickname'],
        'profileImageUrl': response.data['data']['profileImageUrl'],
      };
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

  // 회원정보 신규 등록
  static Future<String> registerUserInfo(
      String providerId, String nickname, File? profileImage) async {
    try {
      var dio = Dio();
      const url = '$baseUrl/profile';
      var formData = FormData.fromMap({
        if (profileImage != null)
          'profileImage': MultipartFile.fromFileSync(profileImage.path),
        'postAuthProfileRequestDto': MultipartFile.fromString(
            jsonEncode({
              'providerId': providerId,
              'nickname': nickname,
            }),
            contentType: MediaType.parse('application/json')),
      });
      final response = await dio.post(url, data: formData);
      if (response.statusCode == 200) {
        await TokenManager.renewAllToken(
          response.data['data']['accessToken'],
          response.data['data']['refreshToken'],
        );
      }
      return nickname;
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

  static Future<String> renewAccessToken(String refreshToken) async {
    try {
      var dio = Dio();
      dio.options.headers = {"Authorization": 'Bearer $refreshToken'};
      const url = '$baseUrl/token/refresh';
      final response = await dio.get(url);
      return response.data['data']['accessToken'];
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      return "";
    }
  }

  static Future<int> logout() async {
    try {
      var dio = Dio();
      final accessToken = await TokenManager.readAccessToken();
      dio.options.headers = {"Authorization": "Bearer $accessToken"};
      const url = '$baseUrl/token/logout';
      final response = await dio.get(url);
      if (response.statusCode == 200) {
        await TokenManager.deleteAll();
        await TokenManager.renewDevicePosition();
        await TokenManager.renewDeviceDepths();
      }
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
      return -1;
    }
  }
}
