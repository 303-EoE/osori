import 'package:freezed_annotation/freezed_annotation.dart';

part 'store_review_summary_model.freezed.dart';
part 'store_review_summary_model.g.dart';

@freezed
class StoreReviewSummaryModel with _$StoreReviewSummaryModel {
  factory StoreReviewSummaryModel({
    required int reviewId,
    required DateTime createdAt,
    required int averageCost,
    required String content,
    required double rate,
    required String billType,
    required String memberNickname,
    required String memberProfileImageUrl,
    required String image,
  }) = _StoreReviewSummaryModel;

  factory StoreReviewSummaryModel.fromJson(Map<String, dynamic> json) =>
      _$StoreReviewSummaryModelFromJson(json);
}
