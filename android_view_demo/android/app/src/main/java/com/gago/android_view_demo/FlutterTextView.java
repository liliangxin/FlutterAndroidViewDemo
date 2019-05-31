package com.gago.android_view_demo;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

/**
 * Created by llx on 2019/5/30
 */
public class FlutterTextView implements PlatformView, MethodChannel.MethodCallHandler {

    TextView textView;
    MethodChannel methodChannel;

    public FlutterTextView(Context context, BinaryMessenger messenger, int id, Map<String, Object> args) {
        textView = new TextView(context);
        methodChannel = new MethodChannel(messenger, "com.gago.fluter/TextView_" + id);
        methodChannel.setMethodCallHandler(this);
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
