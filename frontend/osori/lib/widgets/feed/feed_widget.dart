import 'package:flutter/material.dart';

class Feed extends StatelessWidget {
  const Feed({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Column(
      children: [
        for (var i in [1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
          Container(
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
                padding:
                    const EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
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
                            child: const Text(
                              'nicknamename',
                              overflow: TextOverflow.ellipsis,
                            ),
                          ),
                        ],
                      ),
                      const Icon(Icons.more_vert_outlined),
                    ]),
              ),
              Image.asset(
                i % 2 == 1
                    ? 'assets/images/test.jpg'
                    : 'assets/images/testWidth.jpg',
                width: size.width,
                height: size.width,
                fit: BoxFit.cover,
              ),
              const Padding(
                padding: EdgeInsets.all(10),
                child: Column(children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Flexible(
                        child: Text(
                          "어떤 가게",
                          overflow: TextOverflow.ellipsis,
                          style: TextStyle(
                              fontSize: 24, fontWeight: FontWeight.w600),
                        ),
                      ),
                      Icon(
                        Icons.favorite_outline,
                        size: 32,
                      )
                    ],
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Row(
                    children: [
                      Text(
                        "000,000원",
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                      SizedBox(
                        width: 20,
                      ),
                      Row(
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
                      Text("3.5"),
                    ],
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Text(
                      "짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 짱긴 텍스트지롱 헤헤 "),
                  SizedBox(
                    height: 10,
                  ),
                  Row(
                    children: [
                      Text(
                        '장소 | 20xx년 yy월 dd일',
                        style: TextStyle(color: Colors.grey),
                      ),
                    ],
                  ),
                ]),
              )
            ]),
          )
      ],
    );
  }
}
