package com.royal.admin.modular.system.model.baiduyun;

import lombok.Data;
import org.json.JSONObject;

@Data
public class BdyResult {

    private Object result;

    private Long log_id;

    private String error_msg;

    private Integer cached;

    private Integer error_code;

    private Integer timestamp;

    public static BdyResult toDetectResult(JSONObject res,Object obj) {
        BdyResult bdyResult = new BdyResult();
        bdyResult.setCached(res.getInt("cached"));
        bdyResult.setError_code(res.getInt("error_code"));
        bdyResult.setError_msg(res.getString("error_msg"));
        bdyResult.setLog_id(res.getLong("log_id"));
        bdyResult.setResult(obj);
        bdyResult.setTimestamp(res.getInt("timestamp"));
        return bdyResult;
    }
}
