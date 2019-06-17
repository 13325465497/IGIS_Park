var prefix = ctx + "/point";
//上传园区室外数据
function addDataSubmit() {
    var formData = new FormData($("#pointDataAdd")[0]);
        if (pointData) {
            $.ajax({
                url: prefix + "/ImportPoint",
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
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        } else {
            parent.layer.msg("请上传Excel/csv格式数据");
        }
    }

var pointData = false;
$(function () {
    $("#pointData").change(function () {
        var text = $("#pointData").val();
        if (text) {
            $("#pointData_a").html(text);
            var is = text.substring(text.lastIndexOf("."));
            if (is.toLowerCase() == ".xls".toLowerCase()||is.toLowerCase() == ".xlsx".toLowerCase()||is.toLowerCase() == ".csv".toLowerCase()) {
                pointData = true;
            }else {
                pointData = false;
            }
        } else {
            pointData = false;
            $("#pointData_a").html("请上传Excel/csv格式数据");
        }
    })
});