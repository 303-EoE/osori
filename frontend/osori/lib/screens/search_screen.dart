import 'package:flutter/material.dart';
import 'package:kakao_map_plugin/kakao_map_plugin.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:osori/screens/receipt_scanning_screen.dart';
import 'package:osori/services/other/kakao_local_api_service.dart';
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
                    SizedBox(
                      width: size.width / 3 * 2,
                      height: 60,
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
                        ),
                        textAlign: TextAlign.center,
                        // onSubmitted: KakaoLocalApiService.getStoresByKeyword,
                      ),
                    ),
                    OutlinedButton(
                      onPressed: () async {
                        if (isComplete) stores.clear();
                        stores = await KakaoLocalApiService.getStoresByKeyword(
                          _textController.text,
                          widget.nowPos.longitude.toString(),
                          widget.nowPos.latitude.toString(),
                        );
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
            if (isComplete)
              for (var store in stores)
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        store.placeName,
                        overflow: TextOverflow.ellipsis,
                      ),
                      Row(
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
                            onPressed: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => ReceiptScanningScreen(
                                      storeId: int.parse(store.id)),
                                ),
                              );
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
                    ],
                  ),
                )
          ],
        ),
      ),
    );
  }
}
