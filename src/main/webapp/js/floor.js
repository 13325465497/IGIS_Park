var bool = true;
$('#parkShow').on('click', function() {
	if(bool) {
		$('.parkListBox').show();
		bool = !bool;
	} else {
		$('.parkListBox').hide();
		bool = !bool;
	}
});

// 展示园区地图
var zoom = null;
var parkUrlm = null;
var parkName = null;
var currBuildName = null;
var currFloorName = null;
var floorGeoNameObj = null;
var circleObj = null;
var floorMarkerTips = null;
var mymap = null;
var indoor = null; //室内数据
var tagging = ' <div class="walk-navigation">\n' +
	'            <div id="floorMarker" class="markerPop">\n' +
	'                <div class="markerPop-top">\n' +
	'                    <div class="top-title">\n' +
	'                        <span>操作</span>\n' +
	'                    </div>\n' +
	'                    <div class="pop-close" id="floor-pop-close">&times;</div>\n' +
	'                </div>\n' +
	'                <div class="markerPop-center">\n' +
	'                    <ul class="userEdit">\n' +
	'                        <li>起始位置</li>\n' +
	'                        <li>途径位置</li>\n' +
	'                        <li>结束位置</li>\n' +
	'                    </ul>\n' +
	'                </div>\n' +
	'            </div>\n' +
	'        </div>';

/**
 * 展开园区地图
 * @param parkId 园区id
 * @param lon 经度
 * @param lat 纬度
 */
function parkShow(parkId, lon, lat) {
	$('#buildGis').css('display', 'none');
	$('.parkListBox').hide();
	bool = true;
	currBuildName = null;
	currFloorName = null;
	$.ajax({
		type: 'GET',
		url: 'park/parkService/get?id=' + parkId,
		dataType: "json",
		success: function(data) {
			console.log(data)
			parkUrlm = JSON.parse(data.outdoorwmsservice).serviceURL;//园区服务wms服务url
			parkName = data.parkname; //园区名
			indoor = JSON.parse(data.indoorservice);
			options.url = serverHeader + '/com.cloud.isoft.wxs.service' + parkUrlm;
			options.minZoom = 18;
			initMap(options);
			map.setMapZoom(19);
			map.setMapCenter([data.lon, data.lat]);
			var ranges = map.getDataExtent();
			rangesPoint = ranges.extent;
			zoom = map.getMapZoom();
			addPiont(rangesPoint);
			if(data.outdoorwfsservice) {
				var parkUrlf = JSON.parse(data.outdoorwfsservice).serviceURL;
				var feaType = JSON.parse(data.outdoorwfsservice).layersName.split(',');
				for(var i = 0; i < feaType.length; i++) {
					if(feaType[i] == 'layer_region') {
						floor = new iGis.layers.DataServerLayer({
							gisMapObj: map,
							isHover: true,
							serverType: 'WFSSERVER',
							url: serverHeader + '/com.cloud.isoft.wxs.service' + parkUrlf,
							featureNS: "http://mapserver.gis.umn.edu/mapserver",
							featureType: ['layer_region'],
							dataProjection: 'EPSG:4326',
							layerOptions: {
								strokeColor: '#497D9F',
								strokeWidth: 1.5,
								fillColor: 'rgba(255,255,255,0)',
								selFillColor: 'rgba(0,252,255,0.8)',
								selStrokeColor: '#686868',
								selStrokeWidth: 2
							},
							isRef: true
						});
						// 展示室内地图
						floor.addClickEvent(function(e) {
							indoorLoad(e);
						})
					} else if(feaType[i] == 'layer_poi') {
						floor1 = new iGis.layers.DataServerLayer({
							gisMapObj: map,
							isHover: true,
							serverType: 'WFSSERVER',
							url: serverHeader + '/com.cloud.isoft.wxs.service' + parkUrlf,
							featureNS: "http://mapserver.gis.umn.edu/mapserver",
							featureType: ['layer_poi'],
							dataProjection: 'EPSG:4326',
							isRef: false,
							isSpliceUrl: true
						});
					}
				}
			}
			$('#iGis').append('<div id="loadWorld" onclick="removeLayer()"></div>');
		}
	})
}

function delData() {
	floorallData = [];
	floorArrObj = [];
	floorLineData = [];
	if(floorLineObj) floorLineObj.removeFeatureById('line', true);
	if(floorIconObj) floorIconObj.cleanSource();
}

