
import * as React from 'react'
import * as ReactNative from 'react-native'
import {requireNativeComponent,NativeModules,findNodeHandle,Platform,View,Text} from 'react-native'
import * as PropTypes from "prop-types";

export const AMapManager = Platform.OS == 'ios' ? null : NativeModules.AMapModule
export const AMapLocation = Platform.OS == 'ios' ? null:NativeModules.AMapLocation

/**
 * @interface Coordinate 经纬度
 */
export interface Coordinate{
    /**
     * @field latitude 纬度
     */
    latitude:number;
    /**
     * @field longitude 经度
     */
    longitude:number;
}

/**
 * @interface OnMovedEvent 用户移动地图位置,返回新的经纬度
 */
export interface OnMovedEvent {
    /**
     * @field nativeEvent 移动后的经纬度
     */
    nativeEvent: Coordinate;
}

/**
 * 中心点标记类型
 */
export enum MarkerType{
    HUE_RED         = AMapManager? AMapManager.MARKER_HUE_RED       : undefined,
    HUE_ORANGE      = AMapManager? AMapManager.MARKER_HUE_ORANGE    : undefined,
    HUE_YELLOW      = AMapManager? AMapManager.MARKER_HUE_YELLOW    : undefined,
    HUE_GREEN       = AMapManager? AMapManager.MARKER_HUE_GREEN     : undefined,
    HUE_CYAN        = AMapManager? AMapManager.MARKER_HUE_CYAN      : undefined,
    HUE_AZURE       = AMapManager? AMapManager.MARKER_HUE_AZURE     : undefined,
    HUE_BLUE        = AMapManager? AMapManager.MARKER_HUE_BLUE      : undefined,
    HUE_VIOLET      = AMapManager? AMapManager.MARKER_HUE_VIOLET    : undefined,
    HUE_MAGENTAF    = AMapManager? AMapManager.MARKER_HUE_MAGENTAF  : undefined,
    HUE_ROSE        = AMapManager? AMapManager.MARKER_HUE_ROSE      : undefined,
}

/**
 * @interface AMapOptions 地图显示选项
 */
export interface AMapOptions{
    /**
     * @field center 中心位置
     */
    center?:Coordinate

    /**
     * @field zoomLevel 缩放等级
     * @default 18
     */
    zoomLevel?: number
    /**
     * @field enableMarker 是否显示中心点
     * @default true
     */
    enableMarker?: boolean
    /**
     * @field enableZoomControls 是否显示缩放按钮
     * @default false
     */
    enableZoomControls?:boolean
    /**
     * @field enableZoomGestures 是否支持手势缩放
     * @default true
     */
    enableZoomGestures?:boolean
    /**
     * @field enableScaleControls 显示比例尺
     * @default false
     */
    enableScaleControls?:boolean
    /**
     * @field  enableCompass 显示指南针
     * @default false
     */
    enableCompass?:boolean
    /**
     * @field enableMyLocation 显示我的位置
     * @default false
     */
    enableMyLocation?:boolean
    /**
     * @field enableEnableCenterMarker 是否显示中心点标记
     * @default true
     */
    enableEnableCenterMarker?:boolean
    /**
     * @field 中心点标记类型
     * @default AMapManager.MARKER_HUE_AZURE
     */
    marker?:number
}

/**
 * @interface AMapProps AMap属性
 */
export interface AMapProps extends AMapOptions,ReactNative.ViewProperties{
    /**
     * @event onMoved 用户移动的地图的事件
     * @param {OnMovedEvent} e
     * @returns {any}
     */
    onMoved?: (e:OnMovedEvent)=>any

}

/**
 * POI搜索选项
 */
export interface PoiSearchOptions{
    /**
     * @filed types POI 类型的组合，比如定义如下组合：餐馆|电影院|景点
     * http://a.amap.com/lbs/static/zip/AMap_poicode.zip
     * type和kerwords至少提供一个
     */
    types?:string
    /**
     * @filed keywords  查询字符串，多个关键字用“|”分割
     */
    keywords:string
    /**
     * @filed page 第几页 (从0开始)
     */
    page:number
    /**
     * @filed count 每页个数
     */
    count:number
    /**
     *@filed radius 检索范围,默认1公里
     */
    radius?:number
    /**
     * @field coordinate 检索范围中心点坐标
     */
    coordinate:Coordinate
}

