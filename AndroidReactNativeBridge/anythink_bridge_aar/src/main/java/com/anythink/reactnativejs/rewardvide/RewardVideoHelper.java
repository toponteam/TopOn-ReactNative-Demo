package com.anythink.reactnativejs.rewardvide;

import android.text.TextUtils;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.reactnativejs.ATRewardedVideoRNSDKModule;
import com.anythink.reactnativejs.AnythinkReactContextBaseJavaModule;
import com.anythink.reactnativejs.utils.BaseHelper;
import com.anythink.reactnativejs.utils.Const;
import com.anythink.reactnativejs.utils.MsgTools;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONObject;

public class RewardVideoHelper extends BaseHelper {

    private static final String TAG = RewardVideoHelper.class.getSimpleName();

    ATRewardVideoAd mRewardVideoAd;
    String mPlacementId;

    ATRewardedVideoRNSDKModule mATRewardedVideoRNSDKModule;
    boolean isReady = false;

    public RewardVideoHelper(AnythinkReactContextBaseJavaModule anythinkReactContextBaseJavaModule) {
        super(anythinkReactContextBaseJavaModule);
        MsgTools.pirntMsg(TAG + " >>> " + this);
    }

    private void initVideo(final String placementId) {
        mPlacementId = placementId;
        MsgTools.pirntMsg("initVideo placementId >>> " + mPlacementId);

        mRewardVideoAd = new ATRewardVideoAd(getActivity(), placementId);

        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                MsgTools.pirntMsg("onRewardedVideoAdLoaded .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);

                sendEvent(Const.RewardVideoCallback.LoadedCallbackKey, writableMap);
            }

            @Override
            public void onRewardedVideoAdFailed(final AdError pAdError) {
                MsgTools.pirntMsg("onRewardedVideoAdFailed >> " + mPlacementId + ", " + pAdError.printStackTrace());

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.ErrorMsg, pAdError.getFullErrorInfo());

                sendEvent(Const.RewardVideoCallback.LoadFailCallbackKey, writableMap);
            }

            @Override
            public void onRewardedVideoAdPlayStart(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayStart .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.RewardVideoCallback.PlayStartCallbackKey, writableMap);
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayEnd .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.RewardVideoCallback.PlayEndCallbackKey, writableMap);
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final AdError pAdError, final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayFailed .." + mPlacementId + ", " + pAdError.printStackTrace());

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.ErrorMsg, pAdError.getFullErrorInfo());
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.RewardVideoCallback.PlayFailCallbackKey, writableMap);
            }


            @Override
            public void onRewardedVideoAdClosed(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdClosed .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.RewardVideoCallback.CloseCallbackKey, writableMap);
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayClicked .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.RewardVideoCallback.ClickCallbackKey, writableMap);
            }

            @Override
            public void onReward(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onReward .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.RewardVideoCallback.RewardCallbackKey, writableMap);
            }
        });
    }


    public void loadRewardedVideo(final String placementId, final String settings) {
        MsgTools.pirntMsg("loadRewardedVideo >>> " + placementId + ", settings >>> " + settings);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mRewardVideoAd == null) {
                    initVideo(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {
                    String userId = "";
                    String userData = "";
                    try {
                        JSONObject jsonObject = new JSONObject(settings);

                        if (jsonObject.has(Const.USER_ID)) {
                            userId = jsonObject.optString(Const.USER_ID);
                        }
                        if (jsonObject.has(Const.USER_DATA)) {
                            userData = jsonObject.optString(Const.USER_DATA);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mRewardVideoAd.setUserData(userId, userData);
                }

                mRewardVideoAd.load();
            }
        });
    }

    public void showVideo(final String scenario) {
        MsgTools.pirntMsg("showVideo >>> " + mPlacementId + ", scenario >>> " + scenario);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRewardVideoAd != null) {
                    isReady = false;

                    mRewardVideoAd.show(getActivity(), scenario);
                } else {
                    MsgTools.pirntMsg("showVideo error  ..you must call loadRewardVideo first " + mPlacementId);

                    WritableMap writableMap = Arguments.createMap();
                    writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                    writableMap.putString(Const.CallbackKey.ErrorMsg, "you must call loadRewardVideo first");

                    sendEvent(Const.RewardVideoCallback.LoadFailCallbackKey, writableMap);
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("video isAdReady >>> " + mPlacementId);

        try {
            if (mRewardVideoAd != null) {
                boolean isAdReady = mRewardVideoAd.isAdReady();
                MsgTools.pirntMsg("video isAdReady >>> " + mPlacementId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("video isAdReady error  ..you must call loadRewardedVideo first " + mPlacementId);
            }
            MsgTools.pirntMsg("video isAdReady >end>> " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("video isAdReady >Throwable>> " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.pirntMsg("video checkAdStatus >>> " + mPlacementId);

        try {
            if (mRewardVideoAd != null) {
                ATAdStatusInfo atAdStatusInfo = mRewardVideoAd.checkAdStatus();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("isLoading", atAdStatusInfo.isLoading());
                jsonObject.put("isReady", atAdStatusInfo.isReady());
                jsonObject.put("adInfo", atAdStatusInfo.getATTopAdInfo());

                String result = jsonObject.toString();
                MsgTools.pirntMsg("video checkAdStatus >>> " + mPlacementId + ", " + result);
                return result;
            } else {
                MsgTools.pirntMsg("video checkAdStatus error  ..you must call loadRewardedVideo first " + mPlacementId);
            }
            MsgTools.pirntMsg("video checkAdStatus >end>> " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("video checkAdStatus >Throwable>> " + e.getMessage());
            return "";
        }
        return "";
    }
}
