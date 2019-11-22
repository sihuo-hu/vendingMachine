layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 系统管理--用户管理
     */
    var GoodsList = {
        tableId: "goodsTable"
    };

    /**
     * 初始化表格的列
     */
    GoodsList.initColumn = function () {
        return [[
            {field: 'floorId', hide: true,title: '通道ID'},
            {field: 'commodityId',hide: true, title: '商品ID'},
            {field: 'commodityName', title: '商品名称'},
            {field: 'commodityImg', title: '商品图片'},
            {field: 'grade',hide: true, title: '所需分数'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 80}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + GoodsList.tableId,
        url: Feng.ctxPath + '/machines/getGoodsList?floorId='+Feng.getUrlParam("floorId"),
        page: false,
        height: "full-30",
        cellMinWidth: 100,
        cols: GoodsList.initColumn()
    });

    /**
     * 点击确认按钮
     *
     * @param data 点击按钮时候的行数据
     */
    GoodsList.onBindingGoods = function (data) {
            var ajax = new $ax(Feng.ctxPath + "/machines/setGoods", function () {
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
                admin.putTempData('formOk', true);
                Feng.success("绑定成功!");
            }, function (data) {
                Feng.error("绑定失败!" + data.responseJSON.message + "!");
            });
            ajax.set("floorId", data.floorId);
            ajax.set("commodityId", data.commodityId);
            ajax.start();
    };


    // 工具条点击事件
    table.on('tool(' + GoodsList.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'bindingGoods') {
            GoodsList.onBindingGoods(data);
        }
    });

});
