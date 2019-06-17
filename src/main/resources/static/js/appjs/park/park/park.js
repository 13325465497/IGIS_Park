var prefixPark = ctx + "park/park";
var prefixPoint = ctx + "point";
var prefixBuilding = ctx + "park/building";
var prefixFloor = ctx + "park/floor";
var serverHeader = 'http://' + ipPort + '';   //发布服务head头
$(function () {
    load(1, 10);
});
var load = function (pageNum, pageSize) {
    $('#exampleTable')
        .bootstrapParkTreeTable(
            {
                id: 'id',
                code: 'id',
                parentCode: 'parentId',
                type: "GET", // 请求数据的ajax类型
                url: prefixPark + '/listTree',   // 请求数据的ajax的url
                ajaxParams: {
                    pageNum: pageNum,
                    pageSize: pageSize,
                    parkName: $("#searchName").val()
                }, // 请求数据的ajax的data属性
                expandColumn: '1',// 在哪一列上面显示展开按钮
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: false, // 是否全部展开
                // toolbar : '#exampleToolbar',
                columns: [
                    {
                        title: 'ID',
                        field: 'id',
                        visible: false,
                        align: 'center',
                        valign: 'center',
                        width: '5%'
                    },
                    {
                        title: '名称',
                        valign: 'center',
                        field: 'name',
                        width: '20%'
                    }, {
                        title: '标签',
                        valign: 'center',
                        field: 'lable',
                        width: '20%'
                    }, {
                        title: '面积(㎡)',
                        valign: 'center',
                        field: 'area',
                        width: '20%'
                    },
                    {
                        title: '类型',
                        field: 'type',
                        /*  align: 'center',*/
                        valign: 'center',
                        width: '20%',
                        formatter: function (item, index) {
                            if (item.type === 0) {
                                return '<span class="label label-primary">园区</span>';
                            }
                            if (item.type === 1) {
                                return '<span class="label label-success">楼栋</span>';
                            }
                            if (item.type === 2) {
                                return '<span class="label label-warning">楼层</span>';
                            }
                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (row, index) {
                            //园区
                            var pd = '<a class="btn btn-success btn-sm '
                                + s_add_h
                                + '" href="#" mce_href="#" title="室外数据" onclick="addOutdoorData(\''
                                + row.id
                                + '\')"><i class="fa fa-database"></i></a> ';
                            var pb = '<a class="btn btn-primary btn-sm '
                                + s_edit_h
                                + '" href="#" mce_href="#" title="编辑" onclick="parkEdit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var pc = '<a class="btn btn btn-info btn-sm" href="#" title="发布地图"  mce_href="#" onclick="Release(\''
                                + row.id
                                + '\')"><i class="fa fa-paper-plane-o"></i></a> ';
                            var pa = '<a class="btn btn-warning btn-sm" href="#" title="添加楼栋"  mce_href="#" onclick="buildingAdd(\''
                                + row.id
                                + '\',\'' +
                                row.name
                                + '\')"><i class="fa fa-plus"></i></a> ';
                            var pf = '<a class="btn btn-warning btn-sm" href="#" title="业务数据(点)"  mce_href="#" onclick="pointDataAdd(\''
                                + row.id
                                + '\',\'' +
                                row.name
                                + '\')"><i class="fa fa-map-pin"></i></a> ';
                            var pe = '<a class="btn btn-danger btn-sm '
                                + s_remove_h
                                + '" href="#" title="删除"  mce_href="#" onclick="parkRemove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            //楼栋
                            var bb = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="buildingEdit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';

                            var ba = '<a class="btn btn-warning btn-sm" href="#" title="添加楼层"  mce_href="#" onclick="floorAdd(\''
                                + row.id
                                + '\',\'' +
                                row.name
                                + '\')"><i class="fa fa-plus"></i></a> ';
                            var be = '<a class="btn btn-danger btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="buildingRemove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';

                            //楼层
                            var fb = '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" mce_href="#" title="室内数据" onclick="addIndoorData(\''
                                + row.id
                                + '\')"><i class="fa fa-database"></i></a> ';
                            var fa = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="floorEdit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var fc = '<a class="btn btn-danger btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="floorRemove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            if (row.type == 0) {
                                return pa + pb + pc + pd +pf+ pe;
                            } else if (row.type == 1) {
                                return ba + bb + be;
                            } else if (row.type == 2) {
                                return fa + fb + fc;
                            }
                        }
                    }],
            });
}
/**
 *
 * @param pageCurrent 当前所在页
 * @param pageSum 总页数
 * @param total 总记录数
 */
