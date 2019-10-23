/**
 * 用户详情对话框
 */
layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var grantCondition = 'NEW_CUSTOMER';


    var ajax = new $ax(Feng.ctxPath + "/cashcoupon/getCheckbox");
    var result = ajax.start();
    console.log(result.data);
    $.each(result.data, function(index,item) {
        $('#ccScopeIds').append('<input type="checkbox" name="ccScopeId" value="'+item.symbolCode+'" lay-skin="primary" title="'+item.symbolName+'">');
    });
    form.render();
// 让当前iframe弹层高度适应
    admin.iframeAuto();

    form.on('radio(grantMode)', function(data){
        if(data.value=="MANUAL"){
            $('#startTime_div').css("display","block");
            $('#grantCondition').css("display","none");
            $('#pastDueMode1').click();
            $('#pastDueMode1').removeAttr('disabled');
            form.render('radio');
        }else{
            $('#startTime_div').css("display","none");
            $('#grantCondition').css("display","block");
            $('#pastDueMode2').click();
            $('#pastDueMode1').attr('disabled','true');
            $('#pastDueTime_div').css("display","none");
            $('#pastDueTime_or_div').css("display","none");
            form.render('radio');
        }
    });

    form.on('radio(pastDueMode)', function(data){
        if(data.value=='UNIFY'){
            $('#pastDueTime_div').css("display","block");
            $('#pastDueTime_or_div').css("display","block");
        }else{
            $('#pastDueTime_div').css("display","none");
            $('#pastDueTime_or_div').css("display","none");
        }
    });

    form.on('select(grantCondition)', function(data){
        grantCondition = data.value;
        if(data.value=="FIRST_RECHARGE"){
            $('#amountToMark').css("display","block");
        }else{
            $('#amountToMark').css("display","none");
        }
    });

    // 添加表单验证方法
    form.verify({
        amountToMark: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(grantCondition=="FIRST_RECHARGE"){
                if(value==''||value==0) {
                    return '达标金额不可为空';
                }
            }
        }
    });

    // 渲染时间选择框
    laydate.render({
        type: 'datetime',
        elem: '#startTime'
    });
    laydate.render({
        type: 'datetime',
        elem: '#pastDueTime'
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var arr = new Array();
        console.log( $("input:checkbox[name='ccScopeId']:checked"));
        $("input:checkbox[name='ccScopeId']:checked").each(function(i){
            arr[i] = $(this).val();
        });
        data.field.ccScopeId = arr.join(",");//将数组合并成字符串
        var ajax = new $ax(Feng.ctxPath + "/cashcoupon/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });


});