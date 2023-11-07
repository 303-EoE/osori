import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:osori/services/user_login_service.dart';

/// bottomNavigation에서 미리 토큰 유효성을 검증하기 때문에
/// 여기서는 로그인 과정에만 집중하면 된다.

class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  static const String google = "Google";
  static const String kakao = "Kakao";
  void goToProfile() {
    Navigator.of(context).pushNamedAndRemoveUntil('/profile', (route) => false);
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

  void alertSnackBar(String message) {
    final snackBar = SnackBar(
      content: Text(message),
      action: SnackBarAction(
        label: '확인',
        onPressed: () {
          // Some code to undo the change.
        },
      ),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  void loginWithService(String serviceName) async {
    // 요청과 동시에 프로바이더에 저장
    final result = UserLoginService.loginWithSocialService(serviceName);

    if (result.toString() != "") {
      // 스낵바 만들기
      createSnackBar(result.toString());
      // 프로필로 가기
      goToProfile();
    } else {
      alertSnackBar('로그인 실패!');
    }
  }
  // 로그아웃
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
            const Text('OSORI에 가입하고 다양한 기능을 누려보세요!'),
            const SizedBox(
              height: 50,
            ),
            GestureDetector(
              onTapUp: (details) {
                loginWithService(google);
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
                loginWithService(kakao);
              },
              child: Image.asset('assets/images/continue_with_kakao.png'),
            ),
          ],
        ),
      ),
    );
  }
}
