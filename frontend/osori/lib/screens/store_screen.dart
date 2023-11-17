import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';
import 'package:osori/models/review/review_summary_model.dart';
import 'package:osori/providers/review_summary_model_provider.dart';
import 'package:osori/screens/receipt_scanning_screen.dart';
import 'package:osori/services/osori/review_service.dart';
import 'package:osori/services/osori/store_service.dart';
import 'package:osori/widgets/review/review_widget.dart';

class StoreScreen extends StatefulWidget {
  final int storeId;
  const StoreScreen({
    super.key,
    required this.storeId,
  });

  @override
  State<StoreScreen> createState() => _StoreScreenState();
}

class _StoreScreenState extends State<StoreScreen> {
  late Future<Map<String, dynamic>?> storeModel;
  var numberFormat = NumberFormat('###,###,###,###');

  @override
  void initState() {
    super.initState();
    storeModel = StoreService.getStoreDescription(widget.storeId.toString());
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      appBar: AppBar(
        title: const Text("가게 상세"),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        child: Padding(
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
                            snapshot.data!['name'],
                            style: const TextStyle(
                              fontSize: 32,
                              fontWeight: FontWeight.w600,
                            ),
                          ),
                          Row(
                            children: [
                              const Icon(Icons.savings_outlined),
                              const SizedBox(
                                width: 10,
                              ),
                              Text(
                                '${numberFormat.format(snapshot.data!['averagePrice'])}원',
                                style: const TextStyle(
                                  fontSize: 20,
                                ),
                              ),
                            ],
                          ),
                          Row(
                            children: [
                              const Icon(
                                Icons.star_rounded,
                                color: Colors.yellow,
                              ),
                              const SizedBox(
                                width: 10,
                              ),
                              Text(
                                snapshot.data!['averageRate'].toString(),
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
                              const SizedBox(
                                width: 10,
                              ),
                              Text(snapshot.data!['addressName']),
                            ],
                          ),
                          Row(
                            children: [
                              const Icon(
                                Icons.phone_outlined,
                              ),
                              const SizedBox(
                                width: 10,
                              ),
                              Text(
                                snapshot.data!['phone'] == ""
                                    ? '없음'
                                    : snapshot.data!['phone'],
                              ),
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
                  final AsyncValue<List<ReviewSummaryModel>> reviews =
                      ref.watch(reviewSummaryModelProvider(
                          widget.storeId.toString()));

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
                                            store: null,
                                            storeId: model!['id'],
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
                                      final wholeReview =
                                          await ReviewService.getDetailedReview(
                                              review.id);
                                      if (mounted) {
                                        showDialog(
                                            context: context,
                                            builder: (context) {
                                              return Dialog(
                                                child: SingleChildScrollView(
                                                  scrollDirection:
                                                      Axis.vertical,
                                                  child: Container(
                                                      decoration: BoxDecoration(
                                                        borderRadius:
                                                            BorderRadius
                                                                .circular(20),
                                                      ),
                                                      clipBehavior:
                                                          Clip.hardEdge,
                                                      child: Review(
                                                        review: wholeReview!,
                                                      )),
                                                ),
                                              );
                                            });
                                      }
                                    },
                                    child: Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        const SizedBox(
                                          height: 10,
                                        ),
                                        Text(
                                          '${review.createdAt.split('-')[0]}년 ${review.createdAt.split('-')[1]}월 ${review.createdAt.split('-')[2].split('T')[0]}일',
                                          style: const TextStyle(
                                            color: Colors.grey,
                                          ),
                                        ),
                                        Container(
                                          height: size.width / 3,
                                          clipBehavior: Clip.hardEdge,
                                          decoration: BoxDecoration(
                                              border: Border.all(),
                                              borderRadius:
                                                  const BorderRadius.all(
                                                      Radius.circular(20))),
                                          child: Row(
                                            children: [
                                              Image.network(
                                                'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${review.image}',
                                                width: size.width / 3,
                                              ),
                                              Flexible(
                                                child: Column(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment
                                                          .spaceBetween,
                                                  children: [
                                                    Flexible(
                                                      flex: 2,
                                                      child: Padding(
                                                        padding:
                                                            const EdgeInsets
                                                                .symmetric(
                                                                horizontal: 20),
                                                        child: Row(
                                                          mainAxisAlignment:
                                                              MainAxisAlignment
                                                                  .spaceBetween,
                                                          mainAxisSize:
                                                              MainAxisSize.max,
                                                          children: [
                                                            Row(
                                                              children: [
                                                                Container(
                                                                  decoration: BoxDecoration(
                                                                      borderRadius:
                                                                          BorderRadius.circular(
                                                                              1000)),
                                                                  clipBehavior:
                                                                      Clip.hardEdge,
                                                                  child: Image
                                                                      .network(
                                                                    'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${review.memberProfileImageUrl}',
                                                                    width:
                                                                        size.width /
                                                                            7,
                                                                  ),
                                                                ),
                                                                Text(review
                                                                    .memberNickname),
                                                              ],
                                                            ),
                                                            Column(
                                                              children: [
                                                                Text(
                                                                    '${numberFormat.format(review.averageCost)}원'),
                                                                Row(
                                                                  children: [
                                                                    const Icon(
                                                                      Icons
                                                                          .star_rounded,
                                                                      color: Colors
                                                                          .yellow,
                                                                    ),
                                                                    Text(review
                                                                        .rate
                                                                        .toString()),
                                                                  ],
                                                                ),
                                                              ],
                                                            )
                                                          ],
                                                        ),
                                                      ),
                                                    ),
                                                    Flexible(
                                                      flex: 3,
                                                      child: Text(
                                                        review.content,
                                                        overflow: TextOverflow
                                                            .ellipsis,
                                                      ),
                                                    ),
                                                  ],
                                                ),
                                              )
                                            ],
                                          ),
                                        ),
                                      ],
                                    ))
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
      ),
    );
  }
}
