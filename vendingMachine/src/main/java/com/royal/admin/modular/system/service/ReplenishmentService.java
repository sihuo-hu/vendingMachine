package com.royal.admin.modular.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Replenishment;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.mapper.CommodityMapper;
import com.royal.admin.modular.system.mapper.ReplenishmentMapper;
import com.royal.admin.modular.system.mapper.SpfzMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class ReplenishmentService extends ServiceImpl<ReplenishmentMapper, Replenishment> {


    public Page<Map<String, Object>> selectReplenishment(String status, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage();
       return this.baseMapper.selectReplenishment(page,status,beginTime,endTime);
    }
}
