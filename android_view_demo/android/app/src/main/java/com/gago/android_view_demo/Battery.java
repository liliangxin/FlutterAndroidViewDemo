package com.gago.android_view_demo;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Created by llx on 2019/6/3
 */
public class Battery implements MethodChannel.MethodCallHandler {

    private static final String CHANNEL = "com.gago.android_view_demo/BatteryPlugin";
    private MethodChannel methodChannel;
    private Context context;

    Battery(Context context,BinaryMessenger messenger) {
        methodChannel = new MethodChannel(messenger, CHANNEL);
        methodChannel.setMethodCallHandler(this);
        this.context = context;
    }


    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String methodName = methodCall.method;
        switch (methodName) {
            case "getBattery":
                result.success(getBatteryLevel());
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private int getBatteryLevel() {
        int batteryLevel = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(context.getApplicationContext()).
                    registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }

        return batteryLevel;
    }
}
