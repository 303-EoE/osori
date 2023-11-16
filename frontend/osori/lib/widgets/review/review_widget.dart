import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/screens/profile_screen.dart';
import 'package:osori/services/osori/review_service.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';
import 'package:smooth_page_indicator/smooth_page_indicator.dart';

class Review extends StatefulWidget {
  final ReviewWholeModel review;
  const Review({
    super.key,
    required this.review,
  });

  @override
  State<Review> createState() => _ReviewState();
}

class _ReviewState extends State<Review> {
  int activeIndex = 0;
  var numberFormat = NumberFormat('###,###,###,###');
  bool isTouched = false;
  late bool isLiked;
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Container(
      // height: 600,
      // margin: const EdgeInsets.only(bottom: 10),
      decoration: const BoxDecoration(
        color: Color(0xFFf9f8f8),
        border: Border.symmetric(
          horizontal: BorderSide(width: 0.1),
        ),
      ),
      child: Column(children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 10),
          child:
              Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
            GestureDetector(
              onTapUp: (details) {
                // 사용자 프로필로 넘어가기
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) {
                    return ProfileScreen('${widget.review.memberId}');
                  },
                ));
              },
              child: Row(
                children: [
                  widget.review.memberProfileImageUrl == ""
                      ? Image.asset(
                          'assets/images/logo.png',
                          height: 32,
                        )
                      : Image.network(
                          'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${widget.review.memberProfileImageUrl}',
                          height: 32,
                        ),
                  const SizedBox(
                    width: 16,
                  ),
                  SizedBox(
                    width: size.width / 2,
                    child: Text(
                      widget.review.memberNickname,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ),
                ],
              ),
            ),
            // if (review.id == userId)
            if (widget.review.isMine)
              PopupMenuButton(
                surfaceTintColor: Colors.white,
                onSelected: (value) {
                  showDialog(
                    context: context,
                    builder: (context) {
                      return Dialog(
                        surfaceTintColor: Colors.white,
                        child: SizedBox(
                          width: 200,
                          height: 150,
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.spaceAround,
                              children: [
                                const Text(
                                  '리뷰를 삭제하시겠습니까?',
                                  style: TextStyle(
                                    fontSize: 18,
                                  ),
                                ),
                                const SizedBox(
                                  height: 20,
                                ),
                                Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceAround,
                                  children: [
                                    OutlinedButton(
                                      onPressed: () async {
                                        final response =
                                            await ReviewService.deleteReview(
                                                widget.review.id);
                                        if (context.mounted) {
                                          Navigator.pop(context);
                                          if (response == 200) {
                                            SnackBarManager.completeSnackBar(
                                                context, '리뷰 삭제');
                                          } else {
                                            SnackBarManager.alertSnackBar(
                                                context, '리뷰 삭제 실패!!');
                                          }
                                        }
                                      },
                                      child: const Text('삭제하기'),
                                    ),
                                    OutlinedButton(
                                      onPressed: () {
                                        Navigator.pop(context);
                                      },
                                      child: const Text('취소하기'),
                                    ),
                                  ],
                                )
                              ],
                            ),
                          ),
                        ),
                      );
                    },
                  );
                },
                itemBuilder: (context) {
                  return [
                    const PopupMenuItem(
                      value: 'remove',
                      child: Text('삭제하기'),
                    )
                  ];
                },
              )
          ]),
        ),
        Stack(children: [
          AspectRatio(
            aspectRatio: 1,
            child: CarouselSlider.builder(
              options: CarouselOptions(
                aspectRatio: 1,
                enableInfiniteScroll: false,
                initialPage: 0,
                viewportFraction: 1,
                enlargeCenterPage: true,
                onPageChanged: (index, reason) => setState(() {
                  activeIndex = index;
                }),
              ),
              itemCount: widget.review.images.length,
              itemBuilder: (context, index, realIndex) {
                return Image.network(
                  'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${widget.review.images[index]}',
                  width: size.width,
                  fit: BoxFit.cover,
                );
              },
            ),
          ),
          Container(
              margin: const EdgeInsets.only(top: 20.0),
              alignment: Alignment.bottomCenter,
              child: AnimatedSmoothIndicator(
                activeIndex: activeIndex,
                count: widget.review.images.length,
                effect: const JumpingDotEffect(
                    dotHeight: 6,
                    dotWidth: 6,
                    activeDotColor: Colors.black,
                    dotColor: Colors.grey),
              )),
        ]),
        Padding(
          padding: const EdgeInsets.all(10),
          child: Column(children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Flexible(
                  child: Text(
                    widget.review.storeName,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                        fontSize: 24, fontWeight: FontWeight.w600),
                  ),
                ),
                IconButton(
                  onPressed: () async {
                    if (!isTouched) {
                      isTouched = true;
                      isLiked = widget.review.liked;
                    }
                    await ReviewService.likeReview(widget.review.id);
                    setState(() {
                      isLiked = !isLiked;
                    });
                  },
                  icon: Icon(
                    isTouched
                        ? isLiked
                            ? Icons.favorite
                            : Icons.favorite_outline
                        : widget.review.liked
                            ? Icons.favorite
                            : Icons.favorite_outline,
                    size: 32,
                  ),
                )
              ],
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Flexible(
                  flex: 1,
                  child: Row(
                    children: [
                      Text(
                        '${numberFormat.format(widget.review.averageCost)}원',
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ],
                  ),
                ),
                Flexible(
                  flex: 1,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: [
                      const Icon(
                        Icons.star_rounded,
                        color: Color.fromARGB(255, 255, 230, 0),
                      ),
                      Text(
                        '${widget.review.rate}',
                        style: const TextStyle(fontWeight: FontWeight.w600),
                      ),
                      const SizedBox(
                        width: 15,
                      )
                    ],
                  ),
                ),
              ],
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              widget.review.content,
              maxLines: 5,
              overflow: TextOverflow.ellipsis,
            ),
            const SizedBox(
              height: 10,
            ),
            Row(
              children: [
                Text(
                  '${widget.review.storeDepth1} ${widget.review.storeDepth2}',
                  style: const TextStyle(color: Colors.grey),
                ),
              ],
            ),
          ]),
        )
      ]),
    );
  }
}
