import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_gif/flutter_gif.dart';
import 'package:image_picker/image_picker.dart';
import 'package:osori/screens/write_review_form_screen.dart';

class ReceiptScanningScreen extends StatefulWidget {
  const ReceiptScanningScreen({super.key});

  @override
  State<ReceiptScanningScreen> createState() => _ReceiptScanningScreenState();
}

class _ReceiptScanningScreenState extends State<ReceiptScanningScreen>
    with TickerProviderStateMixin {
  // File? _selectedImage;
  late FlutterGifController controller;
  bool isScanning = false;

  Future _takePicture() async {
    isScanning = true;

    final imagePicker = ImagePicker();
    final pickedImage = await imagePicker.pickImage(source: ImageSource.camera);

    if (pickedImage == null) {
      return;
    }
    /** 
      * 백에게 영수증 스캔 요청
      * 응답받은 내용을 다음 폼에 작성하기
      * sleep(const Duration(seconds: 3)); // 임시
      * 
      * setState(() {
      *   _selectedImage = File(pickedImage.path);
      * });
      * var request = new http.MultipartRequest("POST", Uri.parse(imageApiUrl));
      * goWriteForm(response);
      */

    goWriteForm();
  }

  void goWriteForm() {
    Navigator.pushReplacement(
        context,
        MaterialPageRoute(
          builder: (context) => const WriteReviewFormScreen(),
        ));
  }

  @override
  void initState() {
    super.initState();
    controller = FlutterGifController(vsync: this);
    WidgetsBinding.instance.addPostFrameCallback((timeStamp) {
      controller.repeat(
        max: 50,
        min: 0,
        period: const Duration(milliseconds: 2000),
      );
    });
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      appBar: AppBar(
        title: const Text('리뷰 작성'),
        centerTitle: true,
      ),
      body: Stack(children: [
        Padding(
          padding: EdgeInsets.symmetric(
              vertical: size.height / 6, horizontal: size.width / 4),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset(
                'assets/images/bill.png',
                width: 128,
              ),
              const SizedBox(
                height: 48,
              ),
              const Text(
                '영수증을 인증하고',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.w600,
                ),
              ),
              const SizedBox(
                height: 8,
              ),
              const Text(
                '리뷰를 써보세요!',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.w600,
                ),
              ),
              const SizedBox(
                height: 48,
              ),
              OutlinedButton(
                onPressed: () {
                  _takePicture();
                },
                child: const Text(
                  "인증하기",
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.w500,
                    color: Color(0xFF323232),
                  ),
                ),
              ),
            ],
          ),
        ),
        isScanning
            ? Center(
                child: Container(
                  width: double.infinity,
                  height: double.infinity,
                  decoration: const BoxDecoration(
                    color: Colors.white,
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      GifImage(
                        width: size.width / 2,
                        image: const AssetImage(
                            'assets/images/barcode-scanner.gif'),
                        controller: controller,
                      ),
                      const SizedBox(
                        height: 20,
                      ),
                      const Text(
                        "영수증을 분석 중입니다.",
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ],
                  ),
                ),
              )
            : Container(),
      ]),
    );
  }
}
