import 'dart:async';
import 'package:flutter/material.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';
import 'package:osori/widgets/common/token_manager.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  Future<void> verify() async {
    await TokenManager.renewDevicePosition();
    await TokenManager.renewDeviceDepths();
    final result = await TokenManager.verifyToken();
    if (mounted) {
      if (result == 'login need') {
        SnackBarManager.alertSnackBar(context, '환영합니다. 어서오세요!');
      } else {
        SnackBarManager.welcomeSnackBar(
            context, await TokenManager.readUserNickname());
      }
    }
    if (mounted) {
      debugPrint('1초 뒤 이동');
      Timer(const Duration(seconds: 1), () {
        Navigator.of(context)
            .pushNamedAndRemoveUntil('/review', (route) => false);
      });
    }
  }

  @override
  void initState() {
    super.initState();
    verify();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Scaffold(
      body: Container(
          width: size.width,
          height: size.height,
          decoration: const BoxDecoration(
            color: Color(0xFF3a393a),
          ),
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset(
                  'assets/images/288X288.png',
                  width: size.width / 3,
                ),
                const Text(
                  '오소리',
                  style: TextStyle(
                      fontSize: 32,
                      fontWeight: FontWeight.w600,
                      color: Color(0xFFf9f8f8)),
                ),
                const Text(
                  '현명한 소비 습관을 위한 선택',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.w600,
                    color: Color(0xFFf9f8f8),
                  ),
                ),
              ],
            ),
          )),
    );
  }
}
