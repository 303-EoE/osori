import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/feed/feed_only_image_widget.dart';

/// 여기서 initState로
/// 토큰이 유효한지를 보는게 맞나...??
/// 토큰이 유효하지 않으면 로그인 페이지로 넘어가자-removeUntil로

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          toolbarHeight: size.height / 4,
          title: Center(
            child: Stack(children: [
              Column(
                children: [
                  GestureDetector(
                    onTapUp: (details) {
                      showDialog(
                        context: context,
                        builder: (context) {
                          return Dialog(
                            backgroundColor: const Color.fromRGBO(0, 0, 0, 0),
                            surfaceTintColor: Colors.transparent,
                            child: Image.asset('assets/images/288X288.png'),
                          );
                        },
                      );
                    },
                    child: Container(
                      decoration: BoxDecoration(
                          border: Border.all(),
                          borderRadius: BorderRadius.circular(1000),
                          color: Colors.blueGrey),
                      padding: const EdgeInsets.all(20),
                      child: Image.asset(
                        'assets/images/288X288.png',
                        width: size.width / 4,
                      ),
                    ),
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  const Text(
                    'sweetandsourkiss',
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                  // const Icon(Icons.edit)
                ],
              ),
              GestureDetector(
                onTapUp: (details) {
                  showDialog(
                    context: context,
                    builder: (context) {
                      File? selectedImage;
                      return StatefulBuilder(builder: (context, setState) {
                        return Dialog(
                          surfaceTintColor: Colors.white,
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              mainAxisSize: MainAxisSize.min,
                              children: [
                                const Text(
                                  "프로필 편집",
                                  style: TextStyle(
                                    fontSize: 24,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                                const SizedBox(
                                  height: 20,
                                ),
                                Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceAround,
                                  children: [
                                    IconButton(
                                      onPressed: () async {
                                        final imagePicker = ImagePicker();
                                        final pickedImage =
                                            await imagePicker.pickImage(
                                          source: ImageSource.gallery,
                                        );
                                        if (pickedImage != null) {
                                          setState(() {
                                            selectedImage =
                                                File(pickedImage.path);
                                          });
                                        }
                                      },
                                      icon: const Icon(
                                          Icons.image_search_outlined),
                                      iconSize: 64,
                                    ),
                                    if (selectedImage != null)
                                      Image.file(
                                        selectedImage!,
                                        width: 64,
                                        height: 64,
                                        fit: BoxFit.cover,
                                      )
                                  ],
                                ),
                                const SizedBox(
                                  height: 20,
                                ),
                                const TextField(
                                  textAlign: TextAlign.center,
                                  decoration: InputDecoration(
                                    labelText: '닉네임을 입력하세요',
                                    border: OutlineInputBorder(),
                                  ),
                                ),
                                const SizedBox(
                                  height: 20,
                                ),
                                OutlinedButton(
                                  onPressed: () {},
                                  child: const Text("수정하기"),
                                )
                              ],
                            ),
                          ),
                        );
                      });
                    },
                  );
                },
                child: const Icon(
                  Icons.settings,
                  size: 32,
                  color: Colors.black,
                  shadows: [Shadow(color: Colors.white, offset: Offset(1, 1))],
                ),
              )
            ]),
          ),
          bottom: const TabBar(
            tabs: [
              Tab(
                text: '내 리뷰',
              ),
              Tab(
                text: '좋아요한 리뷰',
              ),
            ],
          ),
        ),
        body: const TabBarView(
          children: [
            FeedOnlyImage(),
            Center(child: Text('내가 좋아요한 리뷰만 보여줄 예정')),
          ],
        ),
        bottomNavigationBar: const BottomNavigation(),
      ),
    );
  }
}
