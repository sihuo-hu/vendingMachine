package com.royal.admin.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
* 描述：图片过滤模型
* @author Royal
* @date 2019年02月17日 18:35:52
*/
@TableName("p_img_md5")
@Data
public class ImgMd5 implements Serializable {

    /**
    *
    */
    @TableId(value = "img_id", type = IdType.INPUT)
    private String imgId;
    /**
    *
    */
    @TableField("img_url")
    private String imgUrl;
    /**
    *
    */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
    *
    */
    @TableField("type_name")
    private String typeName;


     public String getImgId() {
        return this.imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }


     public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


     public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


     public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ImgMd5{" +
                "imgId='" + imgId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", createTime=" + createTime +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}