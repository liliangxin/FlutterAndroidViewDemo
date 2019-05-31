package com.gago.android_view_demo;

import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

/**
 * Created by llx on 2019/5/30
 */
public class FlutterTextViewFactory extends PlatformViewFactory {

    BinaryMessenger messenger;

    public FlutterTextViewFactory(BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
    }

    @Override
    public PlatformView create(Context context, int i, Object o) {
        Map<String,Object> args = (Map<String, Object>) o;
        return new FlutterTextView(context,messenger,i,args);
    }
}
