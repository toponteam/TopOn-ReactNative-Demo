package com.anythink.reactnativejs;

import android.app.Activity;

import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public abstract class AnythinkReactContextBaseJavaModule extends ReactContextBaseJavaModule {
    private static final String TAG = AnythinkReactContextBaseJavaModule.class.getSimpleName();

    protected ReactApplicationContext mApplicationContext;

    public AnythinkReactContextBaseJavaModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mApplicationContext = reactContext;
    }

    public void sendEvent(String eventName, Object data) {
        if (mApplicationContext == null) {
            MsgTools.pirntMsg("AnythinkReactContextBaseJavaModule - mApplicationContext = null");
            return;
        }
        mApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, data);
    }

    public Activity getActivity() {
        if (mApplicationContext == null) {
            MsgTools.pirntMsg("AnythinkReactContextBaseJavaModule - mApplicationContext = null");
            return null;
        }
        return mApplicationContext.getCurrentActivity();
    }

}
