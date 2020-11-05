package com.anythink.reactnativejs;

import com.anythink.reactnativejs.rewardvide.RewardVideoHelper;
import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;

public class ATRewardedVideoRNSDKModule extends AnythinkReactContextBaseJavaModule {

    public static final String TAG = "ATRewardedVideoRNSDK";

    private static final HashMap<String, RewardVideoHelper> sHelperMap = new HashMap<>();

    public ATRewardedVideoRNSDKModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void loadAd(final String placementId, final String settings) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.loadRewardedVideo(placementId, settings);
        }
    }

    @ReactMethod
    public void showAd(String placementId) {
        showAdInScenario(placementId, "");
    }

    @ReactMethod
    public void showAdInScenario(String placementId, String scenario) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showVideo(scenario);
        }
    }

    @ReactMethod
    public void hasAdReady(String placementId, Promise promise) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            promise.resolve(helper.isAdReady());
        } else {
            MsgTools.pirntMsg("RewardVideoHelper = null");
            promise.resolve(false);
        }
    }

    @ReactMethod
    public void checkAdStatus(String placementId, Promise promise) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            promise.resolve(helper.checkAdStatus());
        } else {
            MsgTools.pirntMsg("RewardVideoHelper = null");
            promise.resolve("");
        }
    }


    private RewardVideoHelper getHelper(String placementId) {
        RewardVideoHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new RewardVideoHelper(ATRewardedVideoRNSDKModule.this);
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
