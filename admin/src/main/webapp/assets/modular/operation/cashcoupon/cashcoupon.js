layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 代金券管理--代金券管理
     */
    var MarCashCoupon = {
        tableId: "cashCouponTable",    //表格id
        condition: {
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MarCashCoupon.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '优惠券ID'},
            {field: 'ccName', sort: true, title: '名称'},
            {field: 'ccMoney', sort: true, title: '金额'},
            {field: 'createTime', sort: true, title: '创建日期'},
            {field: 'ccScopeId', sort: true, title: '使用范围'},
            {field: 'grantMode', sort: true, title: '发放方式'},
            {field: 'grantCondition', sort: true, title: '发放条件'},
            {field: 'startTime', sort: true, title: '生效日期'},
            {field: 'pastDueMode', sort: true, title: '失效方式'},
            {field: 'pastDueTime', sort: true, title: '失效日期'},
            {field: 'amountToMark', sort: true, title: '达标金额'},
            {field: 'ccExplain', sort: true, title: '说明'},
            {field: 'ccStatus', sort: true, templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 50}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MarCashCoupon.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MarCashCoupon.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    MarCashCoupon.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加代金券',
            area: '400px',
            offset: '50px',
            content: Feng.ctxPath + '/cashcoupon/cashcoupon_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(MarCashCoupon.tableId);
            }
        });
    };
    /**
     * 点击发放按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    MarCashCoupon.push = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '发放代金券',
            content: Feng.ctxPath + '/cashcoupon/cashcoupon_push?id=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(MarCashCoupon.tableId);
            }
        });
    };
// 修改代金券状态
    form.on('switch(ccStatus)', function (obj) {

        var id = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MarCashCoupon.changeUserStatus(id, checked);
    });

    /**
     * 修改代金券状态
     *
     * @param id 用户id
     * @param checked 是否选中（true,false），选中就是正常，未选中就是停用
     */
    MarCashCoupon.changeUserStatus = function (id, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/cashcoupon/freeze", function (data) {
                Feng.success("开启成功!");
            }, function (data) {
                Feng.error("开启失败!");
                table.reload(MarCashCoupon.tableId);
            });
            ajax.set("id", id);
            ajax.set("checked",'ENABLE');
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/cashcoupon/freeze", function (data) {
                Feng.success("停用成功!");
            }, function (data) {
                Feng.error("停用失败!" + data.responseJSON.message + "!");
                table.reload(MarCashCoupon.tableId);
            });
            ajax.set("id", id);
            ajax.set("checked",'DISABLE');
            ajax.start();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MarCashCoupon.tableId,
        url: Feng.ctxPath + '/cashcoupon/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MarCashCoupon.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MarCashCoupon.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MarCashCoupon.openAddUser();
    });

    // 工具条点击事件
    table.on('tool(' + MarCashCoupon.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'push') {
            MarCashCoupon.push(data);
        }
    });

});
