package com.royal.admin.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Floor;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.mapper.SpfzMapper;
import com.royal.admin.modular.system.model.GoodsList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class SpfzService extends ServiceImpl<SpfzMapper, Spfz> {

    public List<GoodsList> fzList(String machinesId) {
        List<GoodsList> list = new ArrayList<>();
        GoodsList commodity1 = this.baseMapper.fzList(machinesId,"一星");
        GoodsList commodity2 = this.baseMapper.fzList(machinesId,"二星");
        GoodsList commodity3 = this.baseMapper.fzList(machinesId,"三星");
        GoodsList commodity4 = this.baseMapper.fzList(machinesId,"四星");
        GoodsList commodity5 = this.baseMapper.fzList(machinesId,"五星");
        list.add(commodity1);
        list.add(commodity2);
        list.add(commodity3);
        list.add(commodity4);
        list.add(commodity5);
        return list;
    }

    public Floor getFloorListBySpfzId(Integer spfzId,Integer grade) {
        return this.baseMapper.getFloorListBySpfzId(spfzId,grade);
    }
}
