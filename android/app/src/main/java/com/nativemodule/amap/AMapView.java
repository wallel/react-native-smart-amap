package com.nativemodule.amap;

import android.animation.ObjectAnimator;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by zq on 2017/12/20.
 */

public class AMapView extends FrameLayout implements AMap.OnCameraChangeListener{

    private AMap aMap;
    private ThemedReactContext context;
    private ImageView centerView;
    private ViewGroup.LayoutParams layoutParam;
    private MapView   mapView;
    private UiSettings mapUiSettings;

    private LatLng latLng;
    private double zoomLevel        = 18;
    private float marker = BitmapDescriptorFactory.HUE_AZURE;

    private boolean enableZoomControls      = false;
    private boolean enableZoomGestures      = true;
    private boolean enableScaleControls     = false;
    private boolean enableCompass           = false;
    private boolean enableMyLocation        = false;
    private boolean enableCenterMarker      = true;

    public AMapView(ThemedReactContext context){
        super(context);
        Log.d("AMapView",String.valueOf(context.hashCode()) + "  " + context.getCurrentActivity().getPackageName());
        this.context = context;
        this.centerView = new ImageView(context);
        this.layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //居中显示中心点标记
        if (enableCenterMarker){
            MarginLayoutParams params = (MarginLayoutParams)centerView.getLayoutParams();
            int height = getHeight();
            int width = getWidth();
            int viewHeight = centerView.getMeasuredHeight();
            int viewWidth = centerView.getMeasuredWidth();
            params.setMargins(width / 2 - viewWidth / 2, height / 2 - viewHeight / 2,0,0);
            centerView.setLayoutParams(params);
        }
        super.onLayout(changed,left,top,right,bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        if (context.getCurrentActivity().getIntent() != null && context.getCurrentActivity().getIntent().getExtras() != null){
            mapView.onSaveInstanceState(context.getCurrentActivity().getIntent().getExtras());
        }
        return super.onSaveInstanceState();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.removeView(mapView);
        this.removeView(centerView);
        mapView.onDestroy();
        super.onDetachedFromWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus){
            mapView.onResume();
        }else{
            mapView.onPause();
        }
    }
    /**
     * 初始化控件
     */
    private void init(){
        mapView = new MapView(context);
        mapView.setLayoutParams(layoutParam);
        this.addView(mapView);
        mapView.onCreate(context.getCurrentActivity().getIntent().getExtras());
        if (enableCenterMarker){
            centerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            centerView.setImageBitmap(BitmapDescriptorFactory.defaultMarker(marker).getBitmap());
            this.addView(centerView,1);
        }
        setMapOptions();
    }
    private void setMapOptions(){
        aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);

        mapUiSettings = aMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(enableZoomControls); //缩放按钮
        mapUiSettings.setCompassEnabled(enableCompass); //指南针
        mapUiSettings.setZoomGesturesEnabled(enableZoomGestures); //缩放手势
        mapUiSettings.setScaleControlsEnabled(enableScaleControls);//比例尺
        mapUiSettings.setMyLocationButtonEnabled(enableMyLocation);// 定位按钮

        mapUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mapUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);

        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,(float)zoomLevel,30,0)));

        aMap.setOnCameraChangeListener(this);
        aMap.setMyLocationEnabled(enableMyLocation);
        Log.d("AMapView","setMapOptions=>enableZoomControls:" + enableZoomControls + " zoomLevel:" + zoomLevel);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.d("AMapView","onCameraChangeFinish =>lat:");

        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(centerView,"translationY",0.0f,centerView.getMeasuredHeight()/ 2,0.0f);
        transYAnim.setDuration(600);
        transYAnim.start();

        LatLng latlng = cameraPosition.target;//屏幕中心点

        WritableMap centerCoordinateMap = Arguments.createMap();
        centerCoordinateMap.putDouble(Constants.COORDINATE_KEY_LATITUDE, latlng.latitude);
        centerCoordinateMap.putDouble(Constants.COORDINATE_KEY_LONGITUDE, latlng.longitude);
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId()
                ,Constants.EVENT_AMPA_ONMOVEEDDONE,centerCoordinateMap);
    }

    public  void setOptions(ReadableMap map)
    {
        if(map.hasKey(Constants.OPTIONS_KEY_COORDINATE)){
            ReadableMap coordinateMap = map.getMap(Constants.OPTIONS_KEY_COORDINATE);
            setLatLng(new LatLng(coordinateMap.getDouble(Constants.COORDINATE_KEY_LATITUDE)
                    ,coordinateMap.getDouble(Constants.COORDINATE_KEY_LONGITUDE)));
        }
        if (map.hasKey(Constants.OPTIONS_KEY_ZOOMLEVEL)){
            setZoomLevel(map.getDouble(Constants.OPTIONS_KEY_ZOOMLEVEL));
        }
    }

    public void setCenterLocation(double latitude, double longitude) {
        LatLng latlng = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, (float)zoomLevel));
    }
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }
    public void setEnableZoomControls(boolean enableZoomControls) {
        this.enableZoomControls = enableZoomControls;
    }
    public void setEnableZoomGestures(boolean enableZoomGestures) {
        this.enableZoomGestures = enableZoomGestures;
    }
    public void setEnableScaleControls(boolean enableScaleControls) {
        this.enableScaleControls = enableScaleControls;
    }
    public void setEnableCompass(boolean enableCompass) {
        this.enableCompass = enableCompass;
    }
    public void setEnableMyLocation(boolean enableMyLocation) {
        this.enableMyLocation = enableMyLocation;
    }
    public void setEnableCenterMarker(boolean enableCenterMarker) {
        this.enableCenterMarker = enableCenterMarker;
    }
    public void setMarker(float marker) {
        this.marker = marker;
    }
}