//移除室内数据 展示园区  
function removeBulid() {
	if(floorLineObj || floorIconObj) {
		delData();
	}
	floorMarkerTips.removeMapTips();
	currBuildName = null;
	currFloorName = null;
	$('#buildGis').css('display', 'none');
	$('#drivePlan').css('display', 'block');
	$('#parkWalkPlan').css('display', 'block');
	$('#floorWalkPlan').css('display', 'none');
	$('.markerPop').css('display', 'none');
	_map = map;
}

//加载室内数据 室内点  室内导航
var rangesPoint = null;
var floorLineData = []; // 路线规划数据
var floorArrObj = []; // 起始点数据
var floorallData = [];
var floorLineObj = null;
var floorIconObj = null;
var floorDrawObj = null;
var isNav = false; //是否开启路线规划
function indoorLoad(e) {
	delOutData();
	var _buildName = e.feature.values_.buildname;
	if(_buildName) {
		for(var i = 0; i < indoor.buildinfo.length; i++) {
			if(indoor.buildinfo[i].buildId == _buildName) {
				var floorLon = parseFloat(indoor.buildinfo[i].center[0]);
				var floorLat = parseFloat(indoor.buildinfo[i].center[1]);
				currBuildName = indoor.buildinfo[i].buildId;
				currFloorName = indoor.buildinfo[i].floorId[0];
				if(!mymap) {
					mymap = new iGis.GisMap();
				}
				$('#buildGis').css('display', 'block');
				$('#buildGis').append('<div id="loadPark" onclick="removeBulid()"></div>');
				$('#drivePlan').css('display', 'none');
				$('#parkWalkPlan').css('display', 'none');
				$('#floorWalkPlan').css('display', 'block');
				$('.markerPop').css('display', 'block');
				mymap.createMap({
					mapType: iGis.MapType.WMS_MAP,
					url: serverHeader + '/com.cloud.isoft.wxs.service' + parkUrlm,
					divId: 'buildGis',
					layerParams: {
						VERSION: '1.3.0',
						LAYERS: 'gisserver'
					},
					center: [floorLon, floorLat],
					zoom: 19,
					zoomType: 'big',
					projection: 'EPSG:3857',
					isScaleLine: true,
					isMousePosition: true,
					isZoomLevel: true,
					units: 'metric',
					wrapX: false,
					minZoom: 18,
					maxZoom: 22,
					isIndoor: true,
					indoorData: {
						'parkName': indoor.parkName,
						'isDoor': true,
						'buildinfo': [indoor.buildinfo[i]],
						wmsServiceName: indoor.wmsServiceName, //http://localhost:63345/com.cloud.isoft.wxs.service
						wfsServiceName: indoor.wfsServiceName,
						currBuildId: currBuildName, //默认加载某一栋楼
						currFloorId: currFloorName, //默认加载某一栋楼的某一层
						switchFloorFunc: function(param) { //切换楼层后的回调函数
							currBuildName = param.buildId;
							currFloorName = param.floorId;
							if(!isNav) {
								refData();
							}
							if(circleObj) {
								circleObj.cleanSource();
							}
							if(floorGeoNameObj) {
								floorGeoNameObj.cleanSource();
							}
							console.log(param);
							editPoint();
							if(rangesPoint) {
								addPiont(rangesPoint);
							}
						}
					}
				});
				var ranges = mymap.getDataExtent();
				rangesPoint = ranges.extent;
				zoom = mymap.getMapMaxZoom();
				addPiont(rangesPoint);
				_map = mymap;
				if(!document.getElementById('floorMarker')) {
					$('#buildGis').append(tagging);
				}
				delData();
				floorLineObj = new iGis.layers.LineLayer({
					gisMapObj: mymap,
					callBackFun: function(param) {
						param.currObj.setData(floorLineData, param.seq);
					},
					isRef: false,
					layerOptions: {
						isDirect: true,
						baseRadius: 25,
						strokeWidth: 7,
						strokeColor: "#47DEDA"
					}
				});
				floorLineObj.setZIndex(9998);
				floorIconObj = new iGis.layers.IconLayer({
					gisMapObj: mymap,
					isRef: false,
					isHover: false,
					callBackFun: function(param) {
						param.currObj.setData(floorArrObj, param.seq);
					},
					layerOptions: {
						isBox: false,
						src: 'img/start_f51000d.png',
						selSrc: 'img/end_f68595d.png',
						anchor: [40, 45]
					}
				});
				floorIconObj.setZIndex(9999);
				floorDrawObj = new iGis.maptools.DrawFeature({
					gisMapObj: mymap
				});
				floorMarkerTips = new iGis.maptools.MapTips({
					gisMapObj: mymap,
					element: document.getElementById('floorMarker'),
					offset: [-65, -105],
					positioning: 'top-left'
				});
				var clickCoord = [];
				var dataParam = [];

				$('#floorWalkPlan').on('click', function() {
					isNav = true;
					$('.navigation-list').hide();
					nav = true;
				});
				mymap.addCustomRightClickEvent(function(param) {
					if(isNav) {
						$('#floorMarker').show();
						clickCoord = param;
						floorMarkerTips.setPosition(param);
					}
				});
				$('.markerPop .markerPop-center .userEdit li:nth-child(1)').on('click', function() {
					delData();
					floorArrObj.push({
						id: 'p' + new Date().getTime(),
						coord: clickCoord,
						src: 'img/igis_start.png'
					});
					floorIconObj.refreshLayerByData();
					dataParam = [];
					dataParam.push({
						"point": clickCoord,
						"floorName": currFloorName,
						"buildName": currBuildName
					});
					floorMarkerTips.setPosition(undefined);
				});
				$('.markerPop .markerPop-center .userEdit li:nth-child(2)').on('click', function() {
					floorArrObj.push({
						id: 'p' + new Date().getTime(),
						coord: clickCoord,
						src: 'img/blue.png'
					});
					floorIconObj.refreshLayerByData();
					dataParam.push({
						"point": clickCoord,
						"floorName": currFloorName,
						"buildName": currBuildName
					});
					floorMarkerTips.setPosition(undefined);
				});
				$('.markerPop .markerPop-center .userEdit li:nth-child(3)').on('click', function() {
					floorArrObj.push({
						id: 'p' + new Date().getTime(),
						coord: clickCoord,
						src: 'img/igis_end.png'
					});
					floorIconObj.refreshLayerByData();
					dataParam.push({
						"point": clickCoord,
						"floorName": currFloorName,
						"buildName": currBuildName
					});
					floorMarkerTips.setPosition(undefined);
					walkPlan(dataParam);
				});
				$('#floor-pop-close').on('click', function() {
					floorMarkerTips.setPosition(undefined);
				});

				function walkPlan(dataParam) {
					var _data = JSON.stringify({
						"parkName": parkName,
						"navigationDataInfoList": dataParam,
						"isInDoor": true
					});
					$.ajax({
						type: 'post',
						contentType: 'application/json',
						url: navigationHeard + '/getNavigationRoutesForWalk',
						data: _data,
						success: function(msg) {
							floorallData = msg;
							refData();
							isNav = false;
						}
					});
				}

				function refData() {
					floorLineData = [];
					for(var i = 0; i < floorallData.length; i++) {
						if(floorallData[i].buildName == '' && floorallData[i].floorName == '') {
							floorLineData.push({
								id: 'line' + i,
								coord: floorallData[i].points
							});
						} else if(floorallData[i].buildName == currBuildName && floorallData[i].floorName == currFloorName) {
							floorLineData.push({
								id: 'line' + i,
								coord: floorallData[i].points
							});
						}
					}
					console.log(floorLineData);
					if(floorLineData.length != 0) {
						floorLineObj.refreshLayerByData();
					}
				}
				delData();
			}
		}
	}
}

