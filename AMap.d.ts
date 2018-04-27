import * as React from 'react';
import * as ReactNative from 'react-native';

export interface Coordinate {
    latitude: number;
    longitude: number;
}

export interface OnMovedEvent {
    nativeEvent: {data:{centerCoordinate:Coordinate}};
}

export interface Frame {
    width:number;
    height:number;
}
export interface AMapOptions{
    frame?:Frame
    mapType?:boolean
    showTraffic?:boolean
    showsUserLocation?:boolean
    userTrackingMode?:number
    centerCoordinate?:Coordinate
    zoomLevel:number
    centerMarker:string
}
export interface PoiItem{
    uid:string
    name:string
    address:string
    adCode:string
    latitude:number
    longitude:number
    district?:string
    adName?:string
    cityCode?:string
    cityName?:string
    distance?:number
    provinceCode?:string
    provinceName?:string
    tel?:string
    type?:string

}
export interface PoiSearchResult {
    searchResultList?:PoiItem[]
    error?:{code:string}
}
export interface AMapProps extends ReactNative.ViewProperties{
    options:AMapOptions;
    onDidMoveByUser:(e:OnMovedEvent)=>any;
}
export default class AMap extends React.Component<AMapProps>{
    render():JSX.Element;
    setOptions(options:AMapOptions):void;
    searchPoiByCenterCoordinate(params:any):void
    setCenterCoordinate(coordinate:Coordinate):void
    searchLocation(value:any):void
}