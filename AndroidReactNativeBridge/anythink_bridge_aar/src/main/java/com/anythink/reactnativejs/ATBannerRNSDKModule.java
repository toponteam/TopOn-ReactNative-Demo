package com.anythink.reactnativejs;

import com.anythink.reactnativejs.banner.BannerHelper;
import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;

public class ATBannerRNSDKModule extends AnythinkReactContextBaseJavaModule {

    public final String TAG = "ATBannerRNSDK";

    private final HashMap<String, BannerHelper> sHelperMap = new HashMap<>();

    public ATBannerRNSDKModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void loadAd(final String placementId, final String settings) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.loadBanner(placementId, settings);
        }
    }


    @ReactMethod
    public void showAdInRectangle(String placementId, String rectJson) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showBannerWithRect(rectJson);
        }
    }

    @ReactMethod
    public void showAdInPosition(String placementId, String position) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showBannerWithPosition(position);
        }
    }

    @ReactMethod
    public void hideAd(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.hideBanner();
        }
    }

    @ReactMethod
    public void reShowAd(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.reshowBanner();
        }
    }

    @ReactMethod
    public void removeAd(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.removeBanner();
        }
    }

    @ReactMethod
    public void hasAdReady(String placementId, Promise promise) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            promise.resolve(helper.isAdReady());
        } else {
            MsgTools.pirntMsg("BannerHelper = null");
            promise.resolve(false);
        }
    }


    private BannerHelper getHelper(String placementId) {
        BannerHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new BannerHelper(ATBannerRNSDKModule.this);
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