var ParkPointObj = null;
var floorPointObj = null;
var addPointData = [];
var pointType = '';
$('#security').click(function() {
	var security = $('#security').is(":checked");
	if(security) {
		$("#security").prop('checked', true);
		addPiont(rangesPoint);
	} else {
		$("#security").prop('checked', false);
		addPiont(rangesPoint);
	}
});
$('#firecontrol').click(function() {
	var firecontrol = $('#firecontrol').is(":checked");
	if(firecontrol) {
		$("#firecontrol").prop('checked', true);
		addPiont(rangesPoint);
	} else {
		$("#firecontrol").prop('checked', false);
		addPiont(rangesPoint);
	}
})

//添加点
function addPiont(range) {
	if($('#firecontrol').is(":checked") && !($('#security').is(":checked"))) {
		console.log("选消防");
		pointType = 1;
	} else if($('#security').is(":checked") && !($('#firecontrol').is(":checked"))) {
		console.log("选安防");
		pointType = 2;
	} else if($('#security').is(":checked") && $('#firecontrol').is(":checked")) {
		console.log("全选");
		pointType = '';
	} else {
		if(parkName && currBuildName && currFloorName) {
			if(floorPointObj) {
				addPointData = [];
				floorPointObj.refreshLayerByData();
			}
			addMyMap();
		} else {
			if(ParkPointObj) {
				addPointData = [];
				ParkPointObj.refreshLayerByData();
			}
			addMap();
		}
		return false;
	}
	var data = {
		"parkName": parkName,
		"buildName": currBuildName,
		"floorName": currFloorName,
		"pointType": pointType,
		"point": range,
		"zoom": zoom
	};
	$.ajax({
		type: 'POST',
		url: 'point/search',
		contentType: 'application/json',
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(data) {
			var pointData = [];
			//加载新增点
			console.log('点数据：', data);
			var pointData = data;
			if(pointData.length > 0) {
				addPointData = [];
				for(var i = 0; i < pointData.length; i++) {
					var iconobj = {};
					iconobj.id = pointData[i].pointID;
					iconobj.coord = [pointData[i].lon, pointData[i].lat];
					iconobj.src = pointData[i].iconUrl;
					iconobj.label = pointData[i].lable;
					addPointData.push(iconobj);
				}
				if(parkName && currBuildName && currFloorName) {
					if(floorPointObj) {
						floorPointObj.refreshLayerByData();
					}
					addMyMap();
				} else {
					if(ParkPointObj) {
						ParkPointObj.refreshLayerByData();
					}
					addMap();
				}
			}
		}
	})

}
var floorCallBack = null;
var parkCallBack = null;

