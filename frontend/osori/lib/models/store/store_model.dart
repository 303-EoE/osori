import 'package:freezed_annotation/freezed_annotation.dart';

part 'store_model.freezed.dart';
part 'store_model.g.dart';

@freezed
class StoreModel with _$StoreModel {
  factory StoreModel({
    required int id,
    required String name,
    required String category,
    required String longitude,
    required String latitude,
    required String depth1,
    required String depth2,
    required double averageRate,
    required int averagePrice,
    required String defaultBillType,
  }) = _StoreModel;

  factory StoreModel.fromJson(Map<String, dynamic> json) =>
      _$StoreModelFromJson(json);
}
