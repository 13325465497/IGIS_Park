$().ready(function () {
    validateRule();
});
var validator;
$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});

function save() {
    var flag = validator.form();
    if (flag) {
        $.ajax({
            cache: true,
            type: "POST",
            url: ctx + "park/floor/save",
            data: $('#signupForm').serialize(),// 你的formid
            async: false,
            error: function (request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功");
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);

                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });
    }

}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    validator = $("#signupForm").validate({
        rules: {
            floorname: {
                required: true
            },
            floorarea: {
                required: true,
                number: true
            },
            floornum: {
                required: true,
                number: true
            }
        },
        messages: {
            floorname: {
                required: icon + "请输入楼层名称"
            },
            floorarea: {
                required: icon + "请输入楼层面积",
                number: icon + "楼层面积格式不正确"
            },
            floornum: {
                required: icon + "请输入楼层编号",
                number: icon + "楼层标号格式不正确"
            }
        }
    })
}

//选择园区楼栋实现二级联动
function selectBuilding() {
    // 选中园区
    var parkid = $("#parkid").val();
    $("#buildid").find("option:not(:first)").remove();
    $.ajax({
        url: ctx + "park/floor/getBuilding",
        async: false,
        cache: false,
        data: {
            "parkid": parkid
        },
        success: function (data) {
            var obj = JSON.parse(data.msg);
            var buildStr = "";
            for (var i in obj) {
                var key = obj[i].buildid;
                var value = obj[i].buildname;
                buildStr += '<option value="' + key + '">' + value + '</option>';
            }
            //插件原因 需重绘UI
            $("#buildid").html("");
            $('#buildid').append(buildStr);
            $('#buildid').selectpicker('refresh');
        }
    })
}
//是否出入口楼层
function iSGroundFloor(obj)  {
    if (obj == 1) {//是出入口楼层
        $("#floorname").val("GroundFloor");
        $("#floorname").attr('readonly',"readonly");
    }else if (obj == 2) {//no 出入口楼层
        $("#floorname").removeAttr("readonly");
        $("#floorname").val("");
    }
};
