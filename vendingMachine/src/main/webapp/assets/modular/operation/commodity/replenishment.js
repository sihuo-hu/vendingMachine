layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 补货记录
     */
    var replenishment = {
        tableId: "replenishmentTable",    //表格id
        condition: {
            status: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    replenishment.initColumn = function () {
        return [[
            {field: 'b_id', hide: true, title: 'ID'},
            {field: 'status', title: '类别'},
            {field: 'goodsName', title: '商品'},
            {field: 'number', title: '数量'},
            {field: 'floorCode', title: '货道编码'},
            {field: 'machinesName', title: '设备名称'},
            {field: 'createTime', title: '创建时间'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    replenishment.search = function () {
        var queryData = {};
        queryData['status'] = $("#status").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(replenishment.tableId, {where: queryData});
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + replenishment.tableId,
        url: Feng.ctxPath + '/commodity/replenishment/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: replenishment.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        replenishment.search();
    });



});
