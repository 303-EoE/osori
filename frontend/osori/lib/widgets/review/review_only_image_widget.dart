import 'package:flutter/material.dart';
import 'package:osori/models/review/review_whole_model.dart';
import 'package:osori/widgets/common/token_manager.dart';
import 'package:osori/widgets/review/review_widget.dart';

class ReviewOnlyImage extends StatelessWidget {
  final List<ReviewWholeModel> reviews;

  const ReviewOnlyImage({super.key, required this.reviews});

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return GridView.count(
      crossAxisCount: 3,
      children: [
        for (var review in reviews)
          GestureDetector(
            onTapUp: (details) async {
              String userId = await TokenManager.readUserId();
              if (context.mounted) {
                showDialog(
                    context: context,
                    builder: (context) {
                      return Dialog(
                        child: SingleChildScrollView(
                          scrollDirection: Axis.vertical,
                          child: Container(
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(20),
                              ),
                              clipBehavior: Clip.hardEdge,
                              child: Review(
                                review: review.id,
                                userId: userId,
                              )),
                        ),
                      );
                    });
              }
            },
            child: Image.network(
              'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${review.images[0]}',
              width: size.width / 3,
              height: size.width / 3,
              fit: BoxFit.cover,
            ),
          )
      ],
    );
  }
}