/**
 * @interface ResultError 错误结果
 */
export interface ResultError{
    /**
     * @field code 错误码
     */
    code:number
    /**
     * @field desc 错误描述
     */
    desc:string
}
/**
 * POI搜索结果
 */
export interface PoiSearchResult{
    /**
     * @filed uid POIitem唯一ID
     */
    uid?:string
    /**
     * @filed name 名称
     */
    name?:string
    /**
     * @filed address 地址
     */
    address?:number
    /**
     * @filed city 城市
     */
    city?:number
    /**
     *@filed province 省份
     */
    province?:number
    /**
     *@field error 错误信息
     */
    error?:ResultError
}

/**
 * 定位模式
 */
export enum LocationMode{
    batterySaving=AMapLocation? AMapLocation.locationMode.batterySaving : undefined,
    deviceSensors=AMapLocation? AMapLocation.locationMode.deviceSensors : undefined,
    hightAccuracy=AMapLocation? AMapLocation.locationMode.hightAccuracy : undefined
}

/**
 * 定位模式
 */
export enum LocationProtocol{
    http=AMapLocation? AMapLocation.locationProtocol.http : undefined,
    https=AMapLocation? AMapLocation.locationProtocol.https : undefined,
}

/**
 * 定位选项
 */
export interface LocationOptions{
    /**
     * @filed locationMode 定位模式
     */
    locationMode?:LocationMode
    /**
     * @filed locationProtocol 定位协议
     */
    locationProtocol?:LocationProtocol
    /**
     * @filed gpsFirst 设置首次定位是否等待GPS定位结果,
     * 只有在单次定位高精度定位模式下有效,
     * 设置为true时，会等待GPS定位结果返回，最多等待30秒，若30秒后仍无GPS结果返回，返回网络定位结果
     * @default false
     */
    gpsFirst:boolean
    /**
     * @filed httpTimeout http超时时间
     * @default 30000 30秒
     */
    httpTimeout:number
    /**
     *@filed interval 连续定位的定位间隔
     *@default 2000 2秒钟
     */
    interval:number
    /**
     * @field needAddress 是否返回地址
     * @default true
     */
    needAddress:boolean
    /**
     * @field onceLocation 是否单次定位
     * @default false
     */
    onceLocation:boolean
    /**
     * @field onceLocationLatest 设置定位是否等待WIFI列表刷新 定位精度会更高，但是定位速度会变慢1-3秒
     * @default false
     */
    onceLocationLatest:boolean
    /**
     * @field locationCacheEnable 是否缓存定位结果
     * @default true
     */
    locationCacheEnable?:boolean
}

/**
 * 定位结果
 */
export interface LocationResult extends PoiSearchResult{
    /**
     * @field gps 定位结果是否是gps定位的,gps定位结果只有经纬度
     */
    gps:boolean
}


export default class AMap extends React.Component<AMapProps>{

    static propTypes:any= {
        ...ReactNative.View.propTypes,
        center: PropTypes.shape({
            latitude: PropTypes.number.isRequired,
            longitude: PropTypes.number.isRequired,
        }),
        marker:PropTypes.number,
        zoomLevel: PropTypes.number,
        enableMarker: PropTypes.bool,
        enableZoomControls:PropTypes.bool,
        enableZoomGestures:PropTypes.bool,
        enableScaleControls:PropTypes.bool,
        enableCompass:PropTypes.bool,
        enableMyLocation:PropTypes.bool,
        enableEnableCenterMarker:PropTypes.bool,
        onMoved: PropTypes.func,
    };
    constructor(props:any) {
        super(props)
    }

    render() {
        return Platform.OS == 'ios' ? (
            <NativeAMap >
                <Text>暂时不支持IOS</Text>
            </NativeAMap>
        ):(
                <NativeAMap
                    {...this.props}
                />
            )
    }
     setOptions(options:AMapOptions) {
         AMapManager.setOptions(findNodeHandle(this), options)
     }

     searchPoi(params:PoiSearchOptions) {
         AMapManager.searchPoi(params)
     }

    setCenter(coordinate:Coordinate) {
         AMapManager.setCenter(findNodeHandle(this), coordinate)
    }
}

const NativeAMap = Platform.OS == 'ios' ? View : requireNativeComponent<AMapProps>('AMapView', AMap,);
