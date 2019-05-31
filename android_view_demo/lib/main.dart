import 'package:android_view_demo/textview/text_view.dart';
import 'package:android_view_demo/textview/text_view_controller.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  MethodChannel _channel;
  TextViewController _controller;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        child: Column(
          children: <Widget>[
            Container(
              height: 50,
              child: TextView(onViewCreated: (controller){
                _controller = controller;
              },text: 'aaaaaaa',textSize: 14,textColor:0xFF4FC3F7,),
            ),
            RaisedButton(onPressed: (){
              _controller.setTextColor(0xFFB71C1C);
            },child: Text('设置颜色'),),
            RaisedButton(onPressed: (){
              _controller.setTextSize(30);
            },child: Text('设置字体'),),
            RaisedButton(onPressed: (){
              _controller.setText("自定义文字");
            },child: Text('设置文字'),)
          ],
        ),
      )
    );
  }

  Future<void> setText() async {
    return _channel.invokeMethod('setText', 'sssssss');
  }
}
