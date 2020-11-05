package com.anythink.reactnativejs.utils;

import android.util.Log;

public class MsgTools {
    private static final String TAG = "ATReactNativeBridge";
    static boolean isDebug = true;

    public static void pirntMsg(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void setLogDebug(boolean debug) {
        isDebug = debug;
    }

}
