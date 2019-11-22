package com.royal.admin.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("b_floor")
@Data
public class Floor implements Serializable {

    /**
     * AUTO 数据库ID自增
     * INPUT 用户输入ID
     * ID_WORKER 全局唯一ID，Long类型的主键
     * ID_WORKER_STR 字符串全局唯一ID
     * UUID 全局唯一ID，UUID类型的主键
     * NONE 该类型为未设置主键类型
     */
    @TableId(value = "floor_id", type = IdType.AUTO)
    private Integer floorId;
    /**
     * 电机编号
     */
    @TableField("floor_code")
    private String floorCode;
    /**
     * '0' => '货道不存在',
     * '1' => '正常',
     * '2' => '卡货',
     * '3' => '电机故障',
     * '47' => '串口通讯超时',
     */
    @TableField("floor_status")
    private Integer floorStatus;
    /**
     * 货道编号
     */
    @TableField("display_code")
    private String displayCode;
    /**
     * 库存
     */
    @TableField("stock")
    private Integer stock;
    /**
     * 设备ID
     */
    @TableField("machine_id")
    private String machineId;
    /**
     * 所需最低积分
     */
    @TableField("grade")
    private Integer grade;
    /**
     * 商品ID
     */
    @TableField("goods_id")
    private String goodsId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String commodityName;


}
