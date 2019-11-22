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
    var FloorOkList = {
        tableId: "floorOkTable"
    };

    /**
     * 初始化表格的列
     */
    FloorOkList.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'floorId', title: '货道ID'},
            {field: 'baseId', hide: true, title: '商品分组ID'},
            {field: 'displayCode', title: '货道编号'},
            {field: 'floorStatus', title: '货道状态'},
            {field: 'commodityName', title: '商品名称'},
            {field: 'stock', title: '库存'},
            {field: 'grade', title: '积分'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + FloorOkList.tableId,
        url: Feng.ctxPath + '/machines/getFloorFz?id=' + Feng.getUrlParam("id"),
        page: false,
        height: "full-90",
        cellMinWidth: 100,
        cols: FloorOkList.initColumn(),
        done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            console.log(res);
            for (var i = 0; i < res.data.length; i++) {
                if (res.data[i].spfzId) {
                    res.data[i]["LAY_CHECKED"] = 'true';
                    //下面三句是通过更改css来实现选中的效果
                    var index = res.data[i]['LAY_TABLE_INDEX'];
                    $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                    $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                }
            }
            //得到当前页码
            console.log(curr);

            //得到数据总量
            console.log(count);
        }
    });


    /**
     * 点击确认按钮
     *
     * @param data 点击按钮时候的行数据
     */
    FloorOkList.onBindingFloor = function (data) {
        var ajax = new $ax(Feng.ctxPath + "/machines/bindingFloor", function () {
            admin.putTempData('formOk', true);
            Feng.success("绑定成功!");
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("绑定失败!" + data.responseJSON.message + "!");
        });
        var checkRows = table.checkStatus(FloorOkList.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要绑定的数据");
        } else {
            var names = "";
            var displayCodes = "";
            var spfzId;
            for(var i =0;i<checkRows.data.length;i++){
                names=names+checkRows.data[i].commodityName+",";
                displayCodes=displayCodes+checkRows.data[i].displayCode+",";
                spfzId = checkRows.data[i].baseId;
            }
            console.log(checkRows);
            ajax.set("displayCodes", displayCodes);
            ajax.set("names", names);
            ajax.set("spfzId",spfzId);
            ajax.start();
        }
    };

    // 搜索按钮点击事件
    $('#binding').click(function () {
        FloorOkList.onBindingFloor();
    });

    // 添加按钮点击事件
    $('#btnRefresh').click(function () {
        FloorOkList.stratRefresh();
    });


});
