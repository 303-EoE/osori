import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/providers/review_whole_model_provider.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/common/token_manager.dart';
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
  late Map<String, String?>? depths;
  bool isReady = false;
  late String userId;

  void getReviews() async {
    depths = await TokenManager.readDeviceDepths();
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
      appBar: (_showAppbar && isReady)
          ? topHeader(depths)
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
                      AsyncData(:final value) => value.isNotEmpty
                          ? Column(
                              children: [
                                for (var v in value)
                                  Review(
                                    review: v,
                                  )
                              ],
                            )
                          : const Text('리뷰가 없어요!'),
                      AsyncError() => const Text("서버가 아파요.."),
                      _ => const CircularProgressIndicator(),
                    };
                  },
                )
              : const CircularProgressIndicator(),
        ),
      ),
      bottomNavigationBar: const BottomNavigation(),
    );
  }
}
