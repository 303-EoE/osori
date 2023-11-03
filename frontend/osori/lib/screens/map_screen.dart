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
  late LatLng nowPos;
  bool isInputDisabled = true;
  Set<Marker> markers = {};
  late Marker marker;

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

    // 나의 현위치를 초기화해서 마커 찍기
    nowPos = LatLng(position.latitude, position.longitude);
    markers.removeWhere((marker) => marker.markerId == 'nowPos');
    markers.add(Marker(
      markerId: 'nowPos',
      latLng: nowPos,
      width: 40,
      height: 40,
      markerImageSrc:
          'https://cdn-icons-png.flaticon.com/512/10042/10042921.png',
    ));
    print(nowPos);

    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    setState(() {
      timer = Timer.periodic(const Duration(seconds: 1), _determinePosition);
    });
  }

  @override
  void dispose() {
    timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          KakaoMap(
            onMapCreated: ((controller) {
              mapController = controller;
              setState(() {});
            }),
            markers: markers.toList(),
            center: LatLng(
              37.501263, // 멀티캠퍼스
              127.039583, // 멀티캠퍼스
            ),
          ),
          GestureDetector(
            onTapUp: (details) {
              if (nowPos.toString().isNotEmpty) {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) {
                    timer.cancel();
                    return SearchScreen(
                      isInputDisabled: !isInputDisabled,
                      nowPos: nowPos,
                    );
                  }),
                );
              }
            },
            child: Padding(
              padding: const EdgeInsets.symmetric(vertical: 60, horizontal: 40),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Container(
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
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: const Color(0xFFf9f8f8),
        onPressed: () {
          LatLng nowPos = LatLng(position.latitude, position.longitude);
          mapController.setCenter(nowPos);
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
