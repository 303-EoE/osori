import 'package:flutter/material.dart';
import 'package:kakao_map_plugin/kakao_map_plugin.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:osori/screens/receipt_scanning_screen.dart';
import 'package:osori/services/other/kakao_local_api_service.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:url_launcher/url_launcher_string.dart';

class SearchScreen extends StatefulWidget {
  final LatLng nowPos;

  const SearchScreen({
    super.key,
    required this.nowPos,
  });

  @override
  State<SearchScreen> createState() => _SearchScreenState();
}

class _SearchScreenState extends State<SearchScreen> {
  late List<KakaoStoreModel> stores;
  bool isSearching = false;
  bool isComplete = false;
  final TextEditingController _textController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Scaffold(
      appBar: AppBar(
        title: const Text("가게 검색"),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child: Column(
          children: [
            SizedBox(
              width: size.width,
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Container(
                      alignment: Alignment.center,
                      width: size.width / 3 * 2,
                      height: 40,
                      child: TextField(
                        controller: _textController,
                        keyboardType: TextInputType.text,
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.all(
                              Radius.circular(20),
                            ),
                          ),
                          hoverColor: Color(0xFFE8E8E8),
                          hintText: "현재 지도를 중심으로 검색합니다.",
                          contentPadding: EdgeInsets.zero,
                        ),
                        textAlign: TextAlign.center,
                        textAlignVertical: TextAlignVertical.center,
                        // onSubmitted: KakaoLocalApiService.getStoresByKeyword,
                      ),
                    ),
                    OutlinedButton(
                      onPressed: () async {
                        if (isComplete) stores.clear();
                        isSearching = true;
                        setState(() {});
                        stores = await KakaoLocalApiService.getStoresByKeyword(
                          _textController.text,
                          widget.nowPos.longitude.toString(),
                          widget.nowPos.latitude.toString(),
                        );
                        isSearching = false;
                        isComplete = true;
                        setState(() {});
                      },
                      style: OutlinedButton.styleFrom(
                        backgroundColor: Colors.blue,
                      ),
                      child: const Text(
                        '검색',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            if (isSearching)
              const Column(children: [
                SizedBox(
                  height: 20,
                ),
                CircularProgressIndicator()
              ]),
            if (isComplete)
              if (stores.isEmpty)
                const Text('검색 결과가 없습니다.')
              else
                for (var store in stores)
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Flexible(
                          flex: 5,
                          child: Text(
                            store.placeName,
                            overflow: TextOverflow.ellipsis,
                          ),
                        ),
                        Flexible(
                          flex: 3,
                          child: Row(
                            children: [
                              OutlinedButton(
                                onPressed: () async {
                                  await launchUrlString(store.placeUrl);
                                },
                                style: OutlinedButton.styleFrom(
                                  padding: const EdgeInsets.all(0),
                                ),
                                child: const Text(
                                  "가게 상세",
                                  style: TextStyle(fontSize: 12),
                                ),
                              ),
                              const SizedBox(
                                width: 10,
                              ),
                              OutlinedButton(
                                onPressed: () async {
                                  // 토큰 유효한지 검증
                                  final result =
                                      await TokenManager.verifyToken();
                                  if (mounted) {
                                    if (result == 'login need') {
                                      SnackBarManager.alertSnackBar(
                                          context, '로그인이 필요합니다!');
                                      Navigator.of(context)
                                          .pushNamedAndRemoveUntil(
                                              '/profile', (route) => false);
                                    } else {
                                      Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) =>
                                              ReceiptScanningScreen(
                                            store: store,
                                            storeId: null,
                                          ),
                                        ),
                                      );
                                    }
                                  }
                                },
                                style: OutlinedButton.styleFrom(
                                  padding: const EdgeInsets.all(0),
                                ),
                                child: const Text(
                                  '리뷰 쓰기',
                                  style: TextStyle(fontSize: 12),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  )
          ],
        ),
      ),
    );
  }
}
