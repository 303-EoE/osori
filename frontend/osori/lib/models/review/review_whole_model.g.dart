// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'review_whole_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$ReviewWholeModelImpl _$$ReviewWholeModelImplFromJson(
        Map<String, dynamic> json) =>
    _$ReviewWholeModelImpl(
      id: json['id'] as int,
      createdAt: json['createdAt'] as String,
      averageCost: json['averageCost'] as int,
      content: json['content'] as String,
      rate: (json['rate'] as num).toDouble(),
      billType: json['billType'] as String,
      storeId: json['storeId'] as int,
      storeName: json['storeName'] as String,
      storeDepth1: json['storeDepth1'] as String,
      storeDepth2: json['storeDepth2'] as String,
      memberId: json['memberId'] as int,
      memberNickname: json['memberNickname'] as String,
      memberProfileImageUrl: json['memberProfileImageUrl'] as String,
      images:
          (json['images'] as List<dynamic>).map((e) => e as String).toList(),
      liked: json['liked'] as bool,
      isMine: json['isMine'] as bool,
    );

Map<String, dynamic> _$$ReviewWholeModelImplToJson(
        _$ReviewWholeModelImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'createdAt': instance.createdAt,
      'averageCost': instance.averageCost,
      'content': instance.content,
      'rate': instance.rate,
      'billType': instance.billType,
      'storeId': instance.storeId,
      'storeName': instance.storeName,
      'storeDepth1': instance.storeDepth1,
      'storeDepth2': instance.storeDepth2,
      'memberId': instance.memberId,
      'memberNickname': instance.memberNickname,
      'memberProfileImageUrl': instance.memberProfileImageUrl,
      'images': instance.images,
      'liked': instance.liked,
      'isMine': instance.isMine,
    };
