var baseMapUrl = 'http://' + ipPort + '/com.cloud.isoft.wxs.service/WMSServices/loadStdout?serviceName=gisserver_wms_shape_osm_night';
var navigationHeard = 'http://' + ipPort + '/com.cloud.isoft.graphhopperreadershp.service'; //导航header
var serverHeader = 'http://' + ipPort + ''; //服务header
localStorage.setItem('serverAddress', 'http://' + ipPort + '');
localStorage.setItem('wxsServerAddress', 'http://' + ipPort + '/com.cloud.isoft.wxs.service');
var map = new iGis.GisMap();
var _map = map;
var pointDataObj = []; //首页显示全球所有园区,存入
var options = {
    mapType: iGis.MapType.WMS_MAP,
    divId: 'iGis',
    layerParams: {
        VERSION: '1.3.0',
        LAYERS: 'gisserver'
    },
    center: [108.942374, 34.261004], //中心点
    zoom: 5,
    minZoom: 4,
    maxZoom: 22,
    zoomType: 'small',
    projection: "EPSG:4326",
    isScaleLine: true,//比例线
    isMousePosition: true, //鼠标位置
    isZoomLevel: true,//缩放级别
    isOverview: true,//
    units: "metric",//单位
    wrapX: false
};

$(function () {
    // 页面初始化
    $.ajax({
        type: 'GET',
        url: 'park/park/baseMap/get',
        success: function (data) {
            if (data) {
                baseMapUrl = data;
                options.url = baseMapUrl;
            } else {
                options.url = baseMapUrl;
            }
            initMap(options);
        }
    })
    $.ajax({
        type: 'GET',
        url: 'park/parkService/list',
        data: {
            limit: 15, //每页条数
            offset: 0 //页码
        },
        dataType: "json",
        success: function (data) {
            var parkList = '';
            if (data.rows.length > 0) {
                for (var i = 0; i < data.rows.length; i++) {
                    parkList += '<li class="parkListItem" onclick="parkShow(' + data.rows[i].id + ',' + data.rows[i].lon + ',' + data.rows[i].lat + ' )"><a href="#">' + data.rows[i].parkusername + '</a></li>'
                    var dataObj = {};
                    dataObj.id = data.rows[i].id;
                    dataObj.label = data.rows[i].parkusername;
                    dataObj.coord = [data.rows[i].lon, data.rows[i].lat];
                    dataObj.src = 'img/red.png';
                    pointDataObj.push(dataObj);
                }
            }
            $('.parkList').html(parkList);//展示园区
        }
    })
})

