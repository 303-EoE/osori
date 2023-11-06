import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';
import 'package:osori/services/social_api_service.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  static const GOOGLE = "GOOGLE";
  static const KAKAO = "KAKAO";

  void goToMain() {
    Navigator.of(context).pushNamedAndRemoveUntil('/', (route) => false);
  }

  void createSnackBar(String nickname) {
    final snackBar = SnackBar(
      content: Text('$nickname님 반가워요!'),
      action: SnackBarAction(
        label: '저두요!',
        onPressed: () {
          // Some code to undo the change.
        },
      ),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  void login(String loginService) async {
    late String nickname;
    switch (loginService) {
      case GOOGLE:
        nickname = await SocialApiService.loginWithGoogle();
        break;
      case KAKAO:
        nickname = await SocialApiService.loginWithKakao();
        break;
    }
    if (nickname == "") {
      print('다시 로그인하세요');
      return;
    }
    // 스낵바 만들기
    createSnackBar(nickname);
    goToMain();
  }
  // await GoogleSignIn().signOut();
  // await UserApi.instance.logout();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('로그인'),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('OSORI에 가입하고 다양한 기능을 활용해보세요!'),
            const SizedBox(
              height: 50,
            ),
            GestureDetector(
              onTapUp: (details) async {
                login(GOOGLE);
              },
              child: Image.asset(
                'assets/images/continue_with_google.png',
                width: 200,
              ),
            ),
            const SizedBox(
              height: 50,
            ),
            GestureDetector(
              onTapUp: (details) async {
                login(KAKAO);
              },
              child: Image.asset('assets/images/continue_with_kakao.png'),
            ),
            const SizedBox(
              height: 50,
            ),
            IconButton(
              onPressed: () {},
              icon: const Icon(Icons.logout),
            )
          ],
        ),
      ),
    );
  }
}
