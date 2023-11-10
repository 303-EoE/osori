import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:geolocator/geolocator.dart';
import 'package:osori/models/store/review_whole_model.dart';
import 'package:osori/providers/review_whole_model_provider.dart';
import 'package:osori/services/other/device_position_service.dart';
import 'package:osori/services/other/kakao_local_api_service.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/common/top_header_widget.dart';
import 'package:osori/widgets/review/review_widget.dart';

class ReviewScreen extends ConsumerStatefulWidget {
  const ReviewScreen({super.key});

  @override
  ConsumerState<ReviewScreen> createState() => _FeedScreenState();
}

class _FeedScreenState extends ConsumerState<ReviewScreen> {
  bool _showAppbar = true;
  final ScrollController _scrollBottomBarController = ScrollController();
  bool isScrollingDown = false;
  double bottomBarHeight = 75;
  late Map<String, String>? depths;
  bool isReady = false;
  void getReviews() async {
    Position nowPos = await DevicePostionService.getNowPosition();
    depths = await KakaoLocalApiService.getDepthByPosition(
        '${nowPos.longitude}', '${nowPos.latitude}');
    isReady = true;
    setState(() {});
  }

  void myScroll() {
    _scrollBottomBarController.addListener(() {
      if (_scrollBottomBarController.position.userScrollDirection ==
          ScrollDirection.reverse) {
        if (!isScrollingDown) {
          isScrollingDown = true;
          _showAppbar = false;
        }
      }
      if (_scrollBottomBarController.position.userScrollDirection ==
          ScrollDirection.forward) {
        if (isScrollingDown) {
          isScrollingDown = false;
          _showAppbar = true;
        }
      }
      setState(() {});
    });
  }

  @override
  void initState() {
    super.initState();
    getReviews();
    myScroll();
  }

  @override
  void dispose() {
    _scrollBottomBarController.removeListener(() {});
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _showAppbar
          ? topHeader()
          : PreferredSize(
              preferredSize: const Size(0.0, 0.0),
              child: Container(),
            ),
      body: Center(
        child: SingleChildScrollView(
          controller: _scrollBottomBarController,
          child: isReady
              ? Consumer(
                  builder: (context, ref, child) {
                    final AsyncValue<List<ReviewWholeModel>> reviews =
                        ref.watch(reviewWholeLocalModelProvider(
                            depths!['depth1']!, depths!['depth2']!));
                    return switch (reviews) {
                      AsyncData(:final value) => Column(
                          children: [for (var v in value) Review(review: v)]),
                      AsyncError() => const Text("리뷰를 찾지 못했습니다."),
                      _ => const CircularProgressIndicator(),
                    };
                  },
                )
              : const Icon(Icons.animation_sharp),
        ),
      ),
      bottomNavigationBar: const BottomNavigation(),
    );
  }
}
