layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    form.on('radio(targetPopulation)', function (data) {
        if (data.value == "ALL_USER") {
            $('#loginNames_div').css("display", "none");
            admin.iframeAuto();
        } else {
            $('#loginNames_div').css("display", "block");
            admin.iframeAuto();
        }
    });

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/cashcoupon/getCashcoupon?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('cashcouponPushForm', result.data);

    if(result.data.grantMode=="MANUAL"&&result.data.startTime==''){
        $('#startTime2').css("display","block");
        $('#startTime1').css("display","none");
    }






    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/cashcoupon/push", function (data) {
            Feng.success("发放成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("发放失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });

    laydate.render({
        type: 'datetime',
        elem: '#startTime_input2'
    });
});