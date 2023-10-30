import 'package:flutter/material.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/common/top_header_widget.dart';
import 'package:osori/widgets/feed/feed_widget.dart';

class FeedScreen extends StatelessWidget {
  const FeedScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: topHeader(),
      body: const Feed(),
      bottomNavigationBar: const BottomNavigation(),
    );
  }
}