var pageNumber; //记录当前第几页
var setPage = function (pageNum, pages, total) {
    //生成分页
    //有些参数是可选的，比如lang，若不传有默认值
    kkpager.generPageHtml({
        pno: pageNum,
        //总页码
        total: pages,
        //总数据条数
        totalRecords: total,
        mode: 'click', //这里设置为click模式
        isShowTotalPage: true,//是否显示总页数 布尔型 默认值 true
        isShowTotalRecords: true,//是否显示总记录数 布尔型 默认值 true
        isShowFirstPageBtn: true,//是否显示首页按钮 布尔型 默认值 true
        isShowLastPageBtn: true,//是否显示尾页按钮 布尔型 默认值 true
        isShowPrePageBtn: true,//是否显示上一页按钮 布尔型 默认值 true
        isShowNextPageBtn: true,//是否显示下一页按钮 布尔型 默认值 true
        isGoPage: true,//是否显示页码跳转输入框 布尔型 默认值 true
        click: function (n) {
            pageNumber = n;
            load(pageNumber, 10);
            this.selectPage(n);//切换选中页码
        }
        , lang: {
            firstPageText: '首页',
            firstPageTipText: '首页',
            lastPageText: '尾页',
            lastPageTipText: '尾页',
            prePageText: '上一页',
            prePageTipText: '上一页',
            nextPageText: '下一页',
            nextPageTipText: '下一页',
            totalPageBeforeText: '共',
            totalPageAfterText: '页',
            currPageBeforeText: '当前第',
            currPageAfterText: '页',
            totalInfoSplitStr: '/',
            totalRecordsBeforeText: '共',
            totalRecordsAfterText: '条数据',
            gopageBeforeText: '&nbsp;转到',
            gopageButtonOkText: '确定',
            gopageAfterText: '页',
            buttonTipBeforeText: '第',
            buttonTipAfterText: '页'
        }
    });
}

function reLoad() {
    load(pageNumber, 10)
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixPark + '/add' // iframe的url
    });
}

// 上传园区室外数据
function addOutdoorData(id) {
    layer.open({
        type: 2,
        title: '上传园区数据',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixPark + '/outdoorAdd/' + id // iframe的url
    })
}
function pointDataAdd() {
    layer.open({
        type: 2,
        title: '上传园区Point',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixPoint + '/ImportPoint/' // iframe的url
    })
}
function parkEdit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixPark + '/edit/' + id // iframe的url
    });
}

function parkRemove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefixPark + "/remove",
            type: "post",
            async: false, // 上传文件为同步
            data: {
                'parkid': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);


                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });

    })
}

