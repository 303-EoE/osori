import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:geolocator/geolocator.dart';
import 'package:osori/services/osori/auth_service.dart';
import 'package:osori/services/other/device_position_service.dart';
import 'package:osori/services/other/kakao_local_api_service.dart';

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

  static Future<void> deleteUserInfo() async {
    await storage.delete(key: 'id');
    await storage.delete(key: 'nickname');
    await storage.delete(key: 'profileImageUrl');
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
    return value ?? "0";
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
      debugPrint('엑세스 토큰 없음');
      await deleteUserInfo();
      return "login need";
    }
    debugPrint('유저 정보 가져오기');
    Map<String, dynamic>? memberInfo =
        await AuthService.getUserInfo(tAccessToken);
    if (memberInfo == null) {
      debugPrint('액세스 토큰이 만료됨');
      // 토큰이 유효하지 않음 - 리프레시 토큰으로 엑세스 토큰을 갱신
      final tRefreshToken = await readRefreshToken();
      if (tRefreshToken == "") {
        // 리프레시 토큰이 없으면 - 로그인을 해야지
        debugPrint('리프레시 토큰이 없음');
        await deleteUserInfo();
        return "login need";
      } else {
        debugPrint('리프레시 토큰으로 액세스 토큰 갱신하기');
        final rAccessToken = await AuthService.renewAccessToken(tRefreshToken);
        if (rAccessToken == "") {
          // 리프레시 토큰이 만료되었으면 - 로그인
          debugPrint('리프레시 토큰이 만료됨');
          await deleteUserInfo();
          return 'login need';
        } else {
          debugPrint('액세스 토큰으로 얻은 정보를 새로 저장');
          await renewAccessToken(rAccessToken); // 새로 저장
          memberInfo = await AuthService.getUserInfo(rAccessToken);
        }
      }
    }
    // 멤버 인포 무적권 있어
    await renewUserInfo(memberInfo!);
    return "renew success";
  }

  static Future<void> renewDevicePosition() async {
    Position pos = await DevicePostionService.getNowPosition();

    await storage.write(
        key: 'lat', value: '${pos.latitude}', aOptions: getAndroidOptions());
    await storage.write(
        key: 'lon', value: '${pos.longitude}', aOptions: getAndroidOptions());
  }

  static Future<Map<String, String?>> readDevicePosition() async {
    final lat = await storage.read(key: 'lat', aOptions: getAndroidOptions());
    final lon = await storage.read(key: 'lon', aOptions: getAndroidOptions());
    return {
      'lat': lat,
      'lon': lon,
    };
  }

  static Future<void> renewDeviceDepths() async {
    final pos = await readDevicePosition();
    final depths = await KakaoLocalApiService.getDepthByPosition(
        '${pos['lon']}', '${pos['lat']}');
    await storage.write(
        key: 'depth1',
        value: '${depths['depth1']}',
        aOptions: getAndroidOptions());
    await storage.write(
        key: 'depth2',
        value: '${depths['depth2']}',
        aOptions: getAndroidOptions());
  }

  static Future<Map<String, String?>> readDeviceDepths() async {
    final depth1 =
        await storage.read(key: 'depth1', aOptions: getAndroidOptions());
    final depth2 =
        await storage.read(key: 'depth2', aOptions: getAndroidOptions());
    return {
      'depth1': depth1,
      'depth2': depth2,
    };
  }
}
