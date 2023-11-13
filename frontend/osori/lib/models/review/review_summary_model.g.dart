// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'review_summary_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$ReviewSummaryModelImpl _$$ReviewSummaryModelImplFromJson(
        Map<String, dynamic> json) =>
    _$ReviewSummaryModelImpl(
      id: json['id'] as int,
      createdAt: json['createdAt'] as String,
      averageCost: json['averageCost'] as int,
      content: json['content'] as String,
      rate: (json['rate'] as num).toDouble(),
      billType: json['billType'] as String,
      memberNickname: json['memberNickname'] as String,
      memberProfileImageUrl: json['memberProfileImageUrl'] as String,
      image: json['image'] as String,
    );

Map<String, dynamic> _$$ReviewSummaryModelImplToJson(
        _$ReviewSummaryModelImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'createdAt': instance.createdAt,
      'averageCost': instance.averageCost,
      'content': instance.content,
      'rate': instance.rate,
      'billType': instance.billType,
      'memberNickname': instance.memberNickname,
      'memberProfileImageUrl': instance.memberProfileImageUrl,
      'image': instance.image,
    };
