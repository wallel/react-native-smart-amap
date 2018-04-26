import * as React from 'react';
import * as ReactNative from 'react-native';

export interface Coordinate {
    latitude: number;
    longitude: number;
}

export interface OnMovedEvent {
    nativeEvent: Coordinate;
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
    zoomLevel?:number
    centerMarker?:string
}

export interface AMapProps extends ReactNative.ViewProperties{
    options:AMapOptions;
    onDidMoveByUser:(e:OnMovedEvent)=>any;
}
export default class AMap extends React.Component<AMapProps>{
    render():JSX.Element;
    setOptions(options:AMapOptions):void;
    searchPoiByCenterCoordinate(params):void
    setCenterCoordinate(coordinate:Coordinate):void
    searchLocation(value):void
}