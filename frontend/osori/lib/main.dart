import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';
import 'package:kakao_map_plugin/kakao_map_plugin.dart';
import 'package:osori/error/osori_provider_observer.dart';
import 'package:osori/screens/chat_screen.dart';
import 'package:osori/screens/review_screen.dart';
import 'package:osori/screens/login_screen.dart';
import 'package:osori/screens/map_screen.dart';
import 'package:osori/screens/profile_screen.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // runApp() 호출 전 Flutter SDK 초기화
  await dotenv.load(fileName: 'assets/env/.env');
  AuthRepository.initialize(appKey: dotenv.env['KAKAO_JAVASCRIPT_KEY'] ?? '');
  KakaoSdk.init(
    nativeAppKey: dotenv.env['KAKAO_NATIVE_APP_KEY'],
  );
  runApp(ProviderScope(
    observers: [
      OsoriProviderObserver(),
    ],
    child: const Osori(),
  ));
}

class Osori extends StatelessWidget {
  const Osori({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        "/map": (context) => const MapScreen(),
        "/chat": (context) => const ChatScreen(),
        "/profile": (context) => const ProfileScreen(),
        "/login": (context) => const LoginScreen(),
      },
      title: 'OSORI',
      theme: ThemeData(
        fontFamily: 'Roboto',
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.white),
        useMaterial3: true,
      ),
      home: const ReviewScreen(),
    );
  }
}
