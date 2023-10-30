import 'package:flutter/material.dart';

class SearchScreen extends StatelessWidget {
  final bool isInputDisabled;

  const SearchScreen({
    super.key,
    required this.isInputDisabled,
  });

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Scaffold(
      appBar: AppBar(
        title: const Text("가게 검색"),
        centerTitle: true,
      ),
      body: Column(
        children: [
          SizedBox(
            width: size.width,
            child: const Padding(
              padding: EdgeInsets.symmetric(horizontal: 20),
              child: TextField(
                decoration: InputDecoration(
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(
                      Radius.circular(10),
                    ),
                  ),
                  hoverColor: Color(0xFFE8E8E8),
                  hintText: "키워드를 입력하세요!!",
                ),
                textAlign: TextAlign.center,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
