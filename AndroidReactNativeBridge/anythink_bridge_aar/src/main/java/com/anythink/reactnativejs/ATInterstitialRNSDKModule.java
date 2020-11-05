package com.anythink.reactnativejs;

import com.anythink.reactnativejs.interstitial.InterstitialHelper;
import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;

public class ATInterstitialRNSDKModule extends AnythinkReactContextBaseJavaModule {

    public static final String TAG = "ATInterstitialRNSDK";

    private static final HashMap<String, InterstitialHelper> sHelperMap = new HashMap<>();

    public ATInterstitialRNSDKModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void loadAd(final String placementId, final String settings) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.loadInterstitial(placementId, settings);
        }
    }

    @ReactMethod
    public void showAd(String placementId) {
        showAdInScenario(placementId, "");
    }

    @ReactMethod
    public void showAdInScenario(String placementId, String scenario) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showInterstitial(scenario);
        }
    }

    @ReactMethod
    public void hasAdReady(String placementId, Promise promise) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            promise.resolve(helper.isAdReady());
        } else {
            MsgTools.pirntMsg("RewardVideoHelper = null");
            promise.resolve(false);
        }
    }

    @ReactMethod
    public void checkAdStatus(String placementId, Promise promise) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            promise.resolve(helper.checkAdStatus());
        } else {
            MsgTools.pirntMsg("InterstitialHelper = null");
            promise.resolve("");
        }
    }

    private InterstitialHelper getHelper(String placementId) {
        InterstitialHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new InterstitialHelper(ATInterstitialRNSDKModule.this);
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
