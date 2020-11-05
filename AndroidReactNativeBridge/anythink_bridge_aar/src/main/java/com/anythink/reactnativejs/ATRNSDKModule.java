package com.anythink.reactnativejs;

import android.text.TextUtils;

import com.anythink.core.api.ATSDK;
import com.anythink.core.api.NetTrafficeCallback;
import com.anythink.reactnativejs.utils.CommonUtil;
import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ATRNSDKModule extends AnythinkReactContextBaseJavaModule {

    public static final String TAG = "ATRNSDK";

    public ATRNSDKModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void init(final String appId, final String appKey) {
        MsgTools.pirntMsg("initSDK:" + appId + ":" + appKey);
        ATSDK.init(super.mApplicationContext, appId, appKey);
    }

    @ReactMethod
    public void getSDKVersionName(Promise promise) {
        String sdkVersionName = ATSDK.getSDKVersionName();
        MsgTools.pirntMsg("getSDKVersionName: " + sdkVersionName);
        promise.resolve(sdkVersionName);
    }

    @ReactMethod
    public void isChinaSDK(Promise promise) {
        boolean isChinaSDK = ATSDK.isChinaSDK();
        MsgTools.pirntMsg("isChinaSDK: " + isChinaSDK);
        promise.resolve(isChinaSDK);
    }

    @ReactMethod
    public void setExcludeMyOfferPkgList(ReadableArray readableArray) {

        if (readableArray != null) {
            ArrayList<Object> objects = readableArray.toArrayList();
            List<String> list = new ArrayList<>();
            int size = objects.size();
            String p;
            Object o;
            for (int i = 0; i < size; i++) {
                o = objects.get(i);
                if (o instanceof String) {
                    p = (String) o;
                    list.add(p);
                    MsgTools.pirntMsg("exclude MyOffer: " + p);
                }
            }
            ATSDK.setExcludeMyOfferPkgList(list);
        }

    }

    @ReactMethod
    public static void initCustomMap(String customMap) {
        MsgTools.pirntMsg("initCustomMap:" + customMap);
        if (!TextUtils.isEmpty(customMap)) {
            Map<String, Object> map = CommonUtil.jsonStringToMap(customMap);
            ATSDK.initCustomMap(map);
        }
    }

    @ReactMethod
    public static void setPlacementCustomMap(String placementId, String customMap) {
        MsgTools.pirntMsg("setPlacementCustomMap:" + placementId + ":" + customMap);
        if (!TextUtils.isEmpty(customMap)) {
            Map<String, Object> map = CommonUtil.jsonStringToMap(customMap);
            ATSDK.initPlacementCustomMap(placementId, map);
        }
    }

    @ReactMethod
    public void setGDPRLevel(int level) {
        MsgTools.pirntMsg("setGDPRLevel:" + level);
        ATSDK.setGDPRUploadDataLevel(super.mApplicationContext, level);
    }

    @ReactMethod
    public void getGDPRLevel(Promise promise) {
        int gdprDataLevel = ATSDK.getGDPRDataLevel(this.mApplicationContext);
        MsgTools.pirntMsg("getGDPRLevel:" + gdprDataLevel);

        promise.resolve(gdprDataLevel);
    }

    @ReactMethod
    public void getUserLocation(final Promise promise) {
        MsgTools.pirntMsg("getUserLocation");
        ATSDK.checkIsEuTraffic(super.mApplicationContext, new NetTrafficeCallback() {
            @Override
            public void onResultCallback(boolean b) {
                MsgTools.pirntMsg("getUserLocation - onResultCallback: " + b);
                final int result = b ? 1 : 2;

                promise.resolve(result);
            }

            @Override
            public void onErrorCallback(String s) {
                MsgTools.pirntMsg("onErrorCallback:" + s);
                promise.resolve(2);
            }
        });

    }

    @ReactMethod
    public void showGDPRAuth() {
        MsgTools.pirntMsg("showGDPRAuth:");
        if (getCurrentActivity() != null) {
            getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ATSDK.showGdprAuth(ATRNSDKModule.this.mApplicationContext);
                }
            });
        }
    }

    @ReactMethod
    public void setLogDebug(boolean isDebug) {
        MsgTools.setLogDebug(isDebug);
        MsgTools.pirntMsg("setLogDebug:" + isDebug);
        ATSDK.setNetworkLogDebug(isDebug);
    }

    @ReactMethod
    public void deniedUploadDeviceInfo(ReadableArray readableArray) {

        if (readableArray != null) {
            ArrayList<Object> objects = readableArray.toArrayList();
            int size = objects.size();

            if (size > 0) {
                String[] deniedInfos = new String[size];
                String p;
                Object o;
                for (int i = 0; i < size; i++) {
                    o = objects.get(i);
                    if (o instanceof String) {
                        p = (String) o;
                        deniedInfos[i] = p;
                        MsgTools.pirntMsg("deniedUploadDeviceInfo : " + p);
                    }
                }
                ATSDK.deniedUploadDeviceInfo(deniedInfos);
            }


        }

    }

}
