package com.royal.admin.modular.system.service;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.*;
import com.royal.admin.modular.system.entity.Machines;
import com.royal.admin.modular.system.factory.MachinesFactory;
import com.royal.admin.modular.system.mapper.MachinesMapper;
import com.royal.admin.modular.system.model.zdy.ZdyReturn;
import com.royal.admin.modular.system.model.zdy.ZdyMachines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class MachinesService extends ServiceImpl<MachinesMapper, Machines> {

    @Autowired
    private FloorService floorService;

    private static final Logger log = LoggerFactory.getLogger(MachinesService.class);

    /**
     * 获取设备列表
     *
     * @param machinesId
     * @param machinesName
     * @param onlineStatus
     * @return
     */
    public Page<Map<String, Object>> selectMachines(String machinesId, String machinesName, String onlineStatus) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectMachines(page, machinesId, machinesName, onlineStatus);

    }

    /**
     * 修改设备状态
     *
     * @param machinesId
     * @param onlineStatus
     */
    public int setStatus(String machinesId, String onlineStatus) {
        return this.baseMapper.setStatus(machinesId, onlineStatus);
    }

    /**
     * 从中达云同步设备情况
     */
    public void syncMachines() {
        String sign = ZdySignUtil.getSign(new HashMap<>());
        HashMap<String, String> param = new HashMap<>();
        param.put("sign", sign);
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("appid", com.royal.admin.core.common.constant.state.ZdyMachines.APPID.getMessage());
        String s = HttpUtils.doGet(com.royal.admin.core.common.constant.state.ZdyMachines.MACHINES_LIST_URL.getMessage(), param, headerMap);
        ZdyReturn z = JSONUtils.toBean(s, ZdyReturn.class, "data", ZdyMachines.class);
        System.out.println(z.toString());
        if (z.getCode() == 1) {
            List<ZdyMachines> list = (List<ZdyMachines>) z.getData();
            if (list != null && list.size() > 0) {
                for (ZdyMachines zdyMachines : list) {
                    Machines machines = new Machines();
                    MachinesFactory.copyMachines(zdyMachines, machines);
                    Machines old = this.baseMapper.selectById(machines.getMachinesId());
                    if (old == null || ToolUtil.isEmpty(old.getMachinesId())) {
                        this.baseMapper.insert(machines);
                    } else {
                        this.baseMapper.updateById(machines);
                    }
                    floorService.syncFrool(zdyMachines.getMachine_no());
                }
            }
        } else {
            log.info("从远端同步设备列表失败：" + z.toString());
            throw new ServiceException(BizExceptionEnum.REFRESH_ERROR);
        }
    }





}
