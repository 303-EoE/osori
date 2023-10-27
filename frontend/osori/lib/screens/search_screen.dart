import 'package:flutter/material.dart';

class SearchScreen extends StatelessWidget {
  final bool isInputDisabled;

  const SearchScreen({
    super.key,
    required this.isInputDisabled,
  });

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Row(
        children: [
          // TextField(
          //   decoration: const InputDecoration(
          //     border: InputBorder.none,
          //     hintText: "키워드를 입력하세요!!",
          //   ),
          //   textAlign: TextAlign.center,
          //   readOnly: isInputDisabled,
          //   enabled: !isInputDisabled,
          // ),
          SizedBox(
            height: 200,
            child: Center(
              child: Text("SearchScreen"),
            ),
          ),
        ],
      ),
    );
  }
}
