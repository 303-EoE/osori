import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:image_cropper/image_cropper.dart';
import 'package:image_picker/image_picker.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/providers/review_whole_model_provider.dart';
import 'package:osori/services/osori/member_service.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:osori/widgets/review/review_only_image_widget.dart';

class ProfileScreen extends ConsumerStatefulWidget {
  final String? memberId;
  const ProfileScreen(this.memberId, {super.key});

  @override
  ConsumerState<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends ConsumerState<ProfileScreen> {
  late Map<String, dynamic>? info;
  bool isLoaded = false;

  Future<void> validateMember() async {
    if (widget.memberId != null) {
      // memberId로 유저 정보 받아오기
      info = await MemberService.getMemberProfile(int.parse(widget.memberId!));
      if (mounted) {
        if (info == null) {
          SnackBarManager.alertSnackBar(context, '존재하지 않는 사용자입니다!');
          Navigator.pop(context);
        }
      }
    } else {
      // id가 있으면 내 정보 띄워주기
      final myId = await TokenManager.readUserId();
      if (myId == "") {
        if (mounted) {
          // 마운트 된 상태여야 context 값을 확신할 수 있음.
          Navigator.pushNamedAndRemoveUntil(
              context, '/login', (route) => false);
          return;
        }
      }
      info = await MemberService.getMemberProfile(int.parse(myId));
      if (info != null) {
        isLoaded = true;
        setState(() {});
      } else {
        if (mounted) {
          SnackBarManager.alertSnackBar(context, '내 정보가 없음!');
          Navigator.pushNamedAndRemoveUntil(
              context, '/review', (route) => false);
          return;
        }
      }
    }
  }

  @override
  void initState() {
    super.initState();
    validateMember();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return isLoaded
        ? DefaultTabController(
            length: 2,
            child: Scaffold(
              appBar: AppBar(
                toolbarHeight: size.height / 4,
                title: Center(
                  child: Stack(children: [
                    Column(
                      children: [
                        if (info!['profileImageUrl'] != null)
                          GestureDetector(
                            onTapUp: (details) {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return Dialog(
                                    backgroundColor:
                                        const Color.fromRGBO(0, 0, 0, 0),
                                    surfaceTintColor: Colors.transparent,
                                    child: Container(
                                      decoration: BoxDecoration(
                                          borderRadius:
                                              BorderRadius.circular(1000)),
                                      clipBehavior: Clip.hardEdge,
                                      child: Image.network(
                                        'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${info!['profileImageUrl']}',
                                        width: size.width,
                                      ),
                                    ),
                                  );
                                },
                              );
                            },
                            child: Container(
                              padding: const EdgeInsets.all(20),
                              child: Container(
                                decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(1000)),
                                clipBehavior: Clip.hardEdge,
                                child: (info!['profileImageUrl'] != null)
                                    ? Image.network(
                                        'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${info!['profileImageUrl']}',
                                        width: size.width / 4,
                                      )
                                    : Image.asset(
                                        'asssets/images/288X288.png',
                                        width: size.width / 4,
                                      ),
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
                            bool isDefaultImage = false;
                            String selectedImage = "";
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
                                      const Text('프로필 이미지 변경'),
                                      Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceAround,
                                        children: [
                                          IconButton(
                                            onPressed: () async {
                                              if (isDefaultImage) return;
                                              final imagePicker = ImagePicker();
                                              final pickedImage =
                                                  await imagePicker.pickImage(
                                                source: ImageSource.gallery,
                                              );
                                              if (pickedImage != null) {
                                                final croppedFile =
                                                    await ImageCropper().cropImage(
                                                        sourcePath:
                                                            pickedImage.path,
                                                        compressFormat:
                                                            ImageCompressFormat
                                                                .jpg,
                                                        compressQuality: 100,
                                                        uiSettings: [
                                                      AndroidUiSettings(
                                                        toolbarTitle: '이미지 자르기',
                                                        toolbarColor:
                                                            Colors.deepOrange,
                                                        toolbarWidgetColor:
                                                            Colors.white,
                                                        initAspectRatio:
                                                            CropAspectRatioPreset
                                                                .square,
                                                        lockAspectRatio: true,
                                                      )
                                                    ]);
                                                if (croppedFile != null) {
                                                  selectedImage =
                                                      croppedFile.path;
                                                }
                                                setState(() {});
                                              }
                                            },
                                            icon: const Icon(
                                                Icons.image_search_outlined),
                                            iconSize: 64,
                                          ),
                                          if (selectedImage != '')
                                            Image.file(
                                              File(selectedImage),
                                              width: 64,
                                              height: 64,
                                              fit: BoxFit.cover,
                                            )
                                        ],
                                      ),
                                      const SizedBox(
                                        height: 20,
                                      ),
                                      const Text('닉네임 변경'),
                                      TextField(
                                        controller: tec,
                                        textAlign: TextAlign.center,
                                        decoration: const InputDecoration(
                                          labelText: '닉네임을 입력하세요',
                                          border: OutlineInputBorder(),
                                        ),
                                      ),
                                      Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceAround,
                                        children: [
                                          const Text('기본 이미지 사용 여부'),
                                          Checkbox(
                                            value: isDefaultImage,
                                            onChanged: (value) {
                                              setState(() {
                                                isDefaultImage = value!;
                                              });
                                            },
                                          ),
                                        ],
                                      ),
                                      const SizedBox(
                                        height: 20,
                                      ),
                                      OutlinedButton(
                                        onPressed: () async {
                                          // 프로필 수정 api 쏴주기
                                          final response =
                                              await MemberService.updateProfile(
                                                  tec.text == ""
                                                      ? null
                                                      : tec.text,
                                                  selectedImage != ""
                                                      ? File(selectedImage)
                                                      : null,
                                                  isDefaultImage);
                                          if (mounted) {
                                            if (response == 200) {
                                              SnackBarManager.completeSnackBar(
                                                  context, '프로필 수정');
                                              Navigator.pop(context);
                                              info = await MemberService
                                                  .getMemberProfile(int.parse(
                                                      await TokenManager
                                                          .readUserId()));
                                              setState(() {});
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
                bottom: TabBar(
                  tabs: [
                    Tab(
                      text: '${info!['nickname']}의 리뷰',
                    ),
                    Tab(
                      text: '${info!['nickname']}가 좋아요한 리뷰',
                    ),
                  ],
                ),
              ),
              body: TabBarView(
                children: [
                  Consumer(
                    builder: (context, ref, child) {
                      final AsyncValue<List<ReviewWholeModel>> reviews = ref
                          .watch(reviewWholeMemberModelProvider(info!['id']));
                      return switch (reviews) {
                        AsyncData(:final value) =>
                          ReviewOnlyImage(reviews: value),
                        AsyncError() => const Text('리뷰를 찾지 못했습니다.'),
                        _ => const Center(
                            child: SizedBox(
                              width: 50,
                              child: CircularProgressIndicator(),
                            ),
                          ),
                      };
                    },
                  ),
                  Consumer(
                    builder: (context, ref, child) {
                      final AsyncValue<List<ReviewWholeModel>> reviews =
                          ref.watch(reviewWholeLikedModelProvider(info!['id']));
                      return switch (reviews) {
                        AsyncData(:final value) =>
                          ReviewOnlyImage(reviews: value),
                        AsyncError() => const Text('리뷰를 찾지 못했습니다.'),
                        _ => const Center(
                            child: SizedBox(
                              width: 50,
                              child: CircularProgressIndicator(),
                            ),
                          ),
                      };
                    },
                  ),
                ],
              ),
              bottomNavigationBar: const BottomNavigation(),
            ),
          )
        : const Scaffold(body: Center(child: CircularProgressIndicator()));
  }
}
