import 'package:flutter/material.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';

class ChatScreen extends StatelessWidget {
  const ChatScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: SizedBox(
        height: 100,
        child: Center(
          child: Text('Hello, Chat Screen.'),
        ),
      ),
      bottomNavigationBar: BottomNavigation(),
    );
  }
}
