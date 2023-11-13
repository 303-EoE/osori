import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:image_picker/image_picker.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/providers/review_whole_model_provider.dart';
import 'package:osori/services/osori/member_service.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';
import 'package:osori/widgets/review/review_only_image_widget.dart';

/// 여기서 initState로
/// 토큰이 유효한지를 보는게 맞나...??
/// 토큰이 유효하지 않으면 로그인 페이지로 넘어가자-removeUntil 사용

class ProfileScreen extends ConsumerStatefulWidget {
  const ProfileScreen({super.key});

  @override
  ConsumerState<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends ConsumerState<ProfileScreen> {
  late Map<String, dynamic>? info;
  bool isLogined = false;

  Future<void> validateToken() async {
    // 내 회원정보 조회 API를 날려서 에러가 뜨면 login 스크린으로 가기
    info = await MemberService.getMyProfile();
    if (info != null) {
      print(info);
      isLogined = true;
    } else {
      if (!mounted) return; // 마운트 된 상태여야 context 값을 확신할 수 있음.
      Navigator.pushNamedAndRemoveUntil(context, '/login', (route) => false);
    }
  }

  @override
  void initState() {
    super.initState();
    validateToken();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return isLogined
        ? DefaultTabController(
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
                                  backgroundColor:
                                      const Color.fromRGBO(0, 0, 0, 0),
                                  surfaceTintColor: Colors.transparent,
                                  child:
                                      Image.network(info!['profileImageUrl']),
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
                            child: Image.network(
                              info!['profileImageUrl'],
                              width: size.width / 4,
                            ),
                          ),
                        ),
                        const SizedBox(
                          height: 20,
                        ),
                        Text(
                          info!['nickname'],
                          style: const TextStyle(
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
                            String? selectedImage;
                            TextEditingController tec = TextEditingController();
                            return StatefulBuilder(
                                builder: (context, setState) {
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
                                                      pickedImage.path;
                                                });
                                              }
                                            },
                                            icon: const Icon(
                                                Icons.image_search_outlined),
                                            iconSize: 64,
                                          ),
                                          if (selectedImage != null)
                                            Image.file(
                                              File(selectedImage!),
                                              width: 64,
                                              height: 64,
                                              fit: BoxFit.cover,
                                            )
                                        ],
                                      ),
                                      const SizedBox(
                                        height: 20,
                                      ),
                                      TextField(
                                        controller: tec,
                                        textAlign: TextAlign.center,
                                        decoration: const InputDecoration(
                                          labelText: '닉네임을 입력하세요',
                                          border: OutlineInputBorder(),
                                        ),
                                      ),
                                      const SizedBox(
                                        height: 20,
                                      ),
                                      OutlinedButton(
                                        onPressed: () async {
                                          // 프로필 수정 api 쏴주기
                                          final response =
                                              await MemberService.updateProfile(
                                                  tec.text,
                                                  selectedImage!.isNotEmpty
                                                      ? File(selectedImage!)
                                                      : null,
                                                  true);
                                          if (mounted) {
                                            if (response == 200) {
                                              SnackBarManager.completeSnackBar(
                                                  context, '프로필 수정');
                                            } else {
                                              SnackBarManager.alertSnackBar(
                                                  context, '수정이 실패!!');
                                            }
                                          }
                                        },
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
                        shadows: [
                          Shadow(color: Colors.white, offset: Offset(1, 1))
                        ],
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
              body: TabBarView(
                children: [
                  Consumer(
                    builder: (context, ref, child) {
                      final AsyncValue<List<ReviewWholeModel>> reviews =
                          ref.watch(reviewWholeMyModelProvider);
                      return switch (reviews) {
                        AsyncData(:final value) =>
                          ReviewOnlyImage(reviews: value),
                        AsyncError() => const Text('리뷰를 찾지 못했습니다.'),
                        _ => const CircularProgressIndicator(),
                      };
                    },
                  ),
                  Consumer(
                    builder: (context, ref, child) {
                      final AsyncValue<List<ReviewWholeModel>> reviews =
                          ref.watch(reviewWholeLikedModelProvider);
                      return switch (reviews) {
                        AsyncData(:final value) =>
                          ReviewOnlyImage(reviews: value),
                        AsyncError() => const Text('리뷰를 찾지 못했습니다.'),
                        _ => const CircularProgressIndicator(),
                      };
                    },
                  ),
                ],
                // children: [
                //   ReviewOnlyImage(),
                //   Center(child: Text('내가 좋아요한 리뷰만 보여줄 예정')),
                // ],
              ),
              bottomNavigationBar: const BottomNavigation(),
            ),
          )
        : const Scaffold(body: Center(child: CircularProgressIndicator()));
  }
}
