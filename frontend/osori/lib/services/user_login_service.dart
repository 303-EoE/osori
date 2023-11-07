import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:osori/services/social_login_service.dart';
import 'package:osori/token_manager.dart';

class UserLoginService {
  static const String baseUrl = "https://osori.co.kr/auth";
  static final Map<String, String> headers = {
    'content-type': "application/json;charset=UTF-8",
  };
  static const String google = "Google";
  static const String kakao = "Kakao";

  static Future<String> loginWithSocialService(String serviceName) async {
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
      // 기존 회원(유효한 providerId, nickname 존재)
      // 토큰 2개, 닉네임 받아오니깐 그걸로 provider 초기화하기
      final url = Uri.parse('$baseUrl/login');
      final response = await http.post(url, headers: headers, body: {
        provider: provider,
        providerId: providerId,
      });
      final json = jsonDecode(response.body);
      // secure storage에 토큰 저장하기
      TokenManager.renewAllToken(json['accessToken'], json['refreshToken']);
      return json['nickname'];
    } catch (error) {
      print(error);
      return "";
    }
  }
}
