package com.royal.admin.modular.system.model.echarts;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图
 * color: ['#3398DB'],
 * tooltip : {
 * trigger: 'axis',
 * axisPointer : {            // 坐标轴指示器，坐标轴触发有效
 * type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
 * }
 * },
 * grid: {
 * left: '3%',
 * right: '4%',
 * bottom: '3%',
 * containLabel: true
 * },
 * xAxis : [
 * {
 * type : 'category',
 * data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
 * axisTick: {
 * alignWithLabel: true
 * }
 * }
 * ],
 * yAxis : [
 * {
 * type : 'value'
 * }
 * ],
 * series : [
 * {
 * name:'直接访问',
 * type:'bar',
 * barWidth: '60%',
 * data:[10, 52, 200, 334, 390, 330, 220]
 * }
 * ]
 */
@Data
public class Histogram {
    //['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    private List<String> xAxisList = new ArrayList<>();
    //[10, 52, 200, 334, 390, 330, 220]
    private List<Integer> yAxisList = new ArrayList<>();

    public void setxyAxis(List<XYDate> xyDateList) {
        if (xyDateList != null && xyDateList.size() > 0) {
            for (int i = 0; i < xyDateList.size(); i++) {
                XYDate xyDate = xyDateList.get(i);
                xAxisList.add(xyDate.getDate());
                yAxisList.add(xyDate.getNumber());
            }
        }
    }

//    @Override
//    public String toString() {
//        return "{color: ['#3398DB'], tooltip : {trigger: 'axis', axisPointer : {type : 'shadow'}}," +
//                "grid: {left: '3%', right: '4%', bottom: '3%',containLabel: true }," +
//                "xAxis : [{type : 'category',data : " + xAxis + ",axisTick: { alignWithLabel: true}}]," +
//                "yAxis : [{ type : 'value'}],series : [{name:'直接访问',type:'bar',barWidth: '60%',data:" + yAxis + "}]}";
//    }
}
