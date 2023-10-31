import 'dart:async';

import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:kakao_map_plugin/kakao_map_plugin.dart';
import 'package:osori/screens/search_screen.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';

class MapScreen extends StatefulWidget {
  const MapScreen({super.key});

  @override
  State<MapScreen> createState() => _MapScreenState();
}

class _MapScreenState extends State<MapScreen> {
  late KakaoMapController mapController;
  late Timer timer;
  bool isGPSPermissioned = false;
  late Position position;
  bool isInputDisabled = true;

  void _determinePosition(Timer timer) async {
    bool serviceEnabled;
    LocationPermission permission;

    serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      return Future.error('Location services are disabled.');
    }

    permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        return Future.error('Location permissions are denied');
      }
    }

    if (permission == LocationPermission.deniedForever) {
      return Future.error(
          'Location permissions are permanently denied, we cannot request permissions.');
    }
    position = await Geolocator.getCurrentPosition();
    isGPSPermissioned = true;
  }

  @override
  void initState() {
    super.initState();
    setState(() {
      timer = Timer.periodic(const Duration(seconds: 1), _determinePosition);
    });
  }

  @override
  Widget build(BuildContext context) {
    Set<Marker> markers = {};

    return Scaffold(
      body: Stack(
        children: [
          KakaoMap(
            onMapCreated: ((controller) {
              mapController = controller;
              markers.add(Marker(
                markerId: UniqueKey().toString(),
                latLng: LatLng(37.479996, 126.915363),
              ));

              setState(() {});
            }),
            markers: markers.toList(),
            center: LatLng(
              isGPSPermissioned ? position.latitude : 33.452613, // 카카오 제주 본사
              isGPSPermissioned ? position.longitude : 126.570888, // 카카오 제주 본사
            ),
          ),
          GestureDetector(
            onTapUp: (details) {
              // print('push to search');
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => SearchScreen(
                    isInputDisabled: !isInputDisabled,
                  ),
                ),
              );
            },
            child: Padding(
              padding: const EdgeInsets.symmetric(vertical: 60, horizontal: 40),
              child: Row(
                children: [
                  Expanded(
                      child: Container(
                    clipBehavior: Clip.hardEdge,
                    height: 50,
                    decoration: BoxDecoration(
                      color: const Color(0xFFf9f8f8),
                      borderRadius: const BorderRadius.all(Radius.circular(20)),
                      border:
                          Border.all(color: const Color(0xFF818181), width: 2),
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Expanded(
                          child: TextField(
                            decoration: const InputDecoration(
                              border: InputBorder.none,
                              hintText: "키워드를 입력하세요!!",
                            ),
                            textAlign: TextAlign.center,
                            readOnly: isInputDisabled,
                            enabled: !isInputDisabled,
                          ),
                        ),
                      ],
                    ),
                  ))
                ],
              ),
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: const Color(0xFFf9f8f8),
        onPressed: () {
          mapController
              .setCenter(LatLng(position.latitude, position.longitude));
          mapController.setLevel(3);
          // mapController.panTo(LatLng(37.479996, 126.915363)); // 내 집
        },
        child: const Icon(
          Icons.navigation_outlined,
        ),
      ),
      bottomNavigationBar: const BottomNavigation(),
    );
  }
}
// TextField
// Padding(
//               padding: const EdgeInsets.symmetric(vertical: 60, horizontal: 40),
//               child: Row(
//                 children: [
//                   Expanded(
//                       child: Container(
//                     clipBehavior: Clip.hardEdge,
//                     height: 50,
//                     decoration: BoxDecoration(
//                       color: Colors.white,
//                       borderRadius: const BorderRadius.all(Radius.circular(20)),
//                       border:
//                           Border.all(color: const Color(0xFF818181), width: 2),
//                     ),
//                     child: Row(
//                       mainAxisAlignment: MainAxisAlignment.spaceBetween,
//                       children: [
//                         Expanded(
//                           child: TextField(
//                             decoration: const InputDecoration(
//                               border: InputBorder.none,
//                               hintText: "키워드를 입력하세요!!",
//                             ),
//                             textAlign: TextAlign.center,
//                             readOnly: isInputDisaled,
//                           ),
//                         ),
//                       ],
//                     ),
//                   ))
//                 ],
//               ),
//             ),

// 구식
// KakaoMapView(
//             width: size.width,
//             height: 1000,
//             kakaoMapKey: 'c08c97c986c312f22fc43678d907b46e',
//             // ***myHouse***
//             lat: isGPSPermissioned ? position.latitude : 37.480020,
//             lng: isGPSPermissioned ? position.longitude : 126.915378,
//             draggableMarker: true,
//             mapType: MapType.BICYCLE,
//             mapController: (controller) {
//               _mapController = controller;
//             },
//             markerImageURL:
//                 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png',
//             onTapMarker: (message) {
//               ScaffoldMessenger.of(context).showSnackBar(
//                 SnackBar(
//                   content: Text(message.message),
//                 ),
//               );
//             },
//           ),