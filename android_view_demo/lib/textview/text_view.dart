import 'package:android_view_demo/textview/text_view_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class TextView extends StatefulWidget {
  String text;
  int textSize;
  int textColor;
  Function(TextViewController controller) onViewCreated;

  TextView(
      {Key key, this.text, this.textSize, this.textColor,@required this.onViewCreated})
      : super(key: key);

  @override
  State<StatefulWidget> createState() => _TextViewState();
}

class _TextViewState extends State<TextView> {
  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: 'com.gago.fluter/TextView',
      creationParams: {
        'text': widget.text,
        'textSize': widget.textSize,
        'textColor': widget.textColor,
      },
      creationParamsCodec: StandardMessageCodec(),
      onPlatformViewCreated: (id) {
        widget.onViewCreated(TextViewController(id));
      },
    );
  }
}
