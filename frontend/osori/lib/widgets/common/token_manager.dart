import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:osori/services/osori/auth_service.dart';

class TokenManager {
  static const storage = FlutterSecureStorage();
  static AndroidOptions getAndroidOptions() => const AndroidOptions(
        encryptedSharedPreferences: true,
      );
  static const accessToken = 'accessToken';
  static const refreshToken = 'refreshToken';

  static Future<String> readAccessToken() async {
    final value = await storage.read(
      key: accessToken,
      aOptions: getAndroidOptions(),
    );
    return value ?? "";
  }

  static Future<String> readRefreshToken() async {
    final value = await storage.read(
      key: refreshToken,
      aOptions: getAndroidOptions(),
    );
    return value ?? "";
  }

  static Future<void> deleteAll() async {
    await storage.deleteAll();
  }

  static Future<void> renewAccessToken(String accessTokenValue) async {
    await storage.write(key: accessToken, value: accessTokenValue);
  }

  static Future<void> renewAllToken(
      String accessTokenValue, String refreshTokenValue) async {
    await storage.write(
      key: accessToken,
      value: accessTokenValue,
      aOptions: getAndroidOptions(),
    );

    await storage.write(
      key: refreshToken,
      value: refreshTokenValue,
      aOptions: getAndroidOptions(),
    );
  }

  static Future<String> readUserId() async {
    final value = await storage.read(key: 'id', aOptions: getAndroidOptions());
    return value ?? "";
  }

  static Future<String> readUserNickname() async {
    final value =
        await storage.read(key: 'nickname', aOptions: getAndroidOptions());
    return value ?? "";
  }

  static Future<String> readUserProfile() async {
    final value = await storage.read(
        key: 'profileImageUrl', aOptions: getAndroidOptions());
    return value ?? "";
  }

  static Future<void> renewUserInfo(Map<String, dynamic> info) async {
    await storage.write(
        key: 'id', value: info['id'].toString(), aOptions: getAndroidOptions());
    await storage.write(
        key: 'nickname',
        value: info['nickname'],
        aOptions: getAndroidOptions());
    await storage.write(
        key: 'profileImageUrl',
        value: info['profileImageUrl'],
        aOptions: getAndroidOptions());
  }

  static Future<String> verifyToken() async {
    // 1. secure storage에 저장된 엑세스 토큰을 가져온다
    // 2. 액세스 토큰이 유효한지 검증한다
    // 2-1. 토큰이 유효하면 멤버 정보를 갱신하고 id를 리턴한다
    // 2-2. 토큰이 만료되었으면 리프레시 토큰으로 액세스 토큰을 재발급 받는다
    // 2-2-1. 리프레시 토큰으로 액세스 토큰을 재발급 받았으면 새로 저장하고 2번을 다시 실행한다.
    // 2-2-2. 리프레시 토큰이 만료되었다면 secure스토리지를 비우고 로그인 화면으로 간다.
    final tAccessToken = await readAccessToken();
    if (tAccessToken == "") {
      // 엑세스 토큰 없음 - 로그인을 해야지
      debugPrint('엑세스 토큰 없음 - 로그인을 해야지');
      return "login need";
    }
    debugPrint('유저 정보 가져오기');
    Map<String, dynamic>? memberInfo =
        await AuthService.getUserInfo(tAccessToken);
    if (memberInfo == null) {
      debugPrint('액세스 토큰이 없음');
      // 토큰이 유효하지 않음 - 리프레시 토큰으로 엑세스 토큰을 갱신
      final tRefreshToken = await readRefreshToken();
      if (tRefreshToken == "") {
        debugPrint('리프레시 토큰이 없음');
        // 리프레시 토큰이 없으면 - 로그인을 해야지
        return "login need";
      } else {
        final rAccessToken = await AuthService.renewAccessToken(tRefreshToken);
        if (rAccessToken == "") {
          debugPrint('리프레시 토큰이 만료됨');
          // 리프레시 토큰이 만료되었으면 - 로그인
          return 'login need';
        } else {
          debugPrint('액세스 토큰으로 얻은 정보를 새로 저장');
          renewAccessToken(rAccessToken); // 새로 저장
          memberInfo = await AuthService.getUserInfo(rAccessToken);
        }
      }
    }
    // 멤버 인포 무적권 있어
    await renewUserInfo(memberInfo!);
    return "renew success";
  }
}
