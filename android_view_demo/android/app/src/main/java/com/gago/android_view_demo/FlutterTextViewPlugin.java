package com.gago.android_view_demo;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

/**
 * Created by llx on 2019/5/30
 */
public class FlutterTextViewPlugin {



    public static void registerWith(PluginRegistry registry) {

        PluginRegistry.Registrar registrar = registry.registrarFor("com.gago.fluter/TextView");
        registrar
                .platformViewRegistry()
                .registerViewFactory("com.gago.fluter/TextView", new FlutterTextViewFactory(registrar.messenger()));

    }

}
