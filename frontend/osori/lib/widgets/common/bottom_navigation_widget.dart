import 'package:flutter/material.dart';

class BottomNavigation extends StatelessWidget {
  const BottomNavigation({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return NavigationBar(
      destinations: [
        GestureDetector(
          onTapUp: (details) {
            Navigator.of(context).pushNamedAndRemoveUntil(
              '/',
              (route) => route.isFirst,
            );
          },
          child: Icon(
            Icons.feed_outlined,
            size: 32,
            color: ModalRoute.of(context)?.settings.name == "/"
                ? Colors.grey
                : Colors.black,
          ),
        ),
        GestureDetector(
          onTapUp: (details) {
            Navigator.of(context).pushNamedAndRemoveUntil(
              '/map',
              (route) => route.isFirst,
            );
          },
          child: Icon(
            Icons.map_outlined,
            size: 32,
            color: ModalRoute.of(context)?.settings.name == "/map"
                ? Colors.grey
                : Colors.black,
          ),
        ),
        GestureDetector(
          onTapUp: (details) {
            Navigator.of(context).pushNamedAndRemoveUntil(
              '/chat',
              (route) => route.isFirst,
            );
          },
          child: Icon(
            Icons.message_outlined,
            size: 32,
            color: ModalRoute.of(context)?.settings.name == "/chat"
                ? Colors.grey
                : Colors.black,
          ),
        ),
        GestureDetector(
          onTapUp: (details) {
            Navigator.of(context).pushNamedAndRemoveUntil(
              '/profile',
              (route) => route.isFirst,
            );
          },
          child: Icon(
            Icons.person_2_outlined,
            size: 32,
            color: ModalRoute.of(context)?.settings.name == "/profile"
                ? Colors.grey
                : Colors.black,
          ),
        ),
      ],
    );
  }
}
