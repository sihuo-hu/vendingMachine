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
@TableName("b_replenishment")
@Data
public class Replenishment implements Serializable {

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
    @TableField("b_status")
    private String status;
    /**
     * 设备名称
     */
    @TableField("b_number")
    private Integer number;
    @TableField("goods_id")
    private String goodsId;
    @TableField("machines_id")
    private String machinesId;
    @TableField("floor_id")
    private Integer floorId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
