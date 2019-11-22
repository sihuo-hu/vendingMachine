package com.royal.admin.modular.system.model.zdy;

import lombok.Data;

@Data
public class ZdyMachines {
    private String machine_no;
    private String machine_name;
    private String online_status;
    private String lat;
    private String lng;
    private String address;
    private String created_at;
}
