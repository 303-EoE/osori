import 'dart:convert';
import 'dart:io';

import 'package:google_sign_in/google_sign_in.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';
import 'package:http/http.dart' as http;

class SocialApiService {
  static Future<String> loginWithGoogle() async {
    try {
      final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();
      return googleUser?.displayName ?? "";
    } catch (error) {
      print(error);
      throw Error();
    }
  }

  static Future<String> loginWithKakao() async {
    try {
      bool isInstalled = await isKakaoTalkInstalled();

      OAuthToken token = isInstalled
          ? await UserApi.instance.loginWithKakaoTalk()
          : await UserApi.instance.loginWithKakaoAccount();

      final url = Uri.https('kapi.kakao.com', '/v2/user/me');

      final response = await http.get(
        url,
        headers: {
          HttpHeaders.authorizationHeader: 'Bearer ${token.accessToken}'
        },
      );

      final profileInfo = json.decode(response.body);
      // print("**************************************************");
      // print(profileInfo.toString());
      // print("**************************************************");
      return profileInfo?.toString() ?? "";
    } catch (error) {
      print('카카오톡으로 로그인 실패 $error');
      throw Error();
    }
  }
}
