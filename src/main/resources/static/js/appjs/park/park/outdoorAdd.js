var prefix = ctx + "/park/park";
var navigationHeard='http://' + ipPort + '/com.cloud.isoft.graphhopperreadershp.service'; //导航head
//上传园区室外数据
function addDataSubmit(sender) {
    var formData = new FormData($("#outdoorAdd")[0]);
    if (wmsData) {
        if (reghionData && poiData && walkData && dirveData) {
            $.ajax({
                url: prefix + "/outdoorAddData",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code == 0) {
                        parent.layer.msg("操作成功");
                        var parkName = data.parkName;
                        parent.reLoad();
                        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(index);
                        if(dirveDataStatus){
                            $.ajax({
                                url: navigationHeard + "/initDriveHopper?parkName="+parkName,
                                type: "post",
                                success: function (data) {
                                    console.log("园区室外车导数据解析 : "+data)
                                }
                            });
                        }
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        } else {
            parent.layer.msg("请上传Zip格式的数据");
        }
    } else {
        parent.layer.msg("请上传室外WMS数据");
    }
}

var wmsData = false;
var reghionData = true;
var poiData = true;
var walkData = true;
var dirveData = true;
var dirveDataStatus=false;
$(function () {
    $("#wmsData").change(function () {
        var text = $("#wmsData").val();
        if (text) {
            $("#wmsData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                wmsData = true;
            }
        } else {
            wmsData = false;
            $("#wmsData_a").html("请上传Zip格式数据");
        }
    })
    $("#reghionData").change(function () {
        var text = $("#reghionData").val();
        if (text) {
            $("#reghionData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                reghionData = true;
            } else {
                reghionData = false;
            }
        } else {
            $("#reghionData_a").html("请上传Zip格式数据");
        }
    })
    $("#poiData").change(function () {
        var text = $("#poiData").val();
        if (text) {
            $("#poiData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                poiData = true;
            } else {
                poiData = false;
            }
        } else {
            $("#poiData_a").html("请上传Zip格式数据");
        }
    })
    $("#walkData").change(function () {
        var text = $("#walkData").val();
        if (text) {
            $("#walkData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                walkData = true;
            } else {
                walkData = false;
            }
        } else {
            $("#walkData_a").html("请上传Zip格式数据");
        }
    })
    $("#dirveData").change(function () {
        var text = $("#dirveData").val();
        if (text) {
            $("#dirveData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                dirveData = true;
                dirveDataStatus=true;
            } else {
                dirveData = false;
                dirveDataStatus=false;
            }
        } else {
            $("#dirveData_a").html("请上传Zip格式数据");
        }
    })
});