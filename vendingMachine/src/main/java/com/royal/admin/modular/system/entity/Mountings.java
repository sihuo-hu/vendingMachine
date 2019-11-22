package com.royal.admin.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 跳舞机表
 * </p>
 */
@TableName("b_mountings")
@Data
public class Mountings implements Serializable {

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
    /**
     * 设备编码
     */
    @TableField("b_code")
    private String code;
    /**
     * 设备名称
     */
    @TableField("b_name")
    private String name;

    /**
     * 地址
     */
    @TableField("b_address")
    private String address;
    /**
     * 售货机编号
     */
    @TableField("machines_id")
    private String machinesId;
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

    /**
     * 设备名称
     */
    @TableField(exist = false)
    private String machinesName;

}
