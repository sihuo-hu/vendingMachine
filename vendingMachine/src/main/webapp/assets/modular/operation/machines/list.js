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
    var Machines = {
        tableId: "machinesTable",    //表格id
        condition: {
            machinesId: "",
            machinesName: "",
            createdAt: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Machines.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'machinesId', title: '设备编号'},
            {field: 'machinesName', title: '设备名称'},
            {field: 'lat', title: '经度'},
            {field: 'lgn', title: '纬度'},
            {field: 'address',title: '地址'},
            {field: 'createdAt', title: '添加时间'},
            {field: 'updateTime', title: '更新时间'},
            {field: 'onlineStatus', templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 120}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Machines.search = function () {
        var queryData = {};
        queryData['machinesId'] = Machines.condition.machinesId;
        queryData['machinesName'] = $("#machinesName").val();
        queryData['createdAt'] = $("#createdAt").val();
        table.reload(Machines.tableId, {where: queryData});
    };

    Machines.stratRefresh = function () {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/machines/refresh", function () {
                table.reload(Machines.tableId);
                Feng.success("同步成功!");
            }, function (data) {
                Feng.error("同步失败!" + data.responseJSON.message + "!");
            });
            ajax.start();
        };
        Feng.confirm("是否开始从远端开始同步设备信息?", operation);
    };


    /**
     * 修改设备状态
     *
     * @param machinesId 设备id
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    Machines.changeUserStatus = function (machinesId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/machines/unfreeze", function (data) {
                Feng.success("上线成功!");
            }, function (data) {
                Feng.error("上线失败!");
                table.reload(Machines.tableId);
            });
            ajax.set("machinesId", machinesId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/machines/freeze", function (data) {
                Feng.success("下线成功!");
            }, function (data) {
                Feng.error("下线失败!" + data.responseJSON.message + "!");
                table.reload(Machines.tableId);
            });
            ajax.set("machinesId", machinesId);
            ajax.start();
        }
    };

    /**
     * 弹出添加用户对话框
     */
    Machines.onGetFloor = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '设置商品',
            area: ['1120px','650px'],
            content: Feng.ctxPath + '/machines/to_get_goods?machinesId='+data.machinesId,
            end: function () {
                admin.getTempData('formOk');
            }
        });
    };

    /**
     * 弹出添加用户对话框
     */
    Machines.onBindingFloor = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '商品分组',
            area: ['900px','350px'],
            content: Feng.ctxPath + '/machines/to_spfz?machinesId='+data.machinesId,
            end: function () {
                admin.getTempData('formOk');
            }
        });
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Machines.tableId,
        url: Feng.ctxPath + '/machines/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Machines.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#createdAt',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Machines.search();
    });

    // 添加按钮点击事件
    $('#btnRefresh').click(function () {
        Machines.stratRefresh();
    });

    // 修改user状态
    form.on('switch(status)', function (obj) {

        var machinesId = obj.elem.value;
        var checked = obj.elem.checked ;

        Machines.changeUserStatus(machinesId, checked);
    });

    // 工具条点击事件
    table.on('tool(' + Machines.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'getFloor') {
            Machines.onGetFloor(data);
        }
    });

});