// 发布园区服务
function Release(id) {
    $
        .ajax({
            url: prefixPark + "/ReleaseService/" + id,
            type: "get",
            processData: false,
            contentType: false,
            success: function (data) {
                if (data != null) {
                    var outDoorWmsData;
                    var outDoorWfsData;
                    var inDoorWxsData;
                    var centerLon;
                    var centerLat;
                    var parkName = data.parkName;
                    var parkUserName = data.parkUserName;
                    var wmsDataPath = data.parkDirPath + 'outdoor/wms';
                    var wfsDataPath = data.parkDirPath + 'outdoor/wfs';
                    var indoorDataPath = data.parkDirPath + 'indoor';
                    // 发布室外WMS,先判断服务是否存在，存在删除
                    $
                        .ajax({
                            type: 'GET',
                            async: false,
                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/checkServiceExists?serviceName='
                                + parkName
                                + '_WMS'
                                + '&serviceType=WMS',
                            success: function (result) {
                                if (result.data) {
                                    // 删除原来服务
                                    $
                                        .ajax({
                                            type: 'GET',
                                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/deleteService?serviceName='
                                                + parkName
                                                + '_WMS'
                                                + '&serviceType=WMS',
                                            success: function (
                                                result) {
                                                if (result.data) {
                                                    console
                                                        .log(result);
                                                }
                                            }
                                        });
                                }
                            }
                        });
                    // 发布室外WFS,先判断服务是否存在，存在删除
                    $
                        .ajax({
                            type: 'GET',
                            async: false,
                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/checkServiceExists?serviceName='
                                + parkName
                                + '_WFS'
                                + '&serviceType=WFS',
                            success: function (result) {
                                if (result.data) {
                                    // 删除原来服务
                                    $
                                        .ajax({
                                            type: 'GET',
                                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/deleteService?serviceName='
                                                + parkName
                                                + '_WFS'
                                                + '&serviceType=WFS',
                                            success: function (
                                                result) {
                                                if (result.data) {
                                                    console
                                                        .log(result);
                                                }
                                            }
                                        });
                                }
                            }
                        });

                    // 发布室内WMS,先判断服务是否存在，存在删除
                    $
                        .ajax({
                            type: 'GET',
                            async: false,
                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/checkServiceExists?serviceName='
                                + 'gisserver_wms_safepark_'
                                + parkName
                                + '_indoor'
                                + '&serviceType=WMS',
                            success: function (result) {
                                if (result.data) {
                                    // 删除原来服务
                                    $
                                        .ajax({
                                            type: 'GET',
                                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/deleteService?serviceName='
                                                + 'gisserver_wms_safepark_'
                                                + parkName
                                                + '_indoor'
                                                + '&serviceType=WMS',
                                            success: function (
                                                result) {
                                                if (result.data) {

                                                }
                                            }
                                        });
                                }
                            }
                        });
                    // 发布室内WFS,先判断服务是否存在，存在删除
                    $
                        .ajax({
                            type: 'GET',
                            async: false,
                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/checkServiceExists?serviceName='
                                + 'gisserver_wfs_safepark_'
                                + parkName
                                + '_indoor'
                                + '&serviceType=WFS',
                            success: function (result) {
                                if (result.data) {
                                    // 删除原来服务
                                    $
                                        .ajax({
                                            type: 'GET',
                                            url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/deleteService?serviceName='
                                                + 'gisserver_wfs_safepark_'
                                                + parkName
                                                + '_indoor'
                                                + '&serviceType=WFS',
                                            success: function (
                                                result) {
                                                if (result.data) {

                                                }
                                            }
                                        });
                                }
                            }
                        });

                    if (!data.baseMapStatus) {
                        // 发布室外WMS
                        $
                            .ajax({
                                type: 'GET',
                                async: false,
                                url: serverHeader + '/com.cloud.isoft.wxs.service/WMSServices/createServiceByFile?dataPath='
                                    + wmsDataPath
                                    + '&serviceName='
                                    + parkName + '_WMS',
                                success: function (result) {
                                    if (result.resultCode == 1) {
                                        outDoorWmsData = result.data;
                                        // 第一次查询中心点，如果中心点没数据，后面接着查
                                        var serviceName = parkName
                                            + '_WMS';
                                        var param = JSON
                                            .stringify({
                                                "pageNum": 1,
                                                "pageSize": 3,
                                                "condition": {
                                                    "serviceName": serviceName,
                                                    "serviceType": "WMS"
                                                }
                                            });
                                        // 查询中心点
                                        $
                                            .ajax({
                                                type: 'POST',
                                                async: false,
                                                contentType: 'application/json; charset=utf-8',
                                                // dataType :
                                                // 'json',
                                                url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/queryServiceInfo',
                                                data: param,
                                                success: function (
                                                    result) {
                                                    if (result.resultCode == 1) {
                                                        var lonLatStr = result.data.list[0].centerPoint;
                                                        var arr = lonLatStr
                                                            .split(",");
                                                        centerLon = arr[0];
                                                        centerLat = arr[1];
                                                    }
                                                }
                                            });
                                    } else {
                                        layer.msg(result.resultCode);
                                    }
                                }
                            });
                    }

                    if (!data.outlineStatus || !data.poiStatus) {
                        // 发布室外WFS
                        $
                            .ajax({
                                type: 'GET',
                                async: false,
                                url: serverHeader + '/com.cloud.isoft.wxs.service/WFSServices/createServiceByFile?dataPath='
                                    + wfsDataPath
                                    + '&serviceName='
                                    + parkName + '_WFS',
                                success: function (result) {
                                    if (result.resultCode == 1) {
                                        outDoorWfsData = result.data;
                                        // 如果中心点没数据，接着查，如果已经有了，不需要再次查询
                                        if (!centerLon) {
                                            var serviceName = parkName
                                                + '_WFS';
                                            var param = JSON
                                                .stringify({
                                                    "pageNum": 1,
                                                    "pageSize": 3,
                                                    "condition": {
                                                        "serviceName": serviceName,
                                                        "serviceType": "WFS"
                                                    }
                                                });
                                            // 查询中心点
                                            $
                                                .ajax({
                                                    type: 'POST',
                                                    async: false,
                                                    contentType: 'application/json; charset=utf-8',
                                                    // dataType :
                                                    // 'json',
                                                    url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/queryServiceInfo',
                                                    data: param,
                                                    success: function (
                                                        result) {
                                                        if (result.resultCode == 1) {
                                                            var lonLatStr = result.data.list[0].centerPoint;
                                                            var arr = lonLatStr
                                                                .split(",");
                                                            centerLon = arr[0];
                                                            centerLat = arr[1];
                                                        }
                                                    }
                                                });
                                        }

                                    } else {
                                        layer.msg(result.resultCode);
                                    }
                                }
                            });
                    }

                    if (!data.floorDataStatus) {
                        // 发布室内WFS/WMS
                        $.ajax({
                                type: 'GET',
                                async: false,
                                url: serverHeader + '/com.cloud.isoft.wxs.service/SafeParkServiceController/createIndoorServices?dataPath='
                                    + indoorDataPath
                                    + '&parkName='
                                    + parkName
                                    + '_indoor',
                                success: function (result) {
                                    if (result.resultCode == 1) {
                                        inDoorWxsData = result.data;
                                        // 如果中心点没数据，接着查，如果已经有了，不需要再次查询
                                        if (!centerLon) {
                                            var serviceName = "gisserver_wms_safepark_"
                                                + parkName;
                                            var param = JSON
                                                .stringify({
                                                    "pageNum": 1,
                                                    "pageSize": 3,
                                                    "condition": {
                                                        "serviceName": serviceName,
                                                        "serviceType": "WMS"
                                                    }
                                                });
                                            // 查询中心点
                                            $
                                                .ajax({
                                                    type: 'POST',
                                                    async: false,
                                                    contentType: 'application/json; charset=utf-8',
                                                    url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/queryServiceInfo',
                                                    data: param,
                                                    success: function (
                                                        result) {
                                                        if (result.resultCode == 1) {
                                                            var lonLatStr = result.data.list[0].centerPoint;
                                                            var arr = lonLatStr
                                                                .split(",");
                                                            centerLon = arr[0];
                                                            centerLat = arr[1];
                                                        }
                                                    }
                                                });
                                        }
                                        // 如果中心点没数据，接着查，如果已经有了，不需要再次查询
                                        if (!centerLon) {
                                            var serviceName = "gisserver_wfs_safepark_"
                                                + parkName;
                                            var param = JSON
                                                .stringify({
                                                    "pageNum": 1,
                                                    "pageSize": 3,
                                                    "condition": {
                                                        "serviceName": serviceName,
                                                        "serviceType": "WFS"
                                                    }
                                                });
                                            // 查询中心点
                                            $
                                                .ajax({
                                                    type: 'POST',
                                                    async: false,
                                                    contentType: 'application/json; charset=utf-8',
                                                    // dataType :
                                                    // 'json',
                                                    url: serverHeader + '/com.cloud.isoft.wxs.service/serviceManage/queryServiceInfo',
                                                    data: param,
                                                    success: function (
                                                        result) {
                                                        if (result.resultCode == 1) {
                                                            var lonLatStr = result.data.list[0].centerPoint;
                                                            var arr = lonLatStr
                                                                .split(",");
                                                            centerLon = arr[0];
                                                            centerLat = arr[1];
                                                        }
                                                    }
                                                });
                                        }

                                    } else {
                                        layer.msg(result.resultCode);
                                    }
                                }
                            });
                    }

                    var outDoorWmsData = JSON
                        .stringify(outDoorWmsData);
                    var outDoorWfsData = JSON
                        .stringify(outDoorWfsData);
                    var inDoorWxsData = JSON
                        .stringify(inDoorWxsData);
                    var param = JSON
                        .stringify({
                            "lon": centerLon,
                            "lat": centerLat,
                            "parkname": parkName,
                            "parkusername": parkUserName,
                            "indoorservice": inDoorWxsData,
                            "outdoorwmsservice": outDoorWmsData,
                            "outdoorwfsservice": outDoorWfsData
                        });
                    $
                        .ajax({
                            type: 'POST',
                            async: false,
                            contentType: 'application/json; charset=utf-8',
                            url: ctx + 'park/parkService/update',
                            data: param,
                            success: function (
                                result) {
                                if (result.code == 0) {
                                    parent.layer.msg("操作成功"); // 页面提示发布成功
                                }
                            }
                        });
                } else {
                    parent.layer.alert(data.msg)
                }
            }
        });

}

//上传园区室内数据
function addIndoorData(id) {
    layer.open({
        type: 2,
        title: '上传园区数据',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixFloor + '/indoorAdd/' + id // iframe的url
    })
}

function floorAdd(buildId, buildName) {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixFloor + '/add/' + buildId + '/' + buildName // iframe的url
    });
}

function floorEdit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixFloor + '/edit/' + id // iframe的url
    });
}

function floorRemove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefixFloor + "/remove",
            type: "post",
            data: {
                'floorid': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

//添加楼栋
function buildingAdd(id, name) {
    $.ajax({
        url: prefixPark + "/findParkDataStatus/" + id,
        type: "post",
        success: function (flag) {
            if (flag) {
                layer.open({
                    type: 2,
                    title: '增加',
                    maxmin: true,
                    shadeClose: false, // 点击遮罩关闭层
                    area: ['800px', '520px'],
                    content: prefixBuilding + '/add/' + id + '/' + name // iframe的url
                });
            } else {
                layer.msg("请先上传园区内部轮廓数据");
            }
        }
    });

}

function buildingEdit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixBuilding + '/edit/' + id // iframe的url
    });
}

function buildingRemove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefixBuilding + "/remove",
            type: "post",
            data: {
                'buildid': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}