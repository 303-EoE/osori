import 'package:freezed_annotation/freezed_annotation.dart';

part 'store_description_model.freezed.dart';
part 'store_description_model.g.dart';

@freezed
class StoreDescriptionModel with _$StoreDescriptionModel {
  factory StoreDescriptionModel({
    required int id,
    required String name,
    required String category,
    required String roadAddreessName,
    required String addressName,
    required String phone,
    required double averageRate,
    required int averagePrice,
    required int totalReviewCount,
    required String defaultBillType,
    required String depth1,
    required String depth2,
  }) = _StoreDescriptionModel;

  factory StoreDescriptionModel.fromJson(Map<String, dynamic> json) =>
      _$StoreDescriptionModelFromJson(json);
}
