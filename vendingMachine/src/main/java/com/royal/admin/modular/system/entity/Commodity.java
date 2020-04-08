package com.royal.admin.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@TableName("b_commodity")
@Data
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "commodity_id", type = IdType.ID_WORKER_STR)
    private String commodityId;
    /**
     * 名称
     */
    @TableField("commodity_name")
    private String commodityName;
    /**
     * 商品图片
     */
    @TableField("commodity_img")
    private String commodityImg;
    /**
     * 商品详情
     */
    @TableField("commodity_desc")
    private String commodityDesc;
    /**
     * 商品状态
     */
    @TableField("commodity_status")
    private String commodityStatus;
    /**
     * 难度
     */
    @TableField("difficulty")
    private String difficulty;
    /**
     * 商品所需分数
     */
    @TableField("grade")
    private Integer grade;
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

}
