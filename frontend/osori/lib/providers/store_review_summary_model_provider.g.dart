// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'store_review_summary_model_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$storeReviewSummaryModelHash() =>
    r'299c02e8fd627ca753165ff0f7a9c14057681c7e';

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

/// See also [storeReviewSummaryModel].
@ProviderFor(storeReviewSummaryModel)
const storeReviewSummaryModelProvider = StoreReviewSummaryModelFamily();

/// See also [storeReviewSummaryModel].
class StoreReviewSummaryModelFamily
    extends Family<AsyncValue<List<StoreReviewSummaryModel>>> {
  /// See also [storeReviewSummaryModel].
  const StoreReviewSummaryModelFamily();

  /// See also [storeReviewSummaryModel].
  StoreReviewSummaryModelProvider call(
    String storeId,
  ) {
    return StoreReviewSummaryModelProvider(
      storeId,
    );
  }

  @override
  StoreReviewSummaryModelProvider getProviderOverride(
    covariant StoreReviewSummaryModelProvider provider,
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
  String? get name => r'storeReviewSummaryModelProvider';
}

/// See also [storeReviewSummaryModel].
class StoreReviewSummaryModelProvider
    extends AutoDisposeFutureProvider<List<StoreReviewSummaryModel>> {
  /// See also [storeReviewSummaryModel].
  StoreReviewSummaryModelProvider(
    String storeId,
  ) : this._internal(
          (ref) => storeReviewSummaryModel(
            ref as StoreReviewSummaryModelRef,
            storeId,
          ),
          from: storeReviewSummaryModelProvider,
          name: r'storeReviewSummaryModelProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$storeReviewSummaryModelHash,
          dependencies: StoreReviewSummaryModelFamily._dependencies,
          allTransitiveDependencies:
              StoreReviewSummaryModelFamily._allTransitiveDependencies,
          storeId: storeId,
        );

  StoreReviewSummaryModelProvider._internal(
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
    FutureOr<List<StoreReviewSummaryModel>> Function(
            StoreReviewSummaryModelRef provider)
        create,
  ) {
    return ProviderOverride(
      origin: this,
      override: StoreReviewSummaryModelProvider._internal(
        (ref) => create(ref as StoreReviewSummaryModelRef),
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
  AutoDisposeFutureProviderElement<List<StoreReviewSummaryModel>>
      createElement() {
    return _StoreReviewSummaryModelProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is StoreReviewSummaryModelProvider && other.storeId == storeId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, storeId.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin StoreReviewSummaryModelRef
    on AutoDisposeFutureProviderRef<List<StoreReviewSummaryModel>> {
  /// The parameter `storeId` of this provider.
  String get storeId;
}

class _StoreReviewSummaryModelProviderElement
    extends AutoDisposeFutureProviderElement<List<StoreReviewSummaryModel>>
    with StoreReviewSummaryModelRef {
  _StoreReviewSummaryModelProviderElement(super.provider);

  @override
  String get storeId => (origin as StoreReviewSummaryModelProvider).storeId;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
