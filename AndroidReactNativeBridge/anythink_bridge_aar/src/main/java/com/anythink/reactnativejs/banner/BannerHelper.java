package com.anythink.reactnativejs.banner;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.anythink.banner.api.ATBannerListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.AdError;
import com.anythink.reactnativejs.AnythinkReactContextBaseJavaModule;
import com.anythink.reactnativejs.utils.BaseHelper;
import com.anythink.reactnativejs.utils.Const;
import com.anythink.reactnativejs.utils.MsgTools;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BannerHelper extends BaseHelper {

    private final String TAG = getClass().getSimpleName();
    String mPlacementId;
    boolean isReady;

    ATBannerView mBannerView;

    public BannerHelper(AnythinkReactContextBaseJavaModule anythinkReactContextBaseJavaModule) {
        super(anythinkReactContextBaseJavaModule);
        MsgTools.pirntMsg(TAG + " >>> " + this);
        mPlacementId = "";
    }

    public void initBanner(String placementId) {
        mPlacementId = placementId;
        MsgTools.pirntMsg("initBanner >>> " + placementId);

        mBannerView = new ATBannerView(getActivity());
        mBannerView.setUnitId(mPlacementId);
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                isReady = true;
                MsgTools.pirntMsg("onBannerLoaded .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);

                sendEvent(Const.BannerCallback.LoadedCallbackKey, writableMap);
            }

            @Override
            public void onBannerFailed(final AdError adError) {
                isReady = false;
                MsgTools.pirntMsg("onBannerFailed >> " + mPlacementId + ", " + adError.getFullErrorInfo());

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.ErrorMsg, adError.getFullErrorInfo());

                sendEvent(Const.BannerCallback.LoadFailCallbackKey, writableMap);
            }

            @Override
            public void onBannerClicked(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerClicked .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.BannerCallback.ClickCallbackKey, writableMap);
            }

            @Override
            public void onBannerShow(final ATAdInfo adInfo) {
                isReady = false;
                MsgTools.pirntMsg("onBannerShow .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.BannerCallback.ShowCallbackKey, writableMap);
            }

            @Override
            public void onBannerClose(final ATAdInfo adInfo) {
                isReady = false;
                MsgTools.pirntMsg("onBannerClose .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.BannerCallback.CloseCallbackKey, writableMap);
            }

            @Override
            public void onBannerAutoRefreshed(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerAutoRefreshed .." + mPlacementId);

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.AdInfo, adInfo.toString());

                sendEvent(Const.BannerCallback.RefreshCallbackKey, writableMap);
            }

            @Override
            public void onBannerAutoRefreshFail(final AdError adError) {
                isReady = false;
                MsgTools.pirntMsg("onBannerAutoRefreshFail .." + mPlacementId + ", " + adError.getFullErrorInfo());

                WritableMap writableMap = Arguments.createMap();
                writableMap.putString(Const.CallbackKey.PlacementId, mPlacementId);
                writableMap.putString(Const.CallbackKey.ErrorMsg, adError.getFullErrorInfo());

                sendEvent(Const.BannerCallback.RefreshFailCallbackKey, writableMap);
            }
        });
    }

    public void loadBanner(final String placementId, final String settings) {
        MsgTools.pirntMsg("loadBanner >>> " + placementId + ", settings - " + settings);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mBannerView == null) {
                    initBanner(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {//针对 穿山甲第一次banner尺寸不对
                    try {
                        JSONObject jsonObject = new JSONObject(settings);
                        int width = 0;
                        int height = 0;

                        if (jsonObject.has(Const.WIDTH)) {
                            width = jsonObject.optInt(Const.WIDTH);
                        }
                        if (jsonObject.has(Const.HEIGHT)) {
                            height = jsonObject.optInt(Const.HEIGHT);
                        }

                        if (mBannerView != null) {
                            MsgTools.pirntMsg("loadBanner, width: " + width + ", height: " + height);
                            if (mBannerView.getLayoutParams() == null) {
                                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
                                mBannerView.setLayoutParams(lp);
                            } else {
                                mBannerView.getLayoutParams().width = width;
                                mBannerView.getLayoutParams().height = height;
                            }
                        }

                        int adaptiveOrientation = 0;
                        int adaptiveWidth = 0;
                        if (jsonObject.has(Const.Banner.AdaptiveOrientation)) {
                            adaptiveOrientation = jsonObject.optInt(Const.Banner.AdaptiveOrientation);
                        }
                        if (jsonObject.has(Const.Banner.AdaptiveWidth)) {
                            adaptiveWidth = jsonObject.optInt(Const.Banner.AdaptiveWidth);
                        }
                        if (!jsonObject.has(Const.Banner.AdaptiveType)) {
                            jsonObject.put(Const.Banner.AdaptiveType, 0);
                        }

                        jsonObject.put(Const.Banner.InlineAdaptiveOrientation, adaptiveOrientation);
                        jsonObject.put(Const.Banner.InlineAdaptiveWidth, adaptiveWidth);

                        Map<String, Object> localExtra = new HashMap<>();
                        fillMapFromJsonObject(localExtra, jsonObject);

                        if (ATSDK.isNetworkLogDebug()) {
                            MsgTools.pirntMsg("Banner localExtra: " + jsonObject.toString());
                        }

                        mBannerView.setLocalExtra(localExtra);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

                mBannerView.loadAd();
            }
        });
    }

    public void showBannerWithRect(String rectJson) {
        MsgTools.pirntMsg("showBannerWithRect >>> " + mPlacementId + ", rect >>>" + rectJson);

        if (!TextUtils.isEmpty(rectJson)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(rectJson);

                int x = 0, y = 0, width = 0, height = 0;
                if (jsonObject.has(Const.X)) {
                    x = jsonObject.optInt(Const.X);
                }
                if (jsonObject.has(Const.Y)) {
                    y = jsonObject.optInt(Const.Y);
                }
                if (jsonObject.has(Const.WIDTH)) {
                    width = jsonObject.optInt(Const.WIDTH);
                }
                if (jsonObject.has(Const.HEIGHT)) {
                    height = jsonObject.optInt(Const.HEIGHT);
                }

                final int finalWidth = width;
                final int finalHeight = height;
                final int finalX = x;
                final int finalY = y;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mBannerView != null) {
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(finalWidth, finalHeight);
                            layoutParams.leftMargin = finalX;
                            layoutParams.topMargin = finalY;
                            if (mBannerView.getParent() != null) {
                                ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                            }
                            getActivity().addContentView(mBannerView, layoutParams);
                        } else {
                            MsgTools.pirntMsg("showBannerWithRect error  ..you must call loadBanner first, placementId >>>  " + mPlacementId);
                        }
                    }
                });

            } catch (Exception e) {
                MsgTools.pirntMsg("showBannerWithRect error  .. " + e.getMessage());
            }
        } else {
            MsgTools.pirntMsg("showBannerWithRect error without rect, placementId >>> " + mPlacementId);
        }

    }


    public void showBannerWithPosition(final String position) {
        MsgTools.pirntMsg("showBannerWithPostion >>> " + mPlacementId + ", position >>> " + position);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    int width = 0;
                    int height = 0;
                    if (mBannerView.getLayoutParams() != null) {
                        width = mBannerView.getLayoutParams().width;
                        height = mBannerView.getLayoutParams().height;
                    }
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
                    if (Const.BANNER_POSITION_TOP.equals(position)) {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                    } else {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                    }
                    if (mBannerView.getParent() != null) {
                        ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                    }
                    getActivity().addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.pirntMsg("showBannerWithPostion error  ..you must call loadBanner first, placementId >>> " + mPlacementId);
                }

            }
        });
    }

    public void reshowBanner() {
        MsgTools.pirntMsg("reshowBanner >>> " + mPlacementId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.VISIBLE);
                } else {
                    MsgTools.pirntMsg("reshowBanner error  ..you must call loadBanner first, placementId >>> " + mPlacementId);
                }
            }
        });
    }

    public void hideBanner() {
        MsgTools.pirntMsg("hideBanner >>> " + mPlacementId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.GONE);
                } else {
                    MsgTools.pirntMsg("hideBanner error  ..you must call loadBanner first, placementId >>> " + mPlacementId);
                }

            }
        });
    }

    public void removeBanner() {
        MsgTools.pirntMsg("removeBanner >>> " + mPlacementId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null && mBannerView.getParent() != null) {
                    MsgTools.pirntMsg("removeBanner2 placementId >>> " + mPlacementId);
                    ViewParent viewParent = mBannerView.getParent();
                    ((ViewGroup) viewParent).removeView(mBannerView);
                } else {
                    MsgTools.pirntMsg("removeBanner3 >>> no banner need to be removed, placementId >>> " + mPlacementId);
                }
            }
        });
    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("banner isAdReady >>> " + mPlacementId + "：" + isReady);
        return isReady;
    }

}
