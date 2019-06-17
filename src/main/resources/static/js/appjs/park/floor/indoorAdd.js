var prefix = ctx + "park/floor";

//上传园区室外数据
function addDataSubmit() {
    var formData = new FormData($("#indoorAdd")[0]);
    if (poiData && regionData && subRegionData && floorData) {
        $.ajax({
            url: prefix + "/indoorAddData",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("上传成功");
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    parent.layer.alert(data.msg)
                }
            }
        });
    } else {//请上传必须文件
        parent.layer.msg("请上传正确格式的室内数据");
    }

}

/**
 * 修改上传文件样式 / 并校验
 */
var poiData = false;
var regionData = false;
var subRegionData = false;
var floorData = false;
$(function () {
    $("#poiData").change(function () {
        var text = $("#poiData").val();
        if (text) {
            $("#poiData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                poiData = true;
            }
        } else {
            poiData = false;
            $("#poiData_a").html("请上传Zip格式数据");
        }
    })
    $("#regionData").change(function () {
        var text = $("#regionData").val();
        if (text) {
            $("#regionData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                regionData = true;
            }
        } else {
            regionData = false;
            $("#regionData_a").html("请上传Zip格式数据");
        }
    })
    $("#subRegionData").change(function () {
        var text = $("#subRegionData").val();
        if (text) {
            $("#subRegionData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                subRegionData = true;
            }
        } else {
            subRegionData = false;
            $("#subRegionData_a").html("请上传Zip格式数据");
        }
    })
    $("#floorData").change(function () {
        var text = $("#floorData").val();
        if (text) {
            $("#floorData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".zip".toLowerCase()) {
                floorData = true;
            }
        } else {
            floorData = false;
            $("#floorData_a").html("请上传Zip格式数据");
        }
    })
    $("#linkData").change(function () {
        var text = $("#linkData").val();
        if (text) {
            $("#linkData_a").html(text);
        } else {
            $("#linkData_a").html("请上传Zip格式数据");
        }
    })
});