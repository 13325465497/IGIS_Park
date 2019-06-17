$(function() {
    var mymap = new iGis.GisMap();
    mymap.createMap({
        mapType: iGis.MapType.WMS_MAP,
        url: 'http://114.115.183.47:8000/com.cloud.isoft.wxs.service/WMSServices/loadStdout?serviceName=gisserver_wms_shape_osm_common',
        divId: 'iGis2D',
        layerParams: {
            VERSION: '1.3.0',
            LAYERS: 'gisserver'
        },
        center: [108.7633681856088, 34.258602130643126],
        zoom: 17,
        maxZoom: 22,
        zoomType: 'big', // [ { big: 显示(+、-) 和 滑动条}， { small: 显示(+、-)}， { 为空： 不显示(+、-) 和 滑动条 } ]
        projection: "EPSG:4326",
        isScaleLine: true,
        isMousePosition: true,
        isZoomLevel: true,
        units: "metric",
        wrapX: false
    });

    var layerObj = {}; // 当前图层信息（数据交换）
    var drawObj = null; // 绘制图层
    var isClick = true; // 是否显示图层
    var layerJsonData = {}; // 所有图层缓存数据
    var poiIconDataAll = {
        'atm': 'atm.png',
        'Attendance-machine': 'Attendance-machine.png',
        'building': 'building.png',
        'coffee': 'coffee.png',
        'Conference-Room': 'Conference-Room.png',
        'default': 'default.png',
        'Depositary': 'Depositary.png',
        'displayScreen': 'displayScreen.png',
        'doorway': 'doorway.png',
        'Entran-icon': 'Entran-icon.png',
        'Entrance': 'Entrance.png',
        'experiment': 'experiment.png',
        'Indicator': 'Indicator.png',
        'in_automat': 'in_automat.png',
        'in_elevator': 'in_elevator.png',
        'in_manwc': 'in_manwc.png',
        'in_meetingroom': 'in_meetingroom.png',
        'in_other_poi': 'in_other_poi.png',
        'in_printroom': 'in_printroom.png',
        'in_rest': 'in_rest.png',
        'in_service': 'in_service.png',
        'in_smokeroom': 'in_smokeroom.png',
        'in_stair': 'in_stair.png',
        'in_womanwc': 'in_womanwc.png',
        'Library': 'Library.png',
        'parking': 'parking.png',
        'property_cleaning': 'property_cleaning.png',
        'RestArea': 'RestArea.png',
        'SecurityGuard': 'SecurityGuard.png',
        'SmokingZone': 'SmokingZone.png',
        'Storage-Room': 'Storage-Room.png',
        'VisitorSelfServiceTerminal': 'VisitorSelfServiceTerminal.png',
    }; // 存储poi所有图标数据
    var currLayerObj = null; // 当前图层信息
    var selPopupType = null; // 图层信息（信息弹框区分新建图层、已有图层）
    var zoomRange = [12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]; // 级别显示图层（添加图标时数据交换所需）
    var Feature1 = 'regionDataUrl',
        Feature2 = 'poiDataUrl',
        Feature3 = 'newPointIcom';
    var getParkData = JSON.parse(window.sessionStorage.getItem('getParkData'));

    // 地图提示弹出框
    var markerTips = new iGis.maptools.MapTips({
        gisMapObj: mymap,
        offset: [-130, -10],
        element: document.getElementById('marker'),
        positioning: 'bottom-left'
    });

    var getDataUrl = function(data, name) {
        layerJsonData[name] = data;
        if (name == Feature3) currLayerObj = data;
        drawObj = new iGis.maptools.DrawFeature({
            gisMapObj: mymap,
            snapLayer: data
        });
        newFeatures(name);
    }
    layerJsonData[Feature3] = {
        type: 'FeatureCollection',
        srid: '4326',
        geoType: 'Point',
        features: []
    }
    newFeatures(Feature3);

    $.getJSON(ctx + getParkData[Feature1], function(data) {
        console.warn('data:::', data);
        data.features.map(function(item) {
            item['properties']['label'] = item['properties']['name'] = item[
                'properties']['buildname'];
            return item;
        })
        getDataUrl(data, Feature1);
    });
    $.getJSON(ctx + getParkData[Feature2], function(data) {
        console.warn('data:::', data);
        data.features.map(function(item) {
            item['properties']['iconImg'] = item['properties']['icon'];
            delete item['properties']['icon'];
            return item;
        })
        getDataUrl(data, Feature2);
    });
    if (getParkData.hasData) {
        var params = {};
        var resultUrl = ctx+'edit/getUserData';
        params.parkname = getParkData.parkFileName;
        params.buildname = getParkData.buildFileName;
        params.floorname = getParkData.floorFileName;
        $.ajax({
            type: 'GET',
            url: resultUrl,
            async: false,
            contentType: 'application/json',
            data: params,
            success: function(data) {
                console.warn('data:::', data);
                data.map(function(item) {
                    var resultData = {
                        'type': 'Feature',
                        'id': item.id,
                        'properties': {
                            'name': item.lable,
                            'iconImg': item.iconurl,
                            'display': zoomRange
                        },
                        'geometry': {
                            'type': 'Point',
                            'coordinates': [item.lon, item.lat]
                        }
                    }
                    layerJsonData[Feature3].features.push(resultData);
                })
                getDataUrl(layerJsonData[Feature3], Feature3);
            }
        });
    }

    /***********************
     * 图层
     ***********************/
    function newFeatures(typeId) {
        var layerOptions = {
            fillColor: 'rgba(197, 189, 105, 0.79)',
            strokeColor: 'rgba(0, 185, 151, 0.802)',
            strokeWidth: 1,
            selFillColor: '#90742b',
            selStrokeColor: '#008cff',
            labFillColor: '#f0ff95',
            lalFontSize: '13px',
            anchor: [31, 31],
            labOffsetY: -12,
        }
        if (typeId == Feature1) {
            layerOptions.strokeWidth = 0;
            layerOptions.labOffsetY = 2;
            layerOptions.lalFontSize = '16px';
        } else if (typeId == Feature2) {
            layerOptions.strokeColor = layerOptions.labFillColor = 'rgb(255, 115, 0)';
        } else if (typeId == Feature3) {
            layerOptions.strokeWidth = 3;
            layerOptions.strokeColor = layerOptions.labFillColor = 'rgb(164, 0, 216)';
        }
        var options = {
            gisMapObj: mymap,
            serverType: 'GEOSERVER',
            url: layerJsonData[typeId],
            layerOptions: layerOptions,
            dataProjection: 'EPSG:4326'
        };

        var geoNameObj = new iGis.layers.DataServerLayer(options);
        if (typeId == Feature3) {
            layerObj[typeId] = geoNameObj;
            currLayerObj = geoNameObj;
            geoNameObj.addClickEvent(function(res) {
                if (isClick && res.feature.getGeometry().getType() == 'Point') {
                    selectPopNewAdd(res.feature, res.clickCoord, false);
                    console.log(res.feature.getId());
                }
            });
        }
        if (typeId == Feature1) geoNameObj.setZIndex(2);
        else if (typeId == Feature2) geoNameObj.setZIndex(3);
        else if (typeId == Feature3) geoNameObj.setZIndex(4);
    }

    function selectPopNewAdd(body, coord, state) {
        markerTips.setPosition(coord);
        var coo, template, feaType;
        if (state) {
            selPopupType = body;
            coo = body.properties;
            template = '<input type="hidden" id="currId" value=' + body.id + ' />';
            feaType = body.geometry.type;
        } else {
            selPopupType = null;
            coo = body.getProperties();
            template = '<input type="hidden" id="currId" value=' + body.getId() + ' />';
            feaType = body.getGeometry().getType();
        }
        for (var v in coo) {
            var list = null;
            if (v == 'geometry' || v == 'cp' || v == 'property') {
                continue;
            } else {
                if (feaType == 'Point') {
                    if (v == 'iconImg') list = "<li><label class='attr-label'>" + v + "</label><span class='attr-value new-point-icon'>" + coo[v] + "</span></li>";
                    else if (v == 'name') list = "<li><label class='attr-label'>" + v + "</label><span class='attr-value' contenteditable>" + coo[v] + "</li>";
                    else list = "<li><label class='attr-label'>" + v + "</label><span class='attr-value'>" + coo[v] + "</span></li>";
                    template += list;
                }
            }
        }
        document.getElementById('longitude').innerHTML = template;
    }

    /***********************
     * 添加图标
     ***********************/
    function add_newIcon(name) {
        isClick = false;
        drawObj.addDrawFeature(name, function(e) {
            console.log(e);
            var coord = e;
            var template = {
                'type': 'Feature',
                'id': new Date().getTime().toString(),
                'properties': {
                    'name': 'newIcom',
                    'iconImg': '',
                    'display': zoomRange
                },
                'geometry': {
                    'type': 'Point',
                    'coordinates': e
                }
            };
            selectPopNewAdd(template, coord, true);
        });
    }

    $('.igis_button').on('click', '.igis_btn', function() {
        if ($(this).attr('id') == 'Point') add_newIcon('Point');
    })

    $('#longitude').on('click', '.new-point-icon', function() {
        $('.markerImg-center').html('');
        for (let val in poiIconDataAll) {
            $('.markerImg-center').append('<img name="' + poiIconDataAll[val] +
                '" src="/IGIS_Park/img/icon/' + val + '.png">');
        }
        $('#markerImg,.popup-layer').fadeIn();
    });

    $('#closeLayerType,#markerImg-close,#closeService').on('click', function() {
        $('#glassLayer,#markerImg,#changeService,.popup-layer').fadeOut();
    });

    $('#markerImg').on('click', '.markerImg-center > img', function() {
        var src = $(this).attr('name');
        $('#longitude .new-point-icon').text(src);
        $('#markerImg,.popup-layer').fadeOut();
    });

    $('#fun5').on('click', function() {
        mymap.removeLayer(layerObj[Feature3]);
        var currId = $('#currId').val();
        var attrObj = {};

        for (var i = 0; i < $('#longitude li').length; i++) {
            var key = $($('#longitude li')[i]).children('.attr-label').text();
            var value = $($('#longitude li')[i]).children('.attr-value').text();
            if (!key && !value || !key) continue;
            else attrObj[key] = value;
        }
        if (selPopupType) layerJsonData[Feature3].features.push(selPopupType);
        layerJsonData[Feature3].features.forEach(function(v) {
            if (v.id == currId) v.properties = attrObj;
        });
        console.info('保存零件属性：', attrObj, layerJsonData[Feature3]);
        newFeatures(Feature3);
        $('#pop-close').click();
    })

    $('#fun7').on('click', function() {
        var currId = $('#currId').val();
        $('#pop-close').click();
        layerJsonData[Feature3].features.splice(layerJsonData[Feature3].features.findIndex(
            function(item) {
                return item.id == currId;
            }), 1);
        currLayerObj.removeFeatureById(currId);
    });

    $('#pop-close').on('click', function() {
        markerTips.setPosition(undefined);
        isClick = true;
        if (drawObj) drawObj.removeDrawFeature();
    })

    $('#Save').on('click', function() {
        var requestData = [];
        var resultUrl = null;
        layerJsonData[Feature3].features.map(item => {
            var params = {};
            if (getParkData.Type == 0) {
                resultUrl = ctx+'edit/editParkData';
                params.parkname = getParkData.parkFileName;
            } else if (getParkData.Type == 2) {
                resultUrl = ctx+'edit/editFloorData';
                params.parkname = getParkData.parkFileName;
                params.buildname = getParkData.buildFileName;
                params.floorname = getParkData.floorFileName;
            }
            params.lon = item.geometry.coordinates[0];
            params.lat = item.geometry.coordinates[1];
            params.iconurl = item.properties.iconImg;
            params.lable = item.properties.name;
            requestData.push(params);
        })
        console.log('参数：', JSON.stringify(requestData));

        var parm = {
            "userPointList": requestData
        }
        parm = JSON.stringify(parm);
        $.ajax({
            type: 'POST',
            url: resultUrl,
            contentType: 'application/json',
            data: parm,
            success: function(data) {
                parent.layer.alert("操作成功")
                console.log(data);
            }
        });
    })
})