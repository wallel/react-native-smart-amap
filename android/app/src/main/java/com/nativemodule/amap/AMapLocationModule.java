package com.nativemodule.amap;

import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by zq on 2017/12/21.
 */
public class AMapLocationModule extends ReactContextBaseJavaModule implements AMapLocationListener{
    private ReactApplicationContext reactContext        = null;
    private AMapLocationClient locationClient           = null;
    private AMapLocationClientOption locationOption     = null;

    private static Handler mHandler                     = null;
    private static int MSG_LOCATION_FINISH              = -1;

    public AMapLocationModule(final ReactApplicationContext reactContext){
        super(reactContext);
        this.reactContext = reactContext;

    }

    @ReactMethod
    public void init(final ReadableMap options){
        if (locationClient != null){
            return;
        }
        locationOption = new AMapLocationClientOption();
        locationClient = new AMapLocationClient(reactContext.getCurrentActivity());
        if (options != null){
            setOptions(options);
        }
        locationClient.setLocationListener(this);
    }

    @ReactMethod
    public void setOptions(final ReadableMap options){
        if (options.hasKey(Constants.LOCATION_MODE)){
            locationOption.setLocationMode(
                    AMapLocationClientOption.AMapLocationMode.valueOf(options.getString(Constants.LOCATION_MODE)));
        }
        if (options.hasKey(Constants.OPTIONS_GPS_FIRST)){
            locationOption.setGpsFirst(options.getBoolean(Constants.OPTIONS_GPS_FIRST));
        }
        if (options.hasKey(Constants.OPTIONS_HTTPTIMEOUT)){
            locationOption.setHttpTimeOut(options.getInt(Constants.OPTIONS_HTTPTIMEOUT));
        }
        if(options.hasKey(Constants.OPTIONS_INTERVAL)) {
            locationOption.setInterval(options.getInt(Constants.OPTIONS_INTERVAL));
        }
        if(options.hasKey(Constants.OPTIONS_NEEDADDRESS)) {
            locationOption.setNeedAddress(options.getBoolean(Constants.OPTIONS_NEEDADDRESS));
        }
        if(options.hasKey(Constants.OPTIONS_ONCELOCATION)) {
            locationOption.setOnceLocation(options.getBoolean(Constants.OPTIONS_ONCELOCATION));
        }
        if(options.hasKey(Constants.OPTIONS_LOCATIONCACHEENABLE)) {
            locationOption.setLocationCacheEnable(options.getBoolean(Constants.OPTIONS_LOCATIONCACHEENABLE));
        }
        if(options.hasKey(Constants.OPTIONS_ONCELOCATIONLATEST)) {
            locationOption.setOnceLocationLatest(options.getBoolean(Constants.OPTIONS_ONCELOCATIONLATEST));
        }
        if(options.hasKey(Constants.OPTIONS_LOCATIONPROTOCOL)) {
            AMapLocationClientOption.setLocationProtocol(
                    AMapLocationClientOption.AMapLocationProtocol.valueOf(options.getString(Constants.OPTIONS_LOCATIONPROTOCOL)));
        }
    }

    @ReactMethod
    public void cleanUp(){
        if (null != locationClient){
            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient.unRegisterLocationListener(this);
            locationClient = null;
            locationOption = null;
        }
    }
    @ReactMethod
    public void startLocation() {
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

    @Override
    public String getName() {
        return "AMapLocation";
    }

    private WritableMap setResultMap(AMapLocation location){
        WritableMap resultMap = Arguments.createMap();
        do {
            if (location == null) {
                WritableMap errorMap = Arguments.createMap();
                errorMap.putInt(Constants.POI_SEARCH_ERROR_CODE, -1);
                errorMap.putString(Constants.POI_SEARCH_ERROR_DESC, Constants.ERROR_LOCATION_NULL);
                resultMap.putMap(Constants.POI_SEARCH_ERROR, errorMap);
                break;
            }
            if (location.getErrorCode() == 0){
                WritableMap coordinateMap = Arguments.createMap();
                coordinateMap.putDouble(Constants.COORDINATE_KEY_LONGITUDE, location.getLongitude());
                coordinateMap.putDouble(Constants.COORDINATE_KEY_LATITUDE, location.getLatitude());
                resultMap.putMap(Constants.POI_SEARCH_COORDINATE, coordinateMap);
                boolean useGps = location.getProvider().equalsIgnoreCase(android.location.LocationManager.GPS_PROVIDER);
                resultMap.putBoolean(Constants.LOCATION_USE_GPS, useGps);
                if (!useGps) {
                    resultMap.putString(Constants.POI_ITEM_PROVINCENAME, location.getProvince());
                    resultMap.putString(Constants.POI_ITEM_CITYNAME, location.getCity());
                    resultMap.putString(Constants.POI_ITEM_ADDRESS, location.getAddress());
                    resultMap.putString(Constants.POI_ITEM_NAME, location.getPoiName());
                }
                break;
            }

            WritableMap errorMap = Arguments.createMap();
            errorMap.putInt(Constants.POI_SEARCH_ERROR_CODE, location.getErrorCode());
            errorMap.putString(Constants.POI_SEARCH_ERROR_DESC, location.getErrorInfo());
            resultMap.putMap(Constants.POI_SEARCH_ERROR, errorMap);

        }while (false);

        return resultMap;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        WritableMap resultMap = setResultMap(aMapLocation);
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(Constants.EVENT_AMAP_ONLOCATIONDONE, resultMap);
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {

        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put(Constants.LOCATION_MODE, getLocationModeTypes());
                put(Constants.LOCATION_PROTOCOL, getLocationProtocolTypes());
            }
            private Map<String, Object> getLocationModeTypes() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put(Constants.LOCATION_MODE_BATTERYSAVING
                                , String.valueOf(AMapLocationClientOption.AMapLocationMode.Battery_Saving));
                        put(Constants.LOCATION_MODE_DEVICESENSORS
                                , String.valueOf(AMapLocationClientOption.AMapLocationMode.Device_Sensors));
                        put(Constants.LOCATION_MODE_HIGHTACCURACY
                                , String.valueOf(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy));
                    }
                });
            }
            private Map<String, Object> getLocationProtocolTypes() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put(Constants.LOCATION_PROTOCOL_HTTP
                                , String.valueOf(AMapLocationClientOption.AMapLocationProtocol.HTTP));
                        put(Constants.LOCATION_PROTOCOL_HTTPS
                                , String.valueOf(AMapLocationClientOption.AMapLocationProtocol.HTTPS));
                    }
                });
            }
        });
    }
}
