import 'package:flutter/material.dart';

class Review extends StatelessWidget {
  final dynamic review;
  const Review({
    super.key,
    this.review,
  });

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
            Row(
              children: [
                Image.asset(
                  'assets/images/288X288.png',
                  height: 32,
                ),
                const SizedBox(
                  width: 16,
                ),
                SizedBox(
                  width: size.width / 2,
                  child: Text(
                    review.memberNickname,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              ],
            ),
            const Icon(Icons.more_vert_outlined),
          ]),
        ),
        AspectRatio(
          aspectRatio: 1,
          child: Image.network(
            'https://osori-bucket.s3.ap-northeast-2.amazonaws.com/${review.images[0]}',
            width: size.width,
            fit: BoxFit.cover,
          ),
        ),
        Padding(
          padding: const EdgeInsets.all(10),
          child: Column(children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Flexible(
                  child: Text(
                    review.storeName,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(
                        fontSize: 24, fontWeight: FontWeight.w600),
                  ),
                ),
                const Icon(
                  Icons.favorite_outline,
                  size: 32,
                )
              ],
            ),
            const SizedBox(
              height: 10,
            ),
            Row(
              children: [
                Text(
                  '${review.averageCost}',
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w500,
                  ),
                ),
                const SizedBox(
                  width: 20,
                ),
                const Row(
                  children: [
                    Icon(
                      Icons.star_rounded,
                      color: Color.fromARGB(255, 255, 230, 0),
                    ),
                    Icon(
                      Icons.star_rounded,
                      color: Colors.yellow,
                    ),
                    Icon(
                      Icons.star_rounded,
                      color: Colors.yellow,
                    ),
                    Icon(
                      Icons.star_half_rounded,
                      color: Colors.yellow,
                    ),
                    Icon(
                      Icons.star_outline_rounded,
                      color: Colors.yellow,
                    ),
                  ],
                ),
                Text('${review.rate}'),
              ],
            ),
            const SizedBox(
              height: 10,
            ),
            Text(
              review.content,
              maxLines: 5,
              overflow: TextOverflow.ellipsis,
            ),
            const SizedBox(
              height: 10,
            ),
            Row(
              children: [
                Text(
                  '${review.storeDepth1} ${review.storeDepth2}',
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
