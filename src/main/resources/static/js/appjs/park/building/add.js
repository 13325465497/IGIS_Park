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
        if ($("#buildname").val() == "您上传的园区内部轮廓数据有误") {
            parent.layer.msg("楼栋名称格式错误");
        } else {
            $.ajax({
                cache: true,
                type: "POST",
                url: ctx + "park/building/save",
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

}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    validator = $("#signupForm").validate({
        rules: {
            buildname: {
                required: true
            },
            buildarea: {
                required: true,
                number: true
            }
        },
        messages: {
            buildname: {
                required: icon + "请输入楼栋名称"
            },
            buildarea: {
                required: icon + "请输入楼栋面积",
                number: icon + "楼栋面积格式不正确"
            }
        }
    })
}