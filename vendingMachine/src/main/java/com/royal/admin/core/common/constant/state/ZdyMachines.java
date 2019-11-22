package com.royal.admin.core.common.constant.state;

public enum ZdyMachines {

    APPID("10051200"),
    MQTT_KEY("0c0b1f673e266c44754509255d63f26e"),
    SECRET("703d56d77aaaa0739aa4f49871112f09"),
    MACHINES_LIST_URL("http://o.zhongdacloud.com/api/openapi/v1/rent/machines"),
    SELECT_ORDER_URL("http://o.zhongdacloud.com/api/openapi/v1/rent/getOrderStatus"),
    PLACE_AN_ORDER_URL("https://o.zhongdacloud.com/api/openapi/v1/rent/specifiedCodeDelivery"),
    FLOOR_LIST_URL("http://o.zhongdacloud.com/api/openapi/v1/rent/getAllFloors");

    String message;

    ZdyMachines(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
