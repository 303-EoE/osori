import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:http_parser/http_parser.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/widgets/common/token_manager.dart';

class ReviewService {
  static const String baseUrl = "https://test.osori.co.kr/reviews";
  // 가게 리뷰 요약 조회
  static Future<List<Map<String, dynamic>>> getSumarrizedReviews(
      int storeId) async {
    try {
      final token = await TokenManager.readAccessToken();
      final url = '$baseUrl/store?store_id=$storeId';
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      final response = await dio.get(url);
      return response.data['data']['reviews'];
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
    }
  }

  // 영수증 스캔
  static Future<Map<String, dynamic>?> scanImage(File image) async {
    try {
      final token = await TokenManager.readAccessToken();
      const url = '$baseUrl/receipt';
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      var formData = FormData.fromMap(
          {'multipartFile': await MultipartFile.fromFile(image.path)});
      final response = await dio.post(url, data: formData);
      if (response.statusCode == 200) {
        return response.data;
      }
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
    }
    return null;
  }

  // 리뷰 등록
  static Future<int> createReview(
      int storeId,
      String paidAt,
      int totalPrice,
      int headcount,
      double rate,
      String billType,
      int factor,
      String content,
      List<File> reviewImages) async {
    try {
      final result = await TokenManager.verifyToken();
      if (result == 'login need') {
        return -1;
      }
      String userId = await TokenManager.readUserId();
      String userNickname = await TokenManager.readUserNickname();
      String userProfileImageUrl = await TokenManager.readUserProfile();

      var dio = Dio();
      const url = baseUrl;

      List<MultipartFile> images = [];

      for (var file in reviewImages) {
        images.add(MultipartFile.fromFileSync(file.path));
      }
      var formData = FormData.fromMap(
        {
          'reviewImages': images,
          'postReviewRequestDto': MultipartFile.fromString(
              jsonEncode({
                'storeId': storeId,
                'paidAt': paidAt,
                'totalPrice': totalPrice,
                'headcount': headcount,
                'rate': rate,
                'billType': billType,
                'factor': factor,
                'content': content,
                "memberId": userId,
                "memberNickname": userNickname,
                "memberProfileImageUrl": userProfileImageUrl,
              }),
              contentType: MediaType.parse('application/json')),
        },
      );
      final response = await dio.post(url, data: formData);
      return response.statusCode ?? 0;
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      return 0;
    }
  }

  // 리뷰 상세 조회
  static Future<ReviewWholeModel?> getDetailedReview(int reviewId) async {
    try {
      final token = await TokenManager.readAccessToken();
      final memberId = await TokenManager.readUserId();
      print(reviewId);
      final url = '$baseUrl/detail?review_id=$reviewId&member_id=$memberId';
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      final response = await dio.get(url);
      print(response.data);
      return ReviewWholeModel.fromJson(response.data['data']);
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      return null;
    }
  }

  // 리뷰 삭제
  static Future<int> deleteReview(int reviewId) async {
    try {
      final token = await TokenManager.readAccessToken();
      final memberId = await TokenManager.readUserId();
      final url = '$baseUrl?review_id=$reviewId&member_id=$memberId';
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      final response = await dio.delete(url);
      return response.statusCode ?? -1;
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      throw Error();
    }
  }

  // 리뷰 좋아요/취소
  static Future<int> likeReview(int reviewId) async {
    try {
      final memberId = await TokenManager.readUserId();
      final url = '$baseUrl/like?review_id=$reviewId&member_id=$memberId';
      var dio = Dio();
      final response = await dio.post(url);
      return response.statusCode ?? 0;
    } catch (e) {
      if (e is DioException) {
        debugPrint('DioError 발생');
        debugPrint('Response data: ${e.response?.data}');
        debugPrint('Error: ${e.error}');
      } else {
        debugPrint('일반 예외 발생');
        debugPrint('$e');
      }
      return -1;
    }
  }
}
