import 'dart:convert';
import 'dart:io';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';
import 'package:http/http.dart' as http;

// 로그아웃
// await GoogleSignIn().signOut();
// await UserApi.instance.logout();

class SocialLoginService {
  // 1. 로그인을 한다
  // 2. 서비스별 고유 id를 받아온다
  // 3-1. 받아온 id를 서버에 보내서 토큰과 닉네임을 받아온다
  // 3-2. 닉네임이 없는 경우 회원정보 설정을 마무리해서 서버에 보낸다.
  // 4. 토큰은 secure store에 저장하고 닉네임은 리턴한다.
  static Future<String> loginWithGoogle() async {
    try {
      final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();
      return googleUser?.id.toString() ?? "";
    } catch (error) {
      print('구글 로그인 실패 $error');
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

      return profileInfo['id'].toString();
    } catch (error) {
      print('카카오 로그인 실패 $error');
      throw Error();
    }
  }
}
