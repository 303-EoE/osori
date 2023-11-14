import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:image_picker/image_picker.dart';
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
  int providerId = 0;
  void goToProfile() {
    Navigator.of(context).pushNamedAndRemoveUntil('/profile', (route) => false);
  }

  void loginWithService(String serviceName) async {
    final loginResult = await AuthService.loginWithSocialService(serviceName);

    if (!mounted) return;

    if (loginResult['nickname'] == '') {
      SnackBarManager.alertSnackBar(context, '로그인 실패!!');
    } else if (loginResult['nickname'] == "null") {
      SnackBarManager.alertSnackBar(context, '닉네임을 설정하는 모달 띄우기'); // 회원가입
      showDialog(
        context: context,
        builder: (context) {
          TextEditingController controller = TextEditingController();
          String? selectedImage;
          bool isNicknameChecked = false;
          return Dialog(
            child: Column(
              children: [
                Row(
                  children: [
                    const Text('닉네임'),
                    TextField(
                      controller: controller,
                    ),
                    OutlinedButton(
                        onPressed: () {
                          if (isNicknameChecked) {
                            // 닉네임 중복을 체크하는 요청
                            isNicknameChecked = true;
                          } else {
                            isNicknameChecked = false;
                          }
                        },
                        child: const Text('중복 체크'))
                  ],
                ),
                IconButton(
                  onPressed: () async {
                    final imagePicker = ImagePicker();
                    final pickedImage = await imagePicker.pickImage(
                      source: ImageSource.gallery,
                      imageQuality: 50,
                    );
                    if (pickedImage != null) {
                      selectedImage = pickedImage.path;
                    }
                  },
                  icon: const Icon(Icons.image_outlined),
                ),
                OutlinedButton(
                  onPressed: () async {
                    // 프로바이더ID,닉네임, 이미지(String)으로 회원가입을 요청하는 API
                    final result = await AuthService.registerUserInfo(
                        loginResult['providerId'],
                        loginResult['nickname'],
                        selectedImage!.isNotEmpty
                            ? File(selectedImage!)
                            : null);
                    if (result == 200) {
                      if (mounted) {
                        SnackBarManager.welcomeSnackBar(
                            context, loginResult['nickname']);
                      }
                    }
                  },
                  child: const Text('회원가입'),
                )
              ],
            ),
          );
        },
      );
    } else {
      SnackBarManager.welcomeSnackBar(context, loginResult['nickname']);
      // 프로필로 가기
      goToProfile();
    }
  }

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
