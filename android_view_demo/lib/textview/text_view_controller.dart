import 'package:flutter/services.dart';

class TextViewController {
  int id;
  MethodChannel _channel;

  TextViewController(this.id) {
    _channel = MethodChannel('com.gago.fluter/TextView_$id');
  }

  Future<void> setText(String text) async {
    _channel.invokeMethod('setText', text);
  }

  Future<void> setTextSize(int textSize) async {
    _channel.invokeMethod('setTextSize', textSize);
  }

  Future<void> setTextColor(int textColor) async {
    _channel.invokeMethod('setTextColor', textColor);
  }
}