function addMap() {
	parkCallBack = function(param) {
		console.log(param);
		param.currObj.setData(addPointData, param.seq);
		rangesPoint = param.extent.extent;
		zoom = param.extent.zoom;
	};
	if(!ParkPointObj) {
		ParkPointObj = new iGis.layers.IconLayer({
			gisMapObj: map,
			callBackFun: parkCallBack,
			isHover: true,
			layerOptions: {
				labFillColor: '#ffffff',
				labStrokeColor: '#333333',
				labStrokeWidth: 2,
				lalFontSize: '11px',
				minLabShowZoom: 5,
				scale: 0.4,
				isBox: false,
				anchor: [30, 80]
			}
		});
	}
	ParkPointObj.setZIndex(8888);
	ParkPointObj.refreshLayerByData();
}

function addMyMap() {
	floorCallBack = function(param) {
		console.log(param);
		param.currObj.setData(addPointData, param.seq);
		rangesPoint = param.extent.extent;
		zoom = param.extent.zoom;
	};
	if(!floorPointObj) {
		floorPointObj = new iGis.layers.IconLayer({
			gisMapObj: mymap,
			callBackFun: floorCallBack,
			isHover: true,
			layerOptions: {
				labFillColor: '#ffffff',
				labStrokeColor: '#333333',
				labStrokeWidth: 2,
				lalFontSize: '11px',
				minLabShowZoom: 5,
				scale: 0.4,
				isBox: false,
				anchor: [30, 80]
			}
		});
	}
	floorPointObj.setZIndex(8888);
	floorPointObj.refreshLayerByData();
}

//编辑点功能
function editPoint() {
	//加载新增点
	var layerJsonData = {
		type: 'FeatureCollection',
		srid: '4326',
		geoType: 'Point',
		features: []
	}; // 所有图层缓存数据
	var layerObj = {}; // 当前图层信息（数据交换）
	var zoomRange = [12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]; // 级别显示图层（添加图标时数据交换所需）
	var path = 'img/icon/';
	if(parkName && currBuildName && currFloorName) {
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
			success: function(data) {
				var pointData = data.rows;
				if(data.total > 0) {
					pointData.map(function(item) {
						var resultData = {
							'type': 'Feature',
							'id': item.id,
							'properties': {
								'name': item.lable,
								'icon': path + item.iconurl,
								'display': zoomRange,
								'property': {
									'isBox': true,
									'label': item.lable,
									'src': item.iconurl,
									'scale': 0.4,
									'anchor': [31, 31],
									'labOffsetY': 18,
								}
							},
							'geometry': {
								'type': 'Point',
								'coordinates': [item.lon, item.lat]
							}
						}
						layerJsonData.features.push(resultData);
					})
					newFeatures();
				}
			}
		})
	}

	function newFeatures() {
		floorGeoNameObj = new iGis.layers.DataServerLayer({
			gisMapObj: mymap,
			serverType: 'GEOSERVER',
			url: layerJsonData,
			callBackFun: callbackFunction,
			layerOptions: {
				strokeWidth: 3,
				labFillColor: 'rgb(164, 0, 216)',
				lalFontSize: '13px',
				anchor: [31, 31],
				labOffsetY: -12,
			},
			dataProjection: 'EPSG:4326'
		});
		floorGeoNameObj.setZIndex(4);
		var callbackFunction = function(param) {
			console.log(param);
		};
	}
}