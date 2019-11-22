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
@TableName("b_spfz")
@Data
public class Spfz implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "b_id", type = IdType.AUTO)
    private Integer id;
    /**
     * 难度
     */
    @TableField("difficulty")
    private String difficulty;
    /**
     *
     */
    @TableField("floor_codes")
    private String floorCodes;
    /**
     *
     */
    @TableField("goods_names")
    private String goodsNames;

    /**
     *
     */
    @TableField("machines_id")
    private String machinesId;

    public Spfz(Integer id, String difficulty, String floorCodes, String goodsNames, String machinesId) {
        this.id = id;
        this.difficulty = difficulty;
        this.floorCodes = floorCodes;
        this.goodsNames = goodsNames;
        this.machinesId = machinesId;
    }

    public Spfz(String difficulty, String machinesId) {
        this.difficulty = difficulty;
        this.machinesId = machinesId;
    }

    public Spfz() {
    }
}
