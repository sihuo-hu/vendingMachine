/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.royal.admin.modular.system.factory;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.royal.admin.core.common.constant.state.MenuStatus;
import com.royal.admin.modular.system.entity.Machines;
import com.royal.admin.modular.system.model.zdy.ZdyMachines;

/**
 * 用户创建工厂
 *
 * @author fengshuonan
 * @date 2017-05-05 22:43
 */
public class MachinesFactory {

    /**
     * 更新user
     */
    public static Machines copyMachines(ZdyMachines zdyMachines, Machines machines) {
        if (zdyMachines == null || machines == null) {
            return machines;
        } else {
            if (ToolUtil.isNotEmpty(zdyMachines.getAddress())) {
                machines.setAddress(zdyMachines.getAddress());
            }
            if (ToolUtil.isNotEmpty(zdyMachines.getCreated_at())) {
                machines.setCreatedAt(zdyMachines.getCreated_at());
            }
            if (ToolUtil.isNotEmpty(zdyMachines.getLat())) {
                machines.setLat(zdyMachines.getLat());
            }
            if (ToolUtil.isNotEmpty(zdyMachines.getMachine_no())) {
                machines.setMachinesId(zdyMachines.getMachine_no());
            }
            if (ToolUtil.isNotEmpty(zdyMachines.getLng())) {
                machines.setLgn(zdyMachines.getLng());
            }
            if (ToolUtil.isNotEmpty(zdyMachines.getMachine_name())) {
                machines.setMachinesName(zdyMachines.getMachine_name());
            }
            if (ToolUtil.isNotEmpty(zdyMachines.getOnline_status())) {
                if (zdyMachines.getOnline_status().equals("1")) {
                    machines.setOnlineStatus(MenuStatus.ENABLE.getCode());
                } else {
                    machines.setOnlineStatus(MenuStatus.DISABLE.getCode());
                }
            }
            return machines;
        }
    }

}
