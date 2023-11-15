import 'package:flutter/material.dart';

class BottomNavigation extends StatefulWidget {
  const BottomNavigation({
    super.key,
  });

  @override
  State<BottomNavigation> createState() => _BottomNavigationState();
}

class _BottomNavigationState extends State<BottomNavigation> {
  int selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    switch (ModalRoute.of(context)?.settings.name) {
      case '/review':
        selectedIndex = 0;
        break;
      case '/map':
        selectedIndex = 1;
        break;
      case '/profile':
        selectedIndex = 2;
        break;
      default:
    }
    return NavigationBar(
      labelBehavior: NavigationDestinationLabelBehavior.alwaysHide,
      surfaceTintColor: const Color(0xFFf9f8f8),
      indicatorColor: const Color(0xFFe8e8e8),
      selectedIndex: selectedIndex,
      onDestinationSelected: (value) {
        switch (value) {
          case 0:
            Navigator.of(context)
                .pushNamedAndRemoveUntil('/review', (route) => false);
            break;
          case 1:
            Navigator.of(context)
                .pushNamedAndRemoveUntil('/map', (route) => false);
            break;
          case 2:
            Navigator.of(context)
                .pushNamedAndRemoveUntil('/profile', (route) => false);
            break;
        }
      },
      // surfaceTintColor: const Color.fromARGB(255, 236, 101, 101),
      destinations: const [
        NavigationDestination(
          selectedIcon: Icon(Icons.feed_rounded),
          icon: Icon(Icons.feed_outlined),
          label: 'feed',
        ),
        NavigationDestination(
          icon: Icon(Icons.map_outlined),
          selectedIcon: Icon(Icons.map),
          label: 'map',
        ),
        NavigationDestination(
          icon: Icon(Icons.person_4_outlined),
          selectedIcon: Icon(Icons.person_4_rounded),
          label: 'profile',
        ),
      ],
    );
  }
}
