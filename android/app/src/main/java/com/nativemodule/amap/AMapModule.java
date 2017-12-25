package com.nativemodule.amap;

import android.util.Log;

import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by zq on 2017/12/20.
 */

public class AMapModule  extends ReactContextBaseJavaModule implements PoiSearch.OnPoiSearchListener{
    private ReactApplicationContext context;

    private PoiSearch poiSearch;
    // 检索范围:默认1公里
    private int  defaultRadius = 1000;


    public AMapModule(ReactApplicationContext reactContext){
        super(reactContext);

        context = reactContext;
        this.poiSearch = new PoiSearch(context,null);
    }
    @Override
    public String getName() {
        return "AMapModule";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String,Object> constants = new HashMap<>();
        constants.put(Constants.MARKER_COLOR_HUE_RED,BitmapDescriptorFactory.HUE_RED);
        constants.put(Constants.MARKER_COLOR_HUE_ORANGE,BitmapDescriptorFactory.HUE_ORANGE);
        constants.put(Constants.MARKER_COLOR_HUE_YELLOW,BitmapDescriptorFactory.HUE_YELLOW);
        constants.put(Constants.MARKER_COLOR_HUE_GREEN,BitmapDescriptorFactory.HUE_GREEN);
        constants.put(Constants.MARKER_COLOR_HUE_CYAN,BitmapDescriptorFactory.HUE_CYAN);
        constants.put(Constants.MARKER_COLOR_HUE_AZURE,BitmapDescriptorFactory.HUE_AZURE);
        constants.put(Constants.MARKER_COLOR_HUE_BLUE,BitmapDescriptorFactory.HUE_BLUE);
        constants.put(Constants.MARKER_COLOR_HUE_VIOLET,BitmapDescriptorFactory.HUE_VIOLET);
        constants.put(Constants.MARKER_COLOR_HUE_MAGENTAF,BitmapDescriptorFactory.HUE_MAGENTA);
        constants.put(Constants.MARKER_COLOR_HUE_ROSE, BitmapDescriptorFactory.HUE_ROSE);
        return constants;
    }

    @ReactMethod
    public void setOptions(final int reactTag, final ReadableMap options){
        context.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AMapView mapView = (AMapView) context.getCurrentActivity().findViewById(reactTag);
                mapView.setOptions(options);
            }
        });
    }

    @ReactMethod
    public void setCenter(final int reactTag, final ReadableMap coordinate){
        context.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AMapView mapView = (AMapView) context.getCurrentActivity().findViewById(reactTag);
                mapView.setCenterLocation(coordinate.getDouble(Constants.COORDINATE_KEY_LATITUDE)
                        ,coordinate.getDouble(Constants.COORDINATE_KEY_LONGITUDE));
            }
        });
    }

    @ReactMethod
    public void searchPoi(ReadableMap params){
        String types = "";
        if (params.hasKey(Constants.POI_SEARCH_TYPES)){
            types = params.getString(Constants.POI_SEARCH_TYPES);
        }

        String keywords = "";
        if (params.hasKey(Constants.POI_SEARCH_KEYWORDS)){
            keywords = params.getString(Constants.POI_SEARCH_KEYWORDS);
        }

        PoiSearch.Query query = new PoiSearch.Query(keywords, types);

        if(params.hasKey(Constants.POI_SEARCH_COUNT)) {
            int offset = params.getInt(Constants.POI_SEARCH_COUNT);
            query.setPageSize(offset);
        }

        if(params.hasKey(Constants.POI_SEARCH_PAGE)) {
            int page = params.getInt(Constants.POI_SEARCH_PAGE);
            query.setPageNum(page);
        }
        poiSearch.setQuery(query);
        if(params.hasKey(Constants.POI_SEARCH_COORDINATE)) {
            ReadableMap coordinateMap = params.getMap(Constants.POI_SEARCH_COORDINATE);
            double latitude = coordinateMap.getDouble(Constants.COORDINATE_KEY_LATITUDE);
            double longitude = coordinateMap.getDouble(Constants.COORDINATE_KEY_LONGITUDE);
            int radius = defaultRadius;
            if (params.hasKey(Constants.POI_SEARCH_RADIUS)) {
                radius = params.getInt(Constants.POI_SEARCH_RADIUS);
            }
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), radius));
        }
        Log.d("AMapView",
                "searchPoi(types:" + types + " keyword:" + keywords + " count:" + query.getPageSize()
                        + " page:" + query.getPageNum() + " location:"
                        + poiSearch.getBound().getCenter().getLatitude() + ", " + poiSearch.getBound().getCenter().getLongitude());
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }



    @Override
    public void onPoiSearched(PoiResult poiResult, int code) {
        List<PoiItem> poiItems;
        WritableMap map = Arguments.createMap();
        if (code == Constants.POI_SEARCH_ECODE_SUCCESS){
            if (poiResult != null && poiResult.getQuery() != null){
                poiItems = poiResult.getPois();
                WritableArray array = Arguments.createArray();
                for (PoiItem poi: poiItems){
                    WritableMap data = Arguments.createMap();
                    data.putString(Constants.POI_ITEM_UID,poi.getPoiId());
                    data.putString(Constants.POI_ITEM_ADDRESS,poi.getSnippet());
                    data.putString(Constants.POI_ITEM_CITYNAME,poi.getCityName());
                    data.putString(Constants.POI_ITEM_NAME,poi.getTitle());
                    data.putString(Constants.POI_ITEM_PROVINCENAME,poi.getProvinceName());
                    WritableMap lanlong = Arguments.createMap();
                    lanlong.putDouble(Constants.COORDINATE_KEY_LATITUDE,poi.getLatLonPoint().getLatitude());
                    lanlong.putDouble(Constants.COORDINATE_KEY_LONGITUDE,poi.getLatLonPoint().getLongitude());
                    data.putMap(Constants.POI_SEARCH_COORDINATE,lanlong);
                    array.pushMap(data);
                }
                map.putArray(Constants.POI_SEARCH_RESULT,array);
            }
        }else{
            map.putString(Constants.POI_SEARCH_ERROR,String.valueOf(code));
        }
        Log.d("AMapView","searched: code=>" + code + " count:" + poiResult.getPois().size());
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(Constants.EVENT_AMAP_ONPOISEARCHDONE,map);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
