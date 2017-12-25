package com.nativemodule.amap;

import android.util.Log;

import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by zq on 2017/12/20.
 */

public class AMapViewManager extends ViewGroupManager<AMapView> {


    @Override
    public String getName() {
        return "AMapView";
    }

    @Override
    protected AMapView createViewInstance(ThemedReactContext reactContext) {
        Log.d("AMapView","createViewInstance: " + String.valueOf(reactContext.hashCode()));
        return new AMapView(reactContext);
    }

    @ReactProp(name="center")
    public void setLatLng(AMapView view,final ReadableMap map) {
        double lat = 0, lng = 0;
        if (map.hasKey(Constants.COORDINATE_KEY_LATITUDE)){
            lat = map.getDouble(Constants.COORDINATE_KEY_LATITUDE);
        }
        if (map.hasKey(Constants.COORDINATE_KEY_LONGITUDE)){
            lng = map.getDouble(Constants.COORDINATE_KEY_LONGITUDE);
        }

        LatLng latLng = new LatLng(lat,lng);
        view.setLatLng(latLng);
    }

    @ReactProp(name="zoomLevel",defaultDouble=18.0)
    public void setZoomLevel(AMapView view,double zoomLevel) {
        view.setZoomLevel(zoomLevel);
    }

    @ReactProp(name="enableZoomControls",defaultBoolean = false)
    public void setEnableZoomControls(AMapView view,boolean enableZoomControls) {
        view.setEnableZoomControls(enableZoomControls);
    }

    @ReactProp(name="enableZoomGestures",defaultBoolean = true)
    public void setEnableZoomGestures(AMapView view,boolean enableZoomGestures) {
        view.setEnableZoomGestures(enableZoomGestures);
    }

    @ReactProp(name="enableScaleControls",defaultBoolean = false)
    public void setEnableScaleControls(AMapView view,boolean enableScaleControls) {
        view.setEnableScaleControls(enableScaleControls);
    }

    @ReactProp(name="enableCompass",defaultBoolean = false)
    public void setEnableCompass(AMapView view,boolean enableCompass) {
        view.setEnableCompass(enableCompass);
    }

    @ReactProp(name="enableMyLocation",defaultBoolean = false)
    public void setEnableMyLocation(AMapView view,boolean enableMyLocation) {
        view.setEnableMyLocation(enableMyLocation);
    }

    @ReactProp(name="enableEnableCenterMarker",defaultBoolean = true)
    public void setEnableCenterMarker(AMapView view,boolean enableCenterMarker) {
        view.setEnableCenterMarker(enableCenterMarker);
    }

    @ReactProp(name="marker",defaultFloat = BitmapDescriptorFactory.HUE_AZURE)
    public void setMarker(AMapView view,float marker) {
        view.setMarker(marker);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String,Object>builder()
                .put(Constants.EVENT_AMPA_ONMOVEEDDONE
                        ,MapBuilder.of("registrationName","onMoved"))
                .build();
    }
}
