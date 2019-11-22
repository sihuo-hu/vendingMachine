

var uploadUrl = 'http://localhost:8082/vendingMachine/file/upload';

layui.use(['layer', 'form', 'admin', 'ax','upload','layedit'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;
    var layedit = layui.layedit;

    layedit.set({
        uploadImage: {
            url: uploadUrl //接口url
            ,type: 'post' //默认post
        }
    });
    var cdIndex = layedit.build('commodityDesc');

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/commodity/getCommodity?commodityId=" + Feng.getUrlParam("commodityId"));
    var result = ajax.start();
    form.val('commodityForm', result.data);
    $('#uploadImg').attr('src', result.data.commodityImg);
    layedit.setContent(cdIndex, result.data.commodityDesc);

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/commodity/edit", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败！" + data.responseJSON.message)
        });
        console.log(layedit.getContent(cdIndex));
        data.field.commodityDesc=layedit.getContent(cdIndex);
        ajax.set(data.field);
        ajax.start();
    });

    var uploadInst = upload.render({
        elem: '#upload'
        ,data: {'typeName':'goods'}
        ,url: uploadUrl
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#uploadImg').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code != 0){
                var demoText = $('#uploadImgText');
                demoText.html('<span style="color: #FF5722;">上传失败</span>');
                return layer.msg(res.msg);
            }
            //上传成功
            $('#commodityImg').attr('value', res.data.src);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#uploadImgText');
            demoText.html('<span style="color: #FF5722;">上传失败</span>');

        }
    });



});