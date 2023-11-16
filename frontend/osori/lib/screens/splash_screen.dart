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
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: <Color>[
                Color(0xfff9f8f8),
                Color(0xfff7dad8),
              ], // Gradient from https://learnui.design/tools/gradient-generator.html
              tileMode: TileMode.mirror,
            ),
          ),
          child: Center(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '오늘의',
                      style: TextStyle(
                          fontSize: 32,
                          fontWeight: FontWeight.w800,
                          color: Color(0xFF323232)),
                    ),
                    Text(
                      '소비',
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.w800,
                        color: Color(0xFF323232),
                      ),
                    ),
                    Text(
                      '리스트',
                      style: TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.w800,
                        color: Color(0xFF323232),
                      ),
                    ),
                  ],
                ),
                const SizedBox(
                  width: 30,
                ),
                Image.asset(
                  'assets/images/logo.png',
                  width: size.width / 3,
                ),
              ],
            ),
          )),
    );
  }
}
