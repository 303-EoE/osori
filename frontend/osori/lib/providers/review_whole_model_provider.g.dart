// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'review_whole_model_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$reviewWholeLocalModelHash() =>
    r'7e6036072455862798f3dd8659556731037c2f2c';

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
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
