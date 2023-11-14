// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'store_description_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$StoreDescriptionModelImpl _$$StoreDescriptionModelImplFromJson(
        Map<String, dynamic> json) =>
    _$StoreDescriptionModelImpl(
      id: json['id'] as int,
      name: json['name'] as String,
      category: json['category'] as String,
      roadAddreessName: json['roadAddreessName'] as String,
      addressName: json['addressName'] as String,
      phone: json['phone'] as String,
      averageRate: (json['averageRate'] as num).toDouble(),
      averagePrice: json['averagePrice'] as int,
      totalReviewCount: json['totalReviewCount'] as int,
      defaultBillType: json['defaultBillType'] as String,
      depth1: json['depth1'] as String,
      depth2: json['depth2'] as String,
    );

Map<String, dynamic> _$$StoreDescriptionModelImplToJson(
        _$StoreDescriptionModelImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'category': instance.category,
      'roadAddreessName': instance.roadAddreessName,
      'addressName': instance.addressName,
      'phone': instance.phone,
      'averageRate': instance.averageRate,
      'averagePrice': instance.averagePrice,
      'totalReviewCount': instance.totalReviewCount,
      'defaultBillType': instance.defaultBillType,
      'depth1': instance.depth1,
      'depth2': instance.depth2,
    };
