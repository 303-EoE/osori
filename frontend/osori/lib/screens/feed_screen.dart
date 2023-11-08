import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/common/top_header_widget.dart';
import 'package:osori/widgets/feed/feed_widget.dart';

class FeedScreen extends StatefulWidget {
  const FeedScreen({super.key});

  @override
  State<FeedScreen> createState() => _FeedScreenState();
}

class _FeedScreenState extends State<FeedScreen> {
  bool _showAppbar = true;
  final ScrollController _scrollBottomBarController = ScrollController();
  bool isScrollingDown = false;
  double bottomBarHeight = 75;

  void myScroll() async {
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
      body: SingleChildScrollView(
        controller: _scrollBottomBarController,
        child: Column(
          children: [
            for (var i in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]) Feed(idx: i),
          ],
        ),
      ),
      bottomNavigationBar: const BottomNavigation(),
    );
  }
}
