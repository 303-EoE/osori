import 'package:flutter/material.dart';
import 'package:osori/widgets/feed/feed_widget.dart';

class FeedOnlyImage extends StatelessWidget {
  const FeedOnlyImage({super.key});

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return GridView.count(
      crossAxisCount: 3,
      children: [
        for (var i in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
          GestureDetector(
            onTapUp: (details) {
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
                            child: Feed(idx: i)),
                      ),
                    );
                  });
            },
            child: Image.asset(
              i % 2 == 1
                  ? 'assets/images/test.jpg'
                  : 'assets/images/testWidth.jpg',
              width: size.width / 3,
              height: size.width / 3,
              fit: BoxFit.cover,
            ),
          )
      ],
    );
  }
}
