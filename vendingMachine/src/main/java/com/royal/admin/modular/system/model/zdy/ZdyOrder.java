package com.royal.admin.modular.system.model.zdy;

import lombok.Data;

@Data
public class ZdyOrder {
    private Integer order_status;

    private String order_status_text;

    private String deliver_note;
}
