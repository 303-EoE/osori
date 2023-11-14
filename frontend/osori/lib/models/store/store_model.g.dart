// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'store_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$StoreModelImpl _$$StoreModelImplFromJson(Map<String, dynamic> json) =>
    _$StoreModelImpl(
      id: json['id'] as int,
      name: json['name'] as String,
      category: json['category'] as String,
      longitude: json['longitude'] as String,
      latitude: json['latitude'] as String,
      depth1: json['depth1'] as String,
      depth2: json['depth2'] as String,
      averageRate: (json['averageRate'] as num).toDouble(),
      averagePrice: json['averagePrice'] as int,
      defaultBillType: json['defaultBillType'] as String,
    );

Map<String, dynamic> _$$StoreModelImplToJson(_$StoreModelImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'category': instance.category,
      'longitude': instance.longitude,
      'latitude': instance.latitude,
      'depth1': instance.depth1,
      'depth2': instance.depth2,
      'averageRate': instance.averageRate,
      'averagePrice': instance.averagePrice,
      'defaultBillType': instance.defaultBillType,
    };
