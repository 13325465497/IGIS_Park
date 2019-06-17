// 导航规划的点击事件
var nav = true;
$('#navigation').on('click', function () {
    // delData();
    if (nav) {
        $('.navigation-list').show();
        nav = !nav;
    } else {
        $('.navigation-list').hide();
        nav = !nav;
    }
})
var manage = true;
$('#facilitieManage').on('click', function () {
    if (manage) {
        $('.facilities_type').show();
        manage = !manage;
    } else {
        $('.facilities_type').hide();
        manage = !manage;
    }
})
