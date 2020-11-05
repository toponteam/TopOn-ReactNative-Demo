package com.anythink.reactnativejs.utils;

import android.app.Activity;

import com.anythink.reactnativejs.AnythinkReactContextBaseJavaModule;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BaseHelper {

    private static final String TAG = BaseHelper.class.getSimpleName();

    private AnythinkReactContextBaseJavaModule mAnythinkReactContextBaseJavaModule;

    private JSONObject mCallbackJsonObject;
    private String mCallbackNameJson;

    public BaseHelper(AnythinkReactContextBaseJavaModule anythinkReactContextBaseJavaModule) {
        this.mAnythinkReactContextBaseJavaModule = anythinkReactContextBaseJavaModule;
    }

    protected void sendEvent(String eventName, Object data) {
        if (mAnythinkReactContextBaseJavaModule != null) {
            mAnythinkReactContextBaseJavaModule.sendEvent(eventName, data);
        } else {
            MsgTools.pirntMsg("AnythinkReactContextBaseJavaModule = null!");
        }
    }

    protected Activity getActivity() {
        if (mAnythinkReactContextBaseJavaModule != null) {
            return mAnythinkReactContextBaseJavaModule.getActivity();
        }
        return null;
    }

    protected void runOnUiThread(Runnable runnable) {
        try {
            getActivity().runOnUiThread(runnable);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    protected Map<String, Object> getJsonMap(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            String key;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                map.put(key, jsonObject.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    protected static void fillMapFromJsonObject(Map<String, Object> localExtra, JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            Object value = jsonObject.opt(key);
            localExtra.put(key, value);
        }
    }

}
