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
    var Order = {
        tableId: "orderTable",    //表格id
        condition: {
            commodityName: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Order.initColumn = function () {
        return [[
            {field: 'id', hide: true, title: 'ID'},
            {field: 'userId', title: '手机号'},
            {field: 'commodityName', title: '商品名称'},
            {field: 'status', title: '状态'},
            {field: 'grade', title: '得分'},
            {field: 'machinesName', title: '售货机'},
            {field: 'name', title: '跳舞机'},
            {field: 'deliverNote', title: '说明'},
            {field: 'orderNo', title: '订单号'},
            {field: 'createTime', title: '创建时间'},
            {field: 'updateTime', title: '状态更新时间'}
        ]]
    };

    /**
     * 点击查询按钮
     */
    Order.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(Order.tableId, {where: queryData});
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Order.tableId,
        url: Feng.ctxPath + '/order/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Order.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Order.search();
    });


});
