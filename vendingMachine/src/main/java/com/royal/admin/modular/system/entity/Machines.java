package com.royal.admin.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 */
@TableName("b_machines")
@Data
public class Machines implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * AUTO 数据库ID自增
     * INPUT 用户输入ID
     * ID_WORKER 全局唯一ID，Long类型的主键
     * ID_WORKER_STR 字符串全局唯一ID
     * UUID 全局唯一ID，UUID类型的主键
     * NONE 该类型为未设置主键类型
     */
    @TableId(value = "machines_id", type = IdType.INPUT)
    private String machinesId;
    /**
     * 设备名称
     */
    @TableField("machines_name")
    private String machinesName;
    /**
     * 在线状态，1-ENABLE：在线 0-DISABLE：离线
     */
    @TableField("online_status")
    private String onlineStatus;
    /**
     * 经度
     */
    @TableField("lat")
    private String lat;
    /**
     * 纬度
     */
    @TableField("lgn")
    private String lgn;
    /**
     * 地址
     */
    @TableField("address")
    private String address;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private String createdAt;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 操作员姓名
     */
    @TableField(exist = false)
    private String userName;

}
