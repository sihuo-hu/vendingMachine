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
    var Mountings = {
        tableId: "mountingsTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Mountings.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id',hide: true, title: '跳舞机ID'},
            {field: 'code', title: '设备编号'},
            {field: 'name', title: '设备名称'},
            {field: 'address', title: '地址'},
            {field: 'machinesId',hide: true,title: '售货机ID'},
            {field: 'machinesName',title: '售货机名称'},
            {field: 'createTime', title: '添加时间'},
            {field: 'updateTime', title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 220}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Mountings.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        queryData['name'] = $("#name").val();
        table.reload(Mountings.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    Mountings.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加跳舞机',
            content: Feng.ctxPath + '/mountings/to_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Mountings.tableId);
            }
        });
    };
    /**
     * 弹出添加用户对话框
     */
    Mountings.openEditUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改跳舞机',
            content: Feng.ctxPath + '/mountings/to_edit',
            end: function () {
                admin.getTempData('formOk') && table.reload(Mountings.tableId);
            }
        });
    };
    /**
     * 点击删除用户按钮
     *
     * @param data 点击按钮时候的行数据
     */
    Mountings.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/mountings/delete", function () {
                table.reload(Mountings.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.userId);
            ajax.start();
        };
        Feng.confirm("是否删除跳舞机" + data.name + "?", operation);
    };


    /**
     * 弹出绑定商品对话框
     */
    Mountings.openBinding = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '绑定跳舞机',
            area: ['650px','650px'],
            content: Feng.ctxPath + '/mountings/to_binding?id='+data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(Mountings.tableId);
            }
        });
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Mountings.tableId,
        url: Feng.ctxPath + '/mountings/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Mountings.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Mountings.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Mountings.openAddUser();
    });

    // 工具条点击事件
    table.on('tool(' + Mountings.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Mountings.openEditUser(data);
        }
        if (layEvent === 'delete') {
            Mountings.onDeleteUser(data);
        }
        if (layEvent === 'binding') {
            Mountings.openBinding(data);
        }
    });

});