var floor;
var floor1;
var geoNameObj = null;
var iconClu = null;
var arrObj = []; // 起始点
var allData = [];
var linedata = []; // 路线规划数据
var lineObj = null;
var iconObj = null;
var drawObj = null;
var clickCoord = [];
var dataParam = [];
var markerTips = null;
var isWalk = false; // 是否开启路线规划
//初始化首页地图
function initMap(options) {
    delOutData();
    map.createMap(options);
    var ranges = map.getDataExtent(); //包含级别和范围点
    console.log("ranges" + JSON.stringify(ranges))
    zoom = map.getMapZoom();//地图级别
    console.log("zoom" + zoom)
    rangesPoint = ranges.extent;
    if (geoNameObj) {
        geoNameObj.cleanSource();
    }
    var path = 'img/icon/';
    //如果有园区名, 根据园区加载用户新增的点 ~
    if (parkName) {
        var params = {};
        params.parkname = parkName;
        params.buildname = currBuildName;
        params.floorname = currFloorName;
        $.ajax({
            type: 'GET',
            url: 'edit/userPoint/list',
            async: false,
            contentType: 'application/json',
            data: params,
            success: function (data) {
                var pointData = [];
                if (data.total > 0) {
                    for (var i = 0; i < data.rows.length; i++) {
                        if (data.rows[i].buildname == null && data.rows[i].floorname == null) {
                            var iconobj = {};
                            iconobj.id = data.rows[i].id;
                            iconobj.coord = [data.rows[i].lon, data.rows[i].lat];
                            if (data.rows[i].iconurl) {
                                iconobj.src = path + data.rows[i].iconurl;
                            } else {
                                iconobj.src = "img/blue.png";
                            }
                            if (data.rows[i].lable) {
                                iconobj.label = data.rows[i].lable;
                            } else {
                                iconobj.label = "新点";
                            }
                            pointData.push(iconobj);
                            console.log(iconobj);
                        }
                    }
                    var circleObj = new iGis.layers.IconLayer({
                        gisMapObj: map,
                        isRef: true,
                        isHover: false,
                        layerOptions: {
                            scale: 0.4,
                            isBox: false,
                            selSrc: 'img/yellow.png',
                            anchor: [30, 60]
                        }
                    });
                    circleObj.setData(pointData, 0);

                }

            }
        })
    }
    if (iconClu) {
        iconClu.setVisible(false);
    } else {
        //汇聚图标
        iconClu = new iGis.layers.ClusterIconLayer({
            gisMapObj: map,
            callBackFun: function (param) {
                param.currObj.setData(pointDataObj, param.seq);
            },
            layerOptions: {
                isAnimation: true, // 开启动画（默认开启）
                anchor: [0.5, 0.35],
                distance: 20,
                clusterSrc: 'img/blue.png',
                clusterScale: 1,
                clusterAnchor: [0.5, 0.35]
            }
        });
        iconClu.refreshLayerByData();
        iconClu.addClickEvent(function (e) {
            if (e.feature.get('features').length > 1) {
                var features = e.feature.get('features');
                var extent = [Infinity, Infinity, -Infinity, -Infinity];
                console.log(features);
                for (var i = 0; i < features.length; i++) {
                    iGis.util.GisUtil.extentExtend(extent, features[i].getGeometry().getExtent());
                }
                map.setMapExtent(extent);
            } else {
                //调用园区地图
                parkShow(e.feature.values_.features[0].id_, e.feature.values_.geometry.flatCoordinates[0], e.feature.values_.geometry.flatCoordinates[1]);
            }
        })
    }

    document.getElementById('drivePlan').onclick = drivePlan; //园区车导
    lineObj = new iGis.layers.LineLayer({
        gisMapObj: map,
        callBackFun: function (param) {
            param.currObj.setData(linedata, param.seq);
        },
        isRef: false,
        layerOptions: {
            isDirect: true,
            baseRadius: 25,
            strokeWidth: 7,
            strokeColor: "#47DEDA"
        }
    });
    lineObj.setZIndex(9998);
    iconObj = new iGis.layers.IconLayer({
        gisMapObj: map,
        isRef: false,
        isHover: false,
        callBackFun: function (param) {
            param.currObj.setData(arrObj, param.seq);
        },
        layerOptions: {
            isBox: false,
            src: 'img/start_f51000d.png',
            selSrc: 'img/end_f68595d.png',
            anchor: [40, 45]
        }
    });
    iconObj.setZIndex(9999);
    drawObj = new iGis.maptools.DrawFeature({
        gisMapObj: map
    });

    /**
     * 点击车导
     */
    function drivePlan() {
        $('.navigation-list').hide();
        nav = true;
        delOutData();//清空线条灯(好像是)
        //路线点获取 , 可以多个点
        drawObj.addDrawFeature('LineString', function (datas) {
            setTimeout(function () {
                drawObj.removeDrawFeature();
            }, 10);
            console.log("datas" + datas)
            var _data = JSON.stringify({
                "points": datas,
                "parkName": parkName
            })
            $.ajax({
                type: 'post',
                contentType: 'application/json',
                url: navigationHeard + '/getNavigationRoutes',
                data: _data,
                success: function (msg) {
                    linedata = [{
                        id: 'outLine',
                        coord: msg
                    }];
                    lineObj.refreshLayerByData();
                    for (var i = 0; i < datas.length; i++) {
                        var src = '';
                        if (i == 0) {
                            src = 'img/igis_start.png';
                        } else if (i == (datas.length - 1)) {
                            src = 'img/igis_end.png';
                        } else {
                            src = 'img/blue.png';
                        }
                        arrObj.push({
                            id: 'p' + i,
                            coord: datas[i],
                            src: src
                        });
                    }
                    iconObj.refreshLayerByData();
                }
            });
        });
    }

    //屏幕左下角操作
    markerTips = new iGis.maptools.MapTips({
        gisMapObj: map,
        element: document.getElementById('outdoor-marker'),
        offset: [-65, -105],
        positioning: 'top-left'
    });

    $('#parkWalkPlan').on('click', function () {
        isWalk = true;
        $('.navigation-list').hide();
        nav = true;

    });
    //添加地图右键点击事件
    map.addCustomRightClickEvent(function (param) {
        if (isWalk) {
            $('.outdoor-markerPop').css('display', 'block');
            console.log("param" + param)
            clickCoord = param;
            markerTips.setPosition(param);
        }
    });
    //点击起始事件
    $('.outdoor-markerPop .outdoor-markerPop-center .outdoor-userEdit li:nth-child(1)').on('click', function () {
        delOutData();
        arrObj.push({
            id: 'p' + new Date().getTime(),
            coord: clickCoord,
            src: 'img/igis_start.png'
        });
        iconObj.refreshLayerByData();
        dataParam = [];
        dataParam.push({
            "point": clickCoord,
            "floorName": currFloorName,
            "buildName": currBuildName
        });
        console.log("arrObj 0 : " + arrObj);
        console.log("dataParam 0 : " + dataParam);
        markerTips.setPosition(undefined);
    });
    //点击途径事件
    $('.outdoor-markerPop .outdoor-markerPop-center .outdoor-userEdit li:nth-child(2)').on('click', function () {
        arrObj.push({
            id: 'p' + new Date().getTime(),
            coord: clickCoord,
            src: 'img/blue.png'
        });
        iconObj.refreshLayerByData();
        dataParam.push({
            "point": clickCoord,
            "floorName": currFloorName,
            "buildName": currBuildName
        });
        console.log("arrObj 1 : " + arrObj);
        console.log("dataParam 1 : " + dataParam);
        markerTips.setPosition(undefined);
    });
    //点击终点位置事件
    $('.outdoor-markerPop .outdoor-markerPop-center .outdoor-userEdit li:nth-child(3)').on('click', function () {
        arrObj.push({
            id: 'p' + new Date().getTime(),
            coord: clickCoord,
            src: 'img/igis_end.png'
        });
        iconObj.refreshLayerByData();
        dataParam.push({
            "point": clickCoord,
            "floorName": currFloorName,
            "buildName": currBuildName
        });
        console.log("arrObj 2 : " + arrObj);
        console.log("dataParam 2 : " + dataParam);
        markerTips.setPosition(undefined);
        //walkPlan(dataParam);
    });
    $('#outdoor-pop-close').on('click', function () {
        markerTips.setPosition(undefined);
    });

    //室外步导
    function walkPlan(dataParam) {
        var _data = JSON.stringify({
            "parkName": parkName,
            "navigationDataInfoList": dataParam,
            "isInDoor": false
        });
        console.log(_data);
        $.ajax({
            type: 'post',
            contentType: 'application/json',
            url: navigationHeard + '/getNavigationRoutesForWalk',
            data: _data,
            success: function (msg) {
                allData = msg;
                refData();
                isWalk = false;
            }
        });
    }

//导航收据返回 路线展示
    function refData() {
        linedata = [];
        for (var i = 0; i < allData.length; i++) {
            if (allData[i].buildName == '' && allData[i].floorName == '') {
                linedata.push({
                    id: 'outLine' + i,
                    coord: allData[i].points
                });
            } else if (allData[i].buildName == currBuildName && allData[i].floorName == currFloorName) {
                linedata.push({
                    id: 'outLine' + i,
                    coord: allData[i].points
                });
            }
        }
        console.log(linedata);
        if (linedata.length != 0) {
            lineObj.refreshLayerByData();
        }
    }

    delOutData();
}

function delOutData() {
    allData = [];
    arrObj = [];
    linedata = [];
    if (lineObj) lineObj.removeFeatureById('outLine', true);
    if (iconObj) iconObj.cleanSource();
}

function removeLayer() {
    delOutData();
    if (geoNameObj) {
        geoNameObj.cleanSource();
    }
    options.url = baseMapUrl;
    options.minZoom = 5;
    options.center = [108.942374, 34.261004];
    map.createMap(options);
    if (floor) {
        floor.cleanSource();
    }
    if (floor1) {
        floor1.cleanSource();
    }
    map.setMapZoom(5);
    _map = map;
    $('div').remove('#loadWorld');
    iconClu.setVisible(true);
    parkName = null;
}