import 'package:flutter_secure_storage/flutter_secure_storage.dart';

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
        key: 'id', value: info['id'], aOptions: getAndroidOptions());
    await storage.write(
        key: 'nickname',
        value: info['nickname'],
        aOptions: getAndroidOptions());
    await storage.write(
        key: 'profileImageUrl',
        value: info['profileImageUrl'],
        aOptions: getAndroidOptions());
  }
}
