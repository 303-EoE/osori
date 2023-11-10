import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:osori/services/osori/auth_service.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';

/// profile스크린에서 initState단에서 토큰이 존재하는지, 토큰이 유효한지 검증한다.
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

  void loginWithService(String serviceName) async {
    // 요청과 동시에 프로바이더에 저장

    final nickname = await AuthService.loginWithSocialService(serviceName);
    if (nickname == '') {
      if (!mounted) return;
      SnackBarManager.alertSnackBar(context, '로그인 실패!!');
    } else if (nickname == "nickname null") {
      if (!mounted) return;
      SnackBarManager.alertSnackBar(context, '닉네임을 설정하는 모달 띄우기');
    } else {
      if (!mounted) return;
      SnackBarManager.welcomeSnackBar(context, nickname);
      // 프로필로 가기
      goToProfile();
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
            const SizedBox(
              height: 50,
            ),
            OutlinedButton(
                onPressed: () {
                  Navigator.of(context)
                      .pushNamedAndRemoveUntil('/', (route) => false);
                },
                child: const Text('메인으로'))
          ],
        ),
      ),
    );
  }
}
