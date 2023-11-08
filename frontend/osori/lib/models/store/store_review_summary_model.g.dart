// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'store_review_summary_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$StoreReviewSummaryModelImpl _$$StoreReviewSummaryModelImplFromJson(
        Map<String, dynamic> json) =>
    _$StoreReviewSummaryModelImpl(
      reviewId: json['reviewId'] as int,
      createdAt: DateTime.parse(json['createdAt'] as String),
      averageCost: json['averageCost'] as int,
      content: json['content'] as String,
      rate: (json['rate'] as num).toDouble(),
      billType: json['billType'] as String,
      memberNickname: json['memberNickname'] as String,
      memberProfileImageUrl: json['memberProfileImageUrl'] as String,
      image: json['image'] as String,
    );

Map<String, dynamic> _$$StoreReviewSummaryModelImplToJson(
        _$StoreReviewSummaryModelImpl instance) =>
    <String, dynamic>{
      'reviewId': instance.reviewId,
      'createdAt': instance.createdAt.toIso8601String(),
      'averageCost': instance.averageCost,
      'content': instance.content,
      'rate': instance.rate,
      'billType': instance.billType,
      'memberNickname': instance.memberNickname,
      'memberProfileImageUrl': instance.memberProfileImageUrl,
      'image': instance.image,
    };
