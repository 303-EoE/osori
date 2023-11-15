// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'review_whole_model_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$reviewWholeLocalModelHash() =>
    r'152852ee6fe59a4c196a26d200f581ab8da2a02e';

/// Copied from Dart SDK
class _SystemHash {
  _SystemHash._();

  static int combine(int hash, int value) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + value);
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x0007ffff & hash) << 10));
    return hash ^ (hash >> 6);
  }

  static int finish(int hash) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x03ffffff & hash) << 3));
    // ignore: parameter_assignments
    hash = hash ^ (hash >> 11);
    return 0x1fffffff & (hash + ((0x00003fff & hash) << 15));
  }
}

/// See also [reviewWholeLocalModel].
@ProviderFor(reviewWholeLocalModel)
const reviewWholeLocalModelProvider = ReviewWholeLocalModelFamily();

/// See also [reviewWholeLocalModel].
class ReviewWholeLocalModelFamily
    extends Family<AsyncValue<List<ReviewWholeModel>>> {
  /// See also [reviewWholeLocalModel].
  const ReviewWholeLocalModelFamily();

  /// See also [reviewWholeLocalModel].
  ReviewWholeLocalModelProvider call(
    String depth1,
    String depth2,
  ) {
    return ReviewWholeLocalModelProvider(
      depth1,
      depth2,
    );
  }

  @override
  ReviewWholeLocalModelProvider getProviderOverride(
    covariant ReviewWholeLocalModelProvider provider,
  ) {
    return call(
      provider.depth1,
      provider.depth2,
    );
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'reviewWholeLocalModelProvider';
}

/// See also [reviewWholeLocalModel].
class ReviewWholeLocalModelProvider
    extends AutoDisposeFutureProvider<List<ReviewWholeModel>> {
  /// See also [reviewWholeLocalModel].
  ReviewWholeLocalModelProvider(
    String depth1,
    String depth2,
  ) : this._internal(
          (ref) => reviewWholeLocalModel(
            ref as ReviewWholeLocalModelRef,
            depth1,
            depth2,
          ),
          from: reviewWholeLocalModelProvider,
          name: r'reviewWholeLocalModelProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$reviewWholeLocalModelHash,
          dependencies: ReviewWholeLocalModelFamily._dependencies,
          allTransitiveDependencies:
              ReviewWholeLocalModelFamily._allTransitiveDependencies,
          depth1: depth1,
          depth2: depth2,
        );

  ReviewWholeLocalModelProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.depth1,
    required this.depth2,
  }) : super.internal();

  final String depth1;
  final String depth2;

  @override
  Override overrideWith(
    FutureOr<List<ReviewWholeModel>> Function(ReviewWholeLocalModelRef provider)
        create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ReviewWholeLocalModelProvider._internal(
        (ref) => create(ref as ReviewWholeLocalModelRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        depth1: depth1,
        depth2: depth2,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<ReviewWholeModel>> createElement() {
    return _ReviewWholeLocalModelProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ReviewWholeLocalModelProvider &&
        other.depth1 == depth1 &&
        other.depth2 == depth2;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, depth1.hashCode);
    hash = _SystemHash.combine(hash, depth2.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ReviewWholeLocalModelRef
    on AutoDisposeFutureProviderRef<List<ReviewWholeModel>> {
  /// The parameter `depth1` of this provider.
  String get depth1;

  /// The parameter `depth2` of this provider.
  String get depth2;
}

class _ReviewWholeLocalModelProviderElement
    extends AutoDisposeFutureProviderElement<List<ReviewWholeModel>>
    with ReviewWholeLocalModelRef {
  _ReviewWholeLocalModelProviderElement(super.provider);

  @override
  String get depth1 => (origin as ReviewWholeLocalModelProvider).depth1;
  @override
  String get depth2 => (origin as ReviewWholeLocalModelProvider).depth2;
}

String _$reviewWholeMemberModelHash() =>
    r'b8fc5f50912aa1406fe3cdbd9891312ef4e7fc78';

/// See also [reviewWholeMemberModel].
@ProviderFor(reviewWholeMemberModel)
const reviewWholeMemberModelProvider = ReviewWholeMemberModelFamily();

/// See also [reviewWholeMemberModel].
class ReviewWholeMemberModelFamily
    extends Family<AsyncValue<List<ReviewWholeModel>>> {
  /// See also [reviewWholeMemberModel].
  const ReviewWholeMemberModelFamily();

  /// See also [reviewWholeMemberModel].
  ReviewWholeMemberModelProvider call(
    String memberId,
  ) {
    return ReviewWholeMemberModelProvider(
      memberId,
    );
  }

  @override
  ReviewWholeMemberModelProvider getProviderOverride(
    covariant ReviewWholeMemberModelProvider provider,
  ) {
    return call(
      provider.memberId,
    );
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'reviewWholeMemberModelProvider';
}

/// See also [reviewWholeMemberModel].
class ReviewWholeMemberModelProvider
    extends AutoDisposeFutureProvider<List<ReviewWholeModel>> {
  /// See also [reviewWholeMemberModel].
  ReviewWholeMemberModelProvider(
    String memberId,
  ) : this._internal(
          (ref) => reviewWholeMemberModel(
            ref as ReviewWholeMemberModelRef,
            memberId,
          ),
          from: reviewWholeMemberModelProvider,
          name: r'reviewWholeMemberModelProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$reviewWholeMemberModelHash,
          dependencies: ReviewWholeMemberModelFamily._dependencies,
          allTransitiveDependencies:
              ReviewWholeMemberModelFamily._allTransitiveDependencies,
          memberId: memberId,
        );

  ReviewWholeMemberModelProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.memberId,
  }) : super.internal();

  final String memberId;

  @override
  Override overrideWith(
    FutureOr<List<ReviewWholeModel>> Function(
            ReviewWholeMemberModelRef provider)
        create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ReviewWholeMemberModelProvider._internal(
        (ref) => create(ref as ReviewWholeMemberModelRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        memberId: memberId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<ReviewWholeModel>> createElement() {
    return _ReviewWholeMemberModelProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ReviewWholeMemberModelProvider &&
        other.memberId == memberId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, memberId.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ReviewWholeMemberModelRef
    on AutoDisposeFutureProviderRef<List<ReviewWholeModel>> {
  /// The parameter `memberId` of this provider.
  String get memberId;
}

class _ReviewWholeMemberModelProviderElement
    extends AutoDisposeFutureProviderElement<List<ReviewWholeModel>>
    with ReviewWholeMemberModelRef {
  _ReviewWholeMemberModelProviderElement(super.provider);

  @override
  String get memberId => (origin as ReviewWholeMemberModelProvider).memberId;
}

String _$reviewWholeLikedModelHash() =>
    r'9b7bbe46ee38b3066d920d9a8665d7bcd7bc48c9';

/// See also [reviewWholeLikedModel].
@ProviderFor(reviewWholeLikedModel)
const reviewWholeLikedModelProvider = ReviewWholeLikedModelFamily();

/// See also [reviewWholeLikedModel].
class ReviewWholeLikedModelFamily
    extends Family<AsyncValue<List<ReviewWholeModel>>> {
  /// See also [reviewWholeLikedModel].
  const ReviewWholeLikedModelFamily();

  /// See also [reviewWholeLikedModel].
  ReviewWholeLikedModelProvider call(
    String memberId,
  ) {
    return ReviewWholeLikedModelProvider(
      memberId,
    );
  }

  @override
  ReviewWholeLikedModelProvider getProviderOverride(
    covariant ReviewWholeLikedModelProvider provider,
  ) {
    return call(
      provider.memberId,
    );
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'reviewWholeLikedModelProvider';
}

/// See also [reviewWholeLikedModel].
class ReviewWholeLikedModelProvider
    extends AutoDisposeFutureProvider<List<ReviewWholeModel>> {
  /// See also [reviewWholeLikedModel].
  ReviewWholeLikedModelProvider(
    String memberId,
  ) : this._internal(
          (ref) => reviewWholeLikedModel(
            ref as ReviewWholeLikedModelRef,
            memberId,
          ),
          from: reviewWholeLikedModelProvider,
          name: r'reviewWholeLikedModelProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$reviewWholeLikedModelHash,
          dependencies: ReviewWholeLikedModelFamily._dependencies,
          allTransitiveDependencies:
              ReviewWholeLikedModelFamily._allTransitiveDependencies,
          memberId: memberId,
        );

  ReviewWholeLikedModelProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.memberId,
  }) : super.internal();

  final String memberId;

  @override
  Override overrideWith(
    FutureOr<List<ReviewWholeModel>> Function(ReviewWholeLikedModelRef provider)
        create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ReviewWholeLikedModelProvider._internal(
        (ref) => create(ref as ReviewWholeLikedModelRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        memberId: memberId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<ReviewWholeModel>> createElement() {
    return _ReviewWholeLikedModelProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ReviewWholeLikedModelProvider && other.memberId == memberId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, memberId.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ReviewWholeLikedModelRef
    on AutoDisposeFutureProviderRef<List<ReviewWholeModel>> {
  /// The parameter `memberId` of this provider.
  String get memberId;
}

class _ReviewWholeLikedModelProviderElement
    extends AutoDisposeFutureProviderElement<List<ReviewWholeModel>>
    with ReviewWholeLikedModelRef {
  _ReviewWholeLikedModelProviderElement(super.provider);

  @override
  String get memberId => (origin as ReviewWholeLikedModelProvider).memberId;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
