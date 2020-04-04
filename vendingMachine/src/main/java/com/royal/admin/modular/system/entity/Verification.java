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
@TableName("b_verification")
@Data
public class Verification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @TableField("b_code")
    private String code;
    /**
     * 商品图片
     */
    @TableField("phone")
    private String phone;
    /**
     * 1-验证码
     */
    @TableField("b_type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}
