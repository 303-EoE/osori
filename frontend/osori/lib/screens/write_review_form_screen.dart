import 'package:flutter/material.dart';

class WriteReviewFormScreen extends StatelessWidget {
  const WriteReviewFormScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('세부 정보'),
        centerTitle: true,
      ),
      body: const Center(
        child: Text("Hi Write Form"),
      ),
    );
  }
}
