import 'dart:async';

import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:intl/intl.dart';
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
  List<CustomOverlay> customOverlays = [];
  var numberFormat = NumberFormat('###,###,###,###');

  void getNearStores() async {
    position = await DevicePostionService.getNowPosition();
    nowPos = LatLng(position.latitude, position.longitude);
    Map<String, String>? depths = await KakaoLocalApiService.getDepthByPosition(
        '${nowPos.longitude}', '${nowPos.latitude}');
    stores =
        await StoreService.getNearStores(depths['depth1']!, depths['depth2']!);
    Set<Marker> aroundStores = {};
    for (var store in stores) {
      aroundStores.add(Marker(
        markerId: store.id.toString(),
        latLng:
            LatLng(double.parse(store.latitude), double.parse(store.longitude)),
        width: 40,
        height: 40,
        markerImageSrc: store.category == '음식점'
            ? 'https://cdn-icons-png.flaticon.com/128/3170/3170733.png'
            : store.category == '헬스장'
                ? "https://cdn-icons-png.flaticon.com/128/2936/2936886.png"
                : store.category == '네일샵'
                    ? "https://cdn-icons-png.flaticon.com/128/5085/5085468.png"
                    : 'https://cdn-icons-png.flaticon.com/512/10042/10042921.png',
      ));
      var content =
          '<div style="display:flex; flex-direction:column; gap:10px; background-color: #333333; border-radius: 12px; padding: 8px; color: white;">'
          '<div>${store.name}</div>'
          '<div style="display:flex; justify-content:space-between;">'
          '<div>${numberFormat.format(store.averagePrice)}원</div>'
          '<div style="display:flex;">'
          '<img src="https://cdn-icons-png.flaticon.com/128/2107/2107957.png" width=16 height=16/>'
          '<div>${store.averageRate}</div>'
          '</div>'
          '</div>'
          '</div>'
          '</div>';
      final customOverlay = CustomOverlay(
        customOverlayId: store.id.toString(),
        latLng: LatLng(
          double.parse(store.latitude),
          double.parse(store.longitude),
        ),
        content: content,
      );
      customOverlays.add(customOverlay);
    }

    // 지도 마커에 추가하기
    markers.addAll(aroundStores);
    setState(() {});
  }

  void _determinePosition(Timer timer) async {
    position = await DevicePostionService.getNowPosition();
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
    getNearStores();
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
              mapController.setLevel(4);
              setState(() {});
            }),
            customOverlays: customOverlays,
            onCustomOverlayTap: (customOverlayId, latLng) {
              timer.cancel();
              StoreModel tappedStore = stores.firstWhere(
                  (store) => store.id.toString() == customOverlayId);
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => StoreScreen(
                    storeId: tappedStore.id,
                  ),
                ),
              );
            },
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
