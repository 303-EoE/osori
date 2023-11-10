import 'dart:convert';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:http_parser/http_parser.dart';
import 'package:osori/widgets/common/token_manager.dart';

class ReviewService {
  static const String baseUrl = "https://test.osori.co.kr/reviews";

  static Future<List<Map<String, dynamic>>?> getSumarrizedReviews(
      int storeId) async {
    try {
      final token = await TokenManager.readAccessToken();
      final url = '$baseUrl/store?store_id=$storeId';
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      final response = await dio.get(url);
      if (response.statusCode == 200) {
        return response.data['data']['reviews'];
      }
    } catch (error) {
      print('$error');
      return null;
    }
    return null;
  }

  static Future<List<Map<String, dynamic>>?> getAllMyReview() async {
    try {
      final token = await TokenManager.readAccessToken();
      const url = '$baseUrl/my-review';
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
      final response = await dio.get(url);
      if (response.statusCode == 200) {
        return response.data['data']['reviews'];
      }
    } catch (error) {
      print('$error');
      return null;
    }
    return null;
  }

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
    } catch (error) {
      print(error);
    }
    return null;
  }

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
      final token = await TokenManager.readAccessToken();
      var dio = Dio();
      dio.options.headers = {'Authorization': token};
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
              }),
              contentType: MediaType.parse('application/json')),
        },
      );
      final response = await dio.post(url, data: formData);
      return response.statusCode ?? -1;
    } catch (error) {
      print('$error');
      return -1;
    }
  }
}
