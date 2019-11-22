package com.royal.admin.modular.system.service;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.HttpUtils;
import com.royal.admin.core.util.JSONUtils;
import com.royal.admin.core.util.ZdySignUtil;
import com.royal.admin.modular.system.entity.Machines;
import com.royal.admin.modular.system.entity.Mountings;
import com.royal.admin.modular.system.factory.MachinesFactory;
import com.royal.admin.modular.system.mapper.MachinesMapper;
import com.royal.admin.modular.system.mapper.MountingsMapper;
import com.royal.admin.modular.system.model.zdy.ZdyMachines;
import com.royal.admin.modular.system.model.zdy.ZdyReturn;
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
public class MountingsService extends ServiceImpl<MountingsMapper, Mountings> {

    private static final Logger log = LoggerFactory.getLogger(MountingsService.class);

    public Page<Map<String, Object>> selectMountings(String name, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectMountings(page, name, beginTime, endTime);
    }

    /**
     * 查询状态正常的售货机
     * @param id
     * @return
     */
    public List<Map<String, Object>> getMachinesList(Integer id) {
        return this.baseMapper.getMachinesList(id);
    }

    public void binding(Integer mountingId, String machinesId) {
        Mountings mountings = this.baseMapper.selectById(mountingId);
        mountings.setMachinesId(machinesId);
        this.baseMapper.updateById(mountings);
    }
}
