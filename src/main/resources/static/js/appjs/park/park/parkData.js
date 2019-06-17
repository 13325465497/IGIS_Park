var prefixPark = ctx + "park/park";
var prefixBuilding = ctx + "park/building";
var prefixFloor = ctx + "park/floor";
var serverHeader = 'http://' + ipPort + ''; //发布服务head头
$(function() {
    load(1, 10);
});
var load = function(pageNum, pageSize) {
    $('#exampleTable').bootstrapParkTreeTable({
        id: 'id',
        code: 'id',
        parentCode: 'parentId',
        type: "GET", // 请求数据的ajax类型
        url: prefixPark + '/listTree', // 请求数据的ajax的url
        ajaxParams: {
            pageNum: pageNum,
            pageSize: pageSize,
            parkName: $("#searchName").val()
        }, // 请求数据的ajax的data属性
        expandColumn: '1', // 在哪一列上面显示展开按钮
        striped: true, // 是否各行渐变色
        bordered: true, // 是否显示边框
        expandAll: false, // 是否全部展开
        // toolbar : '#exampleToolbar',
        columns: [{
            title: 'ID',
            field: 'id',
            visible: false,
            align: 'center',
            valign: 'center',
            width: '5%'
        }, {
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
        }, {
            title: '类型',
            field: 'type',
            /*  align: 'center',*/
            valign: 'center',
            width: '20%',
            formatter: function(item, index) {
                if(item.type === 0) {
                    return '<span class="label label-primary">园区</span>';
                }
                if(item.type === 1) {
                    return '<span class="label label-success">楼栋</span>';
                }
                if(item.type === 2) {
                    return '<span class="label label-warning">楼层</span>';
                }
            }
        }, {
            title: '操作',
            field: 'id',
            align: 'center',
            formatter: function(row, index) {
            	if(row.type == 1) return false;
                //楼层
                return '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" mce_href="#" title="图层编辑" onclick="layerEdit(\'' + row.id + '\',\'' + row.type + '\')"><i class="fa fa-edit"></i></a> ';
            }
        }],
    });
}

//编辑图层
function layerEdit(id, type) {
	window.sessionStorage.setItem('ParkType', type);
    $.ajax({
        cache: true,
        type: "post",
        url: ctx + "park/parkService/getFileName/" + type + "/" + id,
        async: false,
        success: function(data) {
            if(type == 0) { //获取园区文件名
                var parkFileName = data.parkFileName;
                var body = {
                    'parkFileName': parkFileName
                }
                console.log(type, "parkFileName : ", body);
                getServicePoi(body, type);
            } else if(type == 2) { //获取园区,楼栋,楼层,文件名
                var parkFileName = data.parkFileName;
                var buildFileName = data.buildFileName;
                var floorFileName = data.floorFileName;
                var body = {
                    'parkFileName': parkFileName,
                    'buildFileName': buildFileName,
                    'floorFileName': floorFileName
                }
                console.log(type, "parkFileName : ", body);
                getServicePoi(body, type);
            }
        }
    });
}

// 缓存数据
function session(body, res, type) {
	var state = {Type: type}
	var objNew = Object.assign(body, res, state);
	sessionStorage.setItem('getParkData', JSON.stringify(objNew));
	return objNew;
}

// 判断显示图层poi
function getServicePoi(body, type) {
	var resultUrl = null;
    if(type == 0) resultUrl = ctx + 'edit/getParkData?parkName=' + body.parkFileName;
    else if(type == 2) resultUrl = ctx + 'edit/getFloorData?parkName=' + body.parkFileName + '&buildName=' + body.buildFileName + '&floorName=' + body.floorFileName;
    
    
    $.ajax({
        url: resultUrl,
        async: false,
        type: "GET",
        success: function( data){
            if(data.poiDataUrl && data.regionDataUrl) igisEdit(session(body, data, type));
            else layer.msg('缺少region 或 Poi');
        },
        error: function(error) {
        	console.log(error);
        }
    });
    
    //$.get(resultUrl, function(res) {
    //    console.log('显示图层（poi）或者（region）:', res);
    //    if(res.poiDataUrl && res.regionDataUrl) igisEdit(session(body, res, type));
    //    else layer.msg('缺少region 或 Poi');
    //})
}

//图层编辑
function igisEdit(res) {
    var options = layer.open({
        type: 2,
        title: '图层编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefixPark + '/igisEdit' // iframe的url
    });
    layer.full(options)
}

/**
 *
 * @param pageCurrent 当前所在页
 * @param pageSum 总页数
 * @param total 总记录数
 */
var pageNumber; //记录当前第几页
var setPage = function(pageNum, pages, total) {
    //生成分页
    //有些参数是可选的，比如lang，若不传有默认值
    kkpager.generPageHtml({
        pno: pageNum,
        //总页码
        total: pages,
        //总数据条数
        totalRecords: total,
        mode: 'click', //这里设置为click模式
        isShowTotalPage: true, //是否显示总页数 布尔型 默认值 true
        isShowTotalRecords: true, //是否显示总记录数 布尔型 默认值 true
        isShowFirstPageBtn: true, //是否显示首页按钮 布尔型 默认值 true
        isShowLastPageBtn: true, //是否显示尾页按钮 布尔型 默认值 true
        isShowPrePageBtn: true, //是否显示上一页按钮 布尔型 默认值 true
        isShowNextPageBtn: true, //是否显示下一页按钮 布尔型 默认值 true
        isGoPage: true, //是否显示页码跳转输入框 布尔型 默认值 true
        click: function(n) {
            pageNumber = n;
            load(pageNumber, 10);
            this.selectPage(n); //切换选中页码
        },
        lang: {
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