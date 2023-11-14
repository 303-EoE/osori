import 'dart:async';

import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:kakao_map_plugin/kakao_map_plugin.dart';
import 'package:osori/models/store/store_model.dart';
import 'package:osori/screens/search_screen.dart';
import 'package:osori/screens/store_screen.dart';
import 'package:osori/services/osori/store_service.dart';
import 'package:osori/services/other/device_position_service.dart';
import 'package:osori/services/other/kakao_local_api_service.dart';
import 'package:osori/widgets/common/bottom_navigation_widget.dart';

class MapScreen extends StatefulWidget {
  const MapScreen({super.key});

  @override
  State<MapScreen> createState() => _MapScreenState();
}

class _MapScreenState extends State<MapScreen> {
  late KakaoMapController mapController;
  late Timer timer;
  late Position position;
  late LatLng nowPos;
  late Marker marker;
  Set<Marker> markers = {};
  List<StoreModel> stores = [];
  void getNearStores() async {
    position = await DevicePostionService.getNowPosition();
    // 나의 현위치를 초기화해서 마커 찍기
    nowPos = LatLng(position.latitude, position.longitude);
    Map<String, String>? depths = await KakaoLocalApiService.getDepthByPosition(
        '${nowPos.longitude}', '${nowPos.latitude}');
    await StoreService.getNearStores(depths!['depth1']!, depths['depth2']!);
    // stores = await StoreService.getNearStores('서울', '강남구');
    // stores.add(StoreModel.fromJson({
    //   "id": 26841712, // long
    //   "name": "등촌샤브칼국수 역삼점", // String
    //   "category": "음식점", // String
    //   "longitude": "127.02477328278", // String
    //   "latitude": "37.5064537970402", // String
    //   "depth1": "강남구", // String
    //   "depth2": "역삼동", // String
    //   "averageRate": 5, // double
    //   "averagePrice": 20000, // int
    //   "defaultBillType": "횟수권", // String
    // }));
    Set<Marker> aroundStores = {};
    for (var store in stores) {
      aroundStores.add(Marker(
        markerId: store.id.toString(),
        latLng:
            LatLng(double.parse(store.latitude), double.parse(store.longitude)),
        width: 40,
        height: 40,
        markerImageSrc:
            'https://cdn-icons-png.flaticon.com/512/10042/10042921.png',
      ));
    }
    // 지도 마커에 추가하기
    markers.addAll(aroundStores);
    setState(() {});
  }

  void _determinePosition(Timer timer) async {
    position = await DevicePostionService.getNowPosition();
    // 나의 현위치를 초기화해서 마커 찍기
    nowPos = LatLng(position.latitude, position.longitude);
    markers.removeWhere((marker) => marker.markerId == 'nowPos');
    markers.add(Marker(
      markerId: 'nowPos',
      latLng: nowPos,
      width: 40,
      height: 40,
      markerImageSrc:
          'https://cdn-icons-png.flaticon.com/128/10542/10542406.png',
    ));
    setState(() {});
  }

  @override
  void initState() {
    super.initState();
    // 내 주변 가게 마커 찍기
    getNearStores();
    // 1초마다 위치 초기화하기
    setState(() {
      timer = Timer.periodic(const Duration(seconds: 2), _determinePosition);
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
              mapController.setLevel(4);
              setState(() {});
            }),
            markers: markers.toList(),
            center: LatLng(
              37.501263, // 멀티캠퍼스
              127.039583, // 멀티캠퍼스
            ),
            onMarkerTap: (markerId, latLng, zoomLevel) {
              timer.cancel();
              StoreModel tappedStore =
                  stores.firstWhere((store) => store.id.toString() == markerId);
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => StoreScreen(
                    storeId: tappedStore.id,
                  ),
                ),
              );
            },
          ),
          GestureDetector(
            onTapUp: (details) {
              if (nowPos.toString().isNotEmpty) {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) {
                    timer.cancel();
                    return SearchScreen(
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
                    child: const Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Expanded(
                          child: TextField(
                            decoration: InputDecoration(
                              border: InputBorder.none,
                              hintText: "키워드를 입력하세요!!",
                            ),
                            textAlign: TextAlign.center,
                            readOnly: true,
                            enabled: false,
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
          mapController.setLevel(4);
        },
        child: const Icon(
          Icons.navigation_outlined,
        ),
      ),
      bottomNavigationBar: const BottomNavigation(),
    );
  }
}
