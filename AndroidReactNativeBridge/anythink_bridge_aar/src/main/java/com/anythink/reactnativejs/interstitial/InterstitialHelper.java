package com.anythink.reactnativejs.interstitial;

import android.text.TextUtils;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.anythink.reactnativejs.AnythinkReactContextBaseJavaModule;
import com.anythink.reactnativejs.utils.BaseHelper;
import com.anythink.reactnativejs.utils.Const;
import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InterstitialHelper extends BaseHelper {

    private static final String TAG = InterstitialHelper.class.getSimpleName();

    ATInterstitial mInterstitialAd;
    String mPlacementId;

    boolean isReady = false;

    public InterstitialHelper(AnythinkReactContextBaseJavaModule anythinkReactContextBaseJavaModule) {
        super(anythinkReactContextBaseJavaModule);
        MsgTools.pirntMsg(TAG + " >>> " + this);
    }

    private void initInterstitial(final String placementId) {
        mPlacementId = placementId;
        MsgTools.pirntMsg("initInterstitial  >>> " + mPlacementId);

        mInterstitialAd = new ATInterstitial(getActivity(), placementId);

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.pirntMsg("onInterstitialAdLoaded .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);

                sendEvent(Const.InterstitialCallback.LoadedCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdLoadFail(final AdError adError) {
                MsgTools.pirntMsg("onInterstitialAdLoadFail >> " + mPlacementId + ", " + adError.printStackTrace());

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.ErrorMsg, adError.getFullErrorInfo());

                sendEvent(Const.InterstitialCallback.LoadFailCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdClicked(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdClicked .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, atAdInfo.toString());

                sendEvent(Const.InterstitialCallback.ClickCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdShow(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdShow .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, atAdInfo.toString());

                sendEvent(Const.InterstitialCallback.ShowCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdClose(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdClose .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, atAdInfo.toString());

                sendEvent(Const.InterstitialCallback.CloseCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdVideoStart(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoStart .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, atAdInfo.toString());

                sendEvent(Const.InterstitialCallback.PlayStartCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdVideoEnd(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoEnd .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, atAdInfo.toString());

                sendEvent(Const.InterstitialCallback.PlayEndCallbackKey, writableMap);
            }

            @Override
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.pirntMsg("onInterstitialAdVideoError .." + mPlacementId + ", " + adError.printStackTrace());

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.ErrorMsg, adError.getFullErrorInfo());

                sendEvent(Const.InterstitialCallback.PlayFailCallbackKey, writableMap);
            }
        });
    }

    public void loadInterstitial(final String placementId, final String settings) {
        MsgTools.pirntMsg("loadInterstitial >>> " + placementId + ", settings >>> " + settings);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd == null) {
                    initInterstitial(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {
                    try {
                        JSONObject jsonObject = new JSONObject(settings);
                        Map<String, Object> localExtra = new HashMap<>();
                        if (jsonObject.has(Const.Interstital.UseRewardedVideoAsInterstitial)) {
                            if ((boolean) jsonObject.get(Const.Interstital.UseRewardedVideoAsInterstitial)) {
                                localExtra.put("is_use_rewarded_video_as_interstitial", true);
                            }
                        }
                        fillMapFromJsonObject(localExtra, jsonObject);
                        mInterstitialAd.setLocalExtra(localExtra);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

                mInterstitialAd.load();
            }
        });
    }

    public void showInterstitial(final String scenario) {
        MsgTools.pirntMsg("showInterstitial >>> " + mPlacementId + ", scenario >>> " + scenario);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    isReady = false;
                    mInterstitialAd.show(getActivity(), scenario);
                } else {
                    MsgTools.pirntMsg("showInterstitial error  ..you must call loadRewardVideo first, placementId" + mPlacementId);

                    WritableMap writableMap = Arguments.createMap();
                    writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                    writableMap.putString(Const.CallbackKey.ErrorMsg, "you must call loadRewardVideo first");

                    sendEvent(Const.InterstitialCallback.LoadFailCallbackKey, writableMap);
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("interstitial isAdReady >>> " + mPlacementId);

        try {
            if (mInterstitialAd != null) {
                boolean isAdReady = mInterstitialAd.isAdReady();
                MsgTools.pirntMsg("interstitial isAdReady >>> " + mPlacementId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("interstitial isAdReady error  ..you must call loadInterstitial first " + mPlacementId);
            }
            MsgTools.pirntMsg("interstitial isAdReady >end>> " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("interstitial isAdReady >Throwable>> " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.pirntMsg("interstitial checkAdStatus >>> " + mPlacementId);

        try {
            if (mInterstitialAd != null) {
                ATAdStatusInfo atAdStatusInfo = mInterstitialAd.checkAdStatus();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("isLoading", atAdStatusInfo.isLoading());
                jsonObject.put("isReady", atAdStatusInfo.isReady());
                jsonObject.put("adInfo", atAdStatusInfo.getATTopAdInfo());

                String result = jsonObject.toString();
                MsgTools.pirntMsg("interstitial checkAdStatus >>> " + mPlacementId + ", " + result);
                return result;
            } else {
                MsgTools.pirntMsg("interstitial checkAdStatus error  ..you must call loadInterstitial first " + mPlacementId);
            }
            MsgTools.pirntMsg("interstitial checkAdStatus >end>> " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("interstitial checkAdStatus >Throwable>> " + e.getMessage());
            return "";
        }
        return "";
    }
}
