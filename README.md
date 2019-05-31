# FlutterAndroidViewDemo
一个简单的在flutter中显示Android原生View的Demo

想在flutter中显示Android原生的View其实比较简单，由于只是demo，所以直接写下项目里，正常开发建议做成插件形式。

#### 涉及的类

##### PlatformView

##### PlatformViewFactory

下面以在flutter中显示Android原生TextView作为示例

##### 1.创建想要显示的FlutterView，定义属性

```dart
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
      viewType: 'com.gago.fluter/TextView',//channel的唯一标示
      creationParams: {
        'text': widget.text,
        'textSize': widget.textSize,
        'textColor': widget.textColor,
      },//初始化参数，可根据要求随意添加，这里是一个map
      creationParamsCodec: StandardMessageCodec(),//必选
      onPlatformViewCreated: (id) { //创建成功监听，这里初始化了控制器，可根据业务需求自定义
        widget.onViewCreated(TextViewController(id));
      },
    );
  }
}


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
```

##### 2.创建java相关代码，在android文件夹下

```java
public class FlutterTextView implements PlatformView, MethodChannel.MethodCallHandler {

    TextView textView;
    MethodChannel methodChannel;

    public FlutterTextView(Context context, BinaryMessenger messenger, int id, Map<String, Object> args) {
        textView = new TextView(context);
        methodChannel = new MethodChannel(messenger, "com.gago.fluter/TextView_" + id);
        methodChannel.setMethodCallHandler(this);
      //通过flutter的map初始化的参数，初始化TextView
        if (args.containsKey("text")) {
            String text = (String) args.get("text");
            textView.setText(text);
        }

        if (args.containsKey("textSize")) {
            int textSize = (int) args.get("textSize");
            textView.setTextSize(textSize);
        }

        if (args.containsKey("textColor")) {
            long textColor = (long) args.get("textColor");
            textView.setTextColor((int) textColor);
        }
    }

    @Override
    public View getView() {
        return textView;
    }

    @Override
    public void dispose() {

    }

  // 处理相关操作
    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String method = methodCall.method;
        switch (method) {
            case "setText":
                String text = (String) methodCall.arguments;
                textView.setText(text);
                result.success(null);
                break;
            case "setTextSize":
                int textSize = (int) methodCall.arguments;
                textView.setTextSize(textSize);
                result.success(null);
                break;
            case "setTextColor":
                long textColor = (long) methodCall.arguments;
                textView.setTextColor((int) textColor);
                result.success(null);
                break;
            default:
                result.notImplemented();
        }
    }
}


//工厂类
public class FlutterTextViewFactory extends PlatformViewFactory {

    BinaryMessenger messenger;

    public FlutterTextViewFactory(BinaryMessenger messenger) {
      //此处的super中StandardMessageCodec类是系统实现 
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
    }

    @Override
    public PlatformView create(Context context, int i, Object o) {
      // i 是flutter中创建而来的id
      // o 是初始化参数，一般是map
        Map<String,Object> args = (Map<String, Object>) o;
        return new FlutterTextView(context,messenger,i,args);
    }
}

// 注册插件
public class FlutterTextViewPlugin {

    public static void registerWith(PluginRegistry registry) {

        PluginRegistry.Registrar registrar = registry.registrarFor("com.gago.fluter/TextView");
        registrar
                .platformViewRegistry()
                .registerViewFactory("com.gago.fluter/TextView", new FlutterTextViewFactory(registrar.messenger()));

    }

}
```

通过以上的代码 一个简单的demo就完成了，需要注意的是通过AndroidView显示原生View会填满整个屏幕，因此外部需要做边界规划