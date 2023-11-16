import 'package:flutter/material.dart';

AppBar topHeader(Map<String, String?>? depths) {
  return AppBar(
    surfaceTintColor: const Color(0xFFf9f8f8),
    elevation: 0.2,
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
              '오소리',
              style: TextStyle(
                  color: Colors.black,
                  fontSize: 24,
                  fontWeight: FontWeight.w600),
            ),
          ],
        ),
        Text(
          '${depths?['depth1']} ${depths?['depth2']}',
          style: const TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.w400,
          ),
        )
      ],
    ),
  );
}
