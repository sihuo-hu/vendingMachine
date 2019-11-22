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
    var MachinesList = {
        tableId: "machinesListTable"
    };

    /**
     * 初始化表格的列
     */
    MachinesList.initColumn = function () {
        return [[
            {field: 'mountingId', hide: true,title: '跳舞机ID'},
            {field: 'machinesId',hide: true, title: '售货机ID'},
            {field: 'machinesName', title: '售货机名称'},
            {field: 'address', title: '售货机地址'},
            {field: 'createdAt', title: '售货机创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 80}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MachinesList.tableId,
        url: Feng.ctxPath + '/mountings/getMachinesList?id='+Feng.getUrlParam("id"),
        page: false,
        height: "full-30",
        cellMinWidth: 100,
        cols: MachinesList.initColumn()
    });

    /**
     * 点击确认按钮
     *
     * @param data 点击按钮时候的行数据
     */
    MachinesList.onBindingMachines = function (data) {
            var ajax = new $ax(Feng.ctxPath + "/mountings/binding", function () {
                // var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                // parent.layer.close(index); //再执行关闭
                admin.putTempData('formOk', true);
                Feng.success("绑定成功!");
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("绑定失败!" + data.responseJSON.message + "!");
            });
            ajax.set("mountingId", data.mountingId);
            ajax.set("machinesId", data.machinesId);
            ajax.start();
    };


    // 工具条点击事件
    table.on('tool(' + MachinesList.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'bindingMachines') {
            MachinesList.onBindingMachines(data);
        }
    });

});
