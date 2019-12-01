layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax','echarts'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var $ax = layui.ax;
    var admin = layui.admin;
    var zhu = layui.echarts.init(document.getElementById('zhu'));
    var bin = layui.echarts.init(document.getElementById('bin'));
    var xian = layui.echarts.init(document.getElementById('xian'));

    var histogram = new $ax(Feng.ctxPath + "/instrument/histogram", function (data) {
        console.log(data.xAxisList);
        console.log(data.yAxisList);
        var a = {title : {text: '近10日奖品发放数量', subtext: '', x:'center'},color: ['#3398DB'], tooltip : {trigger: 'axis', axisPointer : {type : 'shadow'}},grid: {left: '3%', right: '4%', bottom: '3%', containLabel: true},
          xAxis : [{type : 'category', data : [], axisTick: {alignWithLabel: true}}],
          yAxis : [{type : 'value'}], series : [{name:'发放数量', type:'bar', barWidth: '60%', data:[]}]};
          a.xAxis[0].data=data.xAxisList;
          a.series[0].data=data.yAxisList;
        console.log(a);
        zhu.setOption(a);
    });
    histogram.start();

    var pie = new $ax(Feng.ctxPath + "/instrument/pie", function (data) {
        var option = {
            title : {text: '商品发放统计', subtext: '', x:'center'},
            tooltip : {trigger: 'item', formatter: "{a} <br/>{b} : {c} ({d}%)"},
            legend: {type: 'scroll', orient: 'vertical', right: 10, top: 20, bottom: 20, data: [{name:'a',value:'122'},{name:'b',value:'122'},{name:'c',value:'122'}], selected:{}},
            series : [{name: '姓名', type: 'pie', radius : '55%', center: ['40%', '50%'], data: [{name:'a',value:'122'},{name:'b',value:'122'},{name:'c',value:'122'}],
                    itemStyle: {
                        emphasis: {shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)'}
                    }
                }
            ]
        };
        option.legend.data=data;
        option.series[0].data=data;
        bin.setOption(option);
    });
    pie.start();

    var line = new $ax(Feng.ctxPath + "/instrument/line", function (data) {
        console.log(data);
        var option = {
            title : {text: '挑战统计'},
            tooltip: {trigger: 'axis'},
            xAxis: {data:[]},
            yAxis: {splitLine: {show: false}},
            toolbox: {left: 'center', feature: {dataZoom: {yAxisIndex: 'none'}, restore: {}, saveAsImage: {}}},
            dataZoom: [{startValue: '2012-03-18'}, {type: 'inside'}],
            visualMap: {top: 10, right: 10,
                pieces: [{gt: 0, lte: 10, color: '#096'}, {gt: 10, lte: 100, color: '#ffde33'}, {gt: 100, lte: 250, color: '#ff9933'},
                    {gt: 250, lte: 500, color: '#cc0033'}, {gt: 500, lte: 1000, color: '#660099'}, {gt: 1000, color: '#7e0023'}],
                    outOfRange: {color: '#999'}},
            series: {name: '挑战次数', type: 'line',
                data: [],
                markLine: {silent: true, data: [{yAxis: 10}, {yAxis: 100}, {yAxis: 250}, {yAxis: 500}, {yAxis: 1000}]}}
        };
        option.dataZoom[0].startValue = data.startValue;
        option.xAxis.data=data.date;
        option.series.data=data.number;
        xian.setOption(option);
    });
    line.start();

    // form.val('mountingsForm', result.data);

});
