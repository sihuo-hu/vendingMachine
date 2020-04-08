package com.royal.admin.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 */
@TableName("b_order")
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * AUTO 数据库ID自增
     * INPUT 用户输入ID
     * ID_WORKER 全局唯一ID，Long类型的主键
     * ID_WORKER_STR 字符串全局唯一ID
     * UUID 全局唯一ID，UUID类型的主键
     * NONE 该类型为未设置主键类型
     */
    @TableId(value = "b_id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "machines_id")
    private String machinesId;
    /**
     *
     */
    @TableField("user_id")
    private String userId;
    /**
     * 在线状态，1-创建 2-跳舞中 3-结算中 4-体验完成 5-已完成 6-已取消 7-出货失败 8-分数不到标
     */
    @TableField("b_status")
    private String status;
    /**
     * 经度
     */
    @TableField("floor_id")
    private Integer floorId;
    /**
     * 纬度
     */
    @TableField("good_id")
    private String goodId;
    /**
     * 地址
     */
    @TableField("grade")
    private Integer grade;
    /**
     * 地址
     */
    @TableField("mountings_id")
    private Integer mountingsId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("order_no")
    private String orderNo;
    @TableField("deliver_note")
    private String deliverNote;
    /**
     * 1未查询 2开始查询，3查询完毕
     */
    @TableField("select_status")
    private Integer selectStatus;
}
