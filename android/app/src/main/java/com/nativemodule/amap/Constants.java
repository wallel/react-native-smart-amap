package com.nativemodule.amap;

/**
 * Created by zq on 2017/12/20.
 */

public class Constants {
    private Constants(){}

    //  中心点坐标
    public static final String OPTIONS_KEY_COORDINATE      = "centerCoordinate";
    //  缩放等级
    public static final String OPTIONS_KEY_ZOOMLEVEL       = "zoomLevel";
    //  中心点标记图标
    public static final String OPTIONS_KEY_CENTERMARKER    = "centerMarker";
    //  纬度
    public static final String COORDINATE_KEY_LATITUDE      = "latitude";
    //  经度
    public static final String COORDINATE_KEY_LONGITUDE     = "longitude";


    // 事件:移动地图
    public static final String EVENT_AMPA_ONMOVEEDDONE      = "amap.onMovedDone";
    // 事件:POI搜索结果
    public static final String EVENT_AMAP_ONPOISEARCHDONE   = "amap.onPOISearchDone";
    // 事件:定位结果
    public static final String EVENT_AMAP_ONLOCATIONDONE     = "amap.onLocationDone";

    // POI搜索
    // 搜索类型
    public static final String POI_SEARCH_TYPES             ="types";
    // 搜索关键字
    public static final String POI_SEARCH_KEYWORDS          ="keywords";
    // 第几页
    public static final String POI_SEARCH_PAGE              ="page";
    // 个数
    public static final String POI_SEARCH_COUNT             ="count";
    // 半径
    public static final String POI_SEARCH_RADIUS            ="radius";
    // 位置
    public static final String POI_SEARCH_COORDINATE        ="coordinate";
    // 检索成功返回码
    public static final int POI_SEARCH_ECODE_SUCCESS        = 1000;
    // 结果
    public static final String POI_SEARCH_RESULT            = "result";
    // 错误
    public static final String POI_SEARCH_ERROR             = "error";
    public static final String POI_SEARCH_ERROR_CODE        = "code";
    public static final String POI_SEARCH_ERROR_DESC        = "desc";

    //POI item
    public static final String POI_ITEM_UID                 = "uid";
    public static final String POI_ITEM_NAME                = "name";
    public static final String POI_ITEM_ADDRESS             = "address";
    public static final String POI_ITEM_CITYNAME            = "city";
    public static final String POI_ITEM_PROVINCENAME        = "province";
    public static final String LOCATION_USE_GPS             = "gps";
    //定位功能
    public static final String LOCATION_MODE                    = "locationMode";
    public static final String LOCATION_MODE_BATTERYSAVING      = "batterySaving";
    public static final String LOCATION_MODE_DEVICESENSORS      = "deviceSensors";
    public static final String LOCATION_MODE_HIGHTACCURACY      = "hightAccuracy";

    public static final String LOCATION_PROTOCOL                    = "locationProtocol";
    public static final String LOCATION_PROTOCOL_HTTP               = "http";
    public static final String LOCATION_PROTOCOL_HTTPS              = "https";

    public static final String OPTIONS_GPS_FIRST                    = "gpsFirst";
    public static final String OPTIONS_HTTPTIMEOUT                  = "httpTimeout";
    public static final String OPTIONS_INTERVAL                     = "interval";
    public static final String OPTIONS_NEEDADDRESS                  = "needAddress";
    public static final String OPTIONS_ONCELOCATION                 = "onceLocation";
    public static final String OPTIONS_LOCATIONCACHEENABLE          = "locationCacheEnable";
    public static final String OPTIONS_LOCATIONPROTOCOL             = "locationProtocol";
    public static final String OPTIONS_ONCELOCATIONLATEST           = "onceLocationLatest";

    public static final String ERROR_LOCATION_NULL                  = "定位结果不存在";
    public static final String ERROR_POI_ERROR                      = "搜索周围地址失败";

    // marker颜色
    public static final String MARKER_COLOR_HUE_RED             ="MARKER_HUE_RED";
    public static final String MARKER_COLOR_HUE_ORANGE          ="MARKER_HUE_ORANGE";
    public static final String MARKER_COLOR_HUE_YELLOW          ="MARKER_HUE_YELLOW";
    public static final String MARKER_COLOR_HUE_GREEN           ="MARKER_HUE_GREEN";
    public static final String MARKER_COLOR_HUE_CYAN            ="MARKER_HUE_CYAN";
    public static final String MARKER_COLOR_HUE_AZURE           ="MARKER_HUE_AZURE";
    public static final String MARKER_COLOR_HUE_BLUE            ="MARKER_HUE_BLUE";
    public static final String MARKER_COLOR_HUE_VIOLET          ="MARKER_HUE_VIOLET";
    public static final String MARKER_COLOR_HUE_MAGENTAF        ="MARKER_HUE_MAGENTAF";
    public static final String MARKER_COLOR_HUE_ROSE            ="MARKER_HUE_ROSE";
}
