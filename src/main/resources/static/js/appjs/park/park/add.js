var provinceObj = $("#province");
var cityObj = $("#city");
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
    console.log(flag);
    if (flag) {
        $.ajax({
            cache: true,
            type: "POST",
            url: ctx + "park/park/save",
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

jQuery.validator.addMethod("isShi", function (value, element) {
    return this.optional(element) || ("请选择--" != value);
}, "请选择省/市");

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    validator = $("#signupForm").validate({
        rules: {
            parkname: {
                required: true
            },
            parkarea: {
                required: true,
                number: true
            },
            province: {
                isShi: true
            },
            city: {
                isShi: true
            }
        },
        messages: {
            parkname: {
                required: icon + "请输入园区名"
            },
            parkarea: {
                required: icon + "请输入园区面积",
                number: icon + "园区面积格式不正确"
            }
        }
    })
}

