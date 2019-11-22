package com.royal.admin.modular.system.model;

import lombok.Data;
import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.Map;

@Data
public class ImgUploadResult {

    private int code;

    private String msg;

    private Map<String, Object> data;

    public ImgUploadResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ImgUploadResult(int code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ImgUploadResult(String... map) {
        this.code = 0;
        this.data = new HashMap<String, Object>();
        int j = map.length / 2;
        for (int i = 0; i < j; i++) {
            this.data.put(map[i * 2], map[i * 2 + 1]);
        }
    }

    public ImgUploadResult() {
    }
}
