import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'review_whole_model_provider.g.dart';

const String baseUrl = "https://osori.co.kr/reviews";

@riverpod
Future<List<ReviewWholeModel>> reviewWholeLocalModel(
    ReviewWholeLocalModelRef ref, String depth1, String depth2) async {
  try {
    List<ReviewWholeModel> reviewInstances = [];
    var memberId = await TokenManager.readUserId();

    var dio = Dio();
    final url =
        '$baseUrl/region?depth1=$depth1&depth2=$depth2&member_id=$memberId';
    final response = await dio.get(url);
    if (response.statusCode == 200) {
      final reviews = response.data['data']['reviews'];
      for (var review in reviews) {
        reviewInstances.add(ReviewWholeModel.fromJson(review));
      }
      return reviewInstances;
    }
    return [];
  } catch (e) {
    if (e is DioException) {
      debugPrint('DioError 발생');
      debugPrint('Response data: ${e.response?.data}');
      debugPrint('Error: ${e.error}');
    } else {
      debugPrint('일반 예외 발생 여기서 뜨는거 맞지?');
      debugPrint('$e');
    }
    return [];
  }
}

@riverpod
Future<List<ReviewWholeModel>> reviewWholeMemberModel(
    ReviewWholeMemberModelRef ref, int memberId) async {
  try {
    List<ReviewWholeModel> reviewInstances = [];
    final token = TokenManager.readAccessToken();
    var dio = Dio();
    dio.options.headers = {"Authorization": token};
    final url = '$baseUrl/member?member_id=$memberId';
    final response = await dio.get(url);
    if (response.statusCode == 200) {
      final reviews = response.data['data']['reviews'];
      for (var review in reviews) {
        reviewInstances.add(ReviewWholeModel.fromJson(review));
      }
      return reviewInstances;
    }
    return [];
  } catch (e) {
    if (e is DioException) {
      debugPrint('DioError 발생');
      debugPrint('Response data: ${e.response?.data}');
      debugPrint('Error: ${e.error}');
    } else {
      debugPrint('일반 예외 발생 여기서 뜨는거 맞지?');
      debugPrint('$e');
    }
    return [];
  }
}

@riverpod
Future<List<ReviewWholeModel>> reviewWholeLikedModel(
    ReviewWholeLikedModelRef ref, int memberId) async {
  try {
    List<ReviewWholeModel> reviewInstances = [];
    final token = TokenManager.readAccessToken();
    var dio = Dio();
    dio.options.headers = {"Authorization": token};
    final url = '$baseUrl/like?member_id=$memberId';
    final response = await dio.get(url);
    if (response.statusCode == 200) {
      final reviews = response.data['data']['reviews'];
      for (var review in reviews) {
        reviewInstances.add(ReviewWholeModel.fromJson(review));
      }
      return reviewInstances;
    }
    return [];
  } catch (e) {
    if (e is DioException) {
      debugPrint('DioError 발생');
      debugPrint('Response data: ${e.response?.data}');
      debugPrint('Error: ${e.error}');
    } else {
      debugPrint('일반 예외 발생 여기서 뜨는거 맞지?');
      debugPrint('$e');
    }
    return [];
  }
}
