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
    var Commodity = {
        tableId: "commodityTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Commodity.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'commodityId', hide: true, title: '商品ID'},
            {field: 'commodityName', title: '商品名称'},
            {field: 'commodityImg', templet: '#goodsImgUrl', title: '图片'},
            {field: 'grade', title: '所需最低积分'},
            {field: 'createTime', title: '创建时间'},
            {field: 'updateTime', title: '更新时间'},
            {field: 'commodityStatus', templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 120}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Commodity.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(Commodity.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    Commodity.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加商品',
            area: '700px',
            content: Feng.ctxPath + '/commodity/to_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Commodity.tableId);
            }
        });
    };


    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Commodity.onEditUser = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: '700px',
            title: '编辑商品',
            content: Feng.ctxPath + '/commodity/to_edit?commodityId=' + data.commodityId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Commodity.tableId);
            }
        });
    };

    /**
     * 修改商品状态
     *
     * @param commodityId 商品id
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    Commodity.changeUserStatus = function (commodityId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/commodity/unfreeze", function (data) {
                Feng.success("上架成功!");
            }, function (data) {
                Feng.error("上架失败!");
                table.reload(Commodity.tableId);
            });
            ajax.set("commodityId", commodityId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/commodity/freeze", function (data) {
                Feng.success("下架成功!");
            }, function (data) {
                Feng.error("下架失败!" + data.responseJSON.message + "!");
                table.reload(Commodity.tableId);
            });
            ajax.set("commodityId", commodityId);
            ajax.start();
        }
    };

// 修改商品状态
    form.on('switch(status)', function (obj) {

        var machinesId = obj.elem.value;
        var checked = obj.elem.checked;

        Commodity.changeUserStatus(machinesId, checked);
    });

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Commodity.tableId,
        url: Feng.ctxPath + '/commodity/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Commodity.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Commodity.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Commodity.openAddUser();
    });


    // 工具条点击事件
    table.on('tool(' + Commodity.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Commodity.onEditUser(data);
        }
    });


});
