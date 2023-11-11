import 'package:freezed_annotation/freezed_annotation.dart';

part 'review_whole_model.freezed.dart';
part 'review_whole_model.g.dart';

@freezed
class ReviewWholeModel with _$ReviewWholeModel {
  factory ReviewWholeModel({
    required int id,
    required String createdAt, // Datetime
    required int averageCost,
    required String content,
    required double rate,
    required String billType,
    required int storeId,
    required String storeName,
    required String storeDepth1,
    required String storeDepth2,
    required int memberId,
    required String memberNickname,
    required String memberProfileImageUrl,
    required List<String> images,
    required bool liked,
    required bool isMine,
  }) = _ReviewWholeModel;

  factory ReviewWholeModel.fromJson(Map<String, dynamic> json) =>
      _$ReviewWholeModelFromJson(json);
}
