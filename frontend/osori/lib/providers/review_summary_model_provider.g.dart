// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'review_summary_model_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$reviewSummaryModelHash() =>
    r'c547714747f5dac21a14e027c26eb7e5bd3d89e6';

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

/// See also [reviewSummaryModel].
@ProviderFor(reviewSummaryModel)
const reviewSummaryModelProvider = ReviewSummaryModelFamily();

/// See also [reviewSummaryModel].
class ReviewSummaryModelFamily
    extends Family<AsyncValue<List<ReviewSummaryModel>>> {
  /// See also [reviewSummaryModel].
  const ReviewSummaryModelFamily();

  /// See also [reviewSummaryModel].
  ReviewSummaryModelProvider call(
    String storeId,
  ) {
    return ReviewSummaryModelProvider(
      storeId,
    );
  }

  @override
  ReviewSummaryModelProvider getProviderOverride(
    covariant ReviewSummaryModelProvider provider,
  ) {
    return call(
      provider.storeId,
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
  String? get name => r'reviewSummaryModelProvider';
}

/// See also [reviewSummaryModel].
class ReviewSummaryModelProvider
    extends AutoDisposeFutureProvider<List<ReviewSummaryModel>> {
  /// See also [reviewSummaryModel].
  ReviewSummaryModelProvider(
    String storeId,
  ) : this._internal(
          (ref) => reviewSummaryModel(
            ref as ReviewSummaryModelRef,
            storeId,
          ),
          from: reviewSummaryModelProvider,
          name: r'reviewSummaryModelProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$reviewSummaryModelHash,
          dependencies: ReviewSummaryModelFamily._dependencies,
          allTransitiveDependencies:
              ReviewSummaryModelFamily._allTransitiveDependencies,
          storeId: storeId,
        );

  ReviewSummaryModelProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.storeId,
  }) : super.internal();

  final String storeId;

  @override
  Override overrideWith(
    FutureOr<List<ReviewSummaryModel>> Function(ReviewSummaryModelRef provider)
        create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ReviewSummaryModelProvider._internal(
        (ref) => create(ref as ReviewSummaryModelRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        storeId: storeId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<List<ReviewSummaryModel>> createElement() {
    return _ReviewSummaryModelProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ReviewSummaryModelProvider && other.storeId == storeId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, storeId.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ReviewSummaryModelRef
    on AutoDisposeFutureProviderRef<List<ReviewSummaryModel>> {
  /// The parameter `storeId` of this provider.
  String get storeId;
}

class _ReviewSummaryModelProviderElement
    extends AutoDisposeFutureProviderElement<List<ReviewSummaryModel>>
    with ReviewSummaryModelRef {
  _ReviewSummaryModelProviderElement(super.provider);

  @override
  String get storeId => (origin as ReviewSummaryModelProvider).storeId;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
