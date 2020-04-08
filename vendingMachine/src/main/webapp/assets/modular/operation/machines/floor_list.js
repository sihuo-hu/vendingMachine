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
    var FloorList = {
        tableId: "floorTable"
    };

    /**
     * 初始化表格的列
     */
    FloorList.initColumn = function () {
        return [[
            // {field: 'floorId', title: '货道ID'},
            {field: 'displayCode', title: '货道编号'},
            {field: 'floorStatus', title: '货道状态'},
            {field: 'goodsId', hide: true, title: '商品ID'},
            {field: 'commodityName', title: '商品名称'},
            {field: 'floorName', edit: 'text', minWidth: '155', title: '货道名称(单击修改)'},
            {field: 'stock', edit: 'text', title: '库存(单击修改)'},
            {field: 'grade', edit: 'text',title: '积分(单击修改)'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 80}
        ]];
    };

    /**
     * 弹出商品选择对话框
     */
    FloorList.onSetGoods = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '商品选择',
            area: ['400px', '650px'],
            content: Feng.ctxPath + '/machines/to_set_goods?floorId=' + data.floorId,
            end: function () {
                admin.getTempData('formOk') && table.reload(FloorList.tableId);
            }
        });
    };
// 渲染表格
    var tableResult = table.render({
        elem: '#' + FloorList.tableId,
        url: Feng.ctxPath + '/machines/getFloor?machinesId=' + Feng.getUrlParam("machinesId"),
        page: false,
        height: "full-30",
        cellMinWidth: 100,
        cols: FloorList.initColumn()
    });

    FloorList.onClearGoods = function (data) {
        var ajax = new $ax(Feng.ctxPath + "/machines/clearGoods", function () {
            tableResult.reload();
            Feng.success("清除成功!");
        }, function (data) {
            Feng.error("清除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("floorId", data.floorId);
        ajax.start();
    };

    table.on('edit(floorTable)', function (obj) { //注：edit是固定事件名，floorTable是table原始容器的属性 lay-filter="对应的值"
        var ajax = new $ax(Feng.ctxPath + "/machines/setGradeOrStock", function () {
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set("floorId", obj.data.floorId);
        ajax.set("grade", obj.data.grade);
        ajax.set("stock", obj.data.stock);
        ajax.set("floorName", obj.data.floorName);
        ajax.start();
    });




    //渲染时间选择框
    laydate.render({
        elem: '#createdAt',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        FloorList.search();
    });

    // 添加按钮点击事件
    $('#btnRefresh').click(function () {
        FloorList.stratRefresh();
    });


    // 工具条点击事件
    table.on('tool(' + FloorList.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'setGoods') {
            FloorList.onSetGoods(data);
        }
        if (layEvent === 'clearGoods') {
            FloorList.onClearGoods(data);
        }
    });

});
