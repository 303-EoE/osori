import 'package:freezed_annotation/freezed_annotation.dart';

part 'review_summary_model.freezed.dart';
part 'review_summary_model.g.dart';

@freezed
class ReviewSummaryModel with _$ReviewSummaryModel {
  factory ReviewSummaryModel({
    required int id,
    required String createdAt, // DateTime
    required int averageCost,
    required String content,
    required double rate,
    required String billType,
    required String memberNickname,
    required String memberProfileImageUrl,
    required String image,
  }) = _ReviewSummaryModel;

  factory ReviewSummaryModel.fromJson(Map<String, dynamic> json) =>
      _$ReviewSummaryModelFromJson(json);
}
