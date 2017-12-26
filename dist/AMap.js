"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const React = require("react");
const ReactNative = require("react-native");
const react_native_1 = require("react-native");
const PropTypes = require("prop-types");
exports.AMapManager = react_native_1.Platform.OS == 'ios' ? null : react_native_1.NativeModules.AMapModule;
exports.AMapLocation = react_native_1.Platform.OS == 'ios' ? null : react_native_1.NativeModules.AMapLocation;
/**
 * 中心点标记类型
 */
var MarkerType;
(function (MarkerType) {
    MarkerType[MarkerType["HUE_RED"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_RED : undefined] = "HUE_RED";
    MarkerType[MarkerType["HUE_ORANGE"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_ORANGE : undefined] = "HUE_ORANGE";
    MarkerType[MarkerType["HUE_YELLOW"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_YELLOW : undefined] = "HUE_YELLOW";
    MarkerType[MarkerType["HUE_GREEN"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_GREEN : undefined] = "HUE_GREEN";
    MarkerType[MarkerType["HUE_CYAN"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_CYAN : undefined] = "HUE_CYAN";
    MarkerType[MarkerType["HUE_AZURE"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_AZURE : undefined] = "HUE_AZURE";
    MarkerType[MarkerType["HUE_BLUE"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_BLUE : undefined] = "HUE_BLUE";
    MarkerType[MarkerType["HUE_VIOLET"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_VIOLET : undefined] = "HUE_VIOLET";
    MarkerType[MarkerType["HUE_MAGENTAF"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_MAGENTAF : undefined] = "HUE_MAGENTAF";
    MarkerType[MarkerType["HUE_ROSE"] = exports.AMapManager ? exports.AMapManager.MARKER_HUE_ROSE : undefined] = "HUE_ROSE";
})(MarkerType = exports.MarkerType || (exports.MarkerType = {}));
/**
 * 定位模式
 */
var LocationMode;
(function (LocationMode) {
    LocationMode[LocationMode["batterySaving"] = exports.AMapLocation ? exports.AMapLocation.locationMode.batterySaving : undefined] = "batterySaving";
    LocationMode[LocationMode["deviceSensors"] = exports.AMapLocation ? exports.AMapLocation.locationMode.deviceSensors : undefined] = "deviceSensors";
    LocationMode[LocationMode["hightAccuracy"] = exports.AMapLocation ? exports.AMapLocation.locationMode.hightAccuracy : undefined] = "hightAccuracy";
})(LocationMode = exports.LocationMode || (exports.LocationMode = {}));
/**
 * 定位模式
 */
var LocationProtocol;
(function (LocationProtocol) {
    LocationProtocol[LocationProtocol["http"] = exports.AMapLocation ? exports.AMapLocation.locationProtocol.http : undefined] = "http";
    LocationProtocol[LocationProtocol["https"] = exports.AMapLocation ? exports.AMapLocation.locationProtocol.https : undefined] = "https";
})(LocationProtocol = exports.LocationProtocol || (exports.LocationProtocol = {}));
class AMap extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return react_native_1.Platform.OS == 'ios' ? (React.createElement(NativeAMap, null,
            React.createElement(react_native_1.Text, null, "\u6682\u65F6\u4E0D\u652F\u6301IOS"))) : (React.createElement(NativeAMap, Object.assign({}, this.props)));
    }
    setOptions(options) {
        exports.AMapManager.setOptions(react_native_1.findNodeHandle(this), options);
    }
    searchPoi(params) {
        exports.AMapManager.searchPoi(params);
    }
    setCenter(coordinate) {
        exports.AMapManager.setCenter(react_native_1.findNodeHandle(this), coordinate);
    }
}
AMap.propTypes = Object.assign({}, ReactNative.View.propTypes, { center: PropTypes.shape({
        latitude: PropTypes.number.isRequired,
        longitude: PropTypes.number.isRequired,
    }), marker: PropTypes.number, zoomLevel: PropTypes.number, enableMarker: PropTypes.bool, enableZoomControls: PropTypes.bool, enableZoomGestures: PropTypes.bool, enableScaleControls: PropTypes.bool, enableCompass: PropTypes.bool, enableMyLocation: PropTypes.bool, enableEnableCenterMarker: PropTypes.bool, onMoved: PropTypes.func });
exports.default = AMap;
const NativeAMap = react_native_1.Platform.OS == 'ios' ? react_native_1.View : react_native_1.requireNativeComponent('AMapView', AMap);
