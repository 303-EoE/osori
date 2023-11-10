import 'package:flutter/material.dart';

class SnackBarManager {
  static void welcomeSnackBar(BuildContext context, String nickname) {
    final snackBar = SnackBar(
      content: Text('$nickname님 반가워요!'),
      action: SnackBarAction(
        label: '저두요!',
        onPressed: () {
          // Some code to undo the change.
        },
      ),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  static void completeSnackBar(BuildContext context, String workName) {
    final snackBar = SnackBar(
      content: Text('$workName이 완료되었습니다.'),
      action: SnackBarAction(
        label: '확인',
        onPressed: () {
          // Some code to undo the change.
        },
      ),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  static void alertSnackBar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
      action: SnackBarAction(
        label: '확인',
        onPressed: () {
          // Some code to undo the change.
        },
      ),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
}
