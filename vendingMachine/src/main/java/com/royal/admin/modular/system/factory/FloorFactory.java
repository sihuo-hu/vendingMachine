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
import com.royal.admin.modular.system.entity.Floor;
import com.royal.admin.modular.system.model.zdy.ZdyFloor;

import java.util.ArrayList;
import java.util.List;

/**
 * 货道创建工厂
 *
 * @author fengshuonan
 * @date 2017-05-05 22:43
 */
public class FloorFactory {

    /**
     * 更新user
     */
    public static List<Floor> foundFloor(List<ZdyFloor> zdyFloorList, String machineNo) {
        if (zdyFloorList == null) {
            return null;
        } else {
            List<Floor> floorList = new ArrayList<>();
            for (ZdyFloor zdyFloor : zdyFloorList) {
                Floor floor = new Floor();
                if (ToolUtil.isNotEmpty(zdyFloor.getCode())) {
                    floor.setFloorCode(zdyFloor.getCode());
                }
                if (ToolUtil.isNotEmpty(zdyFloor.getDisplay_code())) {
                    floor.setDisplayCode(zdyFloor.getDisplay_code());
                }
                if (ToolUtil.isNotEmpty(zdyFloor.getStatus())) {
                    floor.setFloorStatus(zdyFloor.getStatus());
                }
                floor.setMachineId(machineNo);
                floorList.add(floor);
            }
            return floorList;
        }
    }

}
