package com.royal.admin.modular.system.model;

import lombok.Data;

import java.util.List;

@Data
public class ListResult {
    private Integer code = 0;

    private String msg = "请求成功";

    private List data;

    public static ListResult success(List list){
        ListResult listResult = new ListResult();
        listResult.setData(list);
        return listResult;
    }
}
