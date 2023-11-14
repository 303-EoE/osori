// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'store_model_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$storeModelHash() => r'9abc45ffc6e4193f03df83f0641f10c354993497';

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

/// See also [storeModel].
@ProviderFor(storeModel)
const storeModelProvider = StoreModelFamily();

/// See also [storeModel].
class StoreModelFamily extends Family<AsyncValue<List<StoreModel>>> {
  /// See also [storeModel].
  const StoreModelFamily();

  /// See also [storeModel].
  StoreModelProvider call(
    String depth1,
    String depth2,
  ) {
    return StoreModelProvider(
      depth1,
      depth2,
    );
  }

  @override
  StoreModelProvider getProviderOverride(
    covariant StoreModelProvider provider,
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
  String? get name => r'storeModelProvider';
}

/// See also [storeModel].
class StoreModelProvider extends AutoDisposeFutureProvider<List<StoreModel>> {
  /// See also [storeModel].
  StoreModelProvider(
    String depth1,
    String depth2,
  ) : this._internal(
          (ref) => storeModel(
            ref as StoreModelRef,
            depth1,
            depth2,
          ),
          from: storeModelProvider,
          name: r'storeModelProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$storeModelHash,
          dependencies: StoreModelFamily._dependencies,
          allTransitiveDependencies:
              StoreModelFamily._allTransitiveDependencies,
          depth1: depth1,
          depth2: depth2,
        );

  StoreModelProvider._internal(
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
    FutureOr<List<StoreModel>> Function(StoreModelRef provider) create,
  ) {
    return ProviderOverride(
      origin: this,
      override: StoreModelProvider._internal(
        (ref) => create(ref as StoreModelRef),
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
  AutoDisposeFutureProviderElement<List<StoreModel>> createElement() {
    return _StoreModelProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is StoreModelProvider &&
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

mixin StoreModelRef on AutoDisposeFutureProviderRef<List<StoreModel>> {
  /// The parameter `depth1` of this provider.
  String get depth1;

  /// The parameter `depth2` of this provider.
  String get depth2;
}

class _StoreModelProviderElement
    extends AutoDisposeFutureProviderElement<List<StoreModel>>
    with StoreModelRef {
  _StoreModelProviderElement(super.provider);

  @override
  String get depth1 => (origin as StoreModelProvider).depth1;
  @override
  String get depth2 => (origin as StoreModelProvider).depth2;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
