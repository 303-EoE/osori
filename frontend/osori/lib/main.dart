import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:kakao_map_plugin/kakao_map_plugin.dart';
import 'package:osori/screens/chat_screen.dart';
import 'package:osori/screens/map_screen.dart';
import 'package:osori/screens/profile_screen.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/feed/feed_widget.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await dotenv.load(fileName: 'assets/env/.env');
  AuthRepository.initialize(appKey: dotenv.env['APP_KEY'] ?? '');
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        "/map": (context) => const MapScreen(),
        "/chat": (context) => const ChatScreen(),
        "/profile": (context) => const ProfileScreen(),
      },
      title: 'OSORI',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.white),
        useMaterial3: true,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});
  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: topHeader(),
      body: const Feed(),
      bottomNavigationBar: const BottomNavigation(),
    );
  }

  AppBar topHeader() {
    return AppBar(
      surfaceTintColor: const Color(0xFFf9f8f8),
      elevation: 1,
      title: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Row(
            children: [
              Image.asset(
                'assets/images/288X288.png',
                width: 32,
              ),
              const SizedBox(
                width: 10,
              ),
              const Text(
                'OSORI',
                style: TextStyle(
                    color: Colors.black,
                    fontSize: 32,
                    fontWeight: FontWeight.w600),
              ),
            ],
          ),
          IconButton(
            onPressed: () {},
            icon: const Icon(
              Icons.notifications_none_outlined,
              // Icons.notifications,
              color: Colors.black,
              size: 32,
            ),
          ),
        ],
      ),
    );
  }
}
