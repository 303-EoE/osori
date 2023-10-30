import 'package:flutter/material.dart';

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
