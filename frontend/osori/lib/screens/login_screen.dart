import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
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
    Navigator.of(context).pushNamedAndRemoveUntil('/review', (route) => false);
  }

  void loginWithService(String serviceName, Size size) async {
    final loginResult = await AuthService.loginWithSocialService(serviceName);

    if (!mounted) return;

    if (loginResult['nickname'] == '') {
      SnackBarManager.alertSnackBar(context, '로그인 실패!');
    } else if (loginResult['nickname'] == "null") {
      debugPrint('로그인 하기!');
      // 회원가입
      showDialog(
        context: context,
        builder: (context) {
          final formKey = GlobalKey<FormBuilderState>();
          String? selectedImage;
          return Dialog(
            surfaceTintColor: Colors.white,
            child: SizedBox(
              width: size.width / 2,
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Text(
                    '회원가입',
                    style: TextStyle(
                      fontSize: 32,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      const Text('닉네임*'),
                      SizedBox(
                        width: 150,
                        child: FormBuilder(
                          key: formKey,
                          child: FormBuilderTextField(
                            name: 'nickname',
                            decoration: const InputDecoration(
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20),
                                ),
                              ),
                              labelText: '닉네임',
                            ),
                            validator: FormBuilderValidators.compose([
                              FormBuilderValidators.required(),
                            ]),
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      const Text('프로필 이미지'),
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
                        icon: const Icon(
                          Icons.image_outlined,
                          size: 32,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(
                    height: 30,
                  ),
                  OutlinedButton(
                    onPressed: () async {
                      // Validate and save the form values
                      formKey.currentState?.saveAndValidate();
                      debugPrint(formKey.currentState?.value.toString());
                      formKey.currentState?.validate();
                      debugPrint(formKey.currentState?.instantValue.toString());
                      var formValues = formKey.currentState;
                      // 프로바이더ID,닉네임, 이미지(String)으로 회원가입을 요청하는 API
                      final result = await AuthService.registerUserInfo(
                          loginResult['providerId'],
                          formValues?.value['nickname'],
                          selectedImage!.isNotEmpty
                              ? File(selectedImage!)
                              : null);
                      if (mounted) {
                        if (result != "") {
                          SnackBarManager.welcomeSnackBar(context, result);

                          goToProfile();
                        } else {
                          SnackBarManager.alertSnackBar(context, '회원가입 실패!');
                        }
                      }
                    },
                    child: const Text('회원가입'),
                  )
                ],
              ),
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
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      appBar: AppBar(
        title: const Text('로그인'),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'OSORI에 가입하고',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.w500,
              ),
            ),
            const SizedBox(
              height: 20,
            ),
            const Text(
              '다양한 기능을 누려보세요!',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.w500,
              ),
            ),
            const SizedBox(
              height: 50,
            ),
            GestureDetector(
              onTapUp: (details) {
                loginWithService(google, size);
              },
              child: Image.asset(
                'assets/images/continue_with_google.png',
                width: 180,
              ),
            ),
            const SizedBox(
              height: 20,
            ),
            GestureDetector(
              onTapUp: (details) async {
                loginWithService(kakao, size);
              },
              child: Image.asset(
                'assets/images/kakao_login_medium_narrow.png',
                width: 180,
              ),
            ),
            const SizedBox(
              height: 50,
            ),
            OutlinedButton(
                onPressed: () {
                  Navigator.of(context)
                      .pushNamedAndRemoveUntil('/review', (route) => false);
                },
                child: const Text('메인으로'))
          ],
        ),
      ),
    );
  }
}
