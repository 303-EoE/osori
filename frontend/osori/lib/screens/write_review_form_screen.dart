import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:form_builder_validators/form_builder_validators.dart';
import 'package:image_cropper/image_cropper.dart';
import 'package:image_picker/image_picker.dart';
import 'package:osori/models/kakao_store_model.dart';
import 'package:osori/services/osori/review_service.dart';
import 'package:osori/services/osori/store_service.dart';
import 'package:osori/widgets/common/snack_bar_manager.dart';

class WriteReviewFormScreen extends StatefulWidget {
  final KakaoStoreModel? store;
  final int? storeId;
  final String paidAt;
  final int totalPrice;
  const WriteReviewFormScreen({
    super.key,
    required this.paidAt,
    required this.totalPrice,
    required this.store,
    this.storeId,
  });

  @override
  State<WriteReviewFormScreen> createState() => _WriteReviewFormScreenState();
}

class _WriteReviewFormScreenState extends State<WriteReviewFormScreen> {
  final _formKey = GlobalKey<FormBuilderState>();
  String typeUnit = "";
  final List<File> _selectedImages = [];
  bool isSubmitted = false;
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<String> billTypeOptions = ["횟수권", "시간권", "일일권", "개월권", "금액권", "기타"];
    List<double> rates = [0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5];
    return Scaffold(
      appBar: AppBar(
        title: const Text('상세 정보'),
        centerTitle: true,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: EdgeInsets.symmetric(
            horizontal: size.width / 10,
            vertical: size.height / 30,
          ),
          child: Stack(children: [
            FormBuilder(
                skipDisabled: isSubmitted,
                key: _formKey,
                child: Column(
                  children: [
                    FormBuilderTextField(
                      name: "date",
                      initialValue: widget.paidAt,
                      decoration: const InputDecoration(
                        labelText: "방문 날짜",
                        prefixIcon: Icon(Icons.date_range),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                            Radius.circular(20),
                          ),
                        ),
                      ),
                      validator: FormBuilderValidators.compose([
                        FormBuilderValidators.required(),
                      ]),
                      enabled: false,
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    FormBuilderTextField(
                      valueTransformer: (value) => int.tryParse(value!),
                      name: "price",
                      initialValue: '${widget.totalPrice}',
                      decoration: const InputDecoration(
                        labelText: "총 금액",
                        suffixText: "원",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                            Radius.circular(20),
                          ),
                        ),
                      ),
                      validator: FormBuilderValidators.compose([
                        FormBuilderValidators.required(),
                      ]),
                      enabled: false,
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SizedBox(
                          width: size.width / 3,
                          child: FormBuilderTextField(
                            valueTransformer: (value) => int.tryParse(value!),
                            name: "headCount",
                            keyboardType: TextInputType.number,
                            decoration: const InputDecoration(
                              labelText: "방문 인원",
                              suffixText: "명",
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20),
                                ),
                              ),
                            ),
                            validator: FormBuilderValidators.compose([
                              FormBuilderValidators.required(),
                            ]),
                          ),
                        ),
                        SizedBox(
                          width: size.width / 3,
                          child: FormBuilderDropdown<double>(
                            name: 'rate',
                            decoration: const InputDecoration(
                              labelText: '평점',
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20),
                                ),
                              ),
                            ),
                            validator: FormBuilderValidators.compose([
                              FormBuilderValidators.required(),
                            ]),
                            items: rates
                                .map((rate) => DropdownMenuItem(
                                      value: rate,
                                      alignment: Alignment.center,
                                      child: Text("$rate"),
                                    ))
                                .toList(),
                          ),
                        )
                      ],
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.end,
                      children: [
                        SizedBox(
                          width: size.width / 3,
                          child: FormBuilderDropdown<String>(
                            name: "billType",
                            initialValue: typeUnit,
                            onChanged: (context) {
                              setState(() {
                                typeUnit = context!;
                              });
                            },
                            decoration: const InputDecoration(
                              labelText: "결제 유형",
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20),
                                ),
                              ),
                            ),
                            validator: FormBuilderValidators.compose([
                              FormBuilderValidators.required(),
                            ]),
                            items: billTypeOptions
                                .map(
                                  (option) => DropdownMenuItem(
                                    alignment: AlignmentDirectional.center,
                                    value: option,
                                    child: Text(option),
                                  ),
                                )
                                .toList(),
                          ),
                        ),
                        SizedBox(
                          width: size.width / 3,
                          child: FormBuilderTextField(
                            valueTransformer: (value) => int.tryParse(value!),
                            name: "factor",
                            keyboardType: TextInputType.number,
                            initialValue: '1',
                            decoration: InputDecoration(
                              labelText: '단위',
                              suffixText: typeUnit,
                              border: const OutlineInputBorder(
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20),
                                ),
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    if (_selectedImages.isNotEmpty)
                      SingleChildScrollView(
                        clipBehavior: Clip.hardEdge,
                        scrollDirection: Axis.horizontal,
                        child: Row(
                          children: [
                            for (var image in _selectedImages)
                              Image.file(
                                image,
                                fit: BoxFit.cover,
                                width: size.width / 4,
                                height: size.width / 4,
                              ),
                          ],
                        ),
                      ),
                    IconButton(
                      icon: const Icon(Icons.add_photo_alternate_outlined),
                      onPressed: () async {
                        final imagePicker = ImagePicker();
                        final pickedImages =
                            await imagePicker.pickMultiImage(imageQuality: 50);
                        for (var image in pickedImages) {
                          final croppedFile = await ImageCropper().cropImage(
                              sourcePath: image.path,
                              compressFormat: ImageCompressFormat.jpg,
                              compressQuality: 100,
                              uiSettings: [
                                AndroidUiSettings(
                                  toolbarTitle: '이미지 자르기',
                                  toolbarColor: Colors.deepOrange,
                                  toolbarWidgetColor: Colors.white,
                                  initAspectRatio: CropAspectRatioPreset.square,
                                  lockAspectRatio: true,
                                )
                              ]);
                          if (croppedFile != null) {
                            _selectedImages.add(File(croppedFile.path));
                          }
                        }
                        setState(() {});
                      },
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    FormBuilderTextField(
                      maxLines: 2,
                      minLines: 1,
                      name: 'content',
                      decoration: const InputDecoration(
                        labelText: '리뷰',
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(
                            Radius.circular(20),
                          ),
                        ),
                      ),
                      validator: FormBuilderValidators.compose([
                        FormBuilderValidators.required(),
                      ]),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    ElevatedButton(
                        onPressed: () async {
                          isSubmitted = true;
                          setState(() {});
                          // Validate and save the form values
                          _formKey.currentState?.saveAndValidate();
                          debugPrint(_formKey.currentState?.value.toString());
                          _formKey.currentState?.validate();
                          debugPrint(
                              _formKey.currentState?.instantValue.toString());
                          var formValues = _formKey.currentState;
                          // 가게 등록 요청 보내기
                          int dbStoreId = -1;
                          if (widget.store != null && widget.storeId == null) {
                            dbStoreId =
                                await StoreService.registerStore(widget.store!);
                          } else if (widget.store == null &&
                              widget.storeId != null) {
                            dbStoreId = widget.storeId!;
                          }
                          if (mounted) {
                            if (dbStoreId == -1) {
                              SnackBarManager.alertSnackBar(
                                  context, '가게 등록 실패!');
                              return;
                            }
                          }
                          // 리뷰 등록 요청 보내기
                          int result = await ReviewService.createReview(
                            dbStoreId,
                            formValues?.value['date'],
                            formValues?.value['price'],
                            formValues?.value['headCount'],
                            formValues?.value['rate'],
                            formValues?.value['billType'],
                            formValues?.value['factor'],
                            formValues?.value['content'],
                            _selectedImages,
                          );
                          if (mounted) {
                            if (result == 200) {
                              SnackBarManager.completeSnackBar(
                                  context, '리뷰 등록');
                              // 어디로 보낼까? 고민해보자
                              Navigator.of(context).pushNamedAndRemoveUntil(
                                  '/profile', (route) => false);
                            } else if (result == 0) {
                              SnackBarManager.alertSnackBar(
                                  context, '리뷰 등록 실패!');
                              isSubmitted = false;
                              setState(() {});
                            }
                          }
                        },
                        child: const Text("리뷰 등록하기")),
                  ],
                )),
            if (isSubmitted) const Center(child: CircularProgressIndicator())
          ]),
        ),
      ),
    );
  }
}
