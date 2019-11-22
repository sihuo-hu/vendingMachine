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
        tableId: "spfzTable"
    };

    /**
     * 初始化表格的列
     */
    FloorList.initColumn = function () {
        return [[
            {field: 'id',hide: true, title: '组号'},
            {field: 'difficulty', title: '难度'},
            {field: 'floor_codes', minWidth: 300, title: '货道编码'},
            {field: 'goods_names', minWidth: 300,title: '商品名称'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 60}
        ]];
    };

    /**
     * 弹出商品选择对话框
     */
    FloorList.onBindingFloor = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '货道选择',
            area: ['700px','700px'],
            content: Feng.ctxPath + '/machines/to_set_floor?id='+ data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(FloorList.tableId);
            }
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + FloorList.tableId,
        url: Feng.ctxPath + '/machines/getSpfz?machinesId='+Feng.getUrlParam("machinesId"),
        page: false,
        height: "full-30",
        cellMinWidth: 100,
        cols: FloorList.initColumn()
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

        if (layEvent === 'bindingFloor') {
            FloorList.onBindingFloor(data);
        }
    });

});
