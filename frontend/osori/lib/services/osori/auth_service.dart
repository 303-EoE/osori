import 'package:dio/dio.dart';
import 'package:osori/services/other/social_login_service.dart';
import 'package:osori/widgets/common/token_manager.dart';

class AuthService {
  static const String baseUrl = "https://test.osori.co.kr/auth";
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
      var dio = Dio();
      const url = baseUrl;
      final response = await dio.post(url, data: {
        'provider': provider,
        'providerId': providerId,
      });
      final json = response.data['data'];
      if (json['nickname'] == null) {
        return 'nickname null';
      } else {
        TokenManager.renewAllToken(json['accessToken'], json['refreshToken']);
        return json['nickname'];
      }
    } catch (error) {
      print(error);
      return "";
    }
  }
}
