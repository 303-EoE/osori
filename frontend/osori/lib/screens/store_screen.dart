import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:osori/models/review/review_summary_model.dart';
import 'package:osori/providers/review_summary_model_provider.dart';
import 'package:osori/screens/receipt_scanning_screen.dart';
import 'package:osori/services/other/kakao_local_api_service.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:osori/widgets/review/review_widget.dart';

class StoreScreen extends StatefulWidget {
  final Map<String, dynamic> tappedStore;
  const StoreScreen({
    super.key,
    required this.tappedStore,
  });

  @override
  State<StoreScreen> createState() => _StoreScreenState();
}

class _StoreScreenState extends State<StoreScreen> {
  // 키워드로 장소 검색하기 활용
  // name, latitude, longitude, id 필요
  // address_name, phone을 받아오기
  late Future<KakaoStoreModel?> storeModel;
  late String userId;

  @override
  void initState() {
    super.initState();
    storeModel = KakaoLocalApiService.getStoreDesciptionByKeyword(
        widget.tappedStore['name'],
        widget.tappedStore['longitude'],
        widget.tappedStore['latitude'],
        widget.tappedStore['id'].toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("가게 상세"),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(
          horizontal: 20,
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            FutureBuilder(
              future: storeModel,
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return SingleChildScrollView(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          widget.tappedStore['name'],
                          style: const TextStyle(
                            fontSize: 32,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Row(
                          children: [
                            const Icon(Icons.savings_outlined),
                            Text(
                              ' ${widget.tappedStore['averagePrice'].toString()}원',
                              style: const TextStyle(
                                fontSize: 20,
                              ),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            const Icon(
                              Icons.star_outline,
                            ),
                            Text(
                              ' ${widget.tappedStore['averageRate'].toString()} / 5',
                              style: const TextStyle(
                                fontSize: 20,
                              ),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            const Icon(
                              Icons.location_on_outlined,
                            ),
                            Text(snapshot.data!.addressName),
                          ],
                        ),
                        Row(
                          children: [
                            const Icon(
                              Icons.phone_outlined,
                            ),
                            Text(snapshot.data!.phone),
                          ],
                        ),
                      ],
                    ),
                  );
                }
                return const CircularProgressIndicator();
              },
            ),
            const SizedBox(
              height: 20,
            ),
            Consumer(
              builder: (context, ref, child) {
                // 가게 리뷰 요약 조회 (우리 api)로 리뷰 받아오기
                // store_id 필요
                final AsyncValue<List<ReviewSummaryModel>> reviews = ref.watch(
                    reviewSummaryModelProvider(
                        widget.tappedStore['id'].toString()));

                return Center(
                  child: switch (reviews) {
                    AsyncData(:final value) => Column(
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Row(
                                children: [
                                  const Text(
                                    "리뷰 목록 ",
                                    style: TextStyle(
                                      fontSize: 24,
                                    ),
                                  ),
                                  Text(
                                    '${value.length}개',
                                    style: const TextStyle(
                                      color: Colors.grey,
                                    ),
                                  ),
                                ],
                              ),
                              GestureDetector(
                                onTapUp: (details) async {
                                  final model = await storeModel;
                                  if (mounted) {
                                    Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) =>
                                            ReceiptScanningScreen(
                                          store: model!,
                                        ),
                                      ),
                                    );
                                  }
                                },
                                child: const Row(
                                  children: [
                                    Icon(
                                      Icons.add,
                                    ),
                                    Text(
                                      '리뷰 쓰기',
                                      style: TextStyle(
                                        fontSize: 16,
                                      ),
                                    ),
                                  ],
                                ),
                              )
                            ],
                          ),
                          if (value.isEmpty)
                            const Column(
                              children: [
                                SizedBox(
                                  height: 30,
                                ),
                                Icon(
                                  Icons.sentiment_dissatisfied_outlined,
                                  size: 64,
                                ),
                                SizedBox(
                                  height: 10,
                                ),
                                Text(
                                  '아직 리뷰가 없습니다...',
                                  style: TextStyle(
                                    fontSize: 24,
                                    fontWeight: FontWeight.w600,
                                  ),
                                ),
                              ],
                            )
                          else
                            for (var review in value)
                              GestureDetector(
                                  onTapUp: (details) async {
                                    userId = await TokenManager.readUserId();
                                    if (mounted) {
                                      showDialog(
                                          context: context,
                                          builder: (context) {
                                            return Dialog(
                                              child: SingleChildScrollView(
                                                scrollDirection: Axis.vertical,
                                                child: Container(
                                                    decoration: BoxDecoration(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              20),
                                                    ),
                                                    clipBehavior: Clip.hardEdge,
                                                    child: Review(
                                                      review: review,
                                                      userId: userId,
                                                    )),
                                              ),
                                            );
                                          });
                                    }
                                  },
                                  child: Text(review.content))
                        ],
                      ),
                    AsyncError() => const Text('Error happened'),
                    _ => const CircularProgressIndicator(),
                  },
                );
              },
            )
          ],
        ),
      ),
    );
  }
}
