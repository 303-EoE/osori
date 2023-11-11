// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'review_whole_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

ReviewWholeModel _$ReviewWholeModelFromJson(Map<String, dynamic> json) {
  return _ReviewWholeModel.fromJson(json);
}

/// @nodoc
mixin _$ReviewWholeModel {
  int get id => throw _privateConstructorUsedError;
  String get createdAt => throw _privateConstructorUsedError; // Datetime
  int get averageCost => throw _privateConstructorUsedError;
  String get content => throw _privateConstructorUsedError;
  double get rate => throw _privateConstructorUsedError;
  String get billType => throw _privateConstructorUsedError;
  int get storeId => throw _privateConstructorUsedError;
  String get storeName => throw _privateConstructorUsedError;
  String get storeDepth1 => throw _privateConstructorUsedError;
  String get storeDepth2 => throw _privateConstructorUsedError;
  int get memberId => throw _privateConstructorUsedError;
  String get memberNickname => throw _privateConstructorUsedError;
  String get memberProfileImageUrl => throw _privateConstructorUsedError;
  List<String> get images => throw _privateConstructorUsedError;
  bool get liked => throw _privateConstructorUsedError;
  bool get isMine => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ReviewWholeModelCopyWith<ReviewWholeModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ReviewWholeModelCopyWith<$Res> {
  factory $ReviewWholeModelCopyWith(
          ReviewWholeModel value, $Res Function(ReviewWholeModel) then) =
      _$ReviewWholeModelCopyWithImpl<$Res, ReviewWholeModel>;
  @useResult
  $Res call(
      {int id,
      String createdAt,
      int averageCost,
      String content,
      double rate,
      String billType,
      int storeId,
      String storeName,
      String storeDepth1,
      String storeDepth2,
      int memberId,
      String memberNickname,
      String memberProfileImageUrl,
      List<String> images,
      bool liked,
      bool isMine});
}

/// @nodoc
class _$ReviewWholeModelCopyWithImpl<$Res, $Val extends ReviewWholeModel>
    implements $ReviewWholeModelCopyWith<$Res> {
  _$ReviewWholeModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? createdAt = null,
    Object? averageCost = null,
    Object? content = null,
    Object? rate = null,
    Object? billType = null,
    Object? storeId = null,
    Object? storeName = null,
    Object? storeDepth1 = null,
    Object? storeDepth2 = null,
    Object? memberId = null,
    Object? memberNickname = null,
    Object? memberProfileImageUrl = null,
    Object? images = null,
    Object? liked = null,
    Object? isMine = null,
  }) {
    return _then(_value.copyWith(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int,
      createdAt: null == createdAt
          ? _value.createdAt
          : createdAt // ignore: cast_nullable_to_non_nullable
              as String,
      averageCost: null == averageCost
          ? _value.averageCost
          : averageCost // ignore: cast_nullable_to_non_nullable
              as int,
      content: null == content
          ? _value.content
          : content // ignore: cast_nullable_to_non_nullable
              as String,
      rate: null == rate
          ? _value.rate
          : rate // ignore: cast_nullable_to_non_nullable
              as double,
      billType: null == billType
          ? _value.billType
          : billType // ignore: cast_nullable_to_non_nullable
              as String,
      storeId: null == storeId
          ? _value.storeId
          : storeId // ignore: cast_nullable_to_non_nullable
              as int,
      storeName: null == storeName
          ? _value.storeName
          : storeName // ignore: cast_nullable_to_non_nullable
              as String,
      storeDepth1: null == storeDepth1
          ? _value.storeDepth1
          : storeDepth1 // ignore: cast_nullable_to_non_nullable
              as String,
      storeDepth2: null == storeDepth2
          ? _value.storeDepth2
          : storeDepth2 // ignore: cast_nullable_to_non_nullable
              as String,
      memberId: null == memberId
          ? _value.memberId
          : memberId // ignore: cast_nullable_to_non_nullable
              as int,
      memberNickname: null == memberNickname
          ? _value.memberNickname
          : memberNickname // ignore: cast_nullable_to_non_nullable
              as String,
      memberProfileImageUrl: null == memberProfileImageUrl
          ? _value.memberProfileImageUrl
          : memberProfileImageUrl // ignore: cast_nullable_to_non_nullable
              as String,
      images: null == images
          ? _value.images
          : images // ignore: cast_nullable_to_non_nullable
              as List<String>,
      liked: null == liked
          ? _value.liked
          : liked // ignore: cast_nullable_to_non_nullable
              as bool,
      isMine: null == isMine
          ? _value.isMine
          : isMine // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ReviewWholeModelImplCopyWith<$Res>
    implements $ReviewWholeModelCopyWith<$Res> {
  factory _$$ReviewWholeModelImplCopyWith(_$ReviewWholeModelImpl value,
          $Res Function(_$ReviewWholeModelImpl) then) =
      __$$ReviewWholeModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {int id,
      String createdAt,
      int averageCost,
      String content,
      double rate,
      String billType,
      int storeId,
      String storeName,
      String storeDepth1,
      String storeDepth2,
      int memberId,
      String memberNickname,
      String memberProfileImageUrl,
      List<String> images,
      bool liked,
      bool isMine});
}

/// @nodoc
class __$$ReviewWholeModelImplCopyWithImpl<$Res>
    extends _$ReviewWholeModelCopyWithImpl<$Res, _$ReviewWholeModelImpl>
    implements _$$ReviewWholeModelImplCopyWith<$Res> {
  __$$ReviewWholeModelImplCopyWithImpl(_$ReviewWholeModelImpl _value,
      $Res Function(_$ReviewWholeModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = null,
    Object? createdAt = null,
    Object? averageCost = null,
    Object? content = null,
    Object? rate = null,
    Object? billType = null,
    Object? storeId = null,
    Object? storeName = null,
    Object? storeDepth1 = null,
    Object? storeDepth2 = null,
    Object? memberId = null,
    Object? memberNickname = null,
    Object? memberProfileImageUrl = null,
    Object? images = null,
    Object? liked = null,
    Object? isMine = null,
  }) {
    return _then(_$ReviewWholeModelImpl(
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int,
      createdAt: null == createdAt
          ? _value.createdAt
          : createdAt // ignore: cast_nullable_to_non_nullable
              as String,
      averageCost: null == averageCost
          ? _value.averageCost
          : averageCost // ignore: cast_nullable_to_non_nullable
              as int,
      content: null == content
          ? _value.content
          : content // ignore: cast_nullable_to_non_nullable
              as String,
      rate: null == rate
          ? _value.rate
          : rate // ignore: cast_nullable_to_non_nullable
              as double,
      billType: null == billType
          ? _value.billType
          : billType // ignore: cast_nullable_to_non_nullable
              as String,
      storeId: null == storeId
          ? _value.storeId
          : storeId // ignore: cast_nullable_to_non_nullable
              as int,
      storeName: null == storeName
          ? _value.storeName
          : storeName // ignore: cast_nullable_to_non_nullable
              as String,
      storeDepth1: null == storeDepth1
          ? _value.storeDepth1
          : storeDepth1 // ignore: cast_nullable_to_non_nullable
              as String,
      storeDepth2: null == storeDepth2
          ? _value.storeDepth2
          : storeDepth2 // ignore: cast_nullable_to_non_nullable
              as String,
      memberId: null == memberId
          ? _value.memberId
          : memberId // ignore: cast_nullable_to_non_nullable
              as int,
      memberNickname: null == memberNickname
          ? _value.memberNickname
          : memberNickname // ignore: cast_nullable_to_non_nullable
              as String,
      memberProfileImageUrl: null == memberProfileImageUrl
          ? _value.memberProfileImageUrl
          : memberProfileImageUrl // ignore: cast_nullable_to_non_nullable
              as String,
      images: null == images
          ? _value._images
          : images // ignore: cast_nullable_to_non_nullable
              as List<String>,
      liked: null == liked
          ? _value.liked
          : liked // ignore: cast_nullable_to_non_nullable
              as bool,
      isMine: null == isMine
          ? _value.isMine
          : isMine // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$ReviewWholeModelImpl implements _ReviewWholeModel {
  _$ReviewWholeModelImpl(
      {required this.id,
      required this.createdAt,
      required this.averageCost,
      required this.content,
      required this.rate,
      required this.billType,
      required this.storeId,
      required this.storeName,
      required this.storeDepth1,
      required this.storeDepth2,
      required this.memberId,
      required this.memberNickname,
      required this.memberProfileImageUrl,
      required final List<String> images,
      required this.liked,
      required this.isMine})
      : _images = images;

  factory _$ReviewWholeModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$ReviewWholeModelImplFromJson(json);

  @override
  final int id;
  @override
  final String createdAt;
// Datetime
  @override
  final int averageCost;
  @override
  final String content;
  @override
  final double rate;
  @override
  final String billType;
  @override
  final int storeId;
  @override
  final String storeName;
  @override
  final String storeDepth1;
  @override
  final String storeDepth2;
  @override
  final int memberId;
  @override
  final String memberNickname;
  @override
  final String memberProfileImageUrl;
  final List<String> _images;
  @override
  List<String> get images {
    if (_images is EqualUnmodifiableListView) return _images;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_images);
  }

  @override
  final bool liked;
  @override
  final bool isMine;

  @override
  String toString() {
    return 'ReviewWholeModel(id: $id, createdAt: $createdAt, averageCost: $averageCost, content: $content, rate: $rate, billType: $billType, storeId: $storeId, storeName: $storeName, storeDepth1: $storeDepth1, storeDepth2: $storeDepth2, memberId: $memberId, memberNickname: $memberNickname, memberProfileImageUrl: $memberProfileImageUrl, images: $images, liked: $liked, isMine: $isMine)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ReviewWholeModelImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.createdAt, createdAt) ||
                other.createdAt == createdAt) &&
            (identical(other.averageCost, averageCost) ||
                other.averageCost == averageCost) &&
            (identical(other.content, content) || other.content == content) &&
            (identical(other.rate, rate) || other.rate == rate) &&
            (identical(other.billType, billType) ||
                other.billType == billType) &&
            (identical(other.storeId, storeId) || other.storeId == storeId) &&
            (identical(other.storeName, storeName) ||
                other.storeName == storeName) &&
            (identical(other.storeDepth1, storeDepth1) ||
                other.storeDepth1 == storeDepth1) &&
            (identical(other.storeDepth2, storeDepth2) ||
                other.storeDepth2 == storeDepth2) &&
            (identical(other.memberId, memberId) ||
                other.memberId == memberId) &&
            (identical(other.memberNickname, memberNickname) ||
                other.memberNickname == memberNickname) &&
            (identical(other.memberProfileImageUrl, memberProfileImageUrl) ||
                other.memberProfileImageUrl == memberProfileImageUrl) &&
            const DeepCollectionEquality().equals(other._images, _images) &&
            (identical(other.liked, liked) || other.liked == liked) &&
            (identical(other.isMine, isMine) || other.isMine == isMine));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      createdAt,
      averageCost,
      content,
      rate,
      billType,
      storeId,
      storeName,
      storeDepth1,
      storeDepth2,
      memberId,
      memberNickname,
      memberProfileImageUrl,
      const DeepCollectionEquality().hash(_images),
      liked,
      isMine);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ReviewWholeModelImplCopyWith<_$ReviewWholeModelImpl> get copyWith =>
      __$$ReviewWholeModelImplCopyWithImpl<_$ReviewWholeModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$ReviewWholeModelImplToJson(
      this,
    );
  }
}

abstract class _ReviewWholeModel implements ReviewWholeModel {
  factory _ReviewWholeModel(
      {required final int id,
      required final String createdAt,
      required final int averageCost,
      required final String content,
      required final double rate,
      required final String billType,
      required final int storeId,
      required final String storeName,
      required final String storeDepth1,
      required final String storeDepth2,
      required final int memberId,
      required final String memberNickname,
      required final String memberProfileImageUrl,
      required final List<String> images,
      required final bool liked,
      required final bool isMine}) = _$ReviewWholeModelImpl;

  factory _ReviewWholeModel.fromJson(Map<String, dynamic> json) =
      _$ReviewWholeModelImpl.fromJson;

  @override
  int get id;
  @override
  String get createdAt;
  @override // Datetime
  int get averageCost;
  @override
  String get content;
  @override
  double get rate;
  @override
  String get billType;
  @override
  int get storeId;
  @override
  String get storeName;
  @override
  String get storeDepth1;
  @override
  String get storeDepth2;
  @override
  int get memberId;
  @override
  String get memberNickname;
  @override
  String get memberProfileImageUrl;
  @override
  List<String> get images;
  @override
  bool get liked;
  @override
  bool get isMine;
  @override
  @JsonKey(ignore: true)
  _$$ReviewWholeModelImplCopyWith<_$ReviewWholeModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
