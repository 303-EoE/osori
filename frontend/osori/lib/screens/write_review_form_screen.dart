import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:image_picker/image_picker.dart';

class WriteReviewFormScreen extends StatefulWidget {
  const WriteReviewFormScreen({super.key});

  @override
  State<WriteReviewFormScreen> createState() => _WriteReviewFormScreenState();
}

class _WriteReviewFormScreenState extends State<WriteReviewFormScreen> {
  final _formKey = GlobalKey<FormBuilderState>();
  String typeUnit = "";
  final List<File> _selectedImages = [];
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    List<String> billTypeOptions = ["횟수", "시간", "일일", "개월", "금액", "기타"];
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
          child: FormBuilder(
              key: _formKey,
              child: Column(
                children: [
                  FormBuilderTextField(
                    name: "date",
                    initialValue: "2023.11.01",
                    decoration: const InputDecoration(
                      labelText: "방문 날짜",
                      prefixIcon: Icon(Icons.date_range),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(
                          Radius.circular(20),
                        ),
                      ),
                    ),
                    enabled: false,
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  FormBuilderTextField(
                    name: "price",
                    initialValue: "000,000",
                    decoration: const InputDecoration(
                      labelText: "총 금액",
                      suffixText: "원",
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(
                          Radius.circular(20),
                        ),
                      ),
                    ),
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
                          // validator: FormBuilderValidators.compose([
                          //   FormBuilderValidators.required(),
                          // ]),
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
                          name: "factor",
                          keyboardType: TextInputType.number,
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
                      final pickedImages = await imagePicker.pickMultiImage();
                      // print(pickedImages);
                      for (var image in pickedImages) {
                        _selectedImages.add(File(image.path));
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
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  ElevatedButton(
                      onPressed: () {
                        // Validate and save the form values
                        _formKey.currentState?.saveAndValidate();
                        debugPrint(_formKey.currentState?.value.toString());
                        _formKey.currentState?.validate();
                        debugPrint(
                            _formKey.currentState?.instantValue.toString());
                        final snackBar = SnackBar(
                          content: const Text('등록이 완료되었습니다.'),
                          action: SnackBarAction(
                            label: '확인',
                            onPressed: () {
                              // Some code to undo the change.
                            },
                          ),
                        );
                        ScaffoldMessenger.of(context).showSnackBar(snackBar);
                        // 어디로 보낼까? 고민해보자
                        Navigator.pop(context);
                      },
                      child: const Text("리뷰 등록하기")),
                ],
              )),
        ),
      ),
    );
  }
}
